# Author:  DINDIN Meryll
# Date:    20 July 2019
# Project: RoadBuddy

import json
import oauth2
import threading
import datetime
import cherrypy
import warnings
import webbrowser
import numpy as np

from math import factorial
from fitbit.api import Fitbit

def golay_smoothing(y, window_size, order=2, deriv=0, rate=1):

    window_size = np.abs(np.int(window_size))
    order = np.abs(np.int(order))
    order_range = range(order+1)
    half_window = (window_size -1) // 2
    # Precompute coefficients
    b = np.mat([[k**i for i in order_range] for k in range(-half_window, half_window+1)])
    m = np.linalg.pinv(b).A[deriv] * rate**deriv * factorial(deriv)
    # Pad the signal at the extremes with values taken from the signal itself
    firstvals = y[0] - np.abs( y[1:half_window+1][::-1] - y[0])
    lastvals = y[-1] + np.abs(y[-half_window-1:-1][::-1] - y[-1])
    y = np.concatenate((firstvals, y, lastvals))

    return np.convolve(m[::-1], y, mode='valid')

# FitBit Classes Definition

class OAuth2Server:
    
    def __init__(self, credentials='credentials/key_FITBIT.json', redirect_uri='http://127.0.0.1:8080/'):
        
        """ Initialize the FitbitOauth2Client """
        self.success_html = """
            <h1>You are now authorized to access the Fitbit API!</h1>
            <br/><h3>You can close this window</h3>"""
        self.failure_html = """
            <h1>ERROR: %s</h1><br/><h3>You can close this window</h3>%s"""

        with open(credentials) as raw: crd = json.load(raw)
        arg = {'redirect_uri': redirect_uri, 'timeout': 100}
        self.fitbit = Fitbit(crd['id'], crd['key'], **arg)

    def browser_authorize(self):
        """
        Open a browser to the authorization url and spool up a CherryPy
        server to accept the response
        """
        url, _ = self.fitbit.client.authorize_token_url()
        # Open the web browser in a new thread for command-line browser support
        threading.Timer(1, webbrowser.open, args=(url,)).start()
        cherrypy.quickstart(self)

    @cherrypy.expose
    def index(self, state, code=None, error=None):
        """
        Receive a Fitbit response containing a verification code. Use the code
        to fetch the access_token.
        """
        error = None
        if code:
            try:
                self.fitbit.client.fetch_access_token(code)
            except MissingTokenError:
                error = self._fmt_failure(
                    'Missing access token parameter.</br>Please check that '
                    'you are using the correct client_secret')
            except MismatchingStateError:
                error = self._fmt_failure('CSRF Warning! Mismatching state')
        else:
            error = self._fmt_failure('Unknown error while authenticating')
        # Use a thread to shutdown cherrypy so we can return HTML first
        self._shutdown_cherrypy()
        return error if error else self.success_html

    def _fmt_failure(self, message):
        
        tb = traceback.format_tb(sys.exc_info()[2])
        tb_html = '<pre>%s</pre>' % ('\n'.join(tb)) if tb else ''
        return self.failure_html % (message, tb_html)

    def _shutdown_cherrypy(self):
        """ Shutdown cherrypy in one second, if it's running """
        if cherrypy.engine.state == cherrypy.engine.states.STARTED:
            threading.Timer(1, cherrypy.engine.exit).start()

class FitbitGetter:
    
    def __init__(self, credentials='credentials/key_FITBIT.json'):
        
        warnings.simplefilter('ignore')
        server = OAuth2Server(credentials=credentials)
        server.browser_authorize()
        
        cfg = {'access_token': str(server.fitbit.client.session.token['access_token']),
               'refresh_token': str(server.fitbit.client.session.token['refresh_token'])}
        
        with open(credentials) as raw: crd = json.load(raw)
        self.client = Fitbit(crd['id'], crd['key'], oauth2=True, **cfg)
        
    @staticmethod
    def format_time(str_time, day):
    
        arg = dict(zip(['hour', 'minute', 'second'], np.asarray(str_time.split(':')).astype('int')))
        return datetime.datetime(year=day.year, month=day.month, day=day.day, **arg)
    
    def heartrate(self, date, days=2, detail_level='1sec'):
        
        t,v = [], []

        for shift in np.arange(days+1)[::-1]:
            dte = date - datetime.timedelta(days=int(shift))
            arg = {'base_date': dte.strftime("%Y-%m-%d"), 'detail_level': detail_level}
            req = self.client.intraday_time_series('activities/heart', **arg)
            req = req['activities-heart-intraday']['dataset']
            t += [self.format_time(r['time'], dte) for r in req]
            v += [r['value'] for r in req]
        
        return np.asarray(t), np.asarray(v)

if __name__ == '__main__':

    day = datetime.datetime.now()
    fbt = FitbitGetter().heartrate(day)
    print(fbt)