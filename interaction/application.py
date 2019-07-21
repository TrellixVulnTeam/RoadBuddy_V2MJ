# Author:  DINDIN Meryll
# Date:    20 July 2019
# Project: RoadBuddy

from flask import Flask
from flask import request
from flask import Response

try: from interaction.jokes import *
except: from jokes import *
try: from interaction.places import *
except: from places import *
try: from interaction.vehicle import *
except: from vehicle import *

if __name__ == '__main__':

    prs = argparse.ArgumentParser()    
    prs.add_argument('--init_code', help='Configuration Code', type=str)
    prs = prs.parse_args()
    print(prs.init_code)

    with open('credentials/key_BEANSTALK.json') as raw: crd = json.load(raw)
    application = Flask(__name__)
    application.secret_key = crd['key']

    # Defines the request objects
    car = CarStatus()
    car.set_access(prs.init_code)
    pos = car.get()
    plc = PlacesOfInterest()
    jks = DadJoke()

    @application.route('/request', methods=['POST'])
    def request():

        bdy, arg = '', dict(request.args)
        try: pos = car.get()
        except: pass

        if arg['objective'] == 'enumerate':
            msg = plc.get(pos)
            cor = ' ,'.join([e['title'] for e in msg])
            bdy = 'Do you wanna know more about your surroundings? ' 
            bdy += 'Here are five places I picked for you: {}'.format(cor)
        if arg['objective'] == 'joke':
            bdy = 'Okay, here is a good one for you! '
            bdy += jks.get()

        if pos['battery'] < 70:
            bdy += ' By the way, you should consider take a small break, '
            bdy += 'and charge your car which is {} percent loaded.'.format(pos['battery'])

        arg = {'status': 200, 'mimetype': 'application/json'}
        return Response(response=json.dumps({'success': True, 'body': msg}), **arg)

    @application.route('/refresh', methods=['POST'])
    def refresh():

        car.set_access(code=None)

        arg = {'status': 200, 'mimetype': 'application/json'}
        return Response(response=json.dumps({'success': True}), **arg)

    application.run(host='127.0.0.1', port=8000, debug=True)
    # application.run()