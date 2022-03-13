from flask import render_template, request

from flask import Blueprint
from utils.db_util import db_util
from utils.request_util import req_util
import time

good_bp = Blueprint('good', __name__, url_prefix='/good')

COLLECTION_GOOD_DETAIL = 'good_detail'
COLLECTION_COMMENT_DETAIL = 'comment_detail'
COLLECTION_ANALYZE_DETAIL = 'analyze_detail'


@good_bp.route('/<analyze_id>', methods=['GET'])
def get_goods(analyze_id):
    """
    根据分析id获取对应的策略
    :return:
    """
    try:
        data = {}
        result = []
        # goods = db_util.batch_select(COLLECTION_GOOD_DETAIL, {'brand': keyword})
        # _sum = db_util.count(COLLECTION_GOOD_DETAIL, {'brand': keyword})
        # for item in goods:
        #     item['_id'] = None
        #     result.append(item)
        # data['result'] = result
        return render_template('good/good.html', data=data)
    except Exception as e:
        print(f'获取商品列表失败: {e}')
        return render_template('error/500.html')


@good_bp.route('/', methods=['GET'])
def list_analyze():
    """
    分析列表
    :return:
    """
    try:
        result = db_util.batch_select(COLLECTION_ANALYZE_DETAIL)
        if result is None:
            result = []
        return render_template('good/good.html', data=result)
    except Exception as e:
        print(f'分析列表错误:{e}')
        return render_template('error/500.html')


@good_bp.route('/insert', methods=['GET'])
def create_good_html():
    """
    初始化分析
    :return:
    """

    try:
        return render_template('good/create_good.html')
    except Exception as e:
        print(f"初始化商品数据失败： {e}")
        return render_template('error/500.html')


@good_bp.route('/insert', methods=['POST'])
def create_analyze():
    """
    初始化分析
    @:param keyword_type 商品类别
    @:param keyword_brand 商品品牌
    :return:
    """
    try:
        req = request.form
        keyword_type = req.get('keyword_type')
        keyword_brand = req.get('keyword_brand')
        insert_data = {
            'primary_id': int(time.time() * 1000),
            'keyword_type': keyword_type,
            'keyword_brand': keyword_brand
        }

        _ids = db_util.update(COLLECTION_ANALYZE_DETAIL, {'keyword_type': insert_data['keyword_type'],
                                                          'keyword_brand': insert_data['keyword_brand']}, [insert_data])
        return render_template('good/create_good.html', data={'result': len(_ids)})
    except Exception as e:
        print(f"初始化商品数据失败： {e}")
        return render_template('error/500.html')


# @good_bp.route('/insert', methods=['GET'])
# def create_goods(keyword_type, keyword_brand):
#     """
#     初始化商品信息
#     :return:
#     """
#     goods = []
#     success_goods = []
#     try:
#         # 爬取京东商品信息
#         if keyword_brand == '' or keyword_brand is None or len(keyword_brand) == 0 or keyword_brand == 'null':
#             goods.extend(req_util.get_good_id_info(keyword_type=keyword_type))
#         else:
#             goods.extend(req_util.get_good_id_info(keyword_type=keyword_type, keyword_brand=keyword_brand))
#
#         success_goods.extend(db_util.update(COLLECTION_GOOD_DETAIL, goods))
#         return render_template('good/create_good.html', data={'goods': len(goods), 'success_goods': len(success_goods)})
#     except Exception as e:
#         print(f"初始化商品数据失败： {e}")
#         return render_template('error/500.html')
