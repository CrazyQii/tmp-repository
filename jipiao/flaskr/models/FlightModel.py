# coding:utf-8
# 航班模型

from . import db


class FlightModel(db.Model):
    """ 航班模型 """
    __tablename__ = 'tb_flight'
    id = db.Column(db.String(255), doc="航班号", primary_key=True)
    flight_type = db.Column(db.String(255), doc="机型", nullable=True)
    flight_company = db.Column(db.String(255), doc="航空公司", nullable=True)
    flight_number = db.Column(db.Integer, doc="舱位数量", nullable=True)
    from_pos = db.Column(db.String(5), doc="始发地", nullable=True)
    to_pos = db.Column(db.String(13), doc="到达地", nullable=True)
    start_time = db.Column(db.DateTime, doc="始发时间", nullable=True)
    end_time = db.Column(db.DateTime, doc="到达时间", nullable=True)
    price = db.Column(db.Numeric, doc="价格", nullable=True)

    def to_json(self):
        return {
            'id': self.id,
            'flight_type': self.flight_type,
            'flight_company': self.flight_company,
            'flight_number': self.flight_number,
            'from_pos': self.from_pos,
            'to_pos': self.to_pos,
            'start_time': self.start_time,
            'end_time': self.end_time,
            'price': self.price,
        }