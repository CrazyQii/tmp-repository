from enum import Enum


class RespEnum(Enum):
    SUCCESS = {'code': 200, 'msg': '成功'}
    DATABASE_EXCEPTION = {'code': 1001, 'msg': '数据库异常'}
    UPDATE_DATABASE_FAIL = {'code': 1002, 'msg': '更新数据失败'}
    INSERT_DATABASE_FAIL = {'code': 1003, 'msg': '插入数据失败'}
    QUERY_DATABASE_FAIL = {'code': 1004, 'msg': '查询数据失败'}
    DELETE_DATABASE_FAIL = {'code': 1005, 'msg': '删除数据失败'}
    PARAM_INVALID = {'code': 1006, 'msg': '参数异常'}
    SYSTEM_ERROR = {'code': 1007, 'msg': '系统未知错误'}
    INIT_DATABASE_ERROR = {'code': 1008, 'msg': '初始化数据库失败'}