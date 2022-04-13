# coding:utf-8
# 留言模型

from . import db


class MessageModel(db.Model):
    """ 留言模型 """
    __tablename__ = 'tb_message'
    id = db.Column(db.Integer, doc="留言编号", primary_key=True)
    content = db.Column(db.String(255), doc="留言内容", nullable=True)
    phone = db.Column(db.String(14), doc="联系方式", nullable=True)
    account_id = db.Column(db.Integer, doc="账号编号", nullable=True)
    create_time = db.Column(db.String(255), doc="创建时间", nullable=True)
    status = db.Column(db.Integer, doc="处理状态", nullable=True)

    def to_json(self):
        return {
            'id': self.id,
            'content': self.content,
            'phone': self.phone,
            'account_id': self.account_id,
            'create_time': self.create_time,
            'status': self.status
        }
