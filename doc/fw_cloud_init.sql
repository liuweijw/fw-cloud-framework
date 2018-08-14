-- --------------------------------------------------------
-- 主机:                           120.78.74.146
-- 服务器版本:                        5.6.40 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 fw-cloud 的数据库结构
DROP DATABASE IF EXISTS `fw-cloud`;
CREATE DATABASE IF NOT EXISTS `fw-cloud` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fw-cloud`;


-- 导出  表 fw-cloud.t_sys_company 结构
DROP TABLE IF EXISTS `t_sys_company`;
CREATE TABLE IF NOT EXISTS `t_sys_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `code` varchar(32) NOT NULL COMMENT '单位编码',
  `name` varchar(32) NOT NULL COMMENT '单位名称',
  `statu` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否生效， 0 默认生效',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `t_sys_company_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='单位管理';

-- 正在导出表  fw-cloud.t_sys_company 的数据：~1 rows (大约)
DELETE FROM `t_sys_company`;
/*!40000 ALTER TABLE `t_sys_company` DISABLE KEYS */;
INSERT INTO `t_sys_company` (`id`, `code`, `name`, `statu`, `ctime`) VALUES
	(1, 'A7546D759B9A477D8A656C7D78554FDF', '广州会智公司', 0, '2018-08-13 18:03:08');
/*!40000 ALTER TABLE `t_sys_company` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_dept 结构
DROP TABLE IF EXISTS `t_sys_dept`;
CREATE TABLE IF NOT EXISTS `t_sys_dept` (
  `dept_id` int(20) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL COMMENT '上级部门',
  `dept_name` varchar(64) NOT NULL COMMENT '部门名称',
  `statu` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除  1：已删除  0：正常',
  `pos` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门管理';

-- 正在导出表  fw-cloud.t_sys_dept 的数据：~1 rows (大约)
DELETE FROM `t_sys_dept`;
/*!40000 ALTER TABLE `t_sys_dept` DISABLE KEYS */;
INSERT INTO `t_sys_dept` (`dept_id`, `pid`, `dept_name`, `statu`, `pos`, `create_time`, `update_time`) VALUES
	(1, 0, '一级部门', 0, 0, '2018-01-23 03:00:23', '2018-08-03 16:55:51');
/*!40000 ALTER TABLE `t_sys_dept` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_dept_relation 结构
DROP TABLE IF EXISTS `t_sys_dept_relation`;
CREATE TABLE IF NOT EXISTS `t_sys_dept_relation` (
  `pre_id` int(11) NOT NULL COMMENT '节点',
  `after_id` int(11) NOT NULL COMMENT '节点',
  PRIMARY KEY (`pre_id`,`after_id`),
  KEY `pre_id` (`pre_id`),
  KEY `after_id` (`after_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  fw-cloud.t_sys_dept_relation 的数据：~0 rows (大约)
DELETE FROM `t_sys_dept_relation`;
/*!40000 ALTER TABLE `t_sys_dept_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_dept_relation` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_dict 结构
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE IF NOT EXISTS `t_sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` varchar(32) NOT NULL COMMENT '类型',
  `value` varchar(32) NOT NULL COMMENT '数据值',
  `label` varchar(32) NOT NULL COMMENT '标签名',
  `statu` smallint(1) NOT NULL DEFAULT '0' COMMENT '删除标记0--正常 1--删除',
  PRIMARY KEY (`id`),
  KEY `t_sys_dict_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- 正在导出表  fw-cloud.t_sys_dict 的数据：~32 rows (大约)
DELETE FROM `t_sys_dict`;
/*!40000 ALTER TABLE `t_sys_dict` DISABLE KEYS */;
INSERT INTO `t_sys_dict` (`id`, `type`, `value`, `label`, `statu`) VALUES
	(1, 'STATU', '0', '有效', 0),
	(2, 'STATU', '1', '无效', 0),
	(3, 'DEL_FLAG', '0', '正常', 0),
	(4, 'DEL_FLAG', '1', '删除', 0),
	(5, 'SEX', '1', '男', 0),
	(6, 'SEX', '2', '女', 0),
	(8, 'AD_TYPE', '1', '轮播广告', 0),
	(9, 'AD_TYPE', '2', '其它广告', 0),
	(10, 'YES_OR_NO', '1', '是', 0),
	(11, 'YES_OR_NO', '0', '否', 0),
	(12, 'EDUCATION', '1', '小学及以下', 0),
	(13, 'EDUCATION', '2', '初中', 0),
	(14, 'EDUCATION', '3', '高中', 0),
	(15, 'EDUCATION', '4', '中专', 0),
	(16, 'EDUCATION', '5', '大专', 0),
	(17, 'EDUCATION', '6', '本科', 0),
	(18, 'EDUCATION', '7', '研究生', 0),
	(19, 'EDUCATION', '8', '博士', 0),
	(20, 'EDUCATION', '9', '其它', 0),
	(21, 'MEETING_PERSON_TYPE', '1', '代表', 0),
	(22, 'MEETING_PERSON_TYPE', '2', '列席', 0),
	(23, 'MEETING_PERSON_TYPE', '3', '嘉宾', 0),
	(24, 'MEETING_PERSON_DP', '1', '中国国民党革命委员会', 0),
	(25, 'MEETING_PERSON_DP', '2', '中国民主同盟', 0),
	(26, 'MEETING_PERSON_DP', '3', '中国民主建国会', 0),
	(27, 'MEETING_PERSON_DP', '4', '中国民主促进会', 0),
	(28, 'MEETING_PERSON_DP', '5', '中国农工民主党', 0),
	(29, 'MEETING_PERSON_DP', '6', '中国致公党', 0),
	(30, 'MEETING_PERSON_DP', '7', '九三学社', 0),
	(31, 'MEETING_PERSON_DP', '8', '台湾民主自治同盟', 0),
	(32, 'MEETING_P_TYPE', '1', '与会人员', 0),
	(33, 'MEETING_P_TYPE', '2', '工作人员', 0);
/*!40000 ALTER TABLE `t_sys_dict` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_log_0 结构
DROP TABLE IF EXISTS `t_sys_log_0`;
CREATE TABLE IF NOT EXISTS `t_sys_log_0` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` smallint(6) NOT NULL DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `time` mediumtext COMMENT '执行时间',
  `statu` smallint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `exception` longtext COMMENT '异常信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `t_sys_log_0_create_by` (`create_by`) USING BTREE,
  KEY `t_sys_log_0_request_uri` (`request_uri`) USING BTREE,
  KEY `t_sys_log_0_type` (`type`) USING BTREE,
  KEY `t_sys_log_0_create_date` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=236177229892026369 DEFAULT CHARSET=utf8 COMMENT='日志表';

-- 正在导出表  fw-cloud.t_sys_log_0 的数据：~2,797 rows (大约)
DELETE FROM `t_sys_log_0`;
/*!40000 ALTER TABLE `t_sys_log_0` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_log_0` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_log_1 结构
DROP TABLE IF EXISTS `t_sys_log_1`;
CREATE TABLE IF NOT EXISTS `t_sys_log_1` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` smallint(6) NOT NULL DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `time` mediumtext COMMENT '执行时间',
  `statu` smallint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `exception` longtext COMMENT '异常信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `t_sys_log_1_create_by` (`create_by`) USING BTREE,
  KEY `t_sys_log_1_request_uri` (`request_uri`) USING BTREE,
  KEY `t_sys_log_1_type` (`type`) USING BTREE,
  KEY `t_sys_log_1_create_date` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

-- 正在导出表  fw-cloud.t_sys_log_1 的数据：~0 rows (大约)
DELETE FROM `t_sys_log_1`;
/*!40000 ALTER TABLE `t_sys_log_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_log_1` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_menu 结构
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE IF NOT EXISTS `t_sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(32) NOT NULL COMMENT '菜单名称',
  `path` varchar(128) DEFAULT NULL COMMENT '前端URL',
  `url` varchar(128) DEFAULT NULL COMMENT '请求链接',
  `pid` int(11) DEFAULT NULL COMMENT '父菜单ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `statu` smallint(1) DEFAULT '0' COMMENT '0--正常 1--删除',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- 正在导出表  fw-cloud.t_sys_menu 的数据：~19 rows (大约)
DELETE FROM `t_sys_menu`;
/*!40000 ALTER TABLE `t_sys_menu` DISABLE KEYS */;
INSERT INTO `t_sys_menu` (`menu_id`, `menu_name`, `path`, `url`, `pid`, `create_time`, `update_time`, `statu`) VALUES
	(1, '系统管理', '/admin', NULL, 0, '2018-03-09 07:56:00', '2018-04-13 00:03:41', 0),
	(2, '用户管理', 'user', '/admin/user/**', 1, '2018-08-03 17:01:59', '2018-08-03 17:01:59', 0),
	(3, '菜单管理', 'menu', '/admin/menu/**', 1, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(4, '角色管理', 'role', '/admin/role/**', 1, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(5, '日志管理', 'logs', '/admin/logs/**', 1, '2018-03-09 07:56:00', '2018-04-13 07:04:20', 0),
	(6, '字典管理', 'dict', '/admin/dict/**', 1, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(7, '部门管理', 'dept', '/admin/dept/**', 1, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(8, '单位管理', 'company', '/admin/company/**', 1, '2018-08-13 16:58:23', '2018-08-13 16:58:25', 0),
	(31, '基础数据', '/dmc', NULL, 0, '2018-07-19 14:27:15', '2018-07-19 14:28:02', 0),
	(32, '附件管理', 'attach', '/dmc/attach/**', 31, '2018-07-19 14:28:52', '2018-07-19 14:28:53', 0),
	(33, '广告管理', 'adinfo', '/dmc/adinfo/**', 31, '2018-07-19 14:29:36', '2018-07-19 14:29:37', 0),
	(34, '区域管理', 'area', '/dmc/area/**', 31, '2018-07-19 14:30:03', '2018-07-19 14:30:05', 0),
	(40, '会议管理', '/cmc', NULL, 0, '2018-08-06 17:07:09', '2018-08-06 17:37:59', 0),
	(42, '类型管理', 'meetingType', '/cmc/meetingType/**', 40, '2018-08-06 17:08:04', '2018-08-07 23:36:46', 0),
	(43, '会议基础信息', 'meetingInfo', '/cmc/meetingInfo/**', 40, '2018-08-07 15:22:53', '2018-08-07 23:36:41', 0),
	(44, '代表团管理', 'meetingMetmission', '/cmc/meetingMetmission/**', 40, '2018-08-09 11:05:47', '2018-08-09 11:05:47', 0),
	(45, '人员信息管理', 'meetingPerson', '/cmc/meetingPerson/**', 40, '2018-08-09 14:46:14', '2018-08-09 14:46:14', 0),
	(46, '与会人员管理', 'meetingParticipant', '/cmc/meetingParticipant/**', 40, '2018-08-09 17:02:13', '2018-08-09 17:02:13', 0);
/*!40000 ALTER TABLE `t_sys_menu` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_module 结构
DROP TABLE IF EXISTS `t_sys_module`;
CREATE TABLE IF NOT EXISTS `t_sys_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增量',
  `code` varchar(20) NOT NULL COMMENT '编码',
  `name` varchar(100) NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 正在导出表  fw-cloud.t_sys_module 的数据：~6 rows (大约)
DELETE FROM `t_sys_module`;
/*!40000 ALTER TABLE `t_sys_module` DISABLE KEYS */;
INSERT INTO `t_sys_module` (`id`, `code`, `name`) VALUES
	(1, 'add', '新增'),
	(2, 'view', '查看'),
	(3, 'upd', '更新'),
	(4, 'del', '删除'),
	(5, 'export', '导出'),
	(6, 'import', '导入');
/*!40000 ALTER TABLE `t_sys_module` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_role 结构
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE IF NOT EXISTS `t_sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) NOT NULL,
  `role_code` varchar(32) NOT NULL,
  `role_desc` varchar(128) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `statu` smallint(6) NOT NULL DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_idx1_role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 正在导出表  fw-cloud.t_sys_role 的数据：~5 rows (大约)
DELETE FROM `t_sys_role`;
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` (`role_id`, `role_name`, `role_code`, `role_desc`, `create_time`, `update_time`, `statu`) VALUES
	(1, 'SUPER管理员', 'ROLE_SUPER_ADMIN', '超级管理员', '2018-04-13 19:37:40', '2018-08-03 17:04:26', 0),
	(2, '系统管理员', 'ROLE_ADMIN', '系统管理员', '2018-03-09 07:56:00', '2018-08-10 17:45:44', 0),
	(3, '测试Test', 'ROLE_TEST', '测试权限', '2018-03-09 07:56:00', '2018-08-03 17:04:22', 0),
	(4, '演示用户角色', 'ROLE_YANSHI', '提供演示用户角色使用', '2018-08-13 16:16:36', '2018-08-13 16:16:36', 0);
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_role_dept 结构
DROP TABLE IF EXISTS `t_sys_role_dept`;
CREATE TABLE IF NOT EXISTS `t_sys_role_dept` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` int(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色与部门对应关系';

-- 正在导出表  fw-cloud.t_sys_role_dept 的数据：~4 rows (大约)
DELETE FROM `t_sys_role_dept`;
/*!40000 ALTER TABLE `t_sys_role_dept` DISABLE KEYS */;
INSERT INTO `t_sys_role_dept` (`id`, `role_id`, `dept_id`) VALUES
	(13, 3, 1),
	(14, 1, 1),
	(15, 2, 1),
	(16, 4, 1);
/*!40000 ALTER TABLE `t_sys_role_dept` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_role_menu 结构
DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE IF NOT EXISTS `t_sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=753 DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- 正在导出表  fw-cloud.t_sys_role_menu 的数据：~30 rows (大约)
DELETE FROM `t_sys_role_menu`;
/*!40000 ALTER TABLE `t_sys_role_menu` DISABLE KEYS */;
INSERT INTO `t_sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES
	(723, 2, 1),
	(724, 2, 2),
	(725, 2, 4),
	(726, 2, 7),
	(727, 2, 40),
	(728, 2, 41),
	(729, 2, 42),
	(730, 2, 43),
	(731, 2, 44),
	(732, 2, 45),
	(733, 2, 46),
	(734, 1, 32),
	(735, 1, 1),
	(736, 1, 33),
	(737, 1, 2),
	(738, 1, 34),
	(739, 1, 3),
	(740, 1, 4),
	(741, 1, 5),
	(742, 1, 6),
	(743, 1, 7),
	(744, 1, 8),
	(745, 1, 40),
	(746, 1, 41),
	(747, 1, 42),
	(748, 1, 43),
	(749, 1, 44),
	(750, 1, 45),
	(751, 1, 46),
	(752, 1, 31);
/*!40000 ALTER TABLE `t_sys_role_menu` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_role_menu_permission 结构
DROP TABLE IF EXISTS `t_sys_role_menu_permission`;
CREATE TABLE IF NOT EXISTS `t_sys_role_menu_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_menu_id` int(11) NOT NULL,
  `permission` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2941 DEFAULT CHARSET=utf8 COMMENT='功能权限表';

-- 正在导出表  fw-cloud.t_sys_role_menu_permission 的数据：~143 rows (大约)
DELETE FROM `t_sys_role_menu_permission`;
/*!40000 ALTER TABLE `t_sys_role_menu_permission` DISABLE KEYS */;
INSERT INTO `t_sys_role_menu_permission` (`id`, `role_menu_id`, `permission`) VALUES
	(2798, 724, 'user_add'),
	(2799, 724, 'user_view'),
	(2800, 724, 'user_upd'),
	(2801, 724, 'user_del'),
	(2802, 724, 'user_export'),
	(2803, 724, 'user_import'),
	(2804, 725, 'role_view'),
	(2805, 726, 'dept_add'),
	(2806, 726, 'dept_view'),
	(2807, 726, 'dept_upd'),
	(2808, 726, 'dept_del'),
	(2809, 728, 'meetingCompany_add'),
	(2810, 728, 'meetingCompany_view'),
	(2811, 728, 'meetingCompany_upd'),
	(2812, 728, 'meetingCompany_del'),
	(2813, 728, 'meetingCompany_export'),
	(2814, 728, 'meetingCompany_import'),
	(2815, 729, 'meetingType_add'),
	(2816, 729, 'meetingType_view'),
	(2817, 729, 'meetingType_upd'),
	(2818, 729, 'meetingType_del'),
	(2819, 729, 'meetingType_export'),
	(2820, 729, 'meetingType_import'),
	(2821, 730, 'meetingInfo_add'),
	(2822, 730, 'meetingInfo_view'),
	(2823, 730, 'meetingInfo_upd'),
	(2824, 730, 'meetingInfo_del'),
	(2825, 730, 'meetingInfo_export'),
	(2826, 730, 'meetingInfo_import'),
	(2827, 731, 'meetingMetmission_add'),
	(2828, 731, 'meetingMetmission_view'),
	(2829, 731, 'meetingMetmission_upd'),
	(2830, 731, 'meetingMetmission_del'),
	(2831, 731, 'meetingMetmission_export'),
	(2832, 731, 'meetingMetmission_import'),
	(2833, 732, 'meetingPerson_add'),
	(2834, 732, 'meetingPerson_view'),
	(2835, 732, 'meetingPerson_upd'),
	(2836, 732, 'meetingPerson_del'),
	(2837, 732, 'meetingPerson_export'),
	(2838, 732, 'meetingPerson_import'),
	(2839, 733, 'meetingParticipant_add'),
	(2840, 733, 'meetingParticipant_view'),
	(2841, 733, 'meetingParticipant_upd'),
	(2842, 733, 'meetingParticipant_del'),
	(2843, 733, 'meetingParticipant_export'),
	(2844, 733, 'meetingParticipant_import'),
	(2845, 734, 'attach_add'),
	(2846, 734, 'attach_view'),
	(2847, 734, 'attach_upd'),
	(2848, 734, 'attach_del'),
	(2849, 734, 'attach_export'),
	(2850, 734, 'attach_import'),
	(2851, 736, 'adinfo_add'),
	(2852, 736, 'adinfo_view'),
	(2853, 736, 'adinfo_upd'),
	(2854, 736, 'adinfo_del'),
	(2855, 736, 'adinfo_export'),
	(2856, 736, 'adinfo_import'),
	(2857, 737, 'user_add'),
	(2858, 737, 'user_view'),
	(2859, 737, 'user_upd'),
	(2860, 737, 'user_del'),
	(2861, 737, 'user_export'),
	(2862, 737, 'user_import'),
	(2863, 738, 'area_add'),
	(2864, 738, 'area_view'),
	(2865, 738, 'area_upd'),
	(2866, 738, 'area_del'),
	(2867, 738, 'area_export'),
	(2868, 738, 'area_import'),
	(2869, 739, 'menu_add'),
	(2870, 739, 'menu_view'),
	(2871, 739, 'menu_upd'),
	(2872, 739, 'menu_del'),
	(2873, 739, 'menu_export'),
	(2874, 739, 'menu_import'),
	(2875, 740, 'role_add'),
	(2876, 740, 'role_view'),
	(2877, 740, 'role_upd'),
	(2878, 740, 'role_del'),
	(2879, 740, 'role_export'),
	(2880, 740, 'role_import'),
	(2881, 741, 'logs_add'),
	(2882, 741, 'logs_view'),
	(2883, 741, 'logs_upd'),
	(2884, 741, 'logs_del'),
	(2885, 741, 'logs_export'),
	(2886, 741, 'logs_import'),
	(2887, 742, 'dict_add'),
	(2888, 742, 'dict_view'),
	(2889, 742, 'dict_upd'),
	(2890, 742, 'dict_del'),
	(2891, 742, 'dict_export'),
	(2892, 742, 'dict_import'),
	(2893, 743, 'dept_add'),
	(2894, 743, 'dept_view'),
	(2895, 743, 'dept_upd'),
	(2896, 743, 'dept_del'),
	(2897, 743, 'dept_export'),
	(2898, 743, 'dept_import'),
	(2899, 744, 'company_add'),
	(2900, 744, 'company_view'),
	(2901, 744, 'company_upd'),
	(2902, 744, 'company_del'),
	(2903, 744, 'company_export'),
	(2904, 744, 'company_import'),
	(2905, 746, 'meetingCompany_add'),
	(2906, 746, 'meetingCompany_view'),
	(2907, 746, 'meetingCompany_upd'),
	(2908, 746, 'meetingCompany_del'),
	(2909, 746, 'meetingCompany_export'),
	(2910, 746, 'meetingCompany_import'),
	(2911, 747, 'meetingType_add'),
	(2912, 747, 'meetingType_view'),
	(2913, 747, 'meetingType_upd'),
	(2914, 747, 'meetingType_del'),
	(2915, 747, 'meetingType_export'),
	(2916, 747, 'meetingType_import'),
	(2917, 748, 'meetingInfo_add'),
	(2918, 748, 'meetingInfo_view'),
	(2919, 748, 'meetingInfo_upd'),
	(2920, 748, 'meetingInfo_del'),
	(2921, 748, 'meetingInfo_export'),
	(2922, 748, 'meetingInfo_import'),
	(2923, 749, 'meetingMetmission_add'),
	(2924, 749, 'meetingMetmission_view'),
	(2925, 749, 'meetingMetmission_upd'),
	(2926, 749, 'meetingMetmission_del'),
	(2927, 749, 'meetingMetmission_export'),
	(2928, 749, 'meetingMetmission_import'),
	(2929, 750, 'meetingPerson_add'),
	(2930, 750, 'meetingPerson_view'),
	(2931, 750, 'meetingPerson_upd'),
	(2932, 750, 'meetingPerson_del'),
	(2933, 750, 'meetingPerson_export'),
	(2934, 750, 'meetingPerson_import'),
	(2935, 751, 'meetingParticipant_add'),
	(2936, 751, 'meetingParticipant_view'),
	(2937, 751, 'meetingParticipant_upd'),
	(2938, 751, 'meetingParticipant_del'),
	(2939, 751, 'meetingParticipant_export'),
	(2940, 751, 'meetingParticipant_import');
/*!40000 ALTER TABLE `t_sys_role_menu_permission` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_user 结构
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE IF NOT EXISTS `t_sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `usercode` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '用户编号',
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `open_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'openid',
  `mobile` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号码',
  `pic_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `statu` smallint(1) NOT NULL DEFAULT '0' COMMENT '0-正常，1-删除',
  `dept_id` int(11) NOT NULL DEFAULT '0' COMMENT '部门id',
  `company_code` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '单位编码',
  `contact_name` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '联系人姓名',
  `email` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'email',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_idx1_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- 正在导出表  fw-cloud.t_sys_user 的数据：~11 rows (大约)
DELETE FROM `t_sys_user`;
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` (`user_id`, `username`, `usercode`, `password`, `open_id`, `mobile`, `pic_url`, `statu`, `dept_id`, `company_code`, `contact_name`, `email`, `create_time`, `update_time`) VALUES
	(1, 'admin', 'B03B326DBD9249D58F8CEDDC008E14BC', '$2a$10$e.ufNW4gkFny3EkhAsp8qO/y8TZ8Mj0CWOqBpxuvBW4REPJHbfCMy', NULL, '13801233210', 'https://avatars0.githubusercontent.com/u/21272196?s=40&v=4', 0, 1, 'A7546D759B9A477D8A656C7D78554FDF', NULL, NULL, '2017-10-29 23:45:13', '2018-08-14 14:12:20'),
	(2, 'test', '050A0B759BC04B7989CA185E8B76FC4A', '$2a$10$bvIjvNMsFP0d.wkF2yb9puXn00.q086DInosQsCjXIA9zDINbvIBq', NULL, '15721213111', 'https://avatars0.githubusercontent.com/u/21272196?s=40&v=4', 0, 1, 'A7546D759B9A477D8A656C7D78554FDF', NULL, NULL, '2018-02-28 02:24:58', '2018-08-14 14:11:23'),
	(3, 'test2', 'B5EC9B0B41914B389B3C9FBED983209B', '$2a$10$1QLEolaGWQmXGf7woa8G1.UYT17YV3TWPG/WK9Xlc8xP70prErpsC', NULL, '13723122221', NULL, 1, 1, '', NULL, NULL, '2018-03-08 02:12:39', '2018-06-25 11:01:27'),
	(4, 'test3', '62CE725346004181AECC2AA36F3B0DBE', '$2a$10$10ntdT66NtRvsw1A0b3veu1g/JE0XGwlVHhS3i2FztgHNmOa/j/oi', NULL, '15002009676', NULL, 1, 1, '', NULL, NULL, '2018-03-09 22:42:03', '2018-06-25 00:58:09'),
	(5, 'superAdmin', '523C0B138C514A9ABE081D8CD51EB252', '$2a$10$e.ufNW4gkFny3EkhAsp8qO/y8TZ8Mj0CWOqBpxuvBW4REPJHbfCMy', NULL, '13800138000', 'https://avatars0.githubusercontent.com/u/21272196?s=40&v=4', 0, 1, 'A7546D759B9A477D8A656C7D78554FDF', NULL, NULL, '2018-04-13 19:39:11', '2018-08-14 14:12:12'),
	(8, 'yankai111', 'E13F070DB60040788BF1CD4F15C9ADA5', '$2a$10$FM01qgiFrFA4ylTeGYG/b.WW0JL3XuOTNzh5DV21YbSYxfXtN75c6', NULL, '13211232123', NULL, 1, 1, '', NULL, NULL, '2018-05-11 14:17:52', '2018-07-10 20:16:26'),
	(10, 'test22', '0C1D00AEC30D41BEB2F812A21D53B2A2', '$2a$10$6VDgrjdMzrPDOrglSOSfgOCWgCZDMXcxiPB5.ozX3EMzCExya/OBe', NULL, '13822219111', NULL, 1, 1, '', NULL, NULL, '2018-06-25 11:19:54', '2018-06-25 11:19:54'),
	(11, 'test008', '8F8DECDD1BE242549A279B9D4E9C1EFA', '$2a$10$3Gcb2Nw8V4/QA637imVRD.yibHEjmd8aboXFEXJNMXxJySZ1FwZBW', NULL, '13800138000', NULL, 1, 1, '', NULL, NULL, '2018-08-10 17:32:10', '2018-08-10 17:32:16'),
	(12, '广州会智智能科技有限公司', '370925694EC24BD180DDC5E5381D661F', '$2a$10$52PH4O7.ED/iAk27z5IOxugF2A2vtwZ1ejGe947jXhlNLoktmuun6', NULL, '13925067111', NULL, 1, 1, '', NULL, NULL, '2018-08-10 17:34:23', '2018-08-10 21:32:20'),
	(13, 'gzhz', '52BEAC45F5054E66B61CF112684375A9', '$2a$10$zWJBOefvjnLre.TD1jGn7.PIbozWiPLgiem0h/4ti8.eswyE5Aoba', NULL, '13925067111', NULL, 0, 1, 'A7546D759B9A477D8A656C7D78554FDF', NULL, NULL, '2018-08-10 21:33:17', '2018-08-14 14:12:17'),
	(14, '11', '00773C25F01F43FDB93C2C24818C2518', '$2a$10$pFv9DTwM5Ecib80Epqj74.XsDSwOW60DHkr4pcMuSHuh8KGrWmSmm', NULL, '111', NULL, 1, 1, 'A7546D759B9A477D8A656C7D78554FDF', NULL, NULL, '2018-08-14 14:18:30', '2018-08-14 14:18:42'),
	(16, 'test111', 'D9662249A80648308B8C0921A9CF3200', '$2a$10$XX3AOAbMpcdoREXjVNENu..5Y4vt9En5nkL7.dUaCOYroXckf21VO', NULL, '12313', NULL, 0, 1, 'A7546D759B9A477D8A656C7D78554FDF', 'xxx', 'xxxx', '2018-08-14 14:33:56', '2018-08-14 16:02:05'),
	(17, 'xxxxxx', '0236255E0DEF4A0BB46C88E31B775F69', '$2a$10$gKSrfcpKif8VrUERkgdyk.FuR5iE57aa.zq3sje1cAw/IRAO3CS4e', NULL, 'w222', NULL, 1, 1, 'A7546D759B9A477D8A656C7D78554FDF', '222', 'dddd', '2018-08-14 14:42:25', '2018-08-14 14:42:25');
/*!40000 ALTER TABLE `t_sys_user` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_user_role 结构
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- 正在导出表  fw-cloud.t_sys_user_role 的数据：~14 rows (大约)
DELETE FROM `t_sys_user_role`;
/*!40000 ALTER TABLE `t_sys_user_role` DISABLE KEYS */;
INSERT INTO `t_sys_user_role` (`id`, `user_id`, `role_id`) VALUES
	(24, 7, 3),
	(27, 6, 3),
	(35, 9, 3),
	(39, 4, 3),
	(43, 3, 3),
	(46, 10, 3),
	(48, 8, 3),
	(54, 11, 1),
	(56, 12, 1),
	(59, 2, 3),
	(61, 5, 1),
	(62, 13, 1),
	(63, 1, 2),
	(65, 14, 4),
	(70, 17, 3),
	(71, 16, 3);
/*!40000 ALTER TABLE `t_sys_user_role` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
