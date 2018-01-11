/*
Navicat MySQL Data Transfer

Source Server         : maria
Source Server Version : 50505
Source Host           : localhost:3307
Source Database       : spider

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-01-11 16:07:35
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
INSERT INTO `bond_market` VALUES ('1204323625', '000509', '华塑控股', '关于子公司申请解除《收回国有土地使用权补偿协议书》的补充公告', '交易', '2018-01-12', '2018-01-11 15:43:11', 'finalpage/2018-01-12/1204323625.PDF');
INSERT INTO `bond_market` VALUES ('1204323626', '000029', '深深房Ａ', '关于筹划重大资产重组事项停牌进展公告', '其它重大事项', '2018-01-12', '2018-01-11 15:43:11', 'finalpage/2018-01-12/1204323626.PDF');
INSERT INTO `bond_market` VALUES ('1204323627', '000766', '通化金马', '关于全资子公司哈尔滨圣泰生物制药有限公司使用部分闲置募集资金进行现金管理的进展公告', '交易', '2018-01-12', '2018-01-11 15:43:11', 'finalpage/2018-01-12/1204323627.PDF');
INSERT INTO `bond_market` VALUES ('1204323628', '000755', '*ST三维', '关于重大资产重组进展公告', '其它重大事项', '2018-01-12', '2018-01-11 15:43:11', 'finalpage/2018-01-12/1204323628.PDF');
INSERT INTO `bond_market` VALUES ('1204323629', '000877', '天山股份', '中信证券股份有限公司关于公司2017年度持续督导培训情况的报告', '中介机构报告', '2018-01-12', '2018-01-11 15:43:11', 'finalpage/2018-01-12/1204323629.PDF');
INSERT INTO `bond_market` VALUES ('1204323630', '000089', '深圳机场', '关于召开2018年第一次临时股东大会的提示性公告', '股东大会', '2018-01-12', '2018-01-11 15:43:10', 'finalpage/2018-01-12/1204323630.PDF');
INSERT INTO `bond_market` VALUES ('1204323631', '001965', '招商公路', '关于召开2018年第一次临时股东大会的提示性公告', '股东大会', '2018-01-12', '2018-01-11 15:43:10', 'finalpage/2018-01-12/1204323631.PDF');
INSERT INTO `bond_market` VALUES ('1204323632', '000639', '西王食品', '第十二届监事会第五次会议决议公告', '监事会公告', '2018-01-12', '2018-01-11 15:43:10', 'finalpage/2018-01-12/1204323632.PDF');
INSERT INTO `bond_market` VALUES ('1204323633', '000639', '西王食品', '关于新增开设募集资金专户及签署三方监管协议的公告', '其它重大事项', '2018-01-12', '2018-01-11 15:43:10', 'finalpage/2018-01-12/1204323633.PDF');
INSERT INTO `bond_market` VALUES ('1204323634', '000639', '西王食品', '华泰联合证券有限责任公司关于公司使用募集资金置换先期投入的核查意见', '中介机构报告', '2018-01-12', '2018-01-11 15:43:10', 'finalpage/2018-01-12/1204323634.PDF');
INSERT INTO `bond_market` VALUES ('1204323635', '000639', '西王食品', '第十二届董事会第九次会议决议公告', '董事会公告', '2018-01-12', '2018-01-11 15:43:09', 'finalpage/2018-01-12/1204323635.PDF');
INSERT INTO `bond_market` VALUES ('1204323636', '000639', '西王食品', '独立董事关于第十二届董事会第九次会议相关事项之独立意见', '上市公司制度', '2018-01-12', '2018-01-11 15:43:10', 'finalpage/2018-01-12/1204323636.PDF');
INSERT INTO `bond_market` VALUES ('1204323637', '000639', '西王食品', '关于以非公开发行股票募集资金置换预先已投入募投项目自筹资金的公告', '增发', '2018-01-12', '2018-01-11 15:43:09', 'finalpage/2018-01-12/1204323637.PDF');
INSERT INTO `bond_market` VALUES ('1204323638', '000797', '中国武夷', '关于承接埃塞俄比亚国际工程施工项目的公告', '交易', '2018-01-12', '2018-01-11 15:43:08', 'finalpage/2018-01-12/1204323638.PDF');
INSERT INTO `bond_market` VALUES ('1204323639', '000797', '中国武夷', '兴业证券股份有限公司关于公司配股持续督导2017年度现场检查报告', '中介机构报告', '2018-01-12', '2018-01-11 15:43:09', 'finalpage/2018-01-12/1204323639.PDF');
INSERT INTO `bond_market` VALUES ('1204323640', '000797', '中国武夷', '兴业证券股份有限公司关于公司2017年度培训工作报告', '中介机构报告', '2018-01-12', '2018-01-11 15:43:09', 'finalpage/2018-01-12/1204323640.PDF');
INSERT INTO `bond_market` VALUES ('1204323641', '000831', '五矿稀土', '关于公司所属分离企业生产情况的公告', '其它重大事项', '2018-01-12', '2018-01-11 15:43:08', 'finalpage/2018-01-12/1204323641.PDF');
INSERT INTO `bond_market` VALUES ('1204323646', '300398', '飞凯材料', '关于控股股东股份质押的公告', '其它重大事项', '2018-01-11', '2018-01-11 15:43:11', 'finalpage/2018-01-11/1204323646.PDF');
INSERT INTO `bond_market` VALUES ('1204323648', '300465', '高伟达', '关于收到《中国证监会行政许可申请终止审查通知书》的公告', '其它重大事项', '2018-01-11', '2018-01-11 15:43:11', 'finalpage/2018-01-11/1204323648.PDF');
INSERT INTO `bond_market` VALUES ('1204323649', '300308', '中际旭创', '关于使用闲置资金购买银行理财产品情况的公告', '交易', '2018-01-12', '2018-01-11 15:43:08', 'finalpage/2018-01-12/1204323649.PDF');
INSERT INTO `bond_market` VALUES ('1204323650', '300344', '太空板业', '关于控股股东部分股权解除质押的公告', '其它重大事项', '2018-01-12', '2018-01-11 15:43:08', 'finalpage/2018-01-12/1204323650.PDF');
INSERT INTO `bond_market` VALUES ('1204323651', '300092', '科新机电', '关于完成工商变更登记的公告', '其它重大事项', '2018-01-11', '2018-01-11 15:43:11', 'finalpage/2018-01-11/1204323651.PDF');
INSERT INTO `bond_market` VALUES ('1204323652', '300718', '长盛轴承', '国信证券股份有限公司关于对公司2018年度持续督导培训报告', '中介机构报告', '2018-01-12', '2018-01-11 15:43:08', 'finalpage/2018-01-12/1204323652.PDF');
INSERT INTO `bond_market` VALUES ('1204323653', '300718', '长盛轴承', '关于控股子公司完成工商变更登记的公告', '交易', '2018-01-12', '2018-01-11 15:43:08', 'finalpage/2018-01-12/1204323653.PDF');
INSERT INTO `bond_market` VALUES ('1204323654', '002160', '常铝股份', '关于筹划发行股份及支付现金购买资产事项停牌进展公告', '增发', '2018-01-12', '2018-01-11 15:43:08', 'finalpage/2018-01-12/1204323654.PDF');
INSERT INTO `bond_market` VALUES ('1204323655', '002412', '汉森制药', '关于控股孙公司宁乡妇女儿童医院有限公司完成注销登记的公告', '交易', '2018-01-12', '2018-01-11 15:43:08', 'finalpage/2018-01-12/1204323655.PDF');
INSERT INTO `bond_market` VALUES ('1204323656', '002457', '青龙管业', '重大合同公告', '其它重大事项', '2018-01-12', '2018-01-11 15:43:08', 'finalpage/2018-01-12/1204323656.PDF');
INSERT INTO `bond_market` VALUES ('1204323658', '002801', '微光股份', '中天国富证券有限公司关于公司2017年持续督导培训情况报告', '中介机构报告', '2018-01-12', '2018-01-11 15:43:07', 'finalpage/2018-01-12/1204323658.PDF');
INSERT INTO `bond_market` VALUES ('1204323659', '002801', '微光股份', '中天国富证券有限公司关于公司2017年定期现场检查报告', '中介机构报告', '2018-01-12', '2018-01-11 15:43:07', 'finalpage/2018-01-12/1204323659.PDF');

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
INSERT INTO `sys_menu` VALUES ('2', '网站监控', '1', '2', '/druid/index.html', '1');
INSERT INTO `sys_menu` VALUES ('3', 'API文档', '1', '3', '/swagger-ui.html', '1');
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
INSERT INTO `sys_user` VALUES ('25', 'zhangsan', '张三', 'f74ad5b5c5271f653dbcf5641ca029bd', '14');
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
