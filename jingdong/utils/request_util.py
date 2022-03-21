from lxml import etree
import requests
import json
import random
from setting import Config
import time


class RequestUtil(object):

    def __init__(self, cookie):
        self.cookie = cookie
        self.user_agent = [
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0",
            "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; The World)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Avant Browser)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)",
        ]
        self.headers = {
            'cookie': self.cookie,
            'user-agent': random.choice(self.user_agent)
        }

    def get_good_id_info(self, keyword_type, keyword_brand=None, primary_id=None, length=7):
        goods = []

        for page in range(length):
            time.sleep(random.randint(0, 3))
            try:
                # 页码， 每一页有30条数据
                if keyword_brand is not None:
                    url = f"https://search.jd.com/search?keyword={keyword_type}&wq={keyword_type}" \
                          f"&ev=exbrand_{keyword_brand}%5E&psort=3&click=0&page={page}&s=0"
                else:
                    url = f"https://search.jd.com/search?keyword={keyword_type}&wq={keyword_type}&psort=3&click=0&page={page}&s=0"

                # 设置编码
                res = requests.get(url, headers=self.headers)
                res.encoding = 'utf-8'
                text = res.text

                # 解析文档数据
                selector = etree.HTML(text)
                good_list = selector.xpath('//*[@id="J_goodsList"]/ul/li')

                for i in good_list:
                    time.sleep(random.random())
                    title = i.xpath('.//div[@class="p-name p-name-type-2"]/a/em/text()')[0]
                    price = i.xpath('.//div[@class="p-price"]/strong/i/text()')[0]
                    product_id = i.xpath('.//div[@class="p-commit"]/strong/a/@id')[0].replace("J_comment_", "")
                    good = self.get_good_detail(product_id)
                    good['product_id'] = product_id
                    good['primary_id'] = primary_id
                    # good['shop_link'] = shop_link
                    print(good)
                    goods.append(good)

            except Exception as e:
                print(f'爬取信息列表失败 {e}')
        return goods

    def get_good_detail(self, product_id):
        product = {}
        try:
            url = f'https://item.jd.com/{product_id}.html'
            r = requests.get(url, headers=self.headers)
            # str转HTML
            detail = etree.HTML(r.text)
            # 基础信息
            product['link'] = url

            product_intro = []
            products = detail.xpath('//*[@id="detail"]/div[2]/div[1]/div[1]/ul[3]/li')
            if len(products) == 0:
                products = detail.xpath('//*[@id="detail"]/div[2]/div[1]/div[1]/ul[2]/li')
                if len(products) == 0:
                    product['intro'] = '暂无描述'
                else:
                    for i in products:
                        product_intro.extend(i.xpath('text()'))
                    product['intro'] = '  '.join(product_intro)
            else:
                for i in products:
                    product_intro.extend(i.xpath('text()'))
                product['intro'] = '  '.join(product_intro)
            # 解析店铺介绍长段文字
            if '店铺：' in product['intro']:
                product['intro'] = product['intro'].replace('店铺：', '')
            product['intro'] = product['intro'].replace('    ', '</br>')
            product['intro'] = product['intro'].replace('  ', '</br>')

            product['img'] = (detail.xpath('//*[@id="spec-img"]')[0]).xpath('@data-origin')[0]
            shop = detail.xpath('//*[@id="crumb-wrap"]/div/div[2]/div[2]/div[1]/div/a/text()')[0]
            shop_link = detail.xpath('//*[@id="crumb-wrap"]/div/div[2]/div[2]/div[1]/div/a/@href')[0]
            product['shop'] = shop
            product['shop_link'] = shop_link

            # 请求价格信息json
            p = requests.get(f'https://p.3.cn/prices/mgets?skuIds=J_{product_id}', headers=self.headers)  # 请求商品价格json
            [product_dict] = json.loads(p.text)  # 获取商品价格
            # 最高价
            product['max_price'] = product_dict['m']
            # 当前价格
            product['price'] = product_dict['p']
            # 指导价
            product['guide_price'] = product_dict['op']

            product['comment'] = {}
            # 请求评论信息json
            c = requests.get('https://club.jd.com/comment/productCommentSummaries.action?referenceIds=' + product_id,
                             headers=self.headers)  # 请求评论json
            comment_dict = json.loads(c.text.split('[')[-1].split(']')[0])  # json内容截取
            product['comment']['total_comment_num'] = self.parse_str(comment_dict['CommentCountStr'],
                                                                     comment_dict['CommentCount'])
            product['comment']['average_score'] = comment_dict['AverageScore']
            product['comment']['good_count'] = comment_dict['GoodCountStr']
            product['comment']['good_rate'] = comment_dict['GoodRate']
            product['comment']['poor_count'] = comment_dict['PoorCountStr']
            product['comment']['poor_rate'] = comment_dict['PoorRate']

        except Exception as e:
            print(f'爬取商品详细信息失败 {e}')
        finally:
            return product

    def parse_str(self, string, count):
        if count == 0:
            # 对“+”进行操作
            if "+" in string:
                string = string.replace("+", "")
            # 对“万”进行操作
            if "万" in string:
                string = string.replace("万", "")
                string = str(int(float(string) * 10000))
            return string
        return count

    def get_comment_detail(self, product_id):
        comments = []

        for page in range(1, 100):
            try:
                url = f'https://club.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98&productId={product_id}&score=0&sortType=5&page={page}&pageSize=10&isShadowSku=0&rid=0&fold=1'
                url = f'https://club.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98&productId={product_id}&score=0&sortType=5&page={page}&pageSize=10&isShadowSku=1&fold=1'
                # url = f'https://club.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98&productId=100022634064&score=0&sortType=5&page=1&pageSize=10&isShadowSku=0&rid=0&fold=1'
                # 我们可以简单的解析这个网址，前面不动，后面的我们点击下一页，看会出现什么改变
                # https://club.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98&productId=5225346&score=0&sortType=5&page=1&pageSize=10&isShadowSku=0&rid=0&fold=1
                # 我们发现只有page在变化，根据这个我们可以进行翻页爬取，我们先进行第一页的操作

                # 先向浏览器发送请求
                response = requests.get(url, headers=self.headers)
                data = response.text
                print(data)
                if len(data) == 0:
                    continue
                # 由于爬取下来的data太大，就不展示了
                jd = json.loads(data.lstrip('fetchJSON_comment98vv12345(').rstrip(');'))
                data_list = jd['comments']
                for data in data_list:
                    single_comment = {
                        'user_id': data['id'],  # 评论买家id
                        'content': data['content'],  # 评论内容
                        'creation_time': data['creationTime'],  # 评论时间
                        'product_id': product_id
                    }
                    print(single_comment)
                    comments.append(single_comment)
            except Exception as e:
                print(f'爬取评论数据失败：{e}')
                continue
        return comments

    def get_popu_comment(self, product_id):
        url = f'https://club.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98&productId={product_id}&score=0&sortType=5&page=1&pageSize=10&isShadowSku=1&fold=1'
        response = requests.get(url, headers=self.headers)
        data = response.text
        # 由于爬取下来的data太大，就不展示了
        jd = json.loads(data.lstrip('fetchJSON_comment98(').rstrip(');'))
        # print(jd)
        return jd


req_util = RequestUtil(Config.COOKIE)

if __name__ == '__main__':
    cookies = '__jdu=1643247599232628855176; unpl=JF8EAKJnNSttWUMEVhwHTkETSgoBW1QOHh9QamNRVllfSlIEHQYZQEB7XlVdXhRKFB9vYhRUVFNKXQ4aCysSEXtdVV9dDU0eBGdhNWRtW0tkBCsCHBcSSFhSWlUASBUAaGQMXVlcS1ICKzIcEhl7bWRbXQlKEgJuZgJVbVl7VgQaBxoQE0leU24WZkpaA2hiB1dYXk9cDRgAGBUTQlRQWl0OTCcCX2Q; shshshfpa=e0c95879-2eac-e731-3455-eaf55f8a926b-1644833296; shshshfpb=wyiQo_rMB6KHslZZp5Xc0hw; __jdv=76161171|cn.bing.com|-|referral|-|1646381753155; areaId=15; PCSYCityID=CN_330000_330100_0; __jdc=122270672; token=a9cee6b365fe7ac70bea2e0dc3ab641f,2,914900; __tk=yih3xVTKHDv4HMTXICKtwRTKNJb3IbrQyJbRxVTKqfKtKKPXyLhJOEbaIgOswJXQIBh4wRrR,2,914900; __jda=122270672.1643247599232628855176.1643247599.1646817523.1646821046.6; ip_cityCode=1213; ipLoc-djd=15-1213-3038-59931; rkv=1.0; qrsc=3; wlfstk_smdl=34agfo701uvy51o2outof3hm7rs4m44q; __jdb=122270672.20.1643247599232628855176|6.1646821046; shshshfp=7bee43b6a0785c9839fd5a4559bb817a; shshshsID=6fbbddbf12516a00c2814713396794d1_13_1646821948572; 3AB9D23F7A4B3C9B=S6QBSPSF2KAATCSW4P657PXVSZLDAWIP2EO3Q2YT5XUFVD6PCIE6ZTAXMACRHWHUAIEXO6ZPSJJBI7RZ3HD3SAU4UQ'
    #
    req = RequestUtil(cookies)
    comment = req.get_popu_comment('100018640796')
    for item in comment:
        print(item)
#     req.get_good_id_info(keyword_type='电脑')
