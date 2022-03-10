from flask import render_template
import random

from flask import Blueprint
from utils.db_util import db_util
from utils.request_util import req_util
from utils.resp_parse import resp
from utils.resp_enum import RespEnum
import time

good_bp = Blueprint('good', __name__, url_prefix='/good')

COLLECTION_GOOD = 'good_detail'


@good_bp.route('/<keyword>/<page>/<limit>', methods=['GET'])
def get_goods(keyword, page, limit):
    """

    :return:
    """
    data = {}
    result = []
    goods = db_util.batch_select(COLLECTION_GOOD, {'brand': keyword}, limit=(int(page) * int(limit)))
    _sum = db_util.count(COLLECTION_GOOD, {'brand': keyword})
    for item in goods:
        item['_id'] = None
        result.append(item)
    data['result'] = result
    data['sum'] = _sum
    return render_template('good.html', data=data)


@good_bp.route('/init', methods=['GET'])
def init_goods():
    """
    :return:
    """
    _ids = []
    success_ids = []
    try:
        for i in range(21, 30):
            _ids.extend(req_util.get_good_id_info('电脑', '戴尔', page=i))
            success_ids.extend(db_util.update(COLLECTION_GOOD, _ids))
            time.sleep(random.randint(2, 5))
        return resp(data={'sum：': len(_ids), 'failure': len(_ids) - len(success_ids)})
    except Exception as e:
        print("接口异常 " + str(e))
        return resp(RespEnum.INIT_DATABASE_ERROR.value['code'], RespEnum.INIT_DATABASE_ERROR.value['msg'])