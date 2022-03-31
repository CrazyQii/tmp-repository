# coding:utf-8
# 订单逻辑处理

from models.OrderModel import OrderModel  # 航班模型
from sqlalchemy.sql import text


class OrderService(object):

    @classmethod
    def query_flights(cls, limit: int = 10, offset: int = 0, condition: dict = {}, order_by=OrderModel.pay_end_time):
        """
        分页查询订单
        :param order_by:
        :param condition:
        :param limit:
        :param offset:
        :return:
        """
        sql = 'where 1 = 1 '
        if 'flight_type' in condition.keys():
            sql = sql + 'and flight_type = ' + condition.get('flight_type')
        result = {'flights': [], 'sum': 0}
        flights = OrderModel.query.filter(text(sql)).order_by(
            order_by).limit(limit).offset(offset).all()
        # 转化json格式
        if flights is not None:
            for item in flights:
                result['flights'].append(item.to_json())
            result['flights'] = len(result.get('flights'))
        return result
