# Author:  DINDIN Meryll
# Date:    20 July 2019
# Project: RoadBuddy

import re
import json
import requests
import numpy as np
import wikipedia

class PlacesOfInterest:

    def __init__(self, credentials='credentials/key_HERE.json'):

        with open(credentials) as raw: crd = json.load(raw)
        self.ids = crd['id']
        self.key = crd['key']
        del crd

    @staticmethod
    def filter_parentheses(string): 

        return re.sub(r'\(.*\)', '', string)

    @staticmethod
    def associate_wikipedia(place):
    
        try: return wikipedia.summary(place).replace('\n',  ' ')
        except: return None

    def get(self, car_status, n_places=5):

        arg = (','.join([str(np.round(car_status['latitude'], 5)), str(np.round(car_status['longitude'], 5))]), self.ids, self.key)
        url = 'https://places.cit.api.here.com/places/v1/discover/here?at={}&app_id={}&app_code={}'.format(*arg)
        res = json.loads(requests.get(url).content)
        lst = [self.filter_parentheses(item['title']) for item in res['results']['items']]

        for key in ['park', 'religious', 'museum', 'bridge']:
            arg = (','.join([str(np.round(car_status['latitude'], 5)), str(np.round(car_status['longitude'], 5))]), key, self.ids, self.key)
            url = 'https://places.cit.api.here.com/places/v1/autosuggest?at={}&q={}&app_id={}&app_code={}'.format(*arg)
            res = json.loads(requests.get(url).content)
            lst += [self.filter_parentheses(item['title']) for item in res['results']]
            
        lst = np.unique(lst)
        np.random.shuffle(lst)

        places_around = []
        while (len(places_around) < n_places) and len(lst) > 0:
            res = self.associate_wikipedia(lst[0])
            if res is None: pass
            else: places_around.append({'title': lst[0], 'description': res})
            lst = lst[1:]

        return places_around

if __name__ == '__main__':

    car = {'latitude': 37.78742599487305, 
           'longitude': -122.39665222167969, 
           'range': 195.28, 
           'percentRemaining': 0.64}

    plc = PlacesOfInterest().get(car)
    print(plc)