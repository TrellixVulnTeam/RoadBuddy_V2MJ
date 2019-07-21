import json
import boto3
import botocore.vendored.requests as requests
import os
import uuid
from contextlib import closing


def get_url(obj):

    print(obj)
    voice = "Brian"

    rest = obj["body"]

    # Because single invocation of the polly synthesize_speech api can
    # transform text with about 1,500 characters, we are dividing the
    # post into blocks of approximately 1,000 characters.
    textBlocks = []
    while (len(rest) > 1100):
        begin = 0
        end = rest.find(".", 1000)

        if (end == -1):
            end = rest.find(" ", 1000)

        textBlock = rest[begin:end]
        rest = rest[end:]
        textBlocks.append(textBlock)
    textBlocks.append(rest)

    # For each block, invoke Polly API, which will transform text into audio
    polly = boto3.client('polly')
    for textBlock in textBlocks:
        response = polly.synthesize_speech(
            OutputFormat='mp3',
            Text=textBlock,
            VoiceId=voice
        )

        # Save the audio stream returned by Amazon Polly on Lambda's temp
        # directory. If there are multiple text blocks, the audio stream
        # will be combined into a single file.

        gen = str(uuid.uuid1())
        if "AudioStream" in response:
            with closing(response["AudioStream"]) as stream:
                output = os.path.join("/tmp/", gen)
                with open(output, "ab") as file:
                    file.write(stream.read())

    s3 = boto3.client('s3')
    s3.upload_file('/tmp/' + gen,
                   os.environ['BUCKET_NAME'],
                   gen + ".mp3")
    s3.put_object_acl(ACL='public-read',
                      Bucket=os.environ['BUCKET_NAME'],
                      Key=gen + ".mp3")

    location = s3.get_bucket_location(Bucket=os.environ['BUCKET_NAME'])
    region = location['LocationConstraint']

    if region is None:
        url_begining = "https://s3.amazonaws.com/"
    else:
        url_begining = "https://s3-" + str(region) + ".amazonaws.com/"
    url = url_begining + str(os.environ['BUCKET_NAME']) \
        + "/" + str(gen) + ".mp3"

    return {
        'statusCode': 200,
        'body': url
    }

def lambda_handler(event, context):
    objective = event["objective"]
    title = event["title"]
    prm = {'objective': objective, 'title': title}
    res = requests.post('http://roadBuddy.gvp8mkbfpc.us-east-2.elasticbeanstalk.com/exchange', params=prm)
    if res is None:
        return {
        'statusCode': 200,
        'body': json.dumps('No Data ?!'),
        'data': pretty
    }
    pretty = json.loads(res.content)
#    sns = boto3.client('sns')
#    sns.publish(
#        TopicArn="arn:aws:sns:us-east-2:482985412682:roadbuddy-text-to-speech",
#        Message=str(pretty),
#        Subject='Text to speech',
#        MessageStructure='string',
#        MessageAttributes={
#            'message': {
#                'DataType': 'String',
#                'StringValue': "toto"
#            }
#        }
#    )
    resp = get_url(pretty)
    # TODO implement
    return resp
