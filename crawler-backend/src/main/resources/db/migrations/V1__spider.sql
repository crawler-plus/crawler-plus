/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : spider

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2017-11-16 09:46:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bond_market
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of bond_market
-- ----------------------------

-- ----------------------------
-- Table structure for crawler_content
-- ----------------------------
DROP TABLE IF EXISTS `crawler_content`;
CREATE TABLE `crawler_content` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content_body` longtext COLLATE utf8_unicode_ci,
  `created` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '关联的用户id',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=890 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of crawler_content
-- ----------------------------

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `captcha` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '验证码',
  `create_time` datetime DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------

-- ----------------------------
-- Table structure for sys_header_footer_content
-- ----------------------------
DROP TABLE IF EXISTS `sys_header_footer_content`;
CREATE TABLE `sys_header_footer_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `header_content` longtext COLLATE utf8_unicode_ci COMMENT '页眉内容',
  `footer_content` longtext COLLATE utf8_unicode_ci COMMENT '页脚内容',
  `version` int(11) DEFAULT NULL COMMENT '版本号（乐观锁）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_header_footer_content
-- ----------------------------
INSERT INTO `sys_header_footer_content` VALUES ('1', '<div>页眉内容</div>', '<div>页脚内容</div>', '2');

-- ----------------------------
-- Table structure for sys_lock
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
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `login_account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '登录账户',
  `execute_time` datetime DEFAULT NULL COMMENT '执行时间',
  `type_id` int(11) DEFAULT NULL COMMENT '类型（1：登录系统，2：登出系统）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

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
INSERT INTO `sys_menu` VALUES ('13', '系统日志管理', '1', '13', 'crawler/logMgmt.html', '1');
INSERT INTO `sys_menu` VALUES ('14', '自服务管理', '0', '14', '#', '4');
INSERT INTO `sys_menu` VALUES ('15', '用户信息修改', '14', '15', 'crawler/userInformationUpdate.html', '4');
INSERT INTO `sys_menu` VALUES ('16', '页眉页脚管理', '1', '16', 'crawler/headerFooterContentMgmt.html', '1');
INSERT INTO `sys_menu` VALUES ('17', '债券市场查询', '6', '17', 'crawler/bondMarketSearch.html', '2');

-- ----------------------------
-- Table structure for sys_role
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
INSERT INTO `sys_role` VALUES ('1', '系统管理员', '系统管理员角色', '2');
INSERT INTO `sys_role` VALUES ('2', '项目经理', '项目经理角色', '4');
INSERT INTO `sys_role` VALUES ('3', '开发人员', '开发人员角色噶', '2');
INSERT INTO `sys_role` VALUES ('4', '测试人员', '测试人员角色', '2');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('114', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('115', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('116', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('117', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('118', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('119', '1', '13');
INSERT INTO `sys_role_menu` VALUES ('120', '1', '14');
INSERT INTO `sys_role_menu` VALUES ('121', '1', '15');
INSERT INTO `sys_role_menu` VALUES ('127', '3', '6');
INSERT INTO `sys_role_menu` VALUES ('128', '3', '7');
INSERT INTO `sys_role_menu` VALUES ('129', '3', '8');
INSERT INTO `sys_role_menu` VALUES ('130', '3', '9');
INSERT INTO `sys_role_menu` VALUES ('131', '3', '10');
INSERT INTO `sys_role_menu` VALUES ('132', '3', '11');
INSERT INTO `sys_role_menu` VALUES ('133', '3', '12');
INSERT INTO `sys_role_menu` VALUES ('134', '3', '14');
INSERT INTO `sys_role_menu` VALUES ('135', '3', '15');
INSERT INTO `sys_role_menu` VALUES ('136', '4', '6');
INSERT INTO `sys_role_menu` VALUES ('137', '4', '7');
INSERT INTO `sys_role_menu` VALUES ('138', '4', '8');
INSERT INTO `sys_role_menu` VALUES ('139', '4', '9');
INSERT INTO `sys_role_menu` VALUES ('140', '4', '14');
INSERT INTO `sys_role_menu` VALUES ('141', '4', '15');
INSERT INTO `sys_role_menu` VALUES ('148', '2', '16');
INSERT INTO `sys_role_menu` VALUES ('149', '2', '6');
INSERT INTO `sys_role_menu` VALUES ('150', '2', '7');
INSERT INTO `sys_role_menu` VALUES ('151', '2', '8');
INSERT INTO `sys_role_menu` VALUES ('152', '2', '9');
INSERT INTO `sys_role_menu` VALUES ('153', '2', '17');
INSERT INTO `sys_role_menu` VALUES ('154', '2', '10');
INSERT INTO `sys_role_menu` VALUES ('155', '2', '11');
INSERT INTO `sys_role_menu` VALUES ('156', '2', '12');
INSERT INTO `sys_role_menu` VALUES ('157', '2', '14');
INSERT INTO `sys_role_menu` VALUES ('158', '2', '15');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户登录名称',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户密码',
  `version` int(11) DEFAULT NULL COMMENT '乐观锁（版本号）',
  `login_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '登录token',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'crawler', 'f74ad5b5c5271f653dbcf5641ca029bd', '2', '');
INSERT INTO `sys_user` VALUES ('25', 'zhangsan', '张三', 'f74ad5b5c5271f653dbcf5641ca029bd', '13', null);
INSERT INTO `sys_user` VALUES ('26', 'lisi', '李四', 'f74ad5b5c5271f653dbcf5641ca029bd', '2', 'eyJ1aWQiOiIyNiIsInRva2VuS2V5IjoiY3Jhd2xlcl9rZXkiLCJ0aW1lc3RhbXAiOiIxNTEwNzk2MzkxNTAzIn0=');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
INSERT INTO `sys_user_role` VALUES ('78', '1', '1');

-- ----------------------------
-- Table structure for template_config
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
