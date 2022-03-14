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

    def get_good_id_info(self, keyword_type, keyword_brand=None):
        goods = []
        try:
            # 页码， 每一页有30条数据
            if keyword_brand is not None:
                url = f"https://search.jd.com/search?keyword={keyword_type}&wq={keyword_type}" \
                      f"&ev=exbrand_{keyword_brand}%5E&psort=3&click=0"
            else:
                url = f"https://search.jd.com/search?keyword={keyword_type}&wq={keyword_type}&psort=3&click=0"

            # 设置编码
            res = requests.get(url, headers=self.headers)
            res.encoding = 'utf-8'
            text = res.text

            # 解析文档数据
            selector = etree.HTML(text)
            good_list = selector.xpath('//*[@id="J_goodsList"]/ul/li')

            if len(good_list) > 10:
                good_list = good_list[:10]

            for i in good_list:
                title = i.xpath('.//div[@class="p-name p-name-type-2"]/a/em/text()')[0]
                price = i.xpath('.//div[@class="p-price"]/strong/i/text()')[0]
                product_id = i.xpath('.//div[@class="p-commit"]/strong/a/@id')[0].replace("J_comment_", "")
                # good = {
                #     'title': title,
                #     'price': price,
                #     'product_id': product_id
                # }
                good = self.get_good_detail(product_id)
                good['product_id'] = product_id
                print(good)
                goods.append(good)

        except Exception as e:
            print(f'爬取信息列表失败 {e}')
        finally:
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
            product_name = detail.xpath('//*[@id="detail"]/div[2]/div[1]/div[1]/ul[3]/li[1]/@title')  # 商品名称
            if len(product_name) == 0:
                product['name'] = None
            else:
                product['name'] = product_name[0]
            product_brand = detail.xpath('//*[@id="parameter-brand"]/li/a/text()')  # 商品品牌
            if len(product_brand) == 0:
                product['brand'] = None
            else:
                product['brand'] = product_brand[0]

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
            product['comment']['good_count'] = self.parse_str(comment_dict['GoodCountStr'], comment_dict['GoodCount'])
            product['comment']['good_rate'] = comment_dict['GoodRate']
            product['comment']['poor_count'] = self.parse_str(comment_dict['PoorCountStr'], comment_dict['PoorCount'])
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


req_util = RequestUtil(Config.COOKIE)

# if __name__ == '__main__':
#     cookies = '__jdu=1643247599232628855176; unpl=JF8EAKJnNSttWUMEVhwHTkETSgoBW1QOHh9QamNRVllfSlIEHQYZQEB7XlVdXhRKFB9vYhRUVFNKXQ4aCysSEXtdVV9dDU0eBGdhNWRtW0tkBCsCHBcSSFhSWlUASBUAaGQMXVlcS1ICKzIcEhl7bWRbXQlKEgJuZgJVbVl7VgQaBxoQE0leU24WZkpaA2hiB1dYXk9cDRgAGBUTQlRQWl0OTCcCX2Q; shshshfpa=e0c95879-2eac-e731-3455-eaf55f8a926b-1644833296; shshshfpb=wyiQo_rMB6KHslZZp5Xc0hw; __jdv=76161171|cn.bing.com|-|referral|-|1646381753155; areaId=15; PCSYCityID=CN_330000_330100_0; __jdc=122270672; token=a9cee6b365fe7ac70bea2e0dc3ab641f,2,914900; __tk=yih3xVTKHDv4HMTXICKtwRTKNJb3IbrQyJbRxVTKqfKtKKPXyLhJOEbaIgOswJXQIBh4wRrR,2,914900; __jda=122270672.1643247599232628855176.1643247599.1646817523.1646821046.6; ip_cityCode=1213; ipLoc-djd=15-1213-3038-59931; rkv=1.0; qrsc=3; wlfstk_smdl=34agfo701uvy51o2outof3hm7rs4m44q; __jdb=122270672.20.1643247599232628855176|6.1646821046; shshshfp=7bee43b6a0785c9839fd5a4559bb817a; shshshsID=6fbbddbf12516a00c2814713396794d1_13_1646821948572; 3AB9D23F7A4B3C9B=S6QBSPSF2KAATCSW4P657PXVSZLDAWIP2EO3Q2YT5XUFVD6PCIE6ZTAXMACRHWHUAIEXO6ZPSJJBI7RZ3HD3SAU4UQ'
#
#     req = RequestUtil(cookies)
#     req.get_good_id_info(keyword_type='电脑')
