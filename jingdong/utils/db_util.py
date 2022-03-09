from pymongo import MongoClient


class DbUtil(object):

    def __init__(self, url, db):
        self.client = MongoClient(f'mongodb://{url}:27017/')
        self.db = self.client[db]
        self.collection = None



if __name__ == '__main__':

    db = DbUtil(url='121.41.169.208', db='jd_good')