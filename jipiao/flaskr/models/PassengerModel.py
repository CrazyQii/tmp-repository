# coding:utf-8
# 乘客信息模型

from . import db


class PassengerModel(db.Model):
    """ 乘客信息模型 """
    __tablename__ = 'tb_passenger'
    id = db.Column(db.Integer, doc="编号", primary_key=True)
    id_card = db.Column(db.String(20), doc="身份证", nullable=True)
    phone = db.Column(db.String(255), doc="手机号码", nullable=True)
    name = db.Column(db.String(255), doc="姓名", nullable=True)
    flight_id = db.Column(db.Integer, doc="航班编号", nullable=True)
    account_id = db.Column(db.String(5), doc="账号编号", nullable=True)

    def to_json(self):
        return {
            'id': self.id,
            'id_card': self.id_card,
            'phone': self.phone,
            'name': self.name,
            'flight_id': self.flight_id,
            'account_id': self.account_id
        }