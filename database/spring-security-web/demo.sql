/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.101
Source Server Version : 50615
Source Host           : 192.168.1.101:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2017-09-01 10:49:08
*/

SET FOREIGN_KEY_CHECKS=0;

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
INSERT INTO `sys_param` VALUES ('2', 'test1', '0', 'test1', 'test1121', '2017-08-24 16:12:19', null, '1', '1');

-- ----------------------------
-- Table structure for sys_platform
-- ----------------------------
DROP TABLE IF EXISTS `sys_platform`;
CREATE TABLE `sys_platform` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL COMMENT '系统名称',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  `deleStatus` char(1) NOT NULL COMMENT '可见状态(0:不可见;1:可见)',
  `creatorId` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统平台表';

-- ----------------------------
-- Records of sys_platform
-- ----------------------------
INSERT INTO `sys_platform` VALUES ('1', '悦无限', '2013-01-08 10:12:39', '2013-01-08 10:12:42', '1', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `realName` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `userName` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  `deleStatus` char(1) NOT NULL COMMENT '可见状态(0:不可见;1:可见)',
  `creatorId` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `loginTime` datetime DEFAULT NULL COMMENT '登陆时间',
  `platformId` bigint(11) NOT NULL COMMENT '平台id',
  `roleCode` varchar(200) NOT NULL COMMENT '角色编码 xxxx(10001000)格式',
  PRIMARY KEY (`id`),
  KEY `creatorId` (`creatorId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '管理员', 'admin', '4297f44b13955235245b2497399d7a93', 'admin@yuewuxian.cn', '2013-01-08 00:00:00', '2013-01-18 00:00:00', '1', '0', '2017-09-01 10:39:14', '1', '1000');
INSERT INTO `sys_user` VALUES ('34', 'test', 'test', '4297f44b13955235245b2497399d7a93', 'test@test.com', '2017-09-01 10:34:43', '2017-09-01 10:34:43', '1', '1', '2017-09-01 10:35:00', '1', '10001000');

-- ----------------------------
-- Table structure for sys_user_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_module`;
CREATE TABLE `sys_user_module` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '模块名称',
  `securityName` varchar(50) NOT NULL COMMENT '安全名称 用于权限标签',
  `resourceValue` varchar(200) NOT NULL COMMENT 'url',
  `orderNum` int(11) NOT NULL COMMENT '排序',
  `parentId` bigint(11) NOT NULL DEFAULT '0' COMMENT '父模块id',
  `platformId` bigint(11) NOT NULL COMMENT '平台id',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  `deleStatus` char(1) NOT NULL COMMENT '可见状态(0:不可见;1:可见)',
  `creatorId` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  PRIMARY KEY (`id`),
  KEY `platform_id` (`platformId`) USING BTREE,
  CONSTRAINT `sys_user_module_ibfk_1` FOREIGN KEY (`platformId`) REFERENCES `sys_platform` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=utf8 COMMENT='模块表';

-- ----------------------------
-- Records of sys_user_module
-- ----------------------------
INSERT INTO `sys_user_module` VALUES ('1', '权限管理', 'PURVIEW', '', '1', '0', '1', '2013-01-09 16:23:57', '2013-01-09 16:24:02', '1', '1');
INSERT INTO `sys_user_module` VALUES ('2', '用户管理', 'PURVIEW_USER', '/admin/management/sysuser/list**', '1', '1', '1', '2013-01-09 16:24:00', '2013-01-09 16:24:04', '1', '1');
INSERT INTO `sys_user_module` VALUES ('7', '角色管理', 'PURVIEW_ROLE', '/admin/management/sysuserrole/list**', '2', '1', '1', '2013-01-15 16:38:50', '2013-01-15 16:38:54', '1', '1');
INSERT INTO `sys_user_module` VALUES ('8', '模块管理', 'PURVIEW_MODULE', '/admin/management/sysusermodule/list**', '3', '1', '1', '2013-01-21 11:15:33', '2013-01-21 11:15:35', '1', '1');
INSERT INTO `sys_user_module` VALUES ('400', '系统管理', 'SYSTEM', '', '100', '0', '1', '2016-02-02 10:31:08', '2016-02-02 10:31:10', '1', '1');
INSERT INTO `sys_user_module` VALUES ('499', '系统参数', 'SYSTEM_PARAM', '/admin/system/sysparam/**', '100', '400', '1', '2016-02-02 10:31:08', '2016-02-02 10:31:10', '1', '1');

-- ----------------------------
-- Table structure for sys_user_module_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_module_permission`;
CREATE TABLE `sys_user_module_permission` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '操作名称',
  `securityName` varchar(20) NOT NULL COMMENT '安全名称 用于权限标签',
  `resourceValue` varchar(200) DEFAULT NULL COMMENT '权限url',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  `deleStatus` char(1) NOT NULL COMMENT '可见状态(0:不可见;1:可见)',
  `creatorId` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `platformId` bigint(11) NOT NULL COMMENT '平台id',
  `moduleId` bigint(11) DEFAULT NULL COMMENT '模块id',
  PRIMARY KEY (`id`),
  KEY `module_id` (`moduleId`) USING BTREE,
  CONSTRAINT `sys_user_module_permission_ibfk_1` FOREIGN KEY (`moduleId`) REFERENCES `sys_user_module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='模块表于权限表 多对多关联表';

-- ----------------------------
-- Records of sys_user_module_permission
-- ----------------------------
INSERT INTO `sys_user_module_permission` VALUES ('1', '添加', 'ADD', '/admin/management/sysuser/add**', '2013-01-16 16:02:03', '2013-01-16 16:02:03', '1', '0', '1', '2');
INSERT INTO `sys_user_module_permission` VALUES ('2', '编辑', 'UPDATE', '/admin/management/sysuser/update**', '2013-01-08 11:33:06', '2013-01-08 11:33:09', '1', '0', '1', '2');
INSERT INTO `sys_user_module_permission` VALUES ('3', '删除', 'DEL', '/admin/management/sysuser/delete**', '2013-01-08 11:41:48', '2013-01-08 11:41:51', '1', '0', '1', '2');
INSERT INTO `sys_user_module_permission` VALUES ('4', '添加', 'ADD', '/admin/management/sysuserrole/add**', '2013-01-16 16:02:03', '2013-01-16 16:02:03', '1', '0', '1', '7');
INSERT INTO `sys_user_module_permission` VALUES ('5', '编辑', 'UPDATE', '/admin/management/sysuserrole/update**', '2013-01-16 16:01:40', '2013-01-16 16:01:42', '1', '0', '1', '7');
INSERT INTO `sys_user_module_permission` VALUES ('6', '删除', 'DEL', '/admin/management/sysuserrole/delete**', '2013-01-16 16:02:03', '2013-01-16 16:02:06', '1', '0', '1', '7');
INSERT INTO `sys_user_module_permission` VALUES ('7', '添加', 'ADD', '/admin/management/sysusermodule/add**', '2013-01-16 16:02:03', '2013-01-16 16:02:03', '1', '0', '1', '8');
INSERT INTO `sys_user_module_permission` VALUES ('8', '编辑', 'UPDATE', '/admin/management/sysusermodule/update**', '2013-01-16 16:01:40', '2013-01-16 16:01:42', '1', '0', '1', '8');
INSERT INTO `sys_user_module_permission` VALUES ('9', '删除', 'DEL', '/admin/management/sysusermodule/delete**', '2013-01-16 16:02:03', '2013-01-16 16:02:06', '1', '0', '1', '8');

-- ----------------------------
-- Table structure for sys_user_purview
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_purview`;
CREATE TABLE `sys_user_purview` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `purviewText` text COMMENT '权限',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`) USING BTREE,
  CONSTRAINT `sys_user_purview_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_purview
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '角色',
  `comment` varchar(50) DEFAULT NULL COMMENT '备注',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  `deleStatus` char(1) NOT NULL COMMENT '可见状态(0:不可见;1:可见)',
  `creatorId` bigint(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `platformId` bigint(11) NOT NULL COMMENT '平台id',
  `parentId` bigint(11) DEFAULT '0' COMMENT ' 父类id',
  `roleCode` varchar(200) NOT NULL COMMENT '角色编码 xxxx(10001000)格式',
  PRIMARY KEY (`id`),
  KEY `platform_id` (`platformId`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`platformId`) REFERENCES `sys_platform` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '系统管理员', '管理整个系统', '2017-09-01 10:28:30', '2017-09-01 10:28:30', '1', '1', '1', '0', '1000');
INSERT INTO `sys_user_role` VALUES ('108', '查看角色', '查看角色', '2017-09-01 10:34:52', '2017-09-01 10:34:53', '1', '1', '1', '1', '10001000');

-- ----------------------------
-- Table structure for sys_user_role_module_permission_r
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_module_permission_r`;
CREATE TABLE `sys_user_role_module_permission_r` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `modulePermissionId` bigint(11) NOT NULL COMMENT '模块权限表',
  `roleId` bigint(11) NOT NULL COMMENT '角色表',
  PRIMARY KEY (`id`),
  KEY `module_permission_id` (`modulePermissionId`) USING BTREE,
  KEY `role_id` (`roleId`) USING BTREE,
  CONSTRAINT `sys_user_role_module_permission_r_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `sys_user_role` (`id`),
  CONSTRAINT `sys_user_role_module_permission_r_ibfk_2` FOREIGN KEY (`modulePermissionId`) REFERENCES `sys_user_module_permission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of sys_user_role_module_permission_r
-- ----------------------------
INSERT INTO `sys_user_role_module_permission_r` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role_module_permission_r` VALUES ('2', '2', '1');
INSERT INTO `sys_user_role_module_permission_r` VALUES ('3', '3', '1');
INSERT INTO `sys_user_role_module_permission_r` VALUES ('4', '4', '1');
INSERT INTO `sys_user_role_module_permission_r` VALUES ('6', '6', '1');
INSERT INTO `sys_user_role_module_permission_r` VALUES ('7', '7', '1');
INSERT INTO `sys_user_role_module_permission_r` VALUES ('8', '8', '1');
INSERT INTO `sys_user_role_module_permission_r` VALUES ('10', '5', '1');
INSERT INTO `sys_user_role_module_permission_r` VALUES ('44', '9', '1');

-- ----------------------------
-- Table structure for sys_user_role_module_r
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_module_r`;
CREATE TABLE `sys_user_role_module_r` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(11) NOT NULL COMMENT '角色表',
  `moduleId` bigint(11) NOT NULL COMMENT '模块表id',
  PRIMARY KEY (`id`),
  KEY `role_id` (`roleId`) USING BTREE,
  KEY `module_id` (`moduleId`) USING BTREE,
  CONSTRAINT `sys_user_role_module_r_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `sys_user_role` (`id`),
  CONSTRAINT `sys_user_role_module_r_ibfk_2` FOREIGN KEY (`moduleId`) REFERENCES `sys_user_module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COMMENT='角色表于模块表多对多关系表';

-- ----------------------------
-- Records of sys_user_role_module_r
-- ----------------------------
INSERT INTO `sys_user_role_module_r` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role_module_r` VALUES ('2', '1', '2');
INSERT INTO `sys_user_role_module_r` VALUES ('5', '1', '7');
INSERT INTO `sys_user_role_module_r` VALUES ('6', '1', '8');
INSERT INTO `sys_user_role_module_r` VALUES ('9', '1', '400');
INSERT INTO `sys_user_role_module_r` VALUES ('10', '1', '499');
INSERT INTO `sys_user_role_module_r` VALUES ('55', '108', '8');
INSERT INTO `sys_user_role_module_r` VALUES ('56', '108', '1');
INSERT INTO `sys_user_role_module_r` VALUES ('57', '108', '2');
INSERT INTO `sys_user_role_module_r` VALUES ('58', '108', '7');

-- ----------------------------
-- Table structure for sys_user_user_role_r
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_user_role_r`;
CREATE TABLE `sys_user_user_role_r` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `userId` bigint(11) NOT NULL COMMENT '用户表id',
  `roleId` bigint(11) NOT NULL COMMENT '角色表id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`userId`) USING BTREE,
  KEY `role_id` (`roleId`) USING BTREE,
  CONSTRAINT `sys_user_user_role_r_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `sys_user_user_role_r_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `sys_user_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='用户表于角色表多对多关系表';

-- ----------------------------
-- Records of sys_user_user_role_r
-- ----------------------------
INSERT INTO `sys_user_user_role_r` VALUES ('1', '1', '1');
INSERT INTO `sys_user_user_role_r` VALUES ('28', '34', '108');
