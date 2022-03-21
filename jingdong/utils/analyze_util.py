from wordcloud import WordCloud
import matplotlib.pyplot as plt
import base64
from io import BytesIO
from utils.request_util import req_util

def analyze_other(product_id):
    chiyun = []
    items = req_util.get_popu_comment(product_id)
    hotComment = items['hotCommentTagStatistics']
    summaryComment = items['productCommentSummary']
    print("{1:<8}{2:<5}".format('序号', '词语', '频率'))

    if len(hotComment) <= 3:
        # 保存为.html
        html = '<h3 style="text-align: center">评论数据过少，无法生成词云图</h3>'
        return html

    # 需要显示的范围  10即显示前10个，0到9
    for item in hotComment:
        word, count = item['name'], item['count']
        print("{1:<8}{2:>5}".format(word, word, count))
        chiyun.append(word)

    # join 函数 用斜杆拼接词组
    text_cut = '/'.join(chiyun)
    wordcloud = WordCloud(background_color='white', font_path='msyh.ttc', width=1000, height=860,
                          margin=2).generate(text_cut)
    # 显示图片
    plt.imshow(wordcloud)
    plt.axis('off')
    # plt.show()

    # 转base64
    figfile = BytesIO()
    plt.savefig(figfile, format='png')
    figfile.seek(0)
    figdata_png = base64.b64encode(figfile.getvalue())  # 将图片转为base64
    figdata_str = str(figdata_png, "utf-8")  # 提取base64的字符串，不然是b'xxx'

    # 保存为.html
    html = '<img src=\"data:image/png;base64,{}\"/>'.format(figdata_str)
    return {
        'summaryComment': summaryComment,
        'html': html
    }


if __name__ == '__main__':

    # analyze('100022634064')
    analyze_other('100022634064')