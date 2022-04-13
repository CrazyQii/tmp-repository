# coding:utf-8
# 航班逻辑处理

from models.FlightModel import FlightModel  # 航班模型
from sqlalchemy import and_, or_
from models import db


class FlightService(object):

    @classmethod
    def query_flights(cls, flightModel, limit: int = 10, offset: int = 0, order_by=FlightModel.start_time):
        """
        分页多条件查询航班
        :param flightModel:
        :param order_by:
        :param limit:
        :param offset:
        :return:
        """
        result = {'flights': [], 'sum': 0}
        flights = FlightModel.query.filter(
            or_(FlightModel.flight_type == flightModel.flight_type, flightModel.flight_type is None),
            or_(db.cast(FlightModel.start_time, db.Date) == flightModel.start_time, flightModel.start_time is None),
            # or_(FlightModel.end_time == flightModel.end_time, flightModel.end_time is None),
            or_(FlightModel.flight_company == flightModel.flight_company, flightModel.flight_company is None),
            or_(FlightModel.from_pos == flightModel.from_pos, flightModel.from_pos is None),
            or_(FlightModel.to_pos == flightModel.to_pos, flightModel.to_pos is None),
            or_(FlightModel.flight_number == flightModel.flight_number, flightModel.flight_number is None)
        ).order_by(order_by).all()
        # 转化json格式
        if flights is not None:
            for item in flights:
                result['flights'].append(item.to_json())
            result['sum'] = len(result.get('flights'))
        return result

