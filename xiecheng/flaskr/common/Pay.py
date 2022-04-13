#!/usr/bin/env python
# -*- coding: utf-8 -*-
import logging
from alipay.aop.api.AlipayClientConfig import AlipayClientConfig
from alipay.aop.api.DefaultAlipayClient import DefaultAlipayClient
from alipay.aop.api.domain.AlipayTradePagePayModel import AlipayTradePagePayModel
from alipay.aop.api.request.AlipayTradePagePayRequest import AlipayTradePagePayRequest
from alipay.aop.api.response.AlipayTradePagePayResponse import AlipayTradePagePayResponse
from alipay.aop.api.request.AlipayTradeQueryRequest import AlipayTradeQueryRequest
from alipay.aop.api.response.AlipayTradeQueryResponse import AlipayTradeQueryResponse
from alipay.aop.api.domain.AlipayTradeQueryModel import AlipayTradeQueryModel
from alipay.aop.api.request.AlipayTradeCancelRequest import AlipayTradeCancelRequest
from alipay.aop.api.response.AlipayTradeCancelResponse import AlipayTradeCancelResponse
from alipay.aop.api.domain.AlipayTradeCancelModel import AlipayTradeCancelModel
from alipay.aop.api.domain.AlipayTradeCloseModel import AlipayTradeCloseModel
from alipay.aop.api.request.AlipayTradeCloseRequest import AlipayTradeCloseRequest
from alipay.aop.api.response.AlipayTradeCloseResponse import AlipayTradeCloseResponse


class AlipayInterface(object):

    def __init__(self):
        logging.basicConfig(
            level=logging.INFO,
            format='%(asctime)s %(levelname)s %(message)s',
            filemode='a', )
        logger = logging.getLogger('')
        alipay_client_config = AlipayClientConfig()
        alipay_client_config.server_url = 'https://openapi.alipaydev.com/gateway.do'
        alipay_client_config.app_id = '2021000119663861'
        alipay_client_config.app_private_key = 'MIIEowIBAAKCAQEAweTm4Y2kDWXEKc2fXRMBbSB20a2K29hYqoIhl6MBWJ0RLWK0f4le8/8yGjNN8E7EYPcR85mZnUQ7x49UAdSKXEBIA/cnIxGe+tZ/W885oi+WElkDRiSTqbVPnt2mOssIF3g7TRIy9LGQe5iGmtHSzvZogPZbbSgTr0j7YFV78MorxFUFr6Klc2I4Jm3Luv/YUVMUXjOfO9qsvVxgFIcJAt7/DozNpzTj2xQw/oUs8Aqiz097Oz56fUxwfwOy1/jCJM4T73y6zexysYfhRQN1tmVd5FfaMogmp8ld8Hj5bOax5i70oLf6NHLderpw+Vybdki4tUS3J0y2kC4aF+eF5QIDAQABAoIBACQr0a6OP+aH75pk07AHVaLIBaKEH3131MKqWB3zuNKRKp0u5sFMHTyjhPNCUu3bo+L69IxfQTsHead245Mb1Vv0WfrzZoeyR9g/n66+79+DxRlTJbs2I53rRxk5/ELHkZDfSyDLnlnrriqAf8vuDMezTDeSveeVLGbs1IhaKi73FLpCAIHLsYy75ai3dp6mW+ftF17yYuWxQUlqq3OumQX/dDSsioBYSAT5KMaEfoDFYsJ+JcwETVGR2XmLYdP4AE1xr7Q4IzEbDBtfAWvOvUd8fHTNHqSYxNF8UgJMSJJsen5IGA3FSx66PFPRSa8AXVQfaRQEEi/MDnk0Uywxa0ECgYEA9nhRzdA65s3YHKDxJ4m3J28MKO08qfiP7qNgvUJF396fq94QAZGy0aP9vayaAIbKH4b3f1KxGk+HbSPplaimGqSChyFzJEBgp4TpKmdI9Z045ncd7hyS+D1xgoJuvbgda27t5Gu3bc04f1/ywc7UOzanWJgaHuA85k2+59F/xvkCgYEAyWQpL4BWi+xomM61O37Hkc3yAYKZsvfoj2S8bIpD6O4hi6Nijpavatg1nOldWnNOF1LgwFrAWuekM2cPqFvidSIyEG3jk0Kpy3VsJqnL5hu7sLsLXZS8Em+DIK7cZohU60gWVzwik5sWHv5PoWySwTpL4JuHr3BZLZQ/R7SoVU0CgYEA60Ffe98lScbzYCgy5zVpUEeLyMjhXa/22qaxoRTQE2aw6+DTUFwc3kP04AYPHS+LYFp4hNyOVXnR31gnDoXivo2GVauU9uZIG74evS3HE0IV3M2YEP7PPgmP/i23vHkwA6rngV9bv3/iTJpt4ZYlN7vsMgFE9XG+T2moLifnGikCgYAVWpZ3X+9NVNreYQScc1vr3N9tQ5DShjDVi4PjKmH6/8z0xnU700R0WsqMgb4RSiqclIAaRUUiHm+tt57UZjHD48VVMqGvyqDAXwoBq31gP86N9bdFy/CJL+PCgmgCQzBl5jXgOBMefZJB5QtCfYWuEWVcQoGHm60JBTAUhVZX/QKBgDNkWYbsUWEBKvk51oD/AlNMvrl6vdiebUwYR1rMDnzKw2IGiHi2wz6n6bBzhy26H+mslUtWLc8L4gYmeBaEGGrwskzQ6f9g6YxtHEr8PJjgdS8Pwq28R4mHWTVS2YpwOaiUOIpqGrC4m23fNw/EZEdVvCiexychBEiwvzkot5j2'
        alipay_client_config.alipay_public_key = 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv7ognQkzVe7rvuAWskM0gw+GLlNYgCA6TeMSMdP4UsbGj+o0P1d3zAbUj2jC6/z5uR/L9uHvoxUru/f+ZK1qxliGUguafpeWltxluocsXwHGbo+GeuW8yWgLhDaAwY3hnN+513wm2ltUIPwugzZYSIkwwg33QITmImx80T6IqxfdPgK4g1hcv8fieJkyeVtlCQULMVc7e+HZvy3wXl3XlaM7liy9s3x9GRMN/zBaJaUbRk/gYAQfS06n9dL5sdjCK7cVJeEAbnwoFdRzN2wKnv/CiiU4Lp/aaBnXrmk1Tgh0QqZTV3CxrClOOLhl6Ud1LTkkEnbvZYVr/hj6gyst4QIDAQAB'
        self.client = DefaultAlipayClient(alipay_client_config, logger)

    def query_alipay(self, out_trade_no="2088621958310455"):
        """ 查询订单 """
        model = AlipayTradeQueryModel()
        model.out_trade_no = out_trade_no
        request = AlipayTradeQueryRequest(biz_model=model)

        try:
            response_content = self.client.execute(request)
            if not response_content:
                print("failed execute")
            else:
                # 解析响应结果
                response = AlipayTradeQueryResponse()
                response.parse_response_content(response_content)
                return response
        except Exception as e:
            print('查询订单异常', e)

    def close_alipay(self, trade_no):
        """ 关闭订单 """
        model = AlipayTradeCloseModel()
        model.trade_no = trade_no
        request = AlipayTradeCloseRequest(biz_model=model)

        try:
            response_content = self.client.page_execute(request)
            return response_content
        except Exception as e:
            print('关闭订单异常', e)

    def create_alipay(self, out_trade_no, total_amount, subject, buyer_id="2088622958360713"):
        """ 创建订单 """
        # 构造请求参数对象
        model = AlipayTradePagePayModel()
        model.out_trade_no = out_trade_no
        model.total_amount = total_amount
        model.subject = subject
        model.buyer_id = buyer_id
        model.product_code = "FAST_INSTANT_TRADE_PAY"
        request = AlipayTradePagePayRequest(biz_model=model)

        # 执行API调用
        try:
            response_content = self.client.page_execute(request)
            return response_content
        except Exception as e:
            print('创建订单异常', e)


if __name__ == '__main__':
    # 实例化客户端
    a = AlipayInterface()
    # a.create_alipay()
    # a.cancel_alipay()
    a.query_alipay()
