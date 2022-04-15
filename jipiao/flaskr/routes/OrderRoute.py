# coding:utf-8
# 订单路由
import datetime
import time

from flask import Blueprint, request, session, render_template
from common.ResponseEnum import ResponseEnum
from common.Utils import request_parse, pagination, resp
from models.OrderModel import OrderModel
from service.OrderService import OrderService
from models.PassengerModel import PassengerModel
from models import db
from common.Pay import AlipayInterface

order_bp = Blueprint('order', __name__, url_prefix='/order')


@order_bp.route('/orders', methods=['GET'])
def query_orders():
    """ 分页查询订单 """
    try:
        param = request_parse(request)
        if param.get('account_id') is not None:
            data = OrderService.query_orders(param.get('account_id'))
            return resp(data=data)
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('分页查询订单列表异常 ' + str(e))
        return resp(ResponseEnum.QUERY_DATABASE_FAIL.value['code'], ResponseEnum.QUERY_DATABASE_FAIL.value['msg'])


@order_bp.route('/order', methods=['GET'])
def query_order():
    """ 查询单个订单 """
    try:
        param = request_parse(request)
        if param.get('order_id') is not None:
            data = OrderModel.query.filter(OrderModel.id == param.get('order_id')).first()
            return resp(data=data.to_json())
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('分页查询订单列表异常 ' + str(e))
        return resp(ResponseEnum.QUERY_DATABASE_FAIL.value['code'], ResponseEnum.QUERY_DATABASE_FAIL.value['msg'])


@order_bp.route('/order', methods=['POST'])
def post_order():
    """ 添加订单 """
    try:
        param = request_parse(request)
        if param.get('account_id') is not None and param.get('flight_id') is not None \
                and param.get('money') is not None:
            old_order = OrderModel.query.filter(OrderModel.account_id == param.get('account_id')). \
                filter(OrderModel.flight_id == param.get('flight_id')). \
                filter(OrderModel.pay_ready == 0).first()
            if old_order is not None:
                return resp(ResponseEnum.INSERT_DATABASE_FAIL.value['code'],
                            '用户已经生成过该航班的订单')
            order = OrderModel()
            order.id = int(time.time())
            order.account_id = param.get('account_id')
            order.money = param.get('money')
            order.flight_id = param.get('flight_id')
            order.pay_ready = 0
            order.pay_time = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
            end_time = datetime.datetime.now() + datetime.timedelta(hours=48)
            order.pay_end_time = datetime.datetime.strftime(end_time, '%Y-%m-%d %H:%M:%S')
            db.session.add(order)
            db.session.commit()
            return resp(data=order.to_json())
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('添加订单异常 ' + str(e))
        return resp(ResponseEnum.INSERT_DATABASE_FAIL.value['code'], ResponseEnum.INSERT_DATABASE_FAIL.value['msg'])


@order_bp.route('/order', methods=['DELETE'])
def remove_order():
    """ 删除订单 """
    try:
        param = request_parse(request)
        limit, offset = 5, 1
        if param.get('pagelimit') is not None and param.get('pagenum') is not None:
            limit, offset = pagination(param.get('pagelimit'), param.get('pagenum'))
        if param.get('order_id') is not None and param.get('account_id') is not None:
            OrderModel.query.filter(OrderModel.id == param.get('order_id')).update({'pay_ready': 3})
            db.session.commit()
            data = OrderService.query_orders(param.get('account_id'), limit, offset)
            return resp(data=data)
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('删除订单异常 ' + str(e))
        return resp(ResponseEnum.UPDATE_DATABASE_FAIL.value['code'], ResponseEnum.UPDATE_DATABASE_FAIL.value['msg'])


@order_bp.route('/passenger', methods=['GET'])
def query_passenger():
    """ 查询乘客 """
    result = []
    try:
        param = request_parse(request)
        if param.get('account_id') is not None:
            passengers = PassengerModel.query.filter(PassengerModel.account_id == param.get('account_id')).all()
            for passenger in passengers:
                result.append(passenger.to_json())
            return resp(data=result)
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('查询乘客列表异常 ' + str(e))
        return resp(ResponseEnum.QUERY_DATABASE_FAIL.value['code'], ResponseEnum.QUERY_DATABASE_FAIL.value['msg'])


@order_bp.route('/passenger', methods=['DELETE'])
def remove_passenger():
    """ 删除乘客 """
    result = []
    try:
        param = request_parse(request)
        if param.get('account_id') is not None and param.get('id_card') is not None:
            db.session.query(PassengerModel).filter(PassengerModel.account_id == param.get('account_id')).filter(
                PassengerModel.id_card == param.get('id_card')).delete()
            db.session.commit()
            passengers = PassengerModel.query.filter(PassengerModel.account_id == param.get('account_id')).all()
            for passenger in passengers:
                result.append(passenger.to_json())
            return resp(data=result)
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('删除乘客列表异常 ' + str(e))
        return resp(ResponseEnum.DELETE_DATABASE_FAIL.value['code'], ResponseEnum.DELETE_DATABASE_FAIL.value['msg'])


@order_bp.route('/passenger', methods=['POST'])
def post_passenger():
    """ 添加乘客 """
    try:
        param = request_parse(request)
        if param.get('account_id') is not None and param.get('flight_id') is not None \
                and param.get('name') is not None and param.get('id_card') is not None and param.get(
            'phone') is not None:
            passenger = PassengerModel()
            passenger.flight_id = param.get('flight_id')
            passenger.account_id = param.get('account_id')
            passenger.name = param.get('name')
            passenger.id_card = param.get('id_card')
            passenger.phone = param.get('phone')
            db.session.add(passenger)
            db.session.commit()
            return resp(data=passenger.to_json())
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('添加乘客列表异常 ' + str(e))
        return resp(ResponseEnum.INSERT_DATABASE_FAIL.value['code'], ResponseEnum.INSERT_DATABASE_FAIL.value['msg'])


@order_bp.route('/pay', methods=['GET'])
def create_pay():
    """ 创建订单 """
    try:
        param = request_parse(request)
        a = AlipayInterface()
        if param.get('order_id') is not None:
            out_trade_no = param.get('order_id')
            total_amount = param.get('money')
            subject = param.get('name')
            return render_template('pay.html', data=a.create_alipay(out_trade_no, total_amount, subject))
    except Exception as e:
        print('调用支付宝沙盒支付失败 ' + str(e))
        return resp(ResponseEnum.SYSTEM_ERROR.value['code'], ResponseEnum.SYSTEM_ERROR.value['msg'])


@order_bp.route('/query/pay', methods=['GET'])
def query_pay():
    """ 查询是否支付成功 """
    try:
        a = AlipayInterface()
        param = request_parse(request)
        order = a.query_alipay(out_trade_no=param.get('order_id'))
        if order.code == '10000' and order.trade_status == 'TRADE_SUCCESS':
            OrderModel.query.filter(OrderModel.id == param.get('order_id')).update({'pay_ready': 1})
            db.session.commit()
            return resp(data={'pay_ready': 1})
        if order.trade_status == 'TRADE_CLOSED' or order.trade_status == 'TRADE_FINISHED':
            OrderModel.query.filter(OrderModel.id == param.get('order_id')).update({'pay_ready': 3})
            db.session.commit()
            return resp(data={'pay_ready': 3})
        if order.trade_status == 'WAIT_BUYER_PAY':
            OrderModel.query.filter(OrderModel.id == param.get('order_id')).update({'pay_ready': 0})
            db.session.commit()
            return resp(data={'pay_ready': 0})
        if order.code == '40004':
            return resp(data={'pay_ready': 0})
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('调用支付宝沙盒查询失败 ' + str(e))
        return resp(ResponseEnum.SYSTEM_ERROR.value['code'], ResponseEnum.SYSTEM_ERROR.value['msg'])
