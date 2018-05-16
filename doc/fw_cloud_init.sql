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
-- 导出  表 fw-cloud.t_sys_dept 结构
DROP TABLE IF EXISTS `t_sys_dept`;
CREATE TABLE IF NOT EXISTS `t_sys_dept` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL COMMENT '上级部门',
  `dept_name` varchar(64) NOT NULL COMMENT '部门名称',
  `statu` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除  1：已删除  0：正常',
  `pos` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门管理';

-- 正在导出表  fw-cloud.t_sys_dept 的数据：~9 rows (大约)
DELETE FROM `t_sys_dept`;
/*!40000 ALTER TABLE `t_sys_dept` DISABLE KEYS */;
INSERT INTO `t_sys_dept` (`id`, `pid`, `dept_name`, `statu`, `pos`, `create_time`, `update_time`) VALUES
	(1, 0, '一级', 0, 0, '2018-01-22 19:00:23', '2018-03-07 21:47:28'),
	(2, 0, '并行一级', 0, 0, '2018-01-22 19:00:38', '2018-03-07 21:47:43'),
	(3, 1, '一级子1', 0, 0, '2018-01-22 19:00:44', '2018-03-07 21:48:10'),
	(4, 3, '一级子11', 0, 0, '2018-01-22 19:00:52', '2018-03-07 21:48:17'),
	(5, 4, '一级子111', 0, 0, '2018-01-22 19:00:57', '2018-03-07 21:49:06'),
	(6, 5, '一级子1111', 0, 0, '2018-01-22 19:01:06', '2018-03-07 21:49:08'),
	(7, 2, '并行一级子1', 0, 0, '2018-01-22 19:01:57', '2018-03-07 21:48:56'),
	(8, 7, '并行一级子11', 0, 0, '2018-01-22 19:02:03', '2018-03-07 21:48:59'),
	(9, 8, '并行一级子111', 0, 0, '2018-01-22 19:02:14', '2018-03-07 21:49:03');
/*!40000 ALTER TABLE `t_sys_dept` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_dept_relation 结构
DROP TABLE IF EXISTS `t_sys_dept_relation`;
CREATE TABLE IF NOT EXISTS `t_sys_dept_relation` (
  `pre_id` int(11) NOT NULL COMMENT '节点',
  `after_id` int(11) NOT NULL COMMENT '节点',
  PRIMARY KEY (`pre_id`,`after_id`),
  KEY `pre_id` (`pre_id`),
  KEY `after_id` (`after_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- 正在导出表  fw-cloud.t_sys_dict 的数据：~6 rows (大约)
DELETE FROM `t_sys_dict`;
/*!40000 ALTER TABLE `t_sys_dict` DISABLE KEYS */;
INSERT INTO `t_sys_dict` (`id`, `type`, `value`, `label`, `statu`) VALUES
	(1, 'STATU', '0', '有效', 0),
	(2, 'STATU', '1', '无效', 0),
	(3, 'DEL_FLAG', '0', '正常', 0),
	(4, 'DEL_FLAG', '1', '删除', 0),
	(5, 'SEX', '0', '男', 0),
	(6, 'SEX', '1', '女', 0);
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
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `time` mediumtext COMMENT '执行时间',
  `statu` smallint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `exception` text COMMENT '异常信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `t_sys_log_0_create_by` (`create_by`) USING BTREE,
  KEY `t_sys_log_0_request_uri` (`request_uri`) USING BTREE,
  KEY `t_sys_log_0_type` (`type`) USING BTREE,
  KEY `t_sys_log_0_create_date` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=179631651272261633 DEFAULT CHARSET=utf8 COMMENT='日志表';

-- 正在导出表  fw-cloud.t_sys_log_0 的数据：~291 rows (大约)
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
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `time` mediumtext COMMENT '执行时间',
  `statu` smallint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `exception` text COMMENT '异常信息',
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
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  `menu_name` varchar(32) NOT NULL COMMENT '菜单名称',
  `path` varchar(128) DEFAULT NULL COMMENT '前端URL',
  `url` varchar(128) DEFAULT NULL COMMENT '请求链接',
  `pid` int(11) DEFAULT NULL COMMENT '父菜单ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `statu` smallint(1) DEFAULT '0' COMMENT '0--正常 1--删除',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- 正在导出表  fw-cloud.t_sys_menu 的数据：~15 rows (大约)
DELETE FROM `t_sys_menu`;
/*!40000 ALTER TABLE `t_sys_menu` DISABLE KEYS */;
INSERT INTO `t_sys_menu` (`menu_id`, `menu_name`, `path`, `url`, `pid`, `create_time`, `update_time`, `statu`) VALUES
	(1, '系统管理', '/admin', NULL, 0, '2018-03-08 23:56:00', '2018-04-12 16:03:41', 0),
	(2, '用户管理', 'user', '/admin/user/**', 1, '2018-03-08 23:56:00', '2018-03-08 23:56:00', 0),
	(3, '菜单管理', 'menu', '/admin/menu/**', 1, '2018-03-08 23:56:00', '2018-03-08 23:56:00', 0),
	(4, '角色管理', 'role', '/admin/role/**', 1, '2018-03-08 23:56:00', '2018-03-08 23:56:00', 0),
	(5, '日志管理', 'logs', '/admin/logs/**', 1, '2018-03-08 23:56:00', '2018-04-12 23:04:20', 0),
	(6, '字典管理', 'dict', '/admin/dict/**', 1, '2018-03-08 23:56:00', '2018-03-08 23:56:00', 0),
	(7, '部门管理', 'dept', '/admin/dept/**', 1, '2018-03-08 23:56:00', '2018-03-08 23:56:00', 0),
	(8, '系统监控', '/monitor', NULL, 0, '2018-03-08 23:56:00', '2018-04-12 16:03:43', 0),
	(9, '服务监控', 'server', '/admin/server/**', 8, '2018-03-08 23:56:00', '2018-03-08 23:56:00', 0),
	(10, 'zipkin监控', 'zipkin', '/admin/zipkin/**', 8, '2018-03-08 23:56:00', '2018-03-08 23:56:00', 0),
	(11, 'pinpoint监控', 'pinpoint', '/admin/pinpoint/**', 8, '2018-03-08 23:56:00', '2018-03-08 23:56:00', 0),
	(12, '缓存状态', 'cache', '/admin/cache/**', 8, '2018-03-08 23:56:00', '2018-03-08 23:56:00', 0),
	(13, 'ELK状态', 'elk', '/admin/elk/**', 8, '2018-03-08 23:56:00', '2018-03-08 23:56:00', 0),
	(14, '接口文档', 'swagger', '/admin/swagger/**', 8, '2018-03-08 23:56:00', '2018-03-08 23:56:00', 0);
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

-- 正在导出表  fw-cloud.t_sys_role 的数据：~3 rows (大约)
DELETE FROM `t_sys_role`;
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` (`role_id`, `role_name`, `role_code`, `role_desc`, `create_time`, `update_time`, `statu`) VALUES
	(1, 'SUPER管理员', 'ROLE_SUPER_ADMIN', '超级管理员', '2018-04-13 11:37:40', '2018-04-13 11:37:55', 0),
	(2, '超级管理员', 'ROLE_ADMIN', '系统管理员', '2018-03-08 23:56:00', '2018-04-13 11:38:01', 0),
	(3, '测试Test', 'ROLE_TEST', '测试权限', '2018-03-08 23:56:00', '2018-05-16 13:16:41', 0);
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_role_dept 结构
DROP TABLE IF EXISTS `t_sys_role_dept`;
CREATE TABLE IF NOT EXISTS `t_sys_role_dept` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` int(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色与部门对应关系';

-- 正在导出表  fw-cloud.t_sys_role_dept 的数据：~0 rows (大约)
DELETE FROM `t_sys_role_dept`;
/*!40000 ALTER TABLE `t_sys_role_dept` DISABLE KEYS */;
INSERT INTO `t_sys_role_dept` (`id`, `role_id`, `dept_id`) VALUES
	(1, 1, 1);
/*!40000 ALTER TABLE `t_sys_role_dept` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_role_menu 结构
DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE IF NOT EXISTS `t_sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- 正在导出表  fw-cloud.t_sys_role_menu 的数据：~15 rows (大约)
DELETE FROM `t_sys_role_menu`;
/*!40000 ALTER TABLE `t_sys_role_menu` DISABLE KEYS */;
INSERT INTO `t_sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES
	(124, 2, 2),
	(125, 2, 3),
	(126, 2, 4),
	(127, 1, 2),
	(128, 1, 3),
	(129, 1, 4),
	(130, 1, 5),
	(131, 1, 6),
	(132, 1, 7),
	(133, 1, 9),
	(134, 1, 10),
	(135, 1, 11),
	(136, 1, 12),
	(137, 1, 13),
	(138, 1, 14);
/*!40000 ALTER TABLE `t_sys_role_menu` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_role_menu_permission 结构
DROP TABLE IF EXISTS `t_sys_role_menu_permission`;
CREATE TABLE IF NOT EXISTS `t_sys_role_menu_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_menu_id` int(11) NOT NULL,
  `permission` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=257 DEFAULT CHARSET=utf8 COMMENT='功能权限表';

-- 正在导出表  fw-cloud.t_sys_role_menu_permission 的数据：~84 rows (大约)
DELETE FROM `t_sys_role_menu_permission`;
/*!40000 ALTER TABLE `t_sys_role_menu_permission` DISABLE KEYS */;
INSERT INTO `t_sys_role_menu_permission` (`id`, `role_menu_id`, `permission`) VALUES
	(169, 124, 'user_add'),
	(170, 124, 'user_view'),
	(171, 124, 'user_upd'),
	(172, 124, 'user_del'),
	(173, 125, 'menu_add'),
	(174, 125, 'menu_view'),
	(175, 125, 'menu_upd'),
	(176, 125, 'menu_del'),
	(177, 126, 'role_add'),
	(178, 126, 'role_view'),
	(179, 126, 'role_upd'),
	(180, 126, 'role_del'),
	(181, 127, 'user_add'),
	(182, 127, 'user_view'),
	(183, 127, 'user_upd'),
	(184, 127, 'user_del'),
	(185, 127, 'user_export'),
	(186, 127, 'user_import'),
	(187, 128, 'menu_add'),
	(188, 128, 'menu_view'),
	(189, 128, 'menu_upd'),
	(190, 128, 'menu_del'),
	(191, 128, 'menu_export'),
	(192, 128, 'menu_import'),
	(193, 129, 'role_add'),
	(194, 129, 'role_view'),
	(195, 129, 'role_upd'),
	(196, 129, 'role_del'),
	(197, 129, 'role_export'),
	(198, 129, 'role_import'),
	(199, 130, 'logs_add'),
	(200, 130, 'logs_view'),
	(201, 130, 'logs_upd'),
	(202, 130, 'logs_del'),
	(203, 130, 'logs_export'),
	(204, 130, 'logs_import'),
	(205, 131, 'dict_add'),
	(206, 131, 'dict_view'),
	(207, 131, 'dict_upd'),
	(208, 131, 'dict_del'),
	(209, 131, 'dict_export'),
	(210, 131, 'dict_import'),
	(211, 132, 'dept_add'),
	(212, 132, 'dept_view'),
	(213, 132, 'dept_upd'),
	(214, 132, 'dept_del'),
	(215, 132, 'dept_export'),
	(216, 132, 'dept_import'),
	(217, 133, 'server_add'),
	(218, 133, 'server_view'),
	(219, 133, 'server_upd'),
	(220, 133, 'server_del'),
	(221, 133, 'server_export'),
	(222, 133, 'server_import'),
	(223, 134, 'zipkin_add'),
	(224, 134, 'zipkin_view'),
	(225, 134, 'zipkin_upd'),
	(226, 134, 'zipkin_del'),
	(227, 134, 'zipkin_export'),
	(228, 134, 'zipkin_import'),
	(229, 135, 'pinpoint_add'),
	(230, 135, 'pinpoint_view'),
	(231, 135, 'pinpoint_upd'),
	(232, 135, 'pinpoint_del'),
	(233, 135, 'pinpoint_export'),
	(234, 135, 'pinpoint_import'),
	(235, 136, 'cache_add'),
	(236, 136, 'cache_view'),
	(237, 136, 'cache_upd'),
	(238, 136, 'cache_del'),
	(239, 136, 'cache_export'),
	(240, 136, 'cache_import'),
	(241, 137, 'elk_add'),
	(242, 137, 'elk_view'),
	(243, 137, 'elk_upd'),
	(244, 137, 'elk_del'),
	(245, 137, 'elk_export'),
	(246, 137, 'elk_import'),
	(247, 138, 'swagger_add'),
	(248, 138, 'swagger_view'),
	(249, 138, 'swagger_upd'),
	(250, 138, 'swagger_del'),
	(251, 138, 'swagger_export'),
	(252, 138, 'swagger_import');
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
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_idx1_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- 正在导出表  fw-cloud.t_sys_user 的数据：~6 rows (大约)
DELETE FROM `t_sys_user`;
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` (`user_id`, `username`, `password`, `open_id`, `mobile`, `pic_url`, `statu`, `create_time`, `update_time`) VALUES
	(1, 'admin', '$2a$10$e.ufNW4gkFny3EkhAsp8qO/y8TZ8Mj0CWOqBpxuvBW4REPJHbfCMy', NULL, '13801233210', 'https://avatars0.githubusercontent.com/u/21272196?s=40&v=4', 0, '2017-10-29 15:45:13', '2018-05-04 15:12:44'),
	(2, 'test', '$2a$10$bvIjvNMsFP0d.wkF2yb9puXn00.q086DInosQsCjXIA9zDINbvIBq', NULL, NULL, 'https://avatars0.githubusercontent.com/u/21272196?s=40&v=4', 0, '2018-02-27 18:24:58', '2018-05-03 08:38:13'),
	(3, 'test2', '$2a$10$1QLEolaGWQmXGf7woa8G1.UYT17YV3TWPG/WK9Xlc8xP70prErpsC', NULL, NULL, NULL, 0, '2018-03-07 18:12:39', '2018-04-24 17:28:46'),
	(4, 'test3', '$2a$10$10ntdT66NtRvsw1A0b3veu1g/JE0XGwlVHhS3i2FztgHNmOa/j/oi', NULL, '15002009676', NULL, 0, '2018-03-09 14:42:03', '2018-05-15 19:49:54'),
	(5, 'superAdmin', '$2a$10$e.ufNW4gkFny3EkhAsp8qO/y8TZ8Mj0CWOqBpxuvBW4REPJHbfCMy', NULL, '13800138000', 'https://avatars0.githubusercontent.com/u/21272196?s=40&v=4', 0, '2018-04-13 11:39:11', '2018-05-07 14:10:43'),
	(8, 'yankai', '$2a$10$FM01qgiFrFA4ylTeGYG/b.WW0JL3XuOTNzh5DV21YbSYxfXtN75c6', NULL, NULL, NULL, 1, '2018-05-11 06:17:52', '2018-05-11 06:17:52');
/*!40000 ALTER TABLE `t_sys_user` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_user_role 结构
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- 正在导出表  fw-cloud.t_sys_user_role 的数据：~7 rows (大约)
DELETE FROM `t_sys_user_role`;
/*!40000 ALTER TABLE `t_sys_user_role` DISABLE KEYS */;
INSERT INTO `t_sys_user_role` (`id`, `user_id`, `role_id`) VALUES
	(9, 3, 2),
	(16, 2, 2),
	(18, 1, 2),
	(20, 5, 1),
	(24, 7, 3),
	(27, 6, 3),
	(28, 8, 3);
/*!40000 ALTER TABLE `t_sys_user_role` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
