# coding:utf-8
# 认证（登录/注册）路由

from flask import Blueprint, request, session
from models.AccountModel import AccountModel  # 账户模型
from service.UserService import UserService
from models import db
from common.Utils import create_token, login_required, resp, \
    valid_login, valid_register, verify_token, pagination, request_parse
from common.ResponseEnum import ResponseEnum
import time

auth_bp = Blueprint('auth', __name__, url_prefix='/auth')


@auth_bp.route('/login', methods=['POST'])
def login():
    """
    登录
    :param phone
    :param password
    :return:
    """
    try:
        param = request_parse(request)
        account = valid_login(param.get('phone'))
        if account[0]:  # 电话格式合法
            account = db.session.query(AccountModel).filter(
                AccountModel.phone == param.get('phone'),
                AccountModel.password == param.get('password')).first()
            if account is not None:
                # 用户存在，创建token
                token = create_token(account.phone)
                session['token'] = token
                return resp(data={'token': token})
            else:
                return resp(ResponseEnum.USER_NOT_FOUND.value['code'], ResponseEnum.USER_NOT_FOUND.value['msg'])
        else:
            return resp(account[1].value['code'], account[1].value['msg'])
    except Exception as e:
        print('登录异常 ' + str(e))
        return resp(ResponseEnum.SYSTEM_ERROR.value['code'], ResponseEnum.SYSTEM_ERROR.value['msg'])


@auth_bp.route('/sign-in', methods=['POST'])
def sign_in():
    """
    注册
    :param phone
    :param password
    :param nickname
    :return:
    """
    try:
        param = request_parse(request)
        register = valid_register(param.get('phone'), param.get('password'))
        if register[0]:  # 数据是否合法
            account = repeat_register(param.get('phone'))
            if account[0]:  # 账户不存在
                account = AccountModel(nickname=param.get('nickname'),
                                       phone=param.get('phone'),
                                       password=param.get('password'),
                                       role='user',
                                       create_time=round(time.time() * 1000))
                db.session.add(account)
                db.session.commit()
                return resp(data=account.to_json())
            else:
                return resp(ResponseEnum.USER_ALREADY_EXIST.value['code'], ResponseEnum.USER_ALREADY_EXIST.value['msg'])
        else:
            return resp(register[1].value['code'], register[1].value['msg'])
    except Exception as e:
        print('注册异常 ' + str(e))
        return resp(ResponseEnum.SYSTEM_ERROR.value['code'], ResponseEnum.SYSTEM_ERROR.value['msg'])


@auth_bp.route('/logout', methods=['GET', 'POST'])
def logout():
    """
    登出
    :return:
    """
    try:
        session.pop('token', None)
        return resp()
    except Exception as e:
        print('退出登录异常 ' + str(e))
        return resp(ResponseEnum.SYSTEM_ERROR.value['code'], ResponseEnum.SYSTEM_ERROR.value['msg'])


@auth_bp.route('/user', methods=['GET'])
@login_required
def get_userinfo():
    """
    用户信息
    :return:
    """
    token = request.headers["token"]
    # 拿到token，去换取用户信息
    return resp(data=verify_token(token))


@auth_bp.route('/users', methods=['GET'])
def query_users():
    """
    分页查询用户列表
    :param pagelimit
    :param pagenum
    :return:
    """
    try:
        param = request_parse(request)
        if param.get('pagelimit') is not None and param.get('pagenum') is not None:
            limit, offset = pagination(param.get('pagelimit'), param.get('pagenum'))
            data = UserService.query_users(limit, offset)
            return resp(data=data)
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('分页查询用户列表异常 ' + str(e))
        return resp(ResponseEnum.QUERY_DATABASE_FAIL.value['code'], ResponseEnum.QUERY_DATABASE_FAIL.value['msg'])

@auth_bp.route('/user', methods=['DELETE'])
def delete_user():
    """ 删除用户 """
    try:
        param = request_parse(request)
        if param.get('id') is not None:
            ass = AccountModel.query.filter(
                AccountModel.id == request.args.get('id')).first()
            # db.session.delete(ass)
            # db.session.commit()
            if ass is not None:
                return resp()
            return resp(ResponseEnum.QUERY_DATABASE_FAIL.value['code'], '未找到对应的用户')
        else:
            return resp(ResponseEnum.PARAM_INVALID.value['code'], ResponseEnum.PARAM_INVALID.value['msg'])
    except Exception as e:
        print('删除用户列表异常 ' + str(e))
        return resp(ResponseEnum.DELETE_DATABASE_FAIL.value['code'], ResponseEnum.DELETE_DATABASE_FAIL.value['msg'])

############################################
# 辅助函数
############################################


def repeat_register(phone: str):
    """ 判断用户是否已经存在，存在False，不存在True """
    account = db.session.query(AccountModel).filter(AccountModel.phone == phone).first()
    if account is None:
        return True, ''
    else:
        return False, ''
