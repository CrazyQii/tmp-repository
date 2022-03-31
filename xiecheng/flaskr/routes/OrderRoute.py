# coding:utf-8
# 订单路由

from flask import Blueprint, request, session
from common.ResponseEnum import ResponseEnum
from models import db

order_bp = Blueprint('order', __name__, url_prefix='/order')


@order_bp.route('/orders', methods=['GET'])
def query_orders():
    """ 分页查询订单 """
    pass


@order_bp.route('/order', methods=['GET'])
def query_order():
    """ 查询单个订单 """
    pass


@order_bp.route('/order', methods=['POST'])
def post_order():
    """ 添加订单 """
    pass


@order_bp.route('/order', methods=['DELETE'])
def remove_order():
    """ 删除订单 """
    pass


@order_bp.route('/order', methods=['PUT'])
def update_order():
    """ 修改订单 """
    pass