/*
 Navicat Premium Data Transfer

 Source Server         : 121.41.169.208
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 121.41.169.208:3306
 Source Schema         : o2o

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 31/03/2022 21:38:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_area
-- ----------------------------
DROP TABLE IF EXISTS `tb_area`;
CREATE TABLE `tb_area`  (
  `area_id` int NOT NULL AUTO_INCREMENT,
  `area_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `priority` int NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `last_edit_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`area_id`) USING BTREE,
  INDEX `UK_AREA`(`area_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_area
-- ----------------------------
INSERT INTO `tb_area` VALUES (1, '西区', 1, NULL, NULL);
INSERT INTO `tb_area` VALUES (2, '东区', 2, NULL, NULL);

-- ----------------------------
-- Table structure for tb_change
-- ----------------------------
DROP TABLE IF EXISTS `tb_change`;
CREATE TABLE `tb_change`  (
  `excel_id` int NOT NULL,
  `change_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `change_way` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`excel_id`) USING BTREE,
  CONSTRAINT `fk_excel_change` FOREIGN KEY (`excel_id`) REFERENCES `tb_excel` (`excel_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_change
-- ----------------------------

-- ----------------------------
-- Table structure for tb_classes_grade
-- ----------------------------
DROP TABLE IF EXISTS `tb_classes_grade`;
CREATE TABLE `tb_classes_grade`  (
  `excel_id` int NULL DEFAULT NULL,
  `classes_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avg_class_grade` double NULL DEFAULT 0,
  `avg_work_grade` double NULL DEFAULT 0,
  `avg_last_grade` double NULL DEFAULT 0,
  `avg_all_grade` double NULL DEFAULT 0,
  `avg_exp_grade` double NULL DEFAULT 0,
  `student_count` int NULL DEFAULT 0,
  `user_id` int NULL DEFAULT NULL,
  `grade_id` int NOT NULL AUTO_INCREMENT,
  `class_point` double NULL DEFAULT NULL,
  `work_point` double NULL DEFAULT NULL,
  `last_point` double NULL DEFAULT NULL,
  `exp_point` double NULL DEFAULT NULL,
  `all_point` double NULL DEFAULT NULL,
  PRIMARY KEY (`grade_id`) USING BTREE,
  INDEX `excel_class_id`(`excel_id`) USING BTREE,
  CONSTRAINT `excel_class_id` FOREIGN KEY (`excel_id`) REFERENCES `tb_excel` (`excel_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_classes_grade
-- ----------------------------
INSERT INTO `tb_classes_grade` VALUES (20, NULL, 63.09090909090909, 56.09090909090909, 57.22727272727273, 10.454545454545455, 275.09090909090907, 11, 152200, 3, 3.1545454545454548, 5.609090909090909, 1.1445454545454545, 13.754545454545454, 0.10454545454545455);

-- ----------------------------
-- Table structure for tb_course
-- ----------------------------
DROP TABLE IF EXISTS `tb_course`;
CREATE TABLE `tb_course`  (
  `course_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `course_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `course_grade` double NOT NULL,
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_course
-- ----------------------------
INSERT INTO `tb_course` VALUES ('xxx', 'x22', 2);

-- ----------------------------
-- Table structure for tb_excel
-- ----------------------------
DROP TABLE IF EXISTS `tb_excel`;
CREATE TABLE `tb_excel`  (
  `excel_id` int NOT NULL AUTO_INCREMENT,
  `major` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `classes_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `term` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `course` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `owner_id` int NULL DEFAULT NULL,
  `area_id` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `last_edit_time` datetime NULL DEFAULT NULL,
  `excel_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `class_grade` int NULL DEFAULT NULL,
  PRIMARY KEY (`excel_id`) USING BTREE,
  INDEX `fk_grade_profile`(`owner_id`) USING BTREE,
  CONSTRAINT `fk_grade_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_excel
-- ----------------------------
INSERT INTO `tb_excel` VALUES (20, '软件工程', '软件15-1~9', '2017-2018学年第二学期', 'www', 152200, 2, '2021-05-15 10:18:18', '2021-05-15 20:49:32', 'NoSql数据库技术(双语)', 2);
INSERT INTO `tb_excel` VALUES (28, '计算机', '软件工程', 'test4', 'ccc', 152200, NULL, '2021-05-15 10:18:18', '2022-03-30 17:55:53', '软件工程', 2);
INSERT INTO `tb_excel` VALUES (41, '计算机', '一班', '第一学期', '006', 152202, NULL, '2022-03-29 11:06:37', '2022-03-29 11:07:11', '成绩表1', 5);

-- ----------------------------
-- Table structure for tb_grade_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_grade_category`;
CREATE TABLE `tb_grade_category`  (
  `grade_category_id` int NOT NULL AUTO_INCREMENT,
  `grade_category_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `grade_category_desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `grade_category_img` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `priority` int NOT NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  `last_edit_time` datetime NULL DEFAULT NULL,
  `parent_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`grade_category_id`) USING BTREE,
  INDEX `fk_shopp_category_self`(`parent_id`) USING BTREE,
  CONSTRAINT `fk_shopp_category_self` FOREIGN KEY (`parent_id`) REFERENCES `tb_grade_category` (`grade_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_grade_category
-- ----------------------------

-- ----------------------------
-- Table structure for tb_grade_subject
-- ----------------------------
DROP TABLE IF EXISTS `tb_grade_subject`;
CREATE TABLE `tb_grade_subject`  (
  `subject_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `subject_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `subject_score` int NULL DEFAULT NULL,
  `subject_id` int NOT NULL AUTO_INCREMENT,
  `excel_id` int NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`subject_id`) USING BTREE,
  INDEX `tb_subject_excel`(`excel_id`) USING BTREE,
  CONSTRAINT `tb_subject_excel` FOREIGN KEY (`excel_id`) REFERENCES `tb_excel` (`excel_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_grade_subject
-- ----------------------------
INSERT INTO `tb_grade_subject` VALUES ('简答题', 'z', 20, 5, 20, 152200);
INSERT INTO `tb_grade_subject` VALUES ('选择题', '选择题', 2, 8, 20, 152200);
INSERT INTO `tb_grade_subject` VALUES ('多选题', '多选题', 20, 9, 20, 152200);
INSERT INTO `tb_grade_subject` VALUES ('计算题', '计算题', 12, 13, 20, 152200);
INSERT INTO `tb_grade_subject` VALUES ('考核一', '题目一', 100, 15, 41, 152202);

-- ----------------------------
-- Table structure for tb_local_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_local_auth`;
CREATE TABLE `tb_local_auth`  (
  `local_auth_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `last_edit_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`local_auth_id`) USING BTREE,
  INDEX `uk_local_profile`(`user_name`) USING BTREE,
  INDEX `fk_local_profile`(`user_id`) USING BTREE,
  CONSTRAINT `fk_local_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_local_auth
-- ----------------------------

-- ----------------------------
-- Table structure for tb_performance_analysis
-- ----------------------------
DROP TABLE IF EXISTS `tb_performance_analysis`;
CREATE TABLE `tb_performance_analysis`  (
  `analysis_id` int NOT NULL AUTO_INCREMENT,
  `assessment_analysis` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `term_analysis` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `course_analysis` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `graduation_analysis` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `edcation_analysis` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `excel_id` int NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`analysis_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_performance_analysis
-- ----------------------------
INSERT INTO `tb_performance_analysis` VALUES (4, '在授课过程中，学生能够按时上课，部分学生能自主学习课前预习课后练习。在课堂上，学生认真听讲，并记录课堂笔记，同时，也能够跟随课程进度积极思考问题，回答随堂提问。对于课后作业，部分学生能够保质保量完成，作业抄袭现象较多。在实验过程中，部分学生能够结合课堂所学知识动手实践，遇到问题能够积极与老师沟通。', '期末考试卷面简答题和程序设计题组成，全面考察学生对NoSQL数据库基本概念、核心思想、关键开发技术的掌握情况。从试卷的完成情况分析，大部分学生基本掌握了NoSQL数据库的基础概念和核心思想以及基本应用，从程序设计题的答题情况看，部分学生的综合应用能力还有所欠缺。', '从整体上看，课程目标达成度为0.692，基本完成了课程学习任务。具体的说：课程目标1的达成度为0.691，反应出学生能够在课堂上认真听讲，积极完成作业，认真完成实验，从实验考核环节看，部分学生的实验完成不理想，这也造成了期末考试中程序设计题答题情况不好的原因；课程目标2的达成度为0.693，反应出学生基本具备使用NoSQL数据库开发应用系统的能力，但综合应用能力还有欠缺。', '毕业要求指标13.1的达成度为0.138，反应出学生基本具备应用JSP在Web应用领域从事复杂软件系统工程实践的能力；毕业要求指标13.2的达成度为0.208，反应基本具备应用NoSQL数据库在大数据分析领域从事复杂软件系统分析、设计、验证、实现、测试与维护的能力，但综合应用能力还有待加强。', '1.加强本课程与前驱课程和后继课程的衔接。\r\n2.加强学生实际动手能力的培养，增加学生实战的学习时间\r\n3.采取一定的方式方法，激励学生投入更多的时间和精力练习编程技能\r\n4.进一步加强学生综合运用专业知识解决大数据分析领域数据存储问题的实践能力。', 20, 152200);
INSERT INTO `tb_performance_analysis` VALUES (7, 'asd', 'asd', 'asd', 'asd', 'ads', 41, 0);
INSERT INTO `tb_performance_analysis` VALUES (8, 'asd', 'asd', 'asd', 'asd', 'asd', 41, 152202);

-- ----------------------------
-- Table structure for tb_person_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_person_info`;
CREATE TABLE `tb_person_info`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `teacher_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `enabke_status` int NOT NULL DEFAULT 0 COMMENT '0禁止使用本程序1允许使用本程序',
  `user_type` int NOT NULL DEFAULT 1 COMMENT '1代表老师2代表管理员',
  `create_time` datetime NULL DEFAULT NULL,
  `last_edit_time` datetime NULL DEFAULT NULL,
  `user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 152207 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_person_info
-- ----------------------------
INSERT INTO `tb_person_info` VALUES (152200, 'xxx', '管理员', 'aaa', '女', 1, 2, NULL, NULL, 'xxx');
INSERT INTO `tb_person_info` VALUES (152202, 'ccc', 'ccc', NULL, '女', 1, 1, '2021-05-23 20:43:29', '2021-05-23 20:43:29', 'ccc');
INSERT INTO `tb_person_info` VALUES (152203, 'czx', 'ccc', NULL, '女', 1, 1, '2021-05-23 20:43:56', '2021-05-23 20:43:56', 'ccc');

-- ----------------------------
-- Table structure for tb_reach_point
-- ----------------------------
DROP TABLE IF EXISTS `tb_reach_point`;
CREATE TABLE `tb_reach_point`  (
  `excel_id` int NOT NULL,
  `point_id_one` double NOT NULL,
  `point_id_two` double NULL DEFAULT NULL,
  `reach_score_one` double NULL DEFAULT NULL,
  `reach_score_two` double NULL DEFAULT NULL,
  `finall_reach_score_one` double NULL DEFAULT NULL,
  `finall_reach_score_two` double NULL DEFAULT NULL,
  `point_desc_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `point_desc_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `teach_target_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `teach_target_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reach_way_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reach_way_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `evaluation_basis_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `evaluation_basis_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reach_point_id` int NOT NULL AUTO_INCREMENT,
  `work_grade` int NULL DEFAULT 0,
  `class_grade` int NULL DEFAULT 0,
  `exp_grade` int NULL DEFAULT 0,
  `last_grade` int NULL DEFAULT 0,
  `all_grade_one` int NULL DEFAULT NULL,
  `all_grade_two` int NULL DEFAULT 0,
  `user_id` int NULL DEFAULT NULL,
  `point_graduation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reach_record` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reach_change` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reach_resources` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`reach_point_id`) USING BTREE,
  INDEX `tb_reach_point`(`excel_id`) USING BTREE,
  CONSTRAINT `tb_reach_point` FOREIGN KEY (`excel_id`) REFERENCES `tb_excel` (`excel_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_reach_point
-- ----------------------------
INSERT INTO `tb_reach_point` VALUES (20, 13.3, 13.2, 2, 2, 4.075, 0.86, '评价1', '评价2', '目标1', '目标2', '方式1', '方式2', '评价1', '评价2', 8, 10, 20, 20, 50, 50, 50, 152200, 'zxc', 'xxx', 'xxx', 'xxx');
INSERT INTO `tb_reach_point` VALUES (41, 1, 0, 5, 0, 0, 0, '描述', '', '123', '', '123', '', '12', '', 11, NULL, NULL, NULL, NULL, NULL, NULL, 152202, '毕业要求', '', '', '');

-- ----------------------------
-- Table structure for tb_reachdesc
-- ----------------------------
DROP TABLE IF EXISTS `tb_reachdesc`;
CREATE TABLE `tb_reachdesc`  (
  `word_id` int NOT NULL AUTO_INCREMENT,
  `reach_record` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reach_change` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reach_resources` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `excel_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`word_id`) USING BTREE,
  INDEX `tb_reachdesc_excel`(`excel_id`) USING BTREE,
  CONSTRAINT `tb_reachdesc_excel` FOREIGN KEY (`excel_id`) REFERENCES `tb_excel` (`excel_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_reachdesc
-- ----------------------------

-- ----------------------------
-- Table structure for tb_student_grade
-- ----------------------------
DROP TABLE IF EXISTS `tb_student_grade`;
CREATE TABLE `tb_student_grade`  (
  `student_id` int NULL DEFAULT NULL,
  `student_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `class_grade` double NULL DEFAULT NULL,
  `work_grade` double NULL DEFAULT NULL,
  `last_grade` double NULL DEFAULT NULL,
  `all_grade` double NULL DEFAULT NULL,
  `excel_id` int NULL DEFAULT NULL,
  `exp_grade` double NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `exe_student_id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`exe_student_id`) USING BTREE,
  INDEX `fk_student_excel`(`excel_id`) USING BTREE,
  CONSTRAINT `fk_student_excel` FOREIGN KEY (`excel_id`) REFERENCES `tb_excel` (`excel_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_student_grade
-- ----------------------------
INSERT INTO `tb_student_grade` VALUES (1714010114, 'AAA', 6, 13, 27.5, 61, 20, 15, 152200, 1);
INSERT INTO `tb_student_grade` VALUES (1714010701, '曹俊', 20, 20, 20, 80, 20, 20, 152200, 8);
INSERT INTO `tb_student_grade` VALUES (171401716, 'zcxz', 12, 13, 30, 67, 20, 12, 152200, 43);
INSERT INTO `tb_student_grade` VALUES (55556, 'eeee', 8, 16, 49, 88, 20, 15, 0, 44);
INSERT INTO `tb_student_grade` VALUES (1714010103, 'BBB', 8, 16, 49, 88, 20, 15, 0, 45);
INSERT INTO `tb_student_grade` VALUES (1714010114, 'hanlinqi', 100, 100, 100, 400, 20, 100, 0, 47);
INSERT INTO `tb_student_grade` VALUES (1714010114, 'hanlinqi', 100, 100, 100, 400, 20, 100, 0, 48);
INSERT INTO `tb_student_grade` VALUES (1714010114, 'hanlinqi', 100, 100, 100, 400, 20, 100, 0, 49);
INSERT INTO `tb_student_grade` VALUES (1714010114, 'hanlinqi', 100, 100, 100, 400, 20, 100, 152200, 50);
INSERT INTO `tb_student_grade` VALUES (1714010114, 'hanlinqi', 100, 100, 100, 400, 20, 100, 152200, 53);

-- ----------------------------
-- Table structure for tb_wechat_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_wechat_auth`;
CREATE TABLE `tb_wechat_auth`  (
  `wechat_auth_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `open_id` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`wechat_auth_id`) USING BTREE,
  INDEX `fk_wechatauth_profile`(`user_id`) USING BTREE,
  CONSTRAINT `fk_wechatauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_wechat_auth
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
