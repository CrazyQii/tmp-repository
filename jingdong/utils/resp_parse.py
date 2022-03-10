from flask import jsonify

from utils.resp_enum import RespEnum


def resp(code=RespEnum.SUCCESS.value['code'], msg=RespEnum.SUCCESS.value['msg'], data=None) -> object:
    """
    返回函数
    :param code 状态码
    :param msg 状态信息
    :param data 数据
    """
    try:
        result = {'code': code, 'msg': msg, 'data': data}
        return jsonify(result)
    except Exception as e:
        print(f'返回函数错误:{e}')


def request_parse(req_data):
    """
    解析请求数据并以json形式返回
    :param req_data:
    :return:
    """
    data = None
    if req_data.method == 'POST':
        data = req_data.json
    elif req_data.method == 'GET':
        data = req_data.args
    return data


def pagination(pagelimit, pagenum) -> tuple[int, int]:
    """
    分页查询
    :param pagelimit 每页数量
    :param pagenum 页码
    """
    try:
        pagelimit = int(pagelimit)
        pagenum = int(pagenum)
        offset = pagelimit * (pagenum - 1)
        return pagelimit, offset
    except Exception as e:
        print(f'分页函数错误: {e}')