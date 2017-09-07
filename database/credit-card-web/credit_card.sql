/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : credit_card

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-09-07 10:27:29
*/

CREATE DATABASE credit_card DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use credit_card;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for c_bank
-- ----------------------------
DROP TABLE IF EXISTS `c_bank`;
CREATE TABLE `c_bank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  `deleStatus` char(1) CHARACTER SET utf8 NOT NULL COMMENT '可见状态(0:不可见;1:可见)',
  `creatorId` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `creatorId` (`creatorId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='银行';

-- ----------------------------
-- Records of c_bank
-- ----------------------------
INSERT INTO `c_bank` VALUES ('1', '农业银行', '2017-09-04 17:31:51', '2017-09-04 17:31:51', '1', '1');
INSERT INTO `c_bank` VALUES ('2', '广发银行', '2017-09-04 17:31:59', '2017-09-04 17:31:59', '1', '1');
INSERT INTO `c_bank` VALUES ('3', '平安银行', '2017-09-04 17:32:05', '2017-09-04 17:32:05', '1', '1');
INSERT INTO `c_bank` VALUES ('4', '浦发银行', '2017-09-04 17:32:15', '2017-09-04 17:32:15', '1', '1');
INSERT INTO `c_bank` VALUES ('5', '光大银行', '2017-09-04 17:32:21', '2017-09-04 17:32:21', '1', '1');
INSERT INTO `c_bank` VALUES ('6', '工商银行', '2017-09-04 17:32:41', '2017-09-04 17:32:41', '1', '1');
INSERT INTO `c_bank` VALUES ('7', '民生银行', '2017-09-04 17:33:16', '2017-09-04 17:33:16', '1', '1');
INSERT INTO `c_bank` VALUES ('8', '招商银行', '2017-09-04 17:34:27', '2017-09-04 17:34:27', '1', '1');

-- ----------------------------
-- Table structure for c_card_info
-- ----------------------------
DROP TABLE IF EXISTS `c_card_info`;
CREATE TABLE `c_card_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bankId` bigint(20) NOT NULL COMMENT '银行id',
  `cardRangeId` bigint(20) DEFAULT NULL COMMENT '信用规则id',
  `jobDate` date DEFAULT NULL COMMENT '生成任务日期',
  `name` varchar(50) NOT NULL COMMENT '卡名',
  `money` int(11) DEFAULT NULL COMMENT '额度',
  `annualFeeType` int(11) DEFAULT '0' COMMENT '年费类型：1、强制收费，2、刷卡次数，3、免年费',
  `annualFee` int(11) DEFAULT '0' COMMENT '年费',
  `cardNum` int(11) DEFAULT '0' COMMENT '刷卡次数',
  `billDate` int(11) DEFAULT '1' COMMENT '账单日',
  `nextUp` datetime DEFAULT NULL COMMENT '下次提额时间',
  `remarks` longtext COMMENT '备注',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  `deleStatus` char(1) CHARACTER SET utf8 NOT NULL COMMENT '可见状态(0:不可见;1:可见)',
  `creatorId` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  PRIMARY KEY (`id`),
  KEY `bankId` (`bankId`),
  KEY `name` (`name`),
  KEY `creatorId` (`creatorId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='信用卡信息';

-- ----------------------------
-- Records of c_card_info
-- ----------------------------
INSERT INTO `c_card_info` VALUES ('1', '5', '1', '2017-09-18 00:00:00', '淘票票', '12000', '3', '0', '0', '18', null, '1', '2017-09-05 12:37:25', '2017-09-05 12:37:25', '1', '1');

-- ----------------------------
-- Table structure for c_card_job
-- ----------------------------
DROP TABLE IF EXISTS `c_card_job`;
CREATE TABLE `c_card_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bankId` bigint(20) NOT NULL COMMENT '银行id',
  `cardInfoId` bigint(20) NOT NULL COMMENT '信用卡id',
  `cardRangeId` bigint(20) NOT NULL COMMENT '信用卡限制id',
  `jobDate` datetime NOT NULL COMMENT '执行日期',
  `money` double(11,2) DEFAULT '0.00' COMMENT '金额',
  `fee` double(11,2) DEFAULT '0.60' COMMENT '手续费率',
  `feeValue` double(11,2) DEFAULT '0.60' COMMENT '手续费',
  `status` int(1) DEFAULT '0' COMMENT '刷卡状态：1、已刷卡',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `deleStatus` char(1) CHARACTER SET utf8 NOT NULL COMMENT '可见状态(0:不可见;1:可见)',
  `creatorId` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  PRIMARY KEY (`id`),
  KEY `bankId` (`bankId`),
  KEY `cardInfoId` (`cardInfoId`),
  KEY `cardRangeId` (`cardRangeId`),
  KEY `creatorId` (`creatorId`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COMMENT='信用卡刷卡任务';

-- ----------------------------
-- Records of c_card_job
-- ----------------------------
INSERT INTO `c_card_job` VALUES ('76', '5', '1', '1', '2017-09-19 00:00:00', '487.00', '0.60', '2.92', '1', '2017-09-05 18:08:00', '2017-09-06 14:22:12', '1', '1');
INSERT INTO `c_card_job` VALUES ('77', '5', '1', '1', '2017-09-20 00:00:00', '280.00', '0.60', '1.68', '0', '2017-09-05 18:08:00', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('78', '5', '1', '1', '2017-09-21 00:00:00', '33.00', '0.60', '0.20', '0', '2017-09-05 18:08:00', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('79', '5', '1', '1', '2017-09-24 00:00:00', '513.00', '0.60', '3.08', '0', '2017-09-05 18:08:00', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('80', '5', '1', '1', '2017-09-25 00:00:00', '151.00', '0.60', '0.91', '0', '2017-09-05 18:08:00', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('81', '5', '1', '1', '2017-09-26 00:00:00', '313.00', '0.60', '1.88', '0', '2017-09-05 18:08:00', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('82', '5', '1', '1', '2017-09-27 00:00:00', '262.00', '0.60', '1.57', '0', '2017-09-05 18:08:00', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('83', '5', '1', '1', '2017-09-28 00:00:00', '36.00', '0.60', '0.22', '0', '2017-09-05 18:08:00', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('84', '5', '1', '1', '2017-09-30 00:00:00', '87.00', '0.60', '0.52', '0', '2017-09-05 18:08:00', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('85', '5', '1', '1', '2017-10-01 00:00:00', '80.00', '0.60', '0.48', '0', '2017-09-05 18:08:01', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('86', '5', '1', '1', '2017-10-02 00:00:00', '351.00', '0.60', '2.11', '0', '2017-09-05 18:08:01', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('87', '5', '1', '1', '2017-10-04 00:00:00', '122.00', '0.60', '0.73', '0', '2017-09-05 18:08:01', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('88', '5', '1', '1', '2017-10-05 00:00:00', '143.00', '0.60', '0.86', '0', '2017-09-05 18:08:01', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('89', '5', '1', '1', '2017-10-06 00:00:00', '316.00', '0.60', '1.90', '0', '2017-09-05 18:08:01', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('90', '5', '1', '1', '2017-10-07 00:00:00', '347.00', '0.60', '2.08', '0', '2017-09-05 18:08:01', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('91', '5', '1', '1', '2017-10-08 00:00:00', '663.00', '0.60', '3.98', '0', '2017-09-05 18:08:01', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('92', '5', '1', '1', '2017-10-09 00:00:00', '596.00', '0.60', '3.58', '0', '2017-09-05 18:08:01', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('93', '5', '1', '1', '2017-10-10 00:00:00', '58.00', '0.60', '0.35', '0', '2017-09-05 18:08:01', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('94', '5', '1', '1', '2017-10-11 00:00:00', '497.00', '0.60', '2.98', '0', '2017-09-05 18:08:01', '2017-09-05 18:09:33', '1', '1');
INSERT INTO `c_card_job` VALUES ('95', '5', '1', '1', '2017-10-14 00:00:00', '404.00', '0.60', '2.42', '0', '2017-09-05 18:08:01', '2017-09-05 18:09:24', '1', '1');
INSERT INTO `c_card_job` VALUES ('96', '5', '1', '1', '2017-10-15 00:00:00', '21.00', '0.60', '0.13', '0', '2017-09-05 18:08:01', '2017-09-05 18:09:33', '1', '1');

-- ----------------------------
-- Table structure for c_card_range
-- ----------------------------
DROP TABLE IF EXISTS `c_card_range`;
CREATE TABLE `c_card_range` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `moneyPropStartValue` int(11) DEFAULT '40' COMMENT '刷卡比例开始',
  `moneyPropEndValue` int(11) DEFAULT '60' COMMENT '刷卡比例结束',
  `frequencyPropStartValue` int(11) DEFAULT '22' COMMENT '刷卡次数开始',
  `frequencyPropEndtValue` int(11) DEFAULT '35' COMMENT '刷卡次数结束',
  `day` int(11) DEFAULT '30' COMMENT '刷卡天数',
  `remarks` longtext COMMENT '备注',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  `deleStatus` char(1) CHARACTER SET utf8 NOT NULL COMMENT '可见状态(0:不可见;1:可见)',
  `creatorId` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  PRIMARY KEY (`id`),
  KEY `creatorId` (`creatorId`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='信用卡刷卡区间';

-- ----------------------------
-- Records of c_card_range
-- ----------------------------
INSERT INTO `c_card_range` VALUES ('1', '养卡', '20', '35', '20', '25', '30', '养卡规则', '2017-09-07 10:24:32', '2017-09-07 10:24:33', '1', '1');
INSERT INTO `c_card_range` VALUES ('2', '手头紧', '70', '80', '5', '8', '10', '手头紧', '2017-09-07 10:25:43', '2017-09-07 10:25:43', '1', '1');

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '参数名称',
  `type` int(11) NOT NULL COMMENT '参数类型',
  `k` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '参数key',
  `v` varchar(500) CHARACTER SET utf8 NOT NULL COMMENT '参数value',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `deleStatus` char(1) CHARACTER SET utf8 NOT NULL COMMENT '可见状态(0:不可见;1:可见)',
  `creatorId` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建者id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `k` (`k`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='系统参数表';

-- ----------------------------
-- Records of sys_param
-- ----------------------------
INSERT INTO `sys_param` VALUES ('1', 'test', '0', 'test', '参数编辑', '2017-08-24 16:14:25', '2017-08-24 16:14:25', '1', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `realName` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  `loginTime` datetime DEFAULT NULL COMMENT '登陆时间',
  `deleStatus` char(1) NOT NULL COMMENT '可见状态(0:不可见;1:可见)',
  `creatorId` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  PRIMARY KEY (`id`),
  KEY `creatorId` (`creatorId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '4297f44b13955235245b2497399d7a93', 'admin', 'admin@admin.com', '2017-08-30 17:40:01', '2017-08-30 17:40:03', '2017-08-30 17:40:06', '1', '1');
INSERT INTO `sys_user` VALUES ('2', 'test', '4297f44b13955235245b2497399d7a93', 'admin', 'test@test.com', '2017-08-30 18:25:31', '2017-08-30 18:25:32', '2017-08-30 18:25:31', '1', '1');
