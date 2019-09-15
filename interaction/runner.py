import requests, json
import argparse

if __name__ == '__main__':

    prs = argparse.ArgumentParser()    
    prs.add_argument('--objective', help='Configuration Code', type=str)
    prs.add_argument('--title', help='Title', type=str)
    prs = prs.parse_args()

    prm = {'objective': prs.objective, 'title': prs.title}
    req = requests.get('http://general-api.quxttbjkp2.us-east-2.elasticbeanstalk.com/exchange?objective=joke')
    print(json.loads(req.content)['body'])