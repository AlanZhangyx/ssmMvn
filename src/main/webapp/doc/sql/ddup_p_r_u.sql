/*
Navicat MySQL Data Transfer

Source Server         : localhost1
Source Server Version : 50168
Source Host           : localhost:3306
Source Database       : casual

Target Server Type    : MYSQL
Target Server Version : 50168
File Encoding         : 65001

Date: 2015-08-20 12:28:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_privilege
-- ----------------------------
DROP TABLE IF EXISTS `sys_privilege`;
CREATE TABLE `sys_privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '权限描述',
  `action_url` varchar(500) DEFAULT '' COMMENT '权限url',
  `parent_id` int(11) DEFAULT '0' COMMENT '父权限',
  `is_menu` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是菜单，1是，0否',
  `icon` varchar(500) DEFAULT '' COMMENT '权限的图标',
  `description` varchar(100) DEFAULT '' COMMENT '权限描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_privilege
-- ----------------------------
INSERT INTO `sys_privilege` VALUES ('1', '系统管理', '', '0', '', '/ssm/js/widget/zTree3.5/img/diy/1_close.png', '管理权限分配的权限', '2015-08-20 00:01:01', '2015-08-20 00:01:01');
INSERT INTO `sys_privilege` VALUES ('2', '用户管理', '/ssm/user/list', '1', '', '/ssm/js/widget/zTree3.5/img/diy/1_close.png', '用户列表', '2015-08-20 00:01:01', '2015-08-20 00:01:01');
INSERT INTO `sys_privilege` VALUES ('3', '角色管理', '/ssm/role/list', '1', '', '/ssm/js/widget/zTree3.5/img/diy/1_close.png', '角色列表', '2015-08-20 00:01:01', '2015-08-20 00:01:01');
INSERT INTO `sys_privilege` VALUES ('4', '权限管理', '/ssm/privilege/list', '1', '', '/ssm/js/widget/zTree3.5/img/diy/1_close.png', '权限列表', '2015-08-20 00:01:01', '2015-08-20 00:01:01');

INSERT INTO `sys_privilege` VALUES ('5', '查询', '/ssm/user/get', '2', '\0', '/ssm/js/widget/zTree3.5/img/diy/3.png', '用户_查询', '2015-08-20 00:01:01', '2015-08-20 00:01:01');
INSERT INTO `sys_privilege` VALUES ('6', '新增', '/ssm/user/add', '2', '\0', '/ssm/js/widget/zTree3.5/img/diy/3.png', '用户_新增', '2015-08-20 00:01:01', '2015-08-20 00:01:01');
INSERT INTO `sys_privilege` VALUES ('7', '删除', '/ssm/user/delete', '2', '\0', '/ssm/js/widget/zTree3.5/img/diy/3.png', '用户_删除', '2015-08-20 00:01:01', '2015-08-20 00:01:01');
INSERT INTO `sys_privilege` VALUES ('8', '修改', '/ssm/user/update', '2', '\0', '/ssm/js/widget/zTree3.5/img/diy/3.png', '用户_修改', '2015-08-20 00:01:01', '2015-08-20 00:01:01');

INSERT INTO `sys_privilege` VALUES ('9', '查询', '/ssm/role/get', '3', '\0', '/ssm/js/widget/zTree3.5/img/diy/3.png', '角色_查询', '2015-08-20 00:01:01', '2015-08-20 00:01:01');
INSERT INTO `sys_privilege` VALUES ('10', '新增', '/ssm/role/add', '3', '\0', '/ssm/js/widget/zTree3.5/img/diy/3.png', '角色_新增', '2015-08-20 00:01:01', '2015-08-20 00:01:01');
INSERT INTO `sys_privilege` VALUES ('11', '删除', '/ssm/role/delete', '3', '\0', '/ssm/js/widget/zTree3.5/img/diy/3.png', '角色_删除', '2015-08-20 00:01:01', '2015-08-20 00:01:01');
INSERT INTO `sys_privilege` VALUES ('12', '修改', '/ssm/role/update', '3', '\0', '/ssm/js/widget/zTree3.5/img/diy/3.png', '角色_修改', '2015-08-20 00:01:01', '2015-08-20 00:01:01');

INSERT INTO `sys_privilege` VALUES ('13', '查询', '/ssm/privilege/get', '4', '\0', '/ssm/js/widget/zTree3.5/img/diy/3.png', '权限_查询', '2015-08-20 00:01:01', '2015-08-20 00:01:01');
INSERT INTO `sys_privilege` VALUES ('14', '新增', '/ssm/privilege/add', '4', '\0', '/ssm/js/widget/zTree3.5/img/diy/3.png', '权限_新增', '2015-08-20 00:01:01', '2015-08-20 00:01:01');
INSERT INTO `sys_privilege` VALUES ('15', '删除', '/ssm/privilege/delete', '4', '\0', '/ssm/js/widget/zTree3.5/img/diy/3.png', '权限_删除', '2015-08-20 00:01:01', '2015-08-20 00:01:01');
INSERT INTO `sys_privilege` VALUES ('16', '修改', '/ssm/privilege/update', '4', '\0', '/ssm/js/widget/zTree3.5/img/diy/3.png', '权限_修改', '2015-08-20 00:01:01', '2015-08-20 00:01:01');


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '角色名',
  `description` varchar(100) DEFAULT '' COMMENT '角色描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '这是有最高权限的超级管理员', '2015-08-20 00:01:01', '2015-08-20 00:01:01');

-- ----------------------------
-- Table structure for sys_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_privilege`;
CREATE TABLE `sys_role_privilege` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `privilege_id` int(11) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`,`privilege_id`),
  KEY `fk_RP_PRIVILEGE` (`privilege_id`),
  CONSTRAINT `fk_RP_PRIVILEGE` FOREIGN KEY (`privilege_id`) REFERENCES `sys_privilege` (`id`),
  CONSTRAINT `fk_RP_ROLE` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_privilege
-- ----------------------------
INSERT INTO `sys_role_privilege` VALUES ('1', '1');
INSERT INTO `sys_role_privilege` VALUES ('1', '2');
INSERT INTO `sys_role_privilege` VALUES ('1', '3');
INSERT INTO `sys_role_privilege` VALUES ('1', '4');
INSERT INTO `sys_role_privilege` VALUES ('1', '5');
INSERT INTO `sys_role_privilege` VALUES ('1', '6');
INSERT INTO `sys_role_privilege` VALUES ('1', '7');
INSERT INTO `sys_role_privilege` VALUES ('1', '8');
INSERT INTO `sys_role_privilege` VALUES ('1', '9');
INSERT INTO `sys_role_privilege` VALUES ('1', '10');
INSERT INTO `sys_role_privilege` VALUES ('1', '11');
INSERT INTO `sys_role_privilege` VALUES ('1', '12');
INSERT INTO `sys_role_privilege` VALUES ('1', '13');
INSERT INTO `sys_role_privilege` VALUES ('1', '14');
INSERT INTO `sys_role_privilege` VALUES ('1', '15');
INSERT INTO `sys_role_privilege` VALUES ('1', '16');
INSERT INTO `sys_role_privilege` VALUES ('1', '17');
INSERT INTO `sys_role_privilege` VALUES ('1', '18');
INSERT INTO `sys_role_privilege` VALUES ('1', '19');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(50) NOT NULL COMMENT '用户名，登陆用',
  `real_name` varchar(50) DEFAULT '' COMMENT '真实名字',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `mobile_no` char(11) DEFAULT '',
  `email` varchar(100) DEFAULT '',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `login_count` bigint(20) NOT NULL COMMENT '登陆次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '张三', 'admin', '13113112312', '12345@qq.com', '2015-08-20 00:01:01', '2015-08-20 00:01:01', '1');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_ROLE` (`role_id`),
  CONSTRAINT `fk_ROLE` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `fk_USER` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
