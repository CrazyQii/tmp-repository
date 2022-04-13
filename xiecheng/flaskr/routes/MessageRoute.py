# coding:utf-8
# 留言路由
import datetime

from flask import Blueprint, request, session
from common.ResponseEnum import ResponseEnum
from common.Utils import request_parse, pagination, resp
from models.MessageModel import MessageModel
from models import db

message_bp = Blueprint('message', __name__, url_prefix='/message')


@message_bp.route('/messages', methods=['GET'])
def query_messages():
    """ 留言列表 """
    result = []
    try:
        param = request_parse(request)
        limit, offset = 10, 1
        if param.get('pagelimit') is not None and param.get('pagenum') is not None:
            limit, offset = pagination(param.get('pagelimit'), param.get('pagenum'))
            messages = db.session.query(MessageModel).order_by(MessageModel.create_time).limit(limit).offset(offset).all()
            for item in messages:
                result.append(item.to_json())
            return resp(data=result)
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('分页查询留言列表异常 ' + str(e))
        return resp(ResponseEnum.QUERY_DATABASE_FAIL.value['code'], ResponseEnum.QUERY_DATABASE_FAIL.value['msg'])


@message_bp.route('/message', methods=['POST'])
def post_message():
    """ 添加留言 """
    try:
        param = request_parse(request)
        if param.get('account_id') is not None and param.get('content') is not None:
            message = MessageModel()
            message.phone = param.get('phone')
            message.account_id = int(param.get('account_id'))
            message.content = param.get('content')
            message.create_time = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
            db.session.add(message)
            db.session.commit()
            return resp(data=message.to_json())
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('添加留言异常 ' + str(e))
        return resp(ResponseEnum.INSERT_DATABASE_FAIL.value['code'], ResponseEnum.INSERT_DATABASE_FAIL.value['msg'])


@message_bp.route('/message', methods=['DELETE'])
def remove_message():
    """ 删除用留言 """
    result = []
    try:
        param = request_parse(request)
        if param.get('id') is not None:
            MessageModel.query.filter(
                MessageModel.id == request.args.get('id')).update({'status': 1})

            ass = db.session.query(MessageModel).order_by(MessageModel.create_time).all()
            # db.session.delete(ass)
            # db.session.commit()
            if ass is not None:
                for item in ass:
                    result.append(item.to_json())
                return resp(data=result)
            return resp(ResponseEnum.QUERY_DATABASE_FAIL.value['code'], '未找到对应的留言')
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('删除留言异常 ' + str(e))
        return resp(ResponseEnum.DELETE_DATABASE_FAIL.value['code'], ResponseEnum.DELETE_DATABASE_FAIL.value['msg'])
