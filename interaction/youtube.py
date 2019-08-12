# Author:  DINDIN Meryll
# Date:    21 July 2019
# Project: RoadBuddy

import pafy
import vlc
import urllib.request
import numpy as np

from bs4 import BeautifulSoup

class YoutubeStreamer:

    def __init__(self):

        self.instance = vlc.Instance()
        self.player = self.instance.media_player_new()

    @staticmethod
    def check_format(url):
        
        if 'user' in url: return False
        if 'start_radio' in url: return False
        if 'list' in url: return False
        
        return True

    @staticmethod
    def query_youtube(textToSearch):

        query = urllib.parse.quote(textToSearch)
        url = "https://www.youtube.com/results?search_query=" + query
        response = urllib.request.urlopen(url)
        lst, html = [], response.read()
        soup = BeautifulSoup(html, 'html.parser')
        for vid in soup.findAll(attrs={'class':'yt-uix-tile-link'}):
            lst.append('https://www.youtube.com' + vid['href'])
        lst = [e for e in lst if check_format(url)]
        
        return np.random.choice(lst)

    def run_on_search(self, query):

        url = query_youtube(query)
        video = pafy.new(url)
        best = video.getbest()
        playurl = best.url
        Media = self.instance.media_new(playurl)
        Media.get_mrl()
        self.player.set_media(Media)
        self.player.play()

if __name__ == '__main__':

    prs = argparse.ArgumentParser()    
    prs.add_argument('--query', help='Query YouTube terms', type=str)
    prs = prs.parse_args()

    YoutubeStreamer().run_on_search(prs.query)
