from lxml import etree
import requests
import json
import time
import random


class RequestUtil(object):

    def __init__(self, cookie):
        self.cookie = cookie
        self.user_agent = user_agent = [
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

    def get_good_id_info(self, keyword_type, keyword_brand):
        goods = []
        try:
            # 页码， 每一页有30条数据
            page = 1
            url = f"https://search.jd.com/search?keyword={keyword_type}&wq={keyword_type}" \
                  f"&ev=exbrand_{keyword_brand}%5E&page={page}&s=1&click=1"

            # 设置编码
            res = requests.get(url, headers=self.headers)
            res.encoding = 'utf-8'
            text = res.text

            # 解析文档数据
            selector = etree.HTML(text)
            good_list = selector.xpath('//*[@id="J_goodsList"]/ul/li')

            for i in good_list:
                title = i.xpath('.//div[@class="p-name p-name-type-2"]/a/em/text()')[0]
                price = i.xpath('.//div[@class="p-price"]/strong/i/text()')[0]
                product_id = i.xpath('.//div[@class="p-commit"]/strong/a/@id')[0].replace("J_comment_", "")
                good = {
                    'title': title,
                    'price': price,
                    'product_id': product_id
                }
                goods.append(good)

        except Exception as e:
            print(f'爬取信息列表失败 {e}')
        finally:
            return goods

    def comment_count(self, product_id):
        comment_count = None
        try:
            url = f"https://club.jd.com/comment/productCommentSummaries.action?referenceIds={product_id}&callback=jQuery8827474&_={int(time.time() * 1000)}"
            res = requests.get(url, headers=self.headers)
            res.encoding = 'gbk'
            text = res.text.replace("jQuery8827474(", "").replace(");", "")
            text = json.loads(text)
            comment_count = text['CommentsCount'][0]['CommentCountStr']

            comment_count = comment_count.replace("+", "")
            # 对“万”进行操作
            if "万" in comment_count:
                comment_count = str(int(comment_count.replace("万", "")) * 10000)
        except Exception as e:
            print(f'爬取商品评论数量失败 {e}')
        finally:
            return comment_count

    def get_detail_data(self, product_id):
        try:
            url = f'https://item.jd.com/{product_id}.html'
            r = requests.get(url, headers=self.headers)
            # str转HTML
            detail = etree.HTML(r.text)

            [product_name] = detail.xpath('//*[@id="detail"]/div[2]/div[1]/div[1]/ul[2]/li[1]/@title')  # 商品名称
            print("商品：" + str(product_name))

            [product_brand] = detail.xpath('//*[@id="detail"]/div[2]/div[1]/div[1]/ul[1]/li[1]/a/text()')  # 商品品牌
            print("品牌：" + str(product_brand))

            # 请求价格信息json
            p = requests.get(f'https://p.3.cn/prices/mgets?skuIds=J_{product_id}', headers=header)  # 请求商品价格json
            [product_dict] = json.loads(p.text)  # 获取商品价格
            product_m_price = product_dict['m']
            product_price = product_dict['p']
            product_o_price = product_dict['op']

            # 请求评论信息json
            c = requests.get('https://club.jd.com/comment/productCommentSummaries.action?referenceIds=' + product_id,
                             headers=header)  # 请求评论json
            comment_dict = json.loads(c.text.split('[')[-1].split(']')[0])  # json内容截取

            total_comment_num = comment_dict['CommentCount']
            good_comment_num = comment_dict['ShowCount']
            good_percent_com = comment_dict['GoodRate']
            bad_comment_num = comment_dict['PoorCount']
            bad_percent_com = comment_dict['PoorRate']

        except Exception as e:
            print(f'爬取商品详细信息失败 {e}')



if __name__ == '__main__':
    cookies = '__jdu=1643247599232628855176; unpl=JF8EAKJnNSttWUMEVhwHTkETSgoBW1QOHh9QamNRVllfSlIEHQYZQEB7XlVdXhRKFB9vYhRUVFNKXQ4aCysSEXtdVV9dDU0eBGdhNWRtW0tkBCsCHBcSSFhSWlUASBUAaGQMXVlcS1ICKzIcEhl7bWRbXQlKEgJuZgJVbVl7VgQaBxoQE0leU24WZkpaA2hiB1dYXk9cDRgAGBUTQlRQWl0OTCcCX2Q; shshshfpa=e0c95879-2eac-e731-3455-eaf55f8a926b-1644833296; shshshfpb=wyiQo_rMB6KHslZZp5Xc0hw; __jdv=76161171|cn.bing.com|-|referral|-|1646381753155; areaId=15; PCSYCityID=CN_330000_330100_0; __jdc=122270672; token=a9cee6b365fe7ac70bea2e0dc3ab641f,2,914900; __tk=yih3xVTKHDv4HMTXICKtwRTKNJb3IbrQyJbRxVTKqfKtKKPXyLhJOEbaIgOswJXQIBh4wRrR,2,914900; __jda=122270672.1643247599232628855176.1643247599.1646817523.1646821046.6; ip_cityCode=1213; ipLoc-djd=15-1213-3038-59931; rkv=1.0; qrsc=3; wlfstk_smdl=34agfo701uvy51o2outof3hm7rs4m44q; __jdb=122270672.20.1643247599232628855176|6.1646821046; shshshfp=7bee43b6a0785c9839fd5a4559bb817a; shshshsID=6fbbddbf12516a00c2814713396794d1_13_1646821948572; 3AB9D23F7A4B3C9B=S6QBSPSF2KAATCSW4P657PXVSZLDAWIP2EO3Q2YT5XUFVD6PCIE6ZTAXMACRHWHUAIEXO6ZPSJJBI7RZ3HD3SAU4UQ'

    req = RequestUtil(cookies)
    req.get_good_id_info(keyword_type='笔记本', keyword_brand='联想')
