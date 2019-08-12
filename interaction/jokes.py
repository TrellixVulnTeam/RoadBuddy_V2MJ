# Author:  DINDIN Meryll
# Date:    20 July 2019
# Project: RoadBuddy

import json
import random
import numpy as np
import requests

class DadJoke:

    def __init__(self):

        pass

    def get(self):

        req = requests.get('https://icanhazdadjoke.com', headers={"Accept":"application/json"})

        return req.json()['joke']

class FunFact:

    def __init__(self):

        self.url = 'https://some-random-api.ml/facts'
        self.ani = ['panda', 'cat', 'dog', 'fox', 'bird', 'koala']

    def get(self):

        url = '/'.join([self.url, np.random.choice(self.ani)])
        return json.loads(requests.get(url).content)['fact']

if __name__ == '__main__':

    jke = DadJoke().get()
    print(jke)

    fct = FunFact().get()
    print(fct)