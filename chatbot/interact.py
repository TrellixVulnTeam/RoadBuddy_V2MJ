# Author:  DINDIN Meryll
# Date:    15 September 2019
# Project: RoadBuddy

try: from chatbot.imports import *
except: from imports import *

class Contextualizer:
    
    def __init__(self):
        
        try:
            
            self._load_models()
            
        except:
        
            drc = ['models', 'datasets']
            for d in drc: 
                if not os.path.exists(d): os.mkdir(d)
            
            self._download_models()
            self._load_models()

    def _download_models(self):
        
        s3 = boto3.client('s3')
        # Download dataset
        fle = ('datasets.huggingface.co', 'personachat/personachat_self_original.json')
        s3.download_file(*fle, 'datasets/persona-chat.json')
        # Download model
        fle = ('models.huggingface.co', 'transfer-learning-chatbot/finetuned_chatbot_gpt.tar.gz')
        s3.download_file(fle, 'models/gpt.tar.gpz')
        with tarfile.open('models/gpt.tar.gpz', 'r:gz') as archive: archive.extractall('models')
        # Remove tar file
        os.remove('models/gpt.tar.gpz')
        
    def _load_models(self):
        
        self.token = OpenAIGPTTokenizer.from_pretrained('models')
        self.model = OpenAIGPTLMHeadModel.from_pretrained('models')

    def tokenize_personnalities(self):
        
        with open('datasets/persona-chat.json', encoding='utf-8') as f:
            dtb = json.loads(f.read())
            
        def tokenize(obj):
        
            if isinstance(obj, str):
                return self.token.convert_tokens_to_ids(self.token.tokenize(obj))
            if isinstance(obj, dict):
                return dict((n, tokenize(o)) for n, o in obj.items())
            
            return list(tokenize(o) for o in obj)
        
        dtb = tokenize(dtb)
        torch.save(dtb, 'datasets/persona-cached')

class Trigger:

    def __init__(self):

        self.url_jokes = 'https://icanhazdadjoke.com'
        self.url_facts = 'https://some-random-api.ml/facts'
            
    def get(self, message):
        
        if 'joke' in message:
            return requests.get(self.url_jokes, headers={"Accept":"application/json"}).json()['joke']
        
        elif ('fun' in message) and ('fact' in message):
            animal = np.random.choice(['panda', 'cat', 'dog', 'fox', 'bird', 'koala'])
            return json.loads(requests.get('/'.join([self.url_facts, animal])).content)['fact']
        
        else: return ''
        
class Runner:
    
    SPECIAL_TOKENS = ["<bos>", "<eos>", "<speaker1>", "<speaker2>", "<pad>"]
    
    def __init__(self, directory='models'):
        
        self.hists = []
        self.trigs = Trigger()
        self.token = OpenAIGPTTokenizer.from_pretrained(directory)
        self.model = OpenAIGPTLMHeadModel.from_pretrained(directory)
        
    def set_background(self, characteristics):
        
        self.perso = [self.token.convert_tokens_to_ids(self.token.tokenize(e)) for e in characteristics]
        
    def read_background(self):
        
        for e in self.token.decode(chain(*self.perso)): print('-', e)
            
    def input_from_segments(self, history, reply):
    
        bos, eos, speaker1, speaker2 = self.token.convert_tokens_to_ids(self.SPECIAL_TOKENS[:-1])

        instance = {}
        sequence = [[bos] + list(chain(*self.perso))] + history + [reply]
        sequence = [sequence[0]] + [[speaker2 if (len(sequence)-i) % 2 else speaker1] + s for i, s in enumerate(sequence[1:])]
        
        instance["input_ids"] = list(chain(*sequence))
        instance["token_type_ids"] = [speaker2 if i % 2 else speaker1 for i, s in enumerate(sequence) for _ in s]
        instance["mc_token_ids"] = len(instance["input_ids"]) - 1
        instance["lm_labels"] = [-1] * len(instance["input_ids"])

        return instance, sequence
    
    @staticmethod
    def top_filtering(logits, top_k=0, top_p=0.9, threshold=-float('Inf'), filter_value=-float('Inf')):

        assert logits.dim() == 1
        top_k = min(top_k, logits.size(-1))

        if top_k > 0:
            # Remove all tokens with a probability less than the last token in the top-k tokens
            indices_to_remove = logits < torch.topk(logits, top_k)[0][..., -1, None]
            logits[indices_to_remove] = filter_value

        if top_p > 0.0:
            # Compute cumulative probabilities of sorted tokens
            sorted_logits, sorted_indices = torch.sort(logits, descending=True)
            cumulative_probabilities = torch.cumsum(F.softmax(sorted_logits, dim=-1), dim=-1)

            # Remove tokens with cumulative probability above the threshold
            sorted_indices_to_remove = cumulative_probabilities > top_p
            # Shift the indices to the right to keep also the first token above the threshold
            sorted_indices_to_remove[..., 1:] = sorted_indices_to_remove[..., :-1].clone()
            sorted_indices_to_remove[..., 0] = 0

            # Back to unsorted indices and set them to -infinity
            indices_to_remove = sorted_indices[sorted_indices_to_remove]
            logits[indices_to_remove] = filter_value

        indices_to_remove = logits < threshold
        logits[indices_to_remove] = filter_value

        return logits
    
    def sample_sequence(self, history, min_length=1, max_length=30, temperature=0.7, current_output=None):
    
        special_tokens_ids = self.token.convert_tokens_to_ids(self.SPECIAL_TOKENS)

        if current_output is None: current_output = []

        for i in range(max_length):

            instance, sequence = self.input_from_segments(history, current_output)
            input_ids = torch.tensor(instance["input_ids"], device='cpu').unsqueeze(0)
            token_type_ids = torch.tensor(instance["token_type_ids"], device='cpu').unsqueeze(0)
            logits = self.model(input_ids, token_type_ids=token_type_ids)
            logits = logits[0, -1, :] / temperature
            logits = self.top_filtering(logits)
            probs = F.softmax(logits, dim=-1)

            prev = torch.multinomial(probs, 1)
            if i < 1 and prev.item() in special_tokens_ids:
                while prev.item() in special_tokens_ids:
                    prev = torch.multinomial(probs, num_samples=1)
            if prev.item() in special_tokens_ids: break

            current_output.append(prev.item())

        return current_output

    def answer(self, message, time=4):
        
        self.hists.append(self.token.encode(message))
        with torch.no_grad(): out_ids = self.sample_sequence(self.hists)
    
        response = self.token.decode(out_ids, skip_special_tokens=True)
        response = ' '.join([response, self.trigs.get(message)])
        
        self.hists.append(self.token.encode(response))
        self.hists = self.hists[-time:]
        
        return response
