-- --------------------------------------------------------
-- 主机:                           47.106.144.24
-- 服务器版本:                        5.7.21 - MySQL Community Server (GPL)
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

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门管理';

-- 正在导出表  fw-cloud.t_sys_dept 的数据：~10 rows (大约)
DELETE FROM `t_sys_dept`;
/*!40000 ALTER TABLE `t_sys_dept` DISABLE KEYS */;
INSERT INTO `t_sys_dept` (`dept_id`, `pid`, `dept_name`, `statu`, `pos`, `create_time`, `update_time`) VALUES
	(1, 0, '一级部门', 0, 0, '2018-01-23 03:00:23', '2018-06-25 10:48:59'),
	(2, 0, '并行一级', 0, 0, '2018-01-23 03:00:38', '2018-03-08 05:47:43'),
	(3, 1, '一级子部门', 0, 0, '2018-01-23 03:00:44', '2018-06-25 10:49:32'),
	(4, 3, '一级子11', 0, 0, '2018-01-23 03:00:52', '2018-03-08 05:48:17'),
	(5, 4, '一级子111', 0, 0, '2018-01-23 03:00:57', '2018-03-08 05:49:06'),
	(6, 5, '一级子1111', 0, 0, '2018-01-23 03:01:06', '2018-03-08 05:49:08'),
	(7, 2, '并行一级子1', 0, 0, '2018-01-23 03:01:57', '2018-03-08 05:48:56'),
	(8, 7, '并行一级子11', 0, 0, '2018-01-23 03:02:03', '2018-03-08 05:48:59'),
	(9, 8, '并行一级子111', 0, 0, '2018-01-23 03:02:14', '2018-03-08 05:49:03'),
	(10, 4, '一级子1112', 1, 0, '2018-06-25 10:50:13', '2018-06-25 10:50:13');
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- 正在导出表  fw-cloud.t_sys_dict 的数据：~7 rows (大约)
DELETE FROM `t_sys_dict`;
/*!40000 ALTER TABLE `t_sys_dict` DISABLE KEYS */;
INSERT INTO `t_sys_dict` (`id`, `type`, `value`, `label`, `statu`) VALUES
	(1, 'STATU', '0', '有效', 0),
	(2, 'STATU', '1', '无效', 0),
	(3, 'DEL_FLAG', '0', '正常', 0),
	(4, 'DEL_FLAG', '1', '删除', 0),
	(5, 'SEX', '0', '男', 0),
	(6, 'SEX', '1', '女', 0),
	(7, 'Test', '1', 'ces', 1);
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
  `exception` LONGTEXT NULL COMMENT '异常信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `t_sys_log_0_create_by` (`create_by`) USING BTREE,
  KEY `t_sys_log_0_request_uri` (`request_uri`) USING BTREE,
  KEY `t_sys_log_0_type` (`type`) USING BTREE,
  KEY `t_sys_log_0_create_date` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

-- 正在导出表  fw-cloud.t_sys_log_0 的数据：~0 rows (大约)
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
  `exception` LONGTEXT NULL COMMENT '异常信息',
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- 正在导出表  fw-cloud.t_sys_menu 的数据：~14 rows (大约)
DELETE FROM `t_sys_menu`;
/*!40000 ALTER TABLE `t_sys_menu` DISABLE KEYS */;
INSERT INTO `t_sys_menu` (`menu_id`, `menu_name`, `path`, `url`, `pid`, `create_time`, `update_time`, `statu`) VALUES
	(1, '系统管理', '/admin', NULL, 0, '2018-03-09 07:56:00', '2018-04-13 00:03:41', 0),
	(2, '用户管理', 'user', '/admin/user/**', 1, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(3, '菜单管理', 'menu', '/admin/menu/**', 1, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(4, '角色管理', 'role', '/admin/role/**', 1, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(5, '日志管理', 'logs', '/admin/logs/**', 1, '2018-03-09 07:56:00', '2018-04-13 07:04:20', 0),
	(6, '字典管理', 'dict', '/admin/dict/**', 1, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(7, '部门管理', 'dept', '/admin/dept/**', 1, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(8, '系统监控', '/monitor', NULL, 0, '2018-03-09 07:56:00', '2018-04-13 00:03:43', 0),
	(9, '服务监控', 'server', '/admin/server/**', 8, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(10, 'zipkin监控', 'zipkin', '/admin/zipkin/**', 8, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(11, 'pinpoint监控', 'pinpoint', '/admin/pinpoint/**', 8, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(12, '缓存状态', 'cache', '/admin/cache/**', 8, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(13, 'ELK状态', 'elk', '/admin/elk/**', 8, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0),
	(14, '接口文档', 'swagger', '/admin/swagger/**', 8, '2018-03-09 07:56:00', '2018-03-09 07:56:00', 0);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  fw-cloud.t_sys_role 的数据：~3 rows (大约)
DELETE FROM `t_sys_role`;
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` (`role_id`, `role_name`, `role_code`, `role_desc`, `create_time`, `update_time`, `statu`) VALUES
	(1, 'SUPER管理员', 'ROLE_SUPER_ADMIN', '超级管理员', '2018-04-13 19:37:40', '2018-04-13 19:37:55', 0),
	(2, '系统管理员', 'ROLE_ADMIN', '系统管理员', '2018-03-09 07:56:00', '2018-06-25 11:02:02', 0),
	(3, '测试Test', 'ROLE_TEST', '测试权限', '2018-03-09 07:56:00', '2018-06-24 23:35:10', 0);
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_role_dept 结构
DROP TABLE IF EXISTS `t_sys_role_dept`;
CREATE TABLE IF NOT EXISTS `t_sys_role_dept` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` int(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色与部门对应关系';

-- 正在导出表  fw-cloud.t_sys_role_dept 的数据：~3 rows (大约)
DELETE FROM `t_sys_role_dept`;
/*!40000 ALTER TABLE `t_sys_role_dept` DISABLE KEYS */;
INSERT INTO `t_sys_role_dept` (`id`, `role_id`, `dept_id`) VALUES
	(1, 1, 1),
	(4, 3, 4),
	(5, 2, 3);
/*!40000 ALTER TABLE `t_sys_role_dept` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_role_menu 结构
DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE IF NOT EXISTS `t_sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- 正在导出表  fw-cloud.t_sys_role_menu 的数据：~22 rows (大约)
DELETE FROM `t_sys_role_menu`;
/*!40000 ALTER TABLE `t_sys_role_menu` DISABLE KEYS */;
INSERT INTO `t_sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES
	(188, 1, 1),
	(189, 1, 2),
	(190, 1, 3),
	(191, 1, 4),
	(192, 1, 5),
	(193, 1, 6),
	(194, 1, 7),
	(195, 1, 8),
	(196, 1, 9),
	(197, 1, 10),
	(198, 1, 11),
	(199, 1, 12),
	(200, 1, 13),
	(201, 1, 14),
	(204, 2, 2),
	(205, 2, 3),
	(206, 2, 4),
	(207, 2, 5),
	(208, 2, 6),
	(209, 2, 7),
	(210, 3, 3),
	(211, 3, 6);
/*!40000 ALTER TABLE `t_sys_role_menu` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_role_menu_permission 结构
DROP TABLE IF EXISTS `t_sys_role_menu_permission`;
CREATE TABLE IF NOT EXISTS `t_sys_role_menu_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_menu_id` int(11) NOT NULL,
  `permission` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=499 DEFAULT CHARSET=utf8 COMMENT='功能权限表';

-- 正在导出表  fw-cloud.t_sys_role_menu_permission 的数据：~81 rows (大约)
DELETE FROM `t_sys_role_menu_permission`;
/*!40000 ALTER TABLE `t_sys_role_menu_permission` DISABLE KEYS */;
INSERT INTO `t_sys_role_menu_permission` (`id`, `role_menu_id`, `permission`) VALUES
	(416, 189, 'user_add'),
	(417, 189, 'user_view'),
	(418, 189, 'user_upd'),
	(419, 189, 'user_del'),
	(420, 189, 'user_export'),
	(421, 189, 'user_import'),
	(422, 190, 'menu_add'),
	(423, 190, 'menu_view'),
	(424, 190, 'menu_upd'),
	(425, 190, 'menu_del'),
	(426, 190, 'menu_export'),
	(427, 190, 'menu_import'),
	(428, 191, 'role_add'),
	(429, 191, 'role_view'),
	(430, 191, 'role_upd'),
	(431, 191, 'role_del'),
	(432, 191, 'role_export'),
	(433, 191, 'role_import'),
	(434, 192, 'logs_add'),
	(435, 192, 'logs_view'),
	(436, 192, 'logs_upd'),
	(437, 192, 'logs_del'),
	(438, 192, 'logs_export'),
	(439, 192, 'logs_import'),
	(440, 193, 'dict_add'),
	(441, 193, 'dict_view'),
	(442, 193, 'dict_upd'),
	(443, 193, 'dict_del'),
	(444, 193, 'dict_export'),
	(445, 193, 'dict_import'),
	(446, 194, 'dept_add'),
	(447, 194, 'dept_view'),
	(448, 194, 'dept_upd'),
	(449, 194, 'dept_del'),
	(450, 194, 'dept_export'),
	(451, 194, 'dept_import'),
	(452, 196, 'server_add'),
	(453, 196, 'server_view'),
	(454, 196, 'server_upd'),
	(455, 196, 'server_del'),
	(456, 196, 'server_export'),
	(457, 196, 'server_import'),
	(458, 197, 'zipkin_add'),
	(459, 197, 'zipkin_view'),
	(460, 197, 'zipkin_upd'),
	(461, 197, 'zipkin_del'),
	(462, 197, 'zipkin_export'),
	(463, 197, 'zipkin_import'),
	(464, 198, 'pinpoint_add'),
	(465, 198, 'pinpoint_view'),
	(466, 198, 'pinpoint_upd'),
	(467, 198, 'pinpoint_del'),
	(468, 198, 'pinpoint_export'),
	(469, 198, 'pinpoint_import'),
	(470, 199, 'cache_add'),
	(471, 199, 'cache_view'),
	(472, 199, 'cache_upd'),
	(473, 199, 'cache_del'),
	(474, 199, 'cache_export'),
	(475, 199, 'cache_import'),
	(476, 200, 'elk_add'),
	(477, 200, 'elk_view'),
	(478, 200, 'elk_upd'),
	(479, 200, 'elk_del'),
	(480, 200, 'elk_export'),
	(481, 200, 'elk_import'),
	(482, 201, 'swagger_add'),
	(483, 201, 'swagger_view'),
	(484, 201, 'swagger_upd'),
	(485, 201, 'swagger_del'),
	(486, 201, 'swagger_export'),
	(487, 201, 'swagger_import'),
	(490, 204, 'user_view'),
	(491, 205, 'menu_view'),
	(492, 206, 'role_view'),
	(493, 207, 'logs_view'),
	(494, 208, 'dict_view'),
	(495, 208, 'dict_upd'),
	(496, 209, 'dept_view'),
	(497, 210, 'menu_view'),
	(498, 211, 'dict_view');
/*!40000 ALTER TABLE `t_sys_role_menu_permission` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_user 结构
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE IF NOT EXISTS `t_sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `open_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'openid',
  `mobile` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号码',
  `pic_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `statu` smallint(1) NOT NULL DEFAULT '0' COMMENT '0-正常，1-删除',
  `dept_id` int(11) NOT NULL DEFAULT '0' COMMENT '部门id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_idx1_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- 正在导出表  fw-cloud.t_sys_user 的数据：~8 rows (大约)
DELETE FROM `t_sys_user`;
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` (`user_id`, `username`, `password`, `open_id`, `mobile`, `pic_url`, `statu`, `dept_id`, `create_time`, `update_time`) VALUES
	(1, 'admin', '$2a$10$e.ufNW4gkFny3EkhAsp8qO/y8TZ8Mj0CWOqBpxuvBW4REPJHbfCMy', NULL, '13801233210', 'https://avatars0.githubusercontent.com/u/21272196?s=40&v=4', 0, 3, '2017-10-29 23:45:13', '2018-06-25 11:02:14'),
	(2, 'test', '$2a$10$bvIjvNMsFP0d.wkF2yb9puXn00.q086DInosQsCjXIA9zDINbvIBq', NULL, '15721213111', 'https://avatars0.githubusercontent.com/u/21272196?s=40&v=4', 0, 4, '2018-02-28 02:24:58', '2018-06-25 11:01:38'),
	(3, 'test2', '$2a$10$1QLEolaGWQmXGf7woa8G1.UYT17YV3TWPG/WK9Xlc8xP70prErpsC', NULL, '13723122221', NULL, 0, 4, '2018-03-08 02:12:39', '2018-06-25 11:01:27'),
	(4, 'test3', '$2a$10$10ntdT66NtRvsw1A0b3veu1g/JE0XGwlVHhS3i2FztgHNmOa/j/oi', NULL, '15002009676', NULL, 0, 4, '2018-03-09 22:42:03', '2018-06-25 00:58:09'),
	(5, 'superAdmin', '$2a$10$e.ufNW4gkFny3EkhAsp8qO/y8TZ8Mj0CWOqBpxuvBW4REPJHbfCMy', NULL, '13800138000', 'https://avatars0.githubusercontent.com/u/21272196?s=40&v=4', 0, 1, '2018-04-13 19:39:11', '2018-06-25 00:24:58'),
	(8, 'yankai', '$2a$10$FM01qgiFrFA4ylTeGYG/b.WW0JL3XuOTNzh5DV21YbSYxfXtN75c6', NULL, '13211232123', NULL, 0, 4, '2018-05-11 14:17:52', '2018-06-25 09:39:50'),
	(9, 'testlw', '$2a$10$xM8a4AHkR3HxJBZ8kN2U1eAWbqKMdr1r8iz328XR302e53qakQvsu', NULL, NULL, NULL, 1, 0, '2018-05-18 01:15:40', '2018-06-21 19:11:54'),
	(10, 'test22', '$2a$10$6VDgrjdMzrPDOrglSOSfgOCWgCZDMXcxiPB5.ozX3EMzCExya/OBe', NULL, '13822219111', NULL, 0, 4, '2018-06-25 11:19:54', '2018-06-25 11:19:54');
/*!40000 ALTER TABLE `t_sys_user` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_user_role 结构
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- 正在导出表  fw-cloud.t_sys_user_role 的数据：~10 rows (大约)
DELETE FROM `t_sys_user_role`;
/*!40000 ALTER TABLE `t_sys_user_role` DISABLE KEYS */;
INSERT INTO `t_sys_user_role` (`id`, `user_id`, `role_id`) VALUES
	(24, 7, 3),
	(27, 6, 3),
	(35, 9, 3),
	(37, 5, 1),
	(39, 4, 3),
	(42, 8, 3),
	(43, 3, 3),
	(44, 2, 3),
	(45, 1, 2),
	(46, 10, 3);
/*!40000 ALTER TABLE `t_sys_user_role` ENABLE KEYS */;

-- 导出  表 fw-cloud.zipkin_annotations 结构
DROP TABLE IF EXISTS `zipkin_annotations`;
CREATE TABLE IF NOT EXISTS `zipkin_annotations` (
  `trace_id_high` bigint(20) NOT NULL DEFAULT '0' COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` bigint(20) NOT NULL COMMENT 'coincides with zipkin_spans.trace_id',
  `span_id` bigint(20) NOT NULL COMMENT 'coincides with zipkin_spans.id',
  `a_key` varchar(255) NOT NULL COMMENT 'BinaryAnnotation.key or Annotation.value if type == -1',
  `a_value` blob COMMENT 'BinaryAnnotation.value(), which must be smaller than 64KB',
  `a_type` int(11) NOT NULL COMMENT 'BinaryAnnotation.type() or -1 if Annotation',
  `a_timestamp` bigint(20) DEFAULT NULL COMMENT 'Used to implement TTL; Annotation.timestamp or zipkin_spans.timestamp',
  `endpoint_ipv4` int(11) DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_ipv6` binary(16) DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null, or no IPv6 address',
  `endpoint_port` smallint(6) DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_service_name` varchar(255) DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null',
  UNIQUE KEY `trace_id_high` (`trace_id_high`,`trace_id`,`span_id`,`a_key`,`a_timestamp`) COMMENT 'Ignore insert on duplicate',
  KEY `trace_id_high_2` (`trace_id_high`,`trace_id`,`span_id`) COMMENT 'for joining with zipkin_spans',
  KEY `trace_id_high_3` (`trace_id_high`,`trace_id`) COMMENT 'for getTraces/ByIds',
  KEY `endpoint_service_name` (`endpoint_service_name`) COMMENT 'for getTraces and getServiceNames',
  KEY `a_type` (`a_type`) COMMENT 'for getTraces',
  KEY `a_key` (`a_key`) COMMENT 'for getTraces',
  KEY `trace_id` (`trace_id`,`span_id`,`a_key`) COMMENT 'for dependencies job'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED;


-- 导出  表 fw-cloud.zipkin_dependencies 结构
DROP TABLE IF EXISTS `zipkin_dependencies`;
CREATE TABLE IF NOT EXISTS `zipkin_dependencies` (
  `day` date NOT NULL,
  `parent` varchar(255) NOT NULL,
  `child` varchar(255) NOT NULL,
  `call_count` bigint(20) DEFAULT NULL,
  `error_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `day` (`day`,`parent`,`child`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED;


-- 导出  表 fw-cloud.zipkin_spans 结构
DROP TABLE IF EXISTS `zipkin_spans`;
CREATE TABLE IF NOT EXISTS `zipkin_spans` (
  `trace_id_high` bigint(20) NOT NULL DEFAULT '0' COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `debug` bit(1) DEFAULT NULL,
  `start_ts` bigint(20) DEFAULT NULL COMMENT 'Span.timestamp(): epoch micros used for endTs query and to implement TTL',
  `duration` bigint(20) DEFAULT NULL COMMENT 'Span.duration(): micros used for minDuration and maxDuration query',
  UNIQUE KEY `trace_id_high` (`trace_id_high`,`trace_id`,`id`) COMMENT 'ignore insert on duplicate',
  KEY `trace_id_high_2` (`trace_id_high`,`trace_id`,`id`) COMMENT 'for joining with zipkin_annotations',
  KEY `trace_id_high_3` (`trace_id_high`,`trace_id`) COMMENT 'for getTracesByIds',
  KEY `name` (`name`) COMMENT 'for getTraces and getSpanNames',
  KEY `start_ts` (`start_ts`) COMMENT 'for getTraces ordering and range'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

SET FOREIGN_KEY_CHECKS = 1;
