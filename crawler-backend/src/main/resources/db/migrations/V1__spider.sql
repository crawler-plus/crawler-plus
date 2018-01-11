/*
Navicat MySQL Data Transfer

Source Server         : maria
Source Server Version : 50505
Source Host           : localhost:3307
Source Database       : spider

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-01-11 15:12:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bond_market`
-- ----------------------------
DROP TABLE IF EXISTS `bond_market`;
CREATE TABLE `bond_market` (
  `id` varchar(11) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键id',
  `code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '证券代码',
  `abbre` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '证券简称',
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公告标题',
  `category` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '类别',
  `publish_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公告时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `adjunct_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'pdf下载url字符串',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of bond_market
-- ----------------------------

-- ----------------------------
-- Table structure for `crawler_content`
-- ----------------------------
DROP TABLE IF EXISTS `crawler_content`;
CREATE TABLE `crawler_content` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content_body` longtext COLLATE utf8_unicode_ci DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '关联的用户id',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=936 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of crawler_content
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_lock`
-- ----------------------------
DROP TABLE IF EXISTS `sys_lock`;
CREATE TABLE `sys_lock` (
  `system_cron` char(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '系统是否正在运行爬取任务（0：否，1：是）',
  `business_cron` char(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户是否正在运行爬取任务（0：否，1：是）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_lock
-- ----------------------------
INSERT INTO `sys_lock` VALUES ('0', '0');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '菜单名称',
  `menu_parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID',
  `sort_order` int(11) DEFAULT NULL COMMENT '排序',
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'url',
  `group_id` int(11) DEFAULT NULL COMMENT '组id',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '0', '1', '#', '1');
INSERT INTO `sys_menu` VALUES ('2', '网站监控', '1', '2', 'http://127.0.0.1:8088/druid/index.html', '1');
INSERT INTO `sys_menu` VALUES ('3', 'API文档', '1', '3', 'http://127.0.0.1:8088/swagger-ui.html', '1');
INSERT INTO `sys_menu` VALUES ('4', '用户管理', '1', '4', 'crawler/userMgmt.html', '1');
INSERT INTO `sys_menu` VALUES ('5', '角色管理', '1', '5', 'crawler/roleMgmt.html', '1');
INSERT INTO `sys_menu` VALUES ('6', '网页爬虫管理', '0', '6', '#', '2');
INSERT INTO `sys_menu` VALUES ('7', '网页爬虫查询', '6', '7', 'crawler/formSearch.html', '2');
INSERT INTO `sys_menu` VALUES ('8', '二维码信息查询', '6', '8', 'crawler/barCodeSearch.html', '2');
INSERT INTO `sys_menu` VALUES ('9', '微信公众号查询', '6', '9', 'crawler/weChatSearch.html', '2');
INSERT INTO `sys_menu` VALUES ('10', '文章爬虫管理', '0', '10', '#', '3');
INSERT INTO `sys_menu` VALUES ('11', '网站模版管理', '10', '11', 'crawler/templateMgmt.html', '3');
INSERT INTO `sys_menu` VALUES ('12', '文章查询', '10', '12', 'crawler/articleSearch.html', '3');
INSERT INTO `sys_menu` VALUES ('13', '自服务管理', '0', '13', '#', '4');
INSERT INTO `sys_menu` VALUES ('14', '用户信息修改', '13', '14', 'crawler/userInformationUpdate.html', '4');
INSERT INTO `sys_menu` VALUES ('15', '债券市场查询', '6', '15', 'crawler/bondMarketSearch.html', '2');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '乐观锁（版本号）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统管理员', '系统管理员角色', '4');
INSERT INTO `sys_role` VALUES ('2', '项目经理', '项目经理角色', '5');
INSERT INTO `sys_role` VALUES ('3', '开发人员', '开发人员角色噶', '3');
INSERT INTO `sys_role` VALUES ('4', '测试人员', '测试人员角色', '3');

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('182', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('183', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('184', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('185', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('186', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('187', '1', '13');
INSERT INTO `sys_role_menu` VALUES ('188', '1', '14');
INSERT INTO `sys_role_menu` VALUES ('189', '2', '6');
INSERT INTO `sys_role_menu` VALUES ('190', '2', '7');
INSERT INTO `sys_role_menu` VALUES ('191', '2', '8');
INSERT INTO `sys_role_menu` VALUES ('192', '2', '9');
INSERT INTO `sys_role_menu` VALUES ('193', '2', '15');
INSERT INTO `sys_role_menu` VALUES ('194', '2', '10');
INSERT INTO `sys_role_menu` VALUES ('195', '2', '11');
INSERT INTO `sys_role_menu` VALUES ('196', '2', '12');
INSERT INTO `sys_role_menu` VALUES ('197', '2', '14');
INSERT INTO `sys_role_menu` VALUES ('198', '3', '6');
INSERT INTO `sys_role_menu` VALUES ('199', '3', '7');
INSERT INTO `sys_role_menu` VALUES ('200', '3', '8');
INSERT INTO `sys_role_menu` VALUES ('201', '3', '9');
INSERT INTO `sys_role_menu` VALUES ('202', '3', '15');
INSERT INTO `sys_role_menu` VALUES ('203', '3', '10');
INSERT INTO `sys_role_menu` VALUES ('204', '3', '11');
INSERT INTO `sys_role_menu` VALUES ('205', '3', '12');
INSERT INTO `sys_role_menu` VALUES ('206', '3', '14');
INSERT INTO `sys_role_menu` VALUES ('207', '4', '6');
INSERT INTO `sys_role_menu` VALUES ('208', '4', '7');
INSERT INTO `sys_role_menu` VALUES ('209', '4', '8');
INSERT INTO `sys_role_menu` VALUES ('210', '4', '9');
INSERT INTO `sys_role_menu` VALUES ('211', '4', '15');
INSERT INTO `sys_role_menu` VALUES ('212', '4', '14');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户登录名称',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户密码',
  `version` int(11) DEFAULT NULL COMMENT '乐观锁（版本号）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'crawler', 'f74ad5b5c5271f653dbcf5641ca029bd', '4');
INSERT INTO `sys_user` VALUES ('25', 'zhangsan', '张三', 'f74ad5b5c5271f653dbcf5641ca029bd', '13');
INSERT INTO `sys_user` VALUES ('26', 'lisi', '李四', 'f74ad5b5c5271f653dbcf5641ca029bd', '2');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('30', '26', '1');
INSERT INTO `sys_user_role` VALUES ('31', '26', '2');
INSERT INTO `sys_user_role` VALUES ('32', '26', '3');
INSERT INTO `sys_user_role` VALUES ('33', '26', '4');
INSERT INTO `sys_user_role` VALUES ('74', '25', '1');
INSERT INTO `sys_user_role` VALUES ('75', '25', '2');
INSERT INTO `sys_user_role` VALUES ('76', '25', '3');
INSERT INTO `sys_user_role` VALUES ('77', '25', '4');
INSERT INTO `sys_user_role` VALUES ('89', '1', '1');

-- ----------------------------
-- Table structure for `template_config`
-- ----------------------------
DROP TABLE IF EXISTS `template_config`;
CREATE TABLE `template_config` (
  `id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_level_pattern` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `title_pattern` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time_pattern` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content_pattern` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `version` int(11) DEFAULT NULL COMMENT '乐观锁（版本号）',
  `user_id` int(11) DEFAULT NULL COMMENT '关联的用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of template_config
-- ----------------------------
INSERT INTO `template_config` VALUES ('13118d0ada1741f0b5d816907cf7178c', 'http://sports.sina.com.cn/nba/', '.news-list-b', '#j_title', '.article-a__time', '#artibody', '1', '25');
INSERT INTO `template_config` VALUES ('4aafe7ebe6a949d294c5483c81cef361', 'http://sports.sina.com.cn/nba/', '.news-list-b', '#j_title', '.article-a__time', '#artibody', '3', '26');
INSERT INTO `template_config` VALUES ('4aafe7ebe6a949d294c5483c81cef362', 'http://sports.sina.com.cn/csl/', '.blk13', '#j_title', '.article-a__time', '#artibody', '1', '26');
