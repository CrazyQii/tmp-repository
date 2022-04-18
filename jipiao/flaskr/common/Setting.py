# coding:utf-8
# 数据库配置文件

class Config(object):
    """ 基础配置文件 """
    DEBUG = False
    SQLALCHEMY_DATABASE_URI = 'mysql+pymysql://root:123456@127.0.0.1:3306/jipiao'  # 数据库配置连接
    SQLALCHEMY_COMMIT_ON_TEARDOWN = True
    SQLALCHEMY_TRACK_MODIFICATIONS = True
    SECRET_KEY = 'ji_piao'  # flaks 密钥


class DevelopmentConfig(Config):
    """ 开发环境 """
    ENV = 'development'
    DEBUG = True


class ProductionConfig(Config):
    """ 生产环境 """
    ENV = 'production'
    DEBUG = False
