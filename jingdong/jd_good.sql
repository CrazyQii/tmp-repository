/*
 Navicat Premium Data Transfer

 Source Server         : mongodb
 Source Server Type    : MongoDB
 Source Server Version : 50006
 Source Host           : 121.41.169.208:27017
 Source Schema         : jd_good

 Target Server Type    : MongoDB
 Target Server Version : 50006
 File Encoding         : 65001

 Date: 18/03/2022 11:39:24
*/


// ----------------------------
// Collection structure for analyze_detail
// ----------------------------
db.getCollection("analyze_detail").drop();
db.createCollection("analyze_detail");

// ----------------------------
// Documents of analyze_detail
// ----------------------------
db.getCollection("analyze_detail").insert([ {
    _id: ObjectId("6233fa2d1d045480d598d054"),
    "keyword_brand": "迪奥",
    "keyword_type": "口红",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:05.613Z")
} ]);
db.getCollection("analyze_detail").insert([ {
    _id: ObjectId("6233fe851d045480d598d392"),
    "keyword_brand": "华为（HUAWEI）",
    "keyword_type": "手机",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:37:37.296Z")
} ]);

// ----------------------------
// Collection structure for good_detail
// ----------------------------
db.getCollection("good_detail").drop();
db.createCollection("good_detail");

// ----------------------------
// Documents of good_detail
// ----------------------------
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d94188"),
    link: "https://item.jd.com/10040027526625.html",
    intro: "商品名称：【专柜保障】迪奥口红唇膏套装礼盒限量雪花包包蓝金999情人节七夕礼物圣诞节礼物圣诞礼物圣诞节礼物 红管10支(选色) 十支蕾丝礼盒  商品编号：10040027526625  店铺：   商品毛重：1.0kg  功效：滋润，保湿，易上色  色系：红色系  妆效：丝绒",
    "max_price": "15774.00",
    price: "5258.00",
    "guide_price": "5258.00",
    comment: {
        "total_comment_num": "0",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10040027526625",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d94189"),
    link: "https://item.jd.com/10040027526626.html",
    intro: "商品名称：【专柜保障】迪奥口红唇膏套装礼盒限量雪花包包蓝金999情人节七夕礼物圣诞节礼物圣诞礼物圣诞节礼物 蓝金10支(选色) 十支蕾丝礼盒  商品编号：10040027526626  店铺：   商品毛重：1.0kg  功效：滋润，保湿，易上色  色系：红色系  妆效：丝绒",
    "max_price": "15774.00",
    price: "5258.00",
    "guide_price": "5258.00",
    comment: {
        "total_comment_num": "0",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10040027526626",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d9418a"),
    link: "https://item.jd.com/10040027526618.html",
    intro: "商品名称：【专柜保障】迪奥口红唇膏套装礼盒限量雪花包包蓝金999情人节七夕礼物圣诞节礼物圣诞礼物圣诞节礼物 2支红管+2支小羊皮2支方管 +2支蓝金2  商品编号：10040027526618  店铺：   商品毛重：1.0kg  功效：滋润，保湿，易上色  色系：红色系  妆效：丝绒",
    "max_price": "15774.00",
    price: "5258.00",
    "guide_price": "5258.00",
    comment: {
        "total_comment_num": "0",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10040027526618",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d9418b"),
    link: "https://item.jd.com/10040027496287.html",
    intro: "商品名称：【专柜保障】迪奥丝绒999限量蓝金888口红皮包套装礼盒772 840 520滋润760圣诞节礼物 星空皮包限量版套装  商品编号：10040027496287  店铺：   商品毛重：1.0kg  功效：滋润  色系：棕色系  妆效：丝绒",
    "max_price": "22524.00",
    price: "2938.00",
    "guide_price": "2938.00",
    comment: {
        "total_comment_num": "1",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10040027496287",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d9418c"),
    link: "https://item.jd.com/10040027599469.html",
    intro: "商品名称：【专柜保障】迪奥玫瑰花礼盒口红丝绒999哑光正红720烂番茄唇膏888滋润520圣诞节礼物圣诞礼物 520#【老款】滋润烂漫玫红色 +礼袋+礼盒  商品编号：10040027599469  店铺：   商品毛重：1.0kg  功效：滋润，保湿，易上色  色系：红色系  妆效：丝绒",
    "max_price": "3144.00",
    price: "488.00",
    "guide_price": "488.00",
    comment: {
        "total_comment_num": "11",
        "average_score": NumberInt("5"),
        "good_count": "1",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10040027599469",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d9418d"),
    link: "https://item.jd.com/10040821784542.html",
    intro: "商品名称：迪奥烈艳蓝金锁色唇釉999 626轻盈持妆 口红 741 Forever Star 迪奥星光 枫糖棕 基本款  商品编号：10040821784542  店铺：   商品毛重：1.0kg  货号：3HC7W3Jf  功效：修护  色系：黑色系  妆效：闪光/珠光",
    "max_price": "693.00",
    price: "679.14",
    "guide_price": "693.00",
    comment: {
        "total_comment_num": "1",
        "average_score": NumberInt("5"),
        "good_count": "1",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10040821784542",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d9418e"),
    link: "https://item.jd.com/10040822407827.html",
    intro: "商品名称：迪奥魅惑釉唇膏沙丘限量版黑管漆光口红748 879 879  Nomad Red 丝路  商品编号：10040822407827  店铺：   商品毛重：1.0kg  货号：aR234dfy  功效：易上色  色系：裸色/豆沙色系  妆效：润泽/镜面",
    "max_price": "693.00",
    price: "679.14",
    "guide_price": "693.00",
    comment: {
        "total_comment_num": "1",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10040822407827",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d9418f"),
    link: "https://item.jd.com/10040027494067.html",
    intro: "商品名称：【专柜保障】迪奥口红999 丝绒720哑光888 滋润520740礼盒装641圣诞节礼物三八妇女节 436#红管 稀有枫叶红  商品编号：10040027494067  店铺：   商品毛重：1.0kg  功效：滋润  色系：棕色系  妆效：丝绒",
    "max_price": "2424.00",
    price: "708.00",
    "guide_price": "708.00",
    comment: {
        "total_comment_num": "2",
        "average_score": NumberInt("2"),
        "good_count": "0",
        "good_rate": 0,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10040027494067",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d94190"),
    link: "https://item.jd.com/10040027599466.html",
    intro: "商品名称：【专柜保障】迪奥玫瑰花礼盒口红丝绒999哑光正红720烂番茄唇膏888滋润520圣诞节礼物圣诞礼物 080#【新款】缎光微笑正红 +礼袋+礼盒  商品编号：10040027599466  店铺：   商品毛重：1.0kg  功效：滋润，保湿，易上色  色系：红色系  妆效：丝绒",
    "max_price": "3144.00",
    price: "488.00",
    "guide_price": "488.00",
    comment: {
        "total_comment_num": "11",
        "average_score": NumberInt("5"),
        "good_count": "1",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10040027599466",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d94191"),
    link: "https://item.jd.com/10040027599471.html",
    intro: "商品名称：【专柜保障】迪奥玫瑰花礼盒口红丝绒999哑光正红720烂番茄唇膏888滋润520圣诞节礼物圣诞礼物 666#【老款】复古番茄红 +礼袋+礼盒  商品编号：10040027599471  店铺：   商品毛重：1.0kg  功效：滋润，保湿，易上色  色系：红色系  妆效：丝绒",
    "max_price": "3144.00",
    price: "488.00",
    "guide_price": "488.00",
    comment: {
        "total_comment_num": "11",
        "average_score": NumberInt("5"),
        "good_count": "1",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10040027599471",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d94192"),
    link: "https://item.jd.com/10040027599467.html",
    intro: "商品名称：【专柜保障】迪奥玫瑰花礼盒口红丝绒999哑光正红720烂番茄唇膏888滋润520圣诞节礼物圣诞礼物 740#漆光烂番茄色 +礼袋+礼盒  商品编号：10040027599467  店铺：   商品毛重：1.0kg  功效：滋润，保湿，易上色  色系：红色系  妆效：丝绒",
    "max_price": "3144.00",
    price: "488.00",
    "guide_price": "488.00",
    comment: {
        "total_comment_num": "11",
        "average_score": NumberInt("5"),
        "good_count": "1",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10040027599467",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d94193"),
    link: "https://item.jd.com/10037140291496.html",
    intro: "商品名称：【国内专柜】迪奥Dior金艳幻彩唇膏 #360 #770  丝绒哑光口红节日生日礼物送女友 750时尚哑光  商品编号：10037140291496  店铺：   商品毛重：1.0kg  妆效：丝绒",
    "max_price": "1110.00",
    price: "828.00",
    "guide_price": "828.00",
    comment: {
        "total_comment_num": "0",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10037140291496",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d94194"),
    link: "https://item.jd.com/10037140291497.html",
    intro: "商品名称：【国内专柜】迪奥Dior金艳幻彩唇膏 #360 #770  丝绒哑光口红节日生日礼物送女友 770时尚哑光  商品编号：10037140291497  店铺：   商品毛重：1.0kg  妆效：丝绒",
    "max_price": "1110.00",
    price: "828.00",
    "guide_price": "828.00",
    comment: {
        "total_comment_num": "0",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10037140291497",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d94195"),
    link: "https://item.jd.com/10037140302590.html",
    intro: "商品名称：【国内专柜】迪奥Dior烈艳蓝金唇膏星光版668 999口红 新品节日生日礼物送女友 668 Glam 艳惊四座 惊艳红 星光限量版  商品编号：10037140302590  店铺：   商品毛重：1.0kg",
    "max_price": "1155.00",
    price: "858.00",
    "guide_price": "858.00",
    comment: {
        "total_comment_num": "0",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10037140302590",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d94196"),
    link: "https://item.jd.com/10037140302591.html",
    intro: "商品名称：【国内专柜】迪奥Dior烈艳蓝金唇膏星光版668 999口红 新品节日生日礼物送女友 999 传奇红唇 丝绒 常规版  商品编号：10037140302591  店铺：   商品毛重：1.0kg",
    "max_price": "1155.00",
    price: "788.00",
    "guide_price": "788.00",
    comment: {
        "total_comment_num": "0",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10037140302591",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d94197"),
    link: "https://item.jd.com/10037140302593.html",
    intro: "商品名称：【国内专柜】迪奥Dior烈艳蓝金唇膏星光版668 999口红 新品节日生日礼物送女友 720 偶像 豆沙红棕 常规版  商品编号：10037140302593  店铺：   商品毛重：1.0kg",
    "max_price": "1155.00",
    price: "788.00",
    "guide_price": "788.00",
    comment: {
        "total_comment_num": "0",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10037140302593",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d94198"),
    link: "https://item.jd.com/10037140302595.html",
    intro: "商品名称：【国内专柜】迪奥Dior烈艳蓝金唇膏星光版668 999口红 新品节日生日礼物送女友 888 先锋 橘调正红 常规版  商品编号：10037140302595  店铺：   商品毛重：1.0kg",
    "max_price": "1155.00",
    price: "788.00",
    "guide_price": "788.00",
    comment: {
        "total_comment_num": "0",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10037140302595",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d94199"),
    link: "https://item.jd.com/10037140316206.html",
    intro: "商品名称：【国内专柜】迪奥Dior烈艳蓝金润彩唇膏000 720 滋润口红持色节日生日礼物送女友 百搭透明色 丝绒  商品编号：10037140316206  店铺：   商品毛重：1.0kg  功效：滋润",
    "max_price": "1050.00",
    price: "788.00",
    "guide_price": "788.00",
    comment: {
        "total_comment_num": "2",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10037140316206",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d9419a"),
    link: "https://item.jd.com/10037140316207.html",
    intro: "商品名称：【国内专柜】迪奥Dior烈艳蓝金润彩唇膏000 720 滋润口红持色节日生日礼物送女友 100 裸色风尚 杏仁奶茶  商品编号：10037140316207  店铺：   商品毛重：1.0kg  功效：滋润",
    "max_price": "1050.00",
    price: "788.00",
    "guide_price": "788.00",
    comment: {
        "total_comment_num": "2",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10037140316207",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fa44ef4ba067d5d9419b"),
    link: "https://item.jd.com/10037140316209.html",
    intro: "商品名称：【国内专柜】迪奥Dior烈艳蓝金润彩唇膏000 720 滋润口红持色节日生日礼物送女友 846 协奏 番茄柿红  商品编号：10037140316209  店铺：   商品毛重：1.0kg  功效：滋润",
    "max_price": "1050.00",
    price: "788.00",
    "guide_price": "788.00",
    comment: {
        "total_comment_num": "2",
        "average_score": NumberInt("5"),
        "good_count": "0",
        "good_rate": 1,
        "poor_count": "0",
        "poor_rate": 0
    },
    "product_id": "10037140316209",
    "primary_id": NumberLong("1647573545613"),
    "update_time": ISODate("2022-03-18T03:19:32.299Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c45d"),
    link: "https://item.jd.com/100022634064.html",
    intro: "商品名称：华为Mate 40 Pro  商品编号：100022634064  商品毛重：0.6kg  商品产地：中国大陆  CPU型号：麒麟9000  运行内存：8GB  存储卡：NM存储卡  后摄主摄像素：5000万像素  前摄主摄像素：1300万像素  分辨率：其他  充电器：10V/4A；4.5V/5A；11V/6A；5V/2A；10V/2.25A；5V/4.5A；9V/2A  支持IPv6：支持IPv6  系统：HarmonyOS 2  机身内存：256GB",
    "max_price": "10000.00",
    price: "6099.00",
    "guide_price": "6599.00",
    comment: {
        "total_comment_num": "500000",
        "average_score": NumberInt("5"),
        "good_count": "14万+",
        "good_rate": 0.98,
        "poor_count": "1400+",
        "poor_rate": 0.012
    },
    "product_id": "100022634064",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c45e"),
    link: "https://item.jd.com/100016376331.html",
    intro: "商品名称：华为nova 8 SE  商品编号：100016376331  商品毛重：350.00g  商品产地：中国大陆  CPU型号：麒麟710A  运行内存：8GB  后摄主摄像素：6400万像素  前摄主摄像素：1600万像素  分辨率：其他  充电器：11V/6A  机身内存：128GB  系统：HarmonyOS 2",
    "max_price": "3000.00",
    price: "1849.00",
    "guide_price": "2099.00",
    comment: {
        "total_comment_num": "200000",
        "average_score": NumberInt("5"),
        "good_count": "2.8万+",
        "good_rate": 0.95,
        "poor_count": "800+",
        "poor_rate": 0.029
    },
    "product_id": "100016376331",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c45f"),
    link: "https://item.jd.com/100014453209.html",
    intro: "商品名称：华为P50  商品编号：100014453209  商品毛重：330.00g  商品产地：中国大陆  CPU型号：骁龙888 4G  运行内存：8GB  存储卡：NM存储卡  后摄主摄像素：5000万像素  前摄主摄像素：1300万像素  分辨率：全高清FHD+  充电器：其他  支持IPv6：支持IPv6  系统：HarmonyOS 2  机身内存：256GB",
    "max_price": "6000.00",
    price: "4888.00",
    "guide_price": "4988.00",
    comment: {
        "total_comment_num": "20000",
        "average_score": NumberInt("5"),
        "good_count": "8400+",
        "good_rate": 0.94,
        "poor_count": "200+",
        "poor_rate": 0.037
    },
    "product_id": "100014453209",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c460"),
    link: "https://item.jd.com/100016944073.html",
    intro: "商品名称：华为P50系列  商品编号：100016944073  商品毛重：340.00g  商品产地：中国大陆  CPU型号：骁龙888  运行内存：8GB  存储卡：NM存储卡  后摄主摄像素：5000万像素  前摄主摄像素：1300万像素  分辨率：全高清FHD+  充电器：其他  支持IPv6：支持IPv6  系统：HarmonyOS 2  机身内存：256GB",
    "max_price": "8000.00",
    price: "5988.00",
    "guide_price": "5988.00",
    comment: {
        "total_comment_num": "50000",
        "average_score": NumberInt("5"),
        "good_count": "1.4万+",
        "good_rate": 0.96,
        "poor_count": "200+",
        "poor_rate": 0.026
    },
    "product_id": "100016944073",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c461"),
    link: "https://item.jd.com/100029199476.html",
    intro: "商品名称：华为Mate X2  商品编号：100029199476  商品毛重：0.93kg  商品产地：中国大陆  CPU型号：麒麟9000  运行内存：12GB  存储卡：NM存储卡  后摄主摄像素：5000万像素  前摄主摄像素：1600万像素  分辨率：QXGA+  充电器：其他  支持IPv6：支持IPv6  系统：HarmonyOS 2  机身内存：512GB  特征特质：人脸识别，快速充电，NFC，高倍率变焦，5G，折叠屏，屏幕高刷新率",
    "max_price": "100000.00",
    price: "18999.00",
    "guide_price": "18999.00",
    comment: {
        "total_comment_num": "20000",
        "average_score": NumberInt("5"),
        "good_count": "1万+",
        "good_rate": 0.98,
        "poor_count": "70+",
        "poor_rate": 0.017
    },
    "product_id": "100029199476",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c462"),
    link: "https://item.jd.com/100030711496.html",
    intro: "商品名称：华为HUAWEI P50 Pocket  商品编号：100030711496  商品毛重：300.00g  商品产地：中国大陆  运行内存：8GB  后摄主摄像素：4000万像素  分辨率：全高清FHD+  充电器：其他  机身内存：256GB  系统：HarmonyOS 2",
    "max_price": "100000.00",
    price: "8988.00",
    "guide_price": "8988.00",
    comment: {
        "total_comment_num": "10000",
        "average_score": NumberInt("5"),
        "good_count": "4400+",
        "good_rate": 0.98,
        "poor_count": "40+",
        "poor_rate": 0.012
    },
    "product_id": "100030711496",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c463"),
    link: "https://item.jd.com/100026797032.html",
    intro: "商品名称：华为nova 9  商品编号：100026797032  商品毛重：310.00g  商品产地：中国大陆  CPU型号：骁龙778G 4G  运行内存：8GB  后摄主摄像素：5000万像素  前摄主摄像素：3200万像素  分辨率：其他  充电器：10V/4A；5V/2A；4.5V/5A；5V/4.5A；10V/2.25A；9V/2A；11V/6A  支持IPv6：支持IPv6  系统：HarmonyOS 2  机身内存：128GB  特征特质：人脸识别，快速充电，液冷散热，NFC，屏幕指纹，曲面屏，屏幕高刷新率",
    "max_price": "10000.00",
    price: "2619.00",
    "guide_price": "2699.00",
    comment: {
        "total_comment_num": "100000",
        "average_score": NumberInt("5"),
        "good_count": "2万+",
        "good_rate": 0.94,
        "poor_count": "700+",
        "poor_rate": 0.037
    },
    "product_id": "100026797032",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c464"),
    link: "https://item.jd.com/100010667255.html",
    intro: "商品名称：华为Mate 40 RS 保时捷设计  商品编号：100010667255  商品毛重：0.9kg  商品产地：中国大陆  CPU型号：其他  运行内存：8GB  存储卡：NM存储卡  后摄主摄像素：5000万像素  前摄主摄像素：1300万像素  分辨率：其他  充电器：10V/4A；4.5V/5A；11V/6A；5V/2A；10V/2.25A；5V/4.5A；9V/2A  支持IPv6：支持IPv6  系统：其他OS  机身内存：256GB  特征特质：人脸识别，无线充电，快速充电，防水防尘，NFC，屏幕指纹，高倍率变焦，曲面屏，3D(ToF或结构光)，屏幕高刷新率",
    "max_price": "100000.00",
    price: "10499.00",
    "guide_price": "10999.00",
    comment: {
        "total_comment_num": "50000",
        "average_score": NumberInt("5"),
        "good_count": "1.2万+",
        "good_rate": 0.98,
        "poor_count": "90+",
        "poor_rate": 0.015
    },
    "product_id": "100010667255",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c465"),
    link: "https://item.jd.com/100027873626.html",
    intro: "商品名称：华为畅享 20e  商品编号：100027873626  商品毛重：390.00g  商品产地：中国大陆  运行内存：4GB  分辨率：高清HD+  充电器：5V/2A  机身内存：128GB  系统：EMUI 10.1",
    "max_price": "3000.00",
    price: "1099.00",
    "guide_price": "1199.00",
    comment: {
        "total_comment_num": "100000",
        "average_score": NumberInt("5"),
        "good_count": "1.3万+",
        "good_rate": 0.95,
        "poor_count": "300+",
        "poor_rate": 0.03
    },
    "product_id": "100027873626",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c466"),
    link: "https://item.jd.com/100006976889.html",
    intro: "商品名称：华为nova 7 5G  商品编号：100006976889  商品毛重：0.515kg  商品产地：中国大陆  CPU型号：麒麟985  运行内存：8GB  存储卡：不支持存储卡  后摄主摄像素：6400万像素  前摄主摄像素：3200万像素  分辨率：全高清FHD+  充电器：10V/4A  系统：其他OS  特征特质：快速充电，NFC，屏幕指纹，5G  机身内存：128GB",
    "max_price": "10000.00",
    price: "2999.00",
    "guide_price": "2999.00",
    comment: {
        "total_comment_num": "500000",
        "average_score": NumberInt("5"),
        "good_count": "10万+",
        "good_rate": 0.97,
        "poor_count": "1600+",
        "poor_rate": 0.017
    },
    "product_id": "100006976889",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c467"),
    link: "https://item.jd.com/100014438267.html",
    intro: "商品名称：华为nova 9 Pro  商品编号：100014438267  商品毛重：0.55kg  商品产地：中国大陆  运行内存：8GB  后摄主摄像素：5000万像素  前摄主摄像素：3200万像素  分辨率：其他  充电器：10V/4A；20V/5A；5V/2A；4.5V/5A；5V/4.5A；10V/2.25A；9V/2A；11V/6A  支持IPv6：支持IPv6  机身内存：256GB  系统：HarmonyOS 2",
    "max_price": "10000.00",
    price: "3719.00",
    "guide_price": "3899.00",
    comment: {
        "total_comment_num": "50000",
        "average_score": NumberInt("5"),
        "good_count": "8500+",
        "good_rate": 0.95,
        "poor_count": "200+",
        "poor_rate": 0.023
    },
    "product_id": "100014438267",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c468"),
    link: "https://item.jd.com/10022926646994.html",
    intro: "商品名称：华为（HUAWEI）华为 Mate40 Pro  商品编号：10022926646994  商品毛重：300.00g  CPU型号：其他  运行内存：8GB  存储卡：NM存储卡  后摄主摄像素：5000万像素  前摄主摄像素：1300万像素  分辨率：其他  充电器：其他  系统：Android  机身内存：256GB",
    "max_price": "12999.00",
    price: "7199.00",
    "guide_price": "8799.00",
    comment: {
        "total_comment_num": "20000",
        "average_score": NumberInt("5"),
        "good_count": "7300+",
        "good_rate": 0.97,
        "poor_count": "100+",
        "poor_rate": 0.022
    },
    "product_id": "10022926646994",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c469"),
    link: "https://item.jd.com/10034910111253.html",
    intro: "商品名称：华为（HUAWEI）NZone s7pro 5G  商品编号：10034910111253  商品毛重：1.0kg  运行内存：8GB  分辨率：其他  充电器：其他  机身内存：128GB  系统：Android",
    "max_price": "9999.00",
    price: "1688.00",
    "guide_price": "2199.00",
    comment: {
        "total_comment_num": "10000",
        "average_score": NumberInt("5"),
        "good_count": "2200+",
        "good_rate": 0.95,
        "poor_count": "70+",
        "poor_rate": 0.036
    },
    "product_id": "10034910111253",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c46a"),
    link: "https://item.jd.com/10038395776366.html",
    intro: "商品名称：华为智选手机 优畅享20 5G手机 （碎屏险） 优畅享20【樱雪晴空】 6+128GB  商品编号：10038395776366  商品毛重：1.0kg  运行内存：6GB  分辨率：其他  充电器：5V/2A  机身内存：128GB  系统：Android",
    "max_price": "19999.00",
    price: "1298.00",
    "guide_price": "1699.00",
    comment: {
        "total_comment_num": "10000",
        "average_score": NumberInt("5"),
        "good_count": "2600+",
        "good_rate": 0.96,
        "poor_count": "50+",
        "poor_rate": 0.028
    },
    "product_id": "10038395776366",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c46b"),
    link: "https://item.jd.com/10023383467996.html",
    intro: "商品名称：华为（HUAWEI）华为mate40 pro 5G  商品编号：10023383467996  商品毛重：200.00g  CPU型号：其他  运行内存：8GB  存储卡：其他  后摄主摄像素：5000万像素  前摄主摄像素：1300万像素  分辨率：其他  充电器：10V/4A；4.5V/5A；11V/6A；5V/2A；10V/2.25A；5V/4.5A；9V/2A  系统：Android  机身内存：256GB",
    "max_price": "19999.00",
    price: "8999.00",
    "guide_price": "9999.00",
    comment: {
        "total_comment_num": "20000",
        "average_score": NumberInt("5"),
        "good_count": "5300+",
        "good_rate": 0.97,
        "poor_count": "70+",
        "poor_rate": 0.019
    },
    "product_id": "10023383467996",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c46c"),
    link: "https://item.jd.com/100029634648.html",
    intro: "商品名称：华为Mate 40 Pro  商品编号：100029634648  商品毛重：500.00g  商品产地：中国大陆  运行内存：8GB  分辨率：全高清FHD+  充电器：10V/4A；11V/6A；5V/2A  机身内存：256GB  系统：EMUI 11.0（基于Android 10）（可HOTA升级至HarmonyOS，具体升级时间以官方公布的HOTA升级计划为准）",
    "max_price": "7599.00",
    price: "7299.00",
    "guide_price": "7499.00",
    comment: {
        "total_comment_num": "1000",
        "average_score": NumberInt("5"),
        "good_count": "400+",
        "good_rate": 0.98,
        "poor_count": "2",
        "poor_rate": 0.004
    },
    "product_id": "100029634648",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c46d"),
    link: "https://item.jd.com/10036447307727.html",
    intro: "商品名称：华为nova9 新品手机【搭载HarmonyOS2系统】 亮黑色 全网通8+128GB （品牌66W充电器套装）  商品编号：10036447307727  商品毛重：1.0kg  运行内存：8GB  分辨率：全高清FHD+  充电器：其他  机身内存：128GB  系统：HarmonyOS 2",
    "max_price": "6999.00",
    price: "2669.00",
    "guide_price": "2999.00",
    comment: {
        "total_comment_num": "10000",
        "average_score": NumberInt("5"),
        "good_count": "1900+",
        "good_rate": 0.92,
        "poor_count": "100+",
        "poor_rate": 0.061
    },
    "product_id": "10036447307727",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c46e"),
    link: "https://item.jd.com/10042701683801.html",
    intro: "商品名称：华为智选手机 NZONE S7 5G手机 优雅黑 6+128GB  商品编号：10042701683801  商品毛重：1.0kg  CPU型号：天玑700  运行内存：6GB  分辨率：其他  充电器：其他  机身内存：128GB  系统：Android",
    "max_price": "1999.00",
    price: "1399.00",
    "guide_price": "1999.00",
    comment: {
        "total_comment_num": "5000",
        "average_score": NumberInt("5"),
        "good_count": "800+",
        "good_rate": 0.96,
        "poor_count": "10+",
        "poor_rate": 0.014
    },
    "product_id": "10042701683801",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c46f"),
    link: "https://item.jd.com/10023108638660.html",
    intro: "商品名称：华为（HUAWEI）Mate 40 Pro  商品编号：10023108638660  商品毛重：1.0kg  CPU型号：其他  运行内存：8GB  存储卡：其他  后摄主摄像素：其他  前摄主摄像素：其他  分辨率：其他  充电器：10V/4A；4.5V/5A；11V/6A；10V/2.25A；5V/4.5A；9V/2A  系统：Android  游戏独立按键：无游戏独立按键  机身内存：256GB",
    "max_price": "12999.00",
    price: "7199.00",
    "guide_price": "8999.00",
    comment: {
        "total_comment_num": "50000",
        "average_score": NumberInt("5"),
        "good_count": "1万+",
        "good_rate": 0.96,
        "poor_count": "200+",
        "poor_rate": 0.029
    },
    "product_id": "10023108638660",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
db.getCollection("good_detail").insert([ {
    _id: ObjectId("6233fe9dccc9644d72a4c470"),
    link: "https://item.jd.com/10045248697341.html",
    intro: "商品名称：华为（HUAWEI）mate40 e pro  商品编号：10045248697341  商品毛重：200.00g  运行内存：8GB  充电器：10V/4A；11V/6A；5V/2A；10V/2.25A；9V/2A；其他  机身内存：256GB  系统：HarmonyOS 2",
    "max_price": "7599.00",
    price: "6489.00",
    "guide_price": "7599.00",
    comment: {
        "total_comment_num": "93",
        "average_score": NumberInt("5"),
        "good_count": "40+",
        "good_rate": 0.97,
        "poor_count": "1",
        "poor_rate": 0.03
    },
    "product_id": "10045248697341",
    "primary_id": NumberLong("1647574657296"),
    "update_time": ISODate("2022-03-18T03:38:05.247Z")
} ]);
