/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.101
Source Server Version : 50615
Source Host           : 192.168.1.101:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2017-08-31 10:52:25
*/

SET FOREIGN_KEY_CHECKS=0;

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
