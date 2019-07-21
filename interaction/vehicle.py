# Author:  DINDIN Meryll
# Date:    20 July 2019
# Project: RoadBuddy

import json
import argparse
import smartcar

class CarStatus:

    def __init__(self, credentials='credentials/key_SMARTCAR.json'):

        prm = ['read_odometer', 'read_location', 'read_battery']
        with open(credentials) as raw: crd = json.load(raw)
        self.clt = smartcar.AuthClient(crd['id'], crd['key'], 'http://localhost:8080', prm)
        print('Generate Car Object')

    def configure(self):

        print('> Entry Form:')
        print('Email Adress: ', 'hackmobility.tesla@example.com')
        print('Password:     ', 'hackmobility2019')
        print(self.clt.get_auth_url())

    def set_access(self, code=None):

        def get_fresh_access():
    
            self.ass = load_access_from_database()
            if smartcar.is_expired(self.ass['expiration']):
                new_access = client.exchange_refresh_token(self.ass['refresh_token'])
                put_access_into_database(new_access)
                return new_access
            else: return self.ass

        if code is None:
            self.ass = get_fresh_access()
            self.clt.exchange_refresh_token(self.ass['access_token'])
        else: 
            print('Yolo')
            self.ass = self.clt.exchange_code(code)

    def get(self):

        lst = smartcar.get_vehicle_ids(self.ass['access_token'])
        car = smartcar.Vehicle(lst['vehicles'][0], self.ass['access_token'])
        res = car.location()['data']
        res.update(car.battery()['data'])

        return res

if __name__ == '__main__':

    prs = argparse.ArgumentParser()    
    prs.add_argument('--init_code', help='Configuration Code', type=str)
    prs = prs.parse_args()
    
    car = CarStatus()
    car.set_access(prs.init_code)
    prm = car.get()
    print(prm)