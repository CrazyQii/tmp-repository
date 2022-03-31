# coding:utf-8
# 航班路由

from flask import Blueprint, request, session
from common.Utils import request_parse, pagination, resp
from common.ResponseEnum import ResponseEnum
from models.FlightModel import FlightModel  # 账户模型
from models import db
from service.FlightService import FlightService

flight_bp = Blueprint('flight', __name__, url_prefix='/flight')


@flight_bp.route('/flights', methods=['GET'])
def query_flights():
    """ 分页查询航班 """
    flight_model = FlightModel()
    try:
        param = request_parse(request)
        if param.get('pagelimit') is not None and param.get('pagenum') is not None:
            limit, offset = pagination(param.get('pagelimit'), param.get('pagenum'))
            data = FlightService.query_flights(flightModel=flight_model, limit=limit, offset=offset, order_by=FlightModel.price)
            return resp(data=data)
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('分页多条件查询航班列表异常 ' + str(e))
        return resp(ResponseEnum.QUERY_DATABASE_FAIL.value['code'], ResponseEnum.QUERY_DATABASE_FAIL.value['msg'])


@flight_bp.route('/flight', methods=['GET'])
def query_flight():
    """ 查询单个航班 """
    try:
        param = request_parse(request)
        if param.get('flight_id') is not None and param.get('pagenum') is not None:
            limit, offset = pagination(param.get('pagelimit'), param.get('pagenum'))
            data = FlightService.query_flights(flightModel=flight_model, limit=limit, offset=offset,
                                               order_by=FlightModel.price)
            return resp(data=data)
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('分页多条件查询航班列表异常 ' + str(e))
        return resp(ResponseEnum.QUERY_DATABASE_FAIL.value['code'], ResponseEnum.QUERY_DATABASE_FAIL.value['msg'])


@flight_bp.route('/flight', methods=['POST'])
def post_flight():
    """ 添加航班 """
    pass


@flight_bp.route('/flights', methods=['POST'])
def post_batch_flight():
    """ 批量添加航班 """
    pass


@flight_bp.route('/flight', methods=['DELETE'])
def remove_flight():
    """ 删除航班 """
    pass


@flight_bp.route('/flight', methods=['PUT'])
def update_flight():
    """ 修改航班 """
    pass
