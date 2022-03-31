# coding:utf-8
# 航班模型

from . import db


class OrderModel(db.Model):
    """ 航班模型 """
    __tablename__ = 'tb_order'
    id = db.Column(db.String(20), doc="ID", primary_key=True)
    pay_time = db.Column(db.String(255), doc="支付时间", nullable=True)
    pay_end_time = db.Column(db.String(255), doc="支付截止时间", nullable=True)
    pay_ready = db.Column(db.String(255), doc="是否支付", nullable=True)
    flight_id = db.Column(db.Integer, doc="航班编号", nullable=True)
    account_id = db.Column(db.String(5), doc="账号编号", nullable=True)

    def to_json(self):
        return {
            'id': self.id,
            'pay_time': self.pay_time,
            'pay_end_time': self.pay_end_time,
            'pay_ready': self.pay_ready,
            'flight_id': self.flight_id,
            'account_id': self.account_id
        }
