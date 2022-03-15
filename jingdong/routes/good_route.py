from flask import render_template, request

from flask import Blueprint
from utils.db_util import db_util
from utils.request_util import req_util
import time

good_bp = Blueprint('good', __name__, url_prefix='/good')

COLLECTION_GOOD_DETAIL = 'good_detail'
COLLECTION_COMMENT_DETAIL = 'comment_detail'
COLLECTION_ANALYZE_DETAIL = 'analyze_detail'


@good_bp.route('/analyze/<analyze_id>', methods=['GET'])
def get_goods(analyze_id):
    """
    根据分析id获取对应的策略
    :return:
    """
    try:
        result = []

        nums = db_util.count(COLLECTION_GOOD_DETAIL, condition={'primary_id': int(analyze_id)})
        if nums < 5:
            analyze = db_util.select(COLLECTION_ANALYZE_DETAIL, {'primary_id': int(analyze_id)})
            new_goods = request_goods(keyword_type=analyze['keyword_type'], keyword_brand=analyze['keyword_brand'],
                                      primary_id=int(analyze_id))
            db_util.update(COLLECTION_GOOD_DETAIL, {}, new_goods)
        goods = db_util.batch_select(COLLECTION_GOOD_DETAIL, condition={'primary_id': int(analyze_id)})
        for item in goods:
            item['_id'] = None
            result.append(item)
        return render_template('good/good_list.html', data={'result': result})
    except Exception as e:
        return render_template('error/500.html', data=f'获取商品列表失败:{e}')


def request_goods(keyword_type, keyword_brand, primary_id):
    """
    初始化商品信息
    :return:
    """
    goods = []
    # 爬取京东商品信息
    goods.extend(req_util.get_good_id_info(keyword_type=keyword_type, keyword_brand=keyword_brand, primary_id=primary_id))
    if len(goods) == 0:
        raise Exception('爬取基础数据失败')
    return goods


@good_bp.route('/analyze', methods=['GET'])
def list_analyze():
    """
    分析列表
    :return:
    """
    try:
        result = []
        analyze = db_util.batch_select(COLLECTION_ANALYZE_DETAIL)
        for item in analyze:
            item['_id'] = None
            result.append(item)
        return render_template('good/analyze_list.html', data={'result': result})
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
        return render_template('good/create_good.html', data={'result': -1})
    except Exception as e:
        print(f"插入分析数据失败： {e}")
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


@good_bp.route('/comment/<primary_id>', methods=['GET'])
def comment_detail(primary_id):
    """
    数据可视化
    :param primary_id:
    :return:
    """
