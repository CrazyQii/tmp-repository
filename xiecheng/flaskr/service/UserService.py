# coding:utf-8
# 用户信息逻辑处理

from models.AccountModel import AccountModel  # 账户模型


class UserService(object):

    @classmethod
    def query_users(cls, limit: int, offset: int):
        """
        分页查询用户
        :param limit:
        :param offset:
        :return:
        """
        result = {'accounts': [], 'sum': 0}
        accounts = AccountModel.query.filter(AccountModel.role == 'user').order_by(
            AccountModel.create_time).limit(limit).offset(offset).all()
        # 转化json格式
        if accounts is not None:
            for item in accounts:
                result['accounts'].append(item.to_json())
            result['sum'] = len(result.get('accounts'))
        return result
