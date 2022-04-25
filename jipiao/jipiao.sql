/*
 Navicat Premium Data Transfer

 Source Server         : 121.41.169.208
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 121.41.169.208:3306
 Source Schema         : jipiao

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 25/04/2022 09:06:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_account
-- ----------------------------
DROP TABLE IF EXISTS `tb_account`;
CREATE TABLE `tb_account`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `phone` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号码',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `role` char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限',
  `create_time` char(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`, `phone`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_account
-- ----------------------------
INSERT INTO `tb_account` VALUES (1, 'The', '17364520479', '123456 ', 'user', '1620129877089');
INSERT INTO `tb_account` VALUES (2, 'One', '15829907001', '123456', 'admin', '1620129972325');
INSERT INTO `tb_account` VALUES (3, 'the', '1234567891', '1', 'user', '1620130072838');

-- ----------------------------
-- Table structure for tb_flight
-- ----------------------------
DROP TABLE IF EXISTS `tb_flight`;
CREATE TABLE `tb_flight`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '航班序号',
  `flight_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机型',
  `flight_company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '航空公司',
  `flight_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '航班号',
  `sit_number` int NULL DEFAULT NULL COMMENT '舱位数量',
  `from_pos` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '始发地',
  `to_pos` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的地',
  `start_time` datetime NULL DEFAULT NULL COMMENT '始发时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '到达时间',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_flight
-- ----------------------------
INSERT INTO `tb_flight` VALUES (1, '空客320（中）', '四川航空', '3U4332', 186, '银川市', '杭州市', '2022-04-23 07:40:00', '2022-04-23 12:10:00', 860.00);
INSERT INTO `tb_flight` VALUES (2, '空客320（中）', '四川航空', '3U4352', 186, '银川市', '杭州市', '2022-04-23 13:15:00', '2022-04-23 14:15:00', 860.00);
INSERT INTO `tb_flight` VALUES (3, '空客320（中）', '海南航空', 'CZ4612', 186, '银川市', '杭州市', '2022-04-23 19:10:00', '2022-04-23 23:45:00', 620.00);
INSERT INTO `tb_flight` VALUES (4, '空客320（中）', '海南航空', 'CZ4794', 186, '银川市', '杭州市', '2022-04-23 07:40:00', '2022-04-23 12:10:00', 860.00);
INSERT INTO `tb_flight` VALUES (5, '空客320（中）', '海南航空', 'CZ4823', 186, '银川市', '杭州市', '2022-04-23 13:15:00', '2022-04-23 14:15:00', 860.00);
INSERT INTO `tb_flight` VALUES (6, '空客320（中）', '华夏航空', 'G58582', 186, '银川市', '杭州市', '2022-04-23 07:40:00', '2022-04-23 12:10:00', 760.00);
INSERT INTO `tb_flight` VALUES (7, '空客320（中）', '华夏航空', 'G58588', 186, '银川市', '杭州市', '2022-04-23 13:15:00', '2022-04-23 14:15:00', 760.00);
INSERT INTO `tb_flight` VALUES (8, '空客320（中）', '长龙航空', 'GJ3612', 186, '银川市', '杭州市', '2022-04-23 19:10:00', '2022-04-23 23:45:00', 870.00);
INSERT INTO `tb_flight` VALUES (9, '空客320（中）', '长龙航空', 'GJ8618', 186, '银川市', '杭州市', '2022-04-23 07:40:00', '2022-04-23 12:10:00', 600.00);
INSERT INTO `tb_flight` VALUES (10, '空客320（中）', '长龙航空', 'GJ8668', 186, '银川市', '杭州市', '2022-04-23 13:15:00', '2022-04-23 14:15:00', 755.00);
INSERT INTO `tb_flight` VALUES (11, '空客320（中）', '厦门航空', 'MF5815', 186, '银川市', '杭州市', '2022-04-23 07:40:00', '2022-04-23 12:10:00', 950.00);
INSERT INTO `tb_flight` VALUES (12, '空客320（中）', '厦门航空', 'MF5847', 186, '银川市', '杭州市', '2022-04-23 13:15:00', '2022-04-23 14:15:00', 950.00);
INSERT INTO `tb_flight` VALUES (13, '空客320（中）', '厦门航空', 'MF8866', 186, '银川市', '杭州市', '2022-04-23 19:10:00', '2022-04-23 23:45:00', 520.00);
INSERT INTO `tb_flight` VALUES (14, '空客320（中）', '西藏航空', 'TV7016', 186, '银川市', '杭州市', '2022-04-23 07:40:00', '2022-04-23 12:10:00', 950.00);
INSERT INTO `tb_flight` VALUES (15, '空客320（中）', '西藏航空', 'TV7048', 186, '银川市', '杭州市', '2022-04-23 13:15:00', '2022-04-23 14:15:00', 950.00);

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '留言编号',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留言内容',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `account_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `status` smallint NULL DEFAULT NULL COMMENT '处理状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_message
-- ----------------------------
INSERT INTO `tb_message` VALUES (1, '我要留言', '17364520479', '1', '2022-04-24 10:28:49', 0);

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order`  (
  `id` int NOT NULL COMMENT '编号',
  `money` float NOT NULL COMMENT '支付金额',
  `pay_time` datetime NOT NULL COMMENT '支付时间',
  `pay_end_time` datetime NOT NULL COMMENT '支付截止时间',
  `pay_ready` smallint NOT NULL COMMENT '是否支付(0-未支付、1-已支付、2-已过期、3-已退票)',
  `flight_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '航班编号',
  `account_id` int NOT NULL COMMENT '账号编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES (1650848622, 860, '2022-04-25 09:03:42', '2022-04-27 09:03:42', 1, '1', 1);
INSERT INTO `tb_order` VALUES (1650848717, 600, '2022-04-25 09:05:17', '2022-04-27 09:05:17', 0, '9', 1);

-- ----------------------------
-- Table structure for tb_passenger
-- ----------------------------
DROP TABLE IF EXISTS `tb_passenger`;
CREATE TABLE `tb_passenger`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '乘客编号',
  `id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证号',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `flight_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '航班id',
  `account_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_passenger
-- ----------------------------
INSERT INTO `tb_passenger` VALUES (1, '21341421234231421', '5000', 'linqi han', 'GJ3612', '1');

SET FOREIGN_KEY_CHECKS = 1;
