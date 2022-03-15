import time

from pymongo import MongoClient
import datetime
from setting import Config
from bson import ObjectId
import random


class DbUtil(object):

    def __init__(self, url, db):
        self.client = MongoClient(f'mongodb://{url}:27017/')
        self.db = self.client[db]
        self.collection = None

    def insert(self, collection, data: list):
        try:
            post = self.db[collection]
            timestamp = datetime.datetime.utcnow()
            for item in data:
                item['update_time'] = timestamp
            _ids = post.insert_many(data).inserted_ids
            return _ids
        except Exception as e:
            print(f'mongodb 插入数据失败: {e}')

    def select(self, collection, condition):
        try:
            post = self.db[collection]
            if condition is not None:
                return post.find_one(filter=condition)
            else:
                return post.find_one()
        except Exception as e:
            print(f'mongodb 查询数据失败: {e}')

    def batch_select(self, collection, condition=None, limit=10):
        try:
            post = self.db[collection]
            if condition is not None:
                return post.find(condition).limit(limit)
            else:
                return post.find().limit(limit)

        except Exception as e:
            print(f'mongodb 批量查询数据失败: {e}')

    def count(self, collection, condition=None):
        try:
            post = self.db[collection]
            if condition is not None:
                return post.count_documents(condition)
            else:
                return post.count_documents()
        except Exception as e:
            print(f'mongodb 批量查询数据失败: {e}')

    def update(self, collection, condition, data: list):
        try:
            post = self.db[collection]
            timestamp = datetime.datetime.utcnow()
            _ids = []
            for item in data:
                item['update_time'] = timestamp
                if item['brand'] is None and item['name'] is None:
                    continue
                condition['brand'] = item['brand']
                condition['name'] = item['name']
                _id = post.update_one(condition, {'$set': item}, upsert=True).upserted_id
                if _id is None:
                    continue
                _ids.append(_id)
            return _ids
        except Exception as e:
            print(f'mongodb 更新数据失败: {e}')

    def delete(self, collection, data):
        try:
            post = self.db[collection]
            post.find_one_and_delete({'product_id': data['product_id']})
            return True
        except Exception as e:
            print(f'mongodb 删除数据失败: {e}')


db_util = DbUtil(url=Config.MONGODB_URI, db=Config.DB_NAME)

# if __name__ == '__main__':
    # dbu = DbUtil(url='121.41.169.208', db='jd_good')
    # data = [{'link': 'https://item.jd.com/100022334212.html', 'name': '戴尔Ins 16-7610-R1867L', 'brand': '戴尔（DELL）', 'max_price': '13199.00', 'price': '9999.00', 'guide_price': '11499.00', 'comment': {'total_comment_num': '20000', 'average_score': 5, 'good_count': '5600', 'good_rate': 0.92, 'poor_count': '200', 'poor_rate': 0.052}, 'product_id': '100022334212'}, {'link': 'https://item.jd.com/100008858799.html', 'name': '戴尔Ins 24-5401-R1628W', 'brand': '戴尔（DELL）', 'max_price': '5999.00', 'price': '5599.00', 'guide_price': '5799.00', 'comment': {'total_comment_num': '20000', 'average_score': 5, 'good_count': '5400', 'good_rate': 0.96, 'poor_count': '100', 'poor_rate': 0.028}, 'product_id': '100008858799'}]
    # dbu.update('good_detail', data)
#     print(dbu.count('good_detail', {'name': 'fsda'}))
