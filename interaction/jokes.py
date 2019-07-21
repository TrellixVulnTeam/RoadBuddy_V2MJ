# Author:  DINDIN Meryll
# Date:    20 July 2019
# Project: RoadBuddy

import requests

class DadJoke:

    def __init__(self):

        pass

    def get(self):

        req = requests.get('https://icanhazdadjoke.com', headers={"Accept":"application/json"})

        return req.json()['joke']

if __name__ == '__main__':

    jke = DadJoke()
    print(jke)