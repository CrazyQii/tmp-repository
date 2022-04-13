# coding:utf-8
# 航班路由

from flask import Blueprint, request, session
from common.Utils import request_parse, pagination, resp, get_host_ip_location
from common.ResponseEnum import ResponseEnum
from models.FlightModel import FlightModel  # 账户模型
from models import db
from service.FlightService import FlightService

flight_bp = Blueprint('flight', __name__, url_prefix='/flight')


@flight_bp.route('/location', methods=['GET'])
def query_location():
    try:
        location = get_host_ip_location()
        if location is None:
            return resp(ResponseEnum.LOCATION_ERROR.value['code'], ResponseEnum.LOCATION_ERROR.value['msg'])
        else:
            print(location)
            return resp(data=location)
    except Exception as e:
        print('ip地理位置定位失败', e)
        return resp(ResponseEnum.SYSTEM_ERROR.value['code'], ResponseEnum.SYSTEM_ERROR.value['msg'])


@flight_bp.route('/flights', methods=['GET'])
def query_flights():
    """ 分页查询航班 """
    flight_model = FlightModel()
    try:
        param = request_parse(request)
        if param.get('from_pos') is not None:
            flight_model.from_pos = param.get('from_pos')
        if param.get('to_pos') is not None:
            flight_model.to_pos = param.get('to_pos')
        if param.get('start_time') is not None:
            flight_model.start_time = param.get('start_time')
        if param.get('end_time') is not None:
            flight_model.end_time = param.get('end_time')
        print(flight_model.to_json())
        data = FlightService.query_flights(flightModel=flight_model,
                                           order_by=FlightModel.start_time)
        return resp(data=data)
    except Exception as e:
        print('分页多条件查询航班列表异常 ' + str(e))
        return resp(ResponseEnum.QUERY_DATABASE_FAIL.value['code'], ResponseEnum.QUERY_DATABASE_FAIL.value['msg'])


@flight_bp.route('/flight', methods=['GET'])
def query_flight():
    """ 查询单个航班 """
    try:
        param = request_parse(request)
        if param.get('flight_id') is not None:
            flight = db.session.query(FlightModel).filter(FlightModel.id == param.get('flight_id')).first()
            return resp(data=flight.to_json())
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('查询航班列表异常 ' + str(e))
        return resp(ResponseEnum.QUERY_DATABASE_FAIL.value['code'], ResponseEnum.QUERY_DATABASE_FAIL.value['msg'])


@flight_bp.route('/flight', methods=['POST'])
def post_flight():
    """ 添加航班 """
    pass
    # param = request_parse(request)
    # flight = FlightModel()
    # db.session.add(account)
    # db.session.commit()


@flight_bp.route('/flights', methods=['POST'])
def post_batch_flight():
    """ 批量添加航班 """
    pass


@flight_bp.route('/flight', methods=['DELETE'])
def remove_flight():
    """ 删除航班 """
    flight_model = FlightModel()
    try:
        param = request_parse(request)
        if param.get('flight_id') is not None:
            db.session.query(FlightModel).filter(FlightModel.id == param.get('flight_id')).delete()
            db.session.commit()
            if param.get('from_pos') is not None:
                flight_model.from_pos = param.get('from_pos')
            if param.get('to_pos') is not None:
                flight_model.to_pos = param.get('to_pos')
            if param.get('start_time') is not None:
                flight_model.start_time = param.get('start_time')
            if param.get('end_time') is not None:
                flight_model.end_time = param.get('end_time')
            data = FlightService.query_flights(flightModel=flight_model, order_by=FlightModel.start_time)
            return resp(data=data)
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('删除航班列表异常 ' + str(e))
        return resp(ResponseEnum.DELETE_DATABASE_FAIL.value['code'], ResponseEnum.DELETE_DATABASE_FAIL.value['msg'])


@flight_bp.route('/flight', methods=['PUT'])
def update_flight():
    """ 修改航班 """
    try:
        param = request_parse(request)
        if param.get('flight_id') is not None:
            res = db.session.query(FlightModel).filter(FlightModel.id == param.get('flight_id')).update(param)
            print(res)
            db.session.commit()
    except Exception as e:
        print('修改航班列表异常 ' + str(e))
        return resp(ResponseEnum.UPDATE_DATABASE_FAIL.value['code'], ResponseEnum.UPDATE_DATABASE_FAIL.value['msg'])
