# coding:utf-8
# 订单逻辑处理

from models.OrderModel import OrderModel  # 航班模型
from models.FlightModel import FlightModel
from datetime import datetime


class OrderService(object):

    @classmethod
    def query_orders(cls, account_id, limit: int = 10, offset: int = 0, order_by=OrderModel.pay_end_time):
        """
        分页多条件查询航班
        :param account_id:
        :param flight_id:
        :param order_by:
        :param limit:
        :param offset:
        :return:
        """
        result = {'orders': [], 'sum': 0}
        orders = OrderModel.query.filter(OrderModel.account_id == account_id).order_by(order_by.desc()).all()
        # 转化json格式
        if orders is not None:
            for item in orders:
                item = item.to_json()
                flight = FlightModel.query.filter(FlightModel.id == item['flight_id']).first()
                if flight is not None:
                    flight = flight.to_json()
                    flight['start_time'] = datetime.strftime(flight['start_time'], '%Y-%m-%d %H:%M:%S')
                    flight['end_time'] = datetime.strftime(flight['end_time'], '%Y-%m-%d %H:%M:%S')
                    item['flight'] = flight
                else:
                    item['flight'] = {}
                result['orders'].append(item)
            result['sum'] = len(result.get('orders'))
        return result
