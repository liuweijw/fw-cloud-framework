-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.21 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
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
  `name` varchar(64) DEFAULT NULL COMMENT '部门名称',
  `pid` int(11) DEFAULT NULL COMMENT '上级部门',
  `pos` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` smallint(1) DEFAULT '0' COMMENT '是否删除  1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门管理';

-- 正在导出表  fw-cloud.t_sys_dept 的数据：~9 rows (大约)
DELETE FROM `t_sys_dept`;
/*!40000 ALTER TABLE `t_sys_dept` DISABLE KEYS */;
INSERT INTO `t_sys_dept` (`id`, `name`, `pid`, `pos`, `create_time`, `update_time`, `del_flag`) VALUES
	(1, '一级', 0, NULL, '2018-01-22 19:00:23', '2018-03-07 21:47:28', 0),
	(2, '并行一级', 0, NULL, '2018-01-22 19:00:38', '2018-03-07 21:47:43', 0),
	(3, '一级子1', 1, NULL, '2018-01-22 19:00:44', '2018-03-07 21:48:10', 0),
	(4, '一级子11', 3, NULL, '2018-01-22 19:00:52', '2018-03-07 21:48:17', 0),
	(5, '一级子111', 4, NULL, '2018-01-22 19:00:57', '2018-03-07 21:49:06', 0),
	(6, '一级子1111', 5, NULL, '2018-01-22 19:01:06', '2018-03-07 21:49:08', 0),
	(7, '并行一级子1', 2, NULL, '2018-01-22 19:01:57', '2018-03-07 21:48:56', 0),
	(8, '并行一级子11', 7, NULL, '2018-01-22 19:02:03', '2018-03-07 21:48:59', 0),
	(9, '并行一级子111', 8, NULL, '2018-01-22 19:02:14', '2018-03-07 21:49:03', 0);
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


-- 导出  表 fw-cloud.t_sys_log_0 结构
DROP TABLE IF EXISTS `t_sys_log_0`;
CREATE TABLE IF NOT EXISTS `t_sys_log_0` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `time` mediumtext COMMENT '执行时间',
  `del_flag` smallint(6) DEFAULT '0' COMMENT '删除标记',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `t_sys_log_0_create_by` (`create_by`) USING BTREE,
  KEY `t_sys_log_0_request_uri` (`request_uri`) USING BTREE,
  KEY `t_sys_log_0_type` (`type`) USING BTREE,
  KEY `t_sys_log_0_create_date` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=178457802618961921 DEFAULT CHARSET=utf8 COMMENT='日志表';

-- 正在导出表  fw-cloud.t_sys_log_0 的数据：~11 rows (大约)
DELETE FROM `t_sys_log_0`;
/*!40000 ALTER TABLE `t_sys_log_0` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_log_0` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_log_1 结构
DROP TABLE IF EXISTS `t_sys_log_1`;
CREATE TABLE IF NOT EXISTS `t_sys_log_1` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `time` mediumtext COMMENT '执行时间',
  `del_flag` smallint(6) DEFAULT '0' COMMENT '删除标记',
  `exception` text COMMENT '异常信息',
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
  `name` varchar(32) NOT NULL COMMENT '菜单名称',
  `permission` varchar(32) DEFAULT NULL COMMENT '菜单权限标识',
  `path` varchar(128) DEFAULT NULL COMMENT '前端URL',
  `url` varchar(128) DEFAULT NULL COMMENT '请求链接',
  `method` varchar(32) DEFAULT NULL COMMENT '请求方法',
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID',
  `icon` varchar(32) DEFAULT NULL COMMENT '图标',
  `component` varchar(64) DEFAULT NULL COMMENT 'VUE页面',
  `sort` int(11) DEFAULT NULL COMMENT '排序值',
  `type` char(1) DEFAULT NULL COMMENT '菜单类型 （0菜单 1按钮）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` smallint(6) DEFAULT '0' COMMENT '0--正常 1--删除',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- 正在导出表  fw-cloud.t_sys_menu 的数据：~6 rows (大约)
DELETE FROM `t_sys_menu`;
/*!40000 ALTER TABLE `t_sys_menu` DISABLE KEYS */;
INSERT INTO `t_sys_menu` (`menu_id`, `name`, `permission`, `path`, `url`, `method`, `parent_id`, `icon`, `component`, `sort`, `type`, `create_time`, `update_time`, `del_flag`) VALUES
	(1, '系统管理', NULL, '/admin', NULL, NULL, -1, NULL, 'Layout', NULL, '0', '2017-11-07 20:56:00', '2017-11-14 14:26:03', 0),
	(2, '用户管理', NULL, 'user', NULL, NULL, 1, NULL, '_import(\'admin/user\')', NULL, '0', '2017-11-02 22:24:37', '2017-11-14 15:22:40', 0),
	(3, '菜单管理', NULL, 'menu', NULL, NULL, 1, NULL, '_import(\'admin/menu\')', NULL, '0', '2017-11-08 09:57:27', '2017-11-14 15:22:45', 0),
	(4, '角色管理', NULL, 'role', NULL, NULL, 1, NULL, '_import(\'admin/role\')', NULL, '0', '2017-11-08 10:13:37', '2017-11-14 15:22:51', 0),
	(5, '日志管理', NULL, 'log', NULL, NULL, 1, NULL, '_import(\'admin/log\')', NULL, '0', '2017-11-20 14:06:22', '2017-11-20 14:14:11', 0),
	(6, '字典管理', NULL, 'dict', NULL, NULL, 1, NULL, '_import(\'admin/dict\')', NULL, '0', '2017-11-29 11:30:52', '2017-11-29 11:31:15', 0),
	(7, '部门管理', NULL, 'dept', NULL, NULL, 1, NULL, '_import(\'admin/dept\')', NULL, '0', '2018-01-20 13:17:19', '2018-03-08 10:36:27', 0),
	(21, '用户查看', 't_sys_user_view', NULL, '/admin/user/**', 'GET', 2, NULL, NULL, NULL, '1', '2017-11-07 20:58:05', '2018-03-08 10:44:59', 0),
	(22, '用户新增', 't_sys_user_add', NULL, '/admin/user/*', 'POST', 2, NULL, NULL, NULL, '1', '2017-11-08 09:52:09', '2017-12-04 16:31:10', 0),
	(23, '用户修改', 't_sys_user_upd', NULL, '/admin/user/**', 'PUT', 2, NULL, NULL, NULL, '1', '2017-11-08 09:52:48', '2018-01-17 17:40:01', 0),
	(24, '用户删除', 't_sys_user_del', NULL, '/admin/user/*', 'DELETE', 2, NULL, NULL, NULL, '1', '2017-11-08 09:54:01', '2017-12-04 16:31:18', 0),
	(31, '菜单查看', 't_sys_menu_view', NULL, '/admin/menu/**', 'GET', 3, NULL, NULL, NULL, '1', '2017-11-08 09:57:56', '2018-02-28 15:37:17', 0),
	(32, '菜单新增', 't_sys_menu_add', NULL, '/admin/menu/*', 'POST', 3, NULL, NULL, NULL, '1', '2017-11-08 10:15:53', '2018-01-20 14:37:50', 0),
	(33, '菜单修改', 't_sys_menu_edit', NULL, '/admin/menu/*', 'PUT', 3, NULL, NULL, NULL, '1', '2017-11-08 10:16:23', '2018-01-20 14:37:56', 0),
	(34, '菜单删除', 't_sys_menu_del', NULL, '/admin/menu/*', 'DELETE', 3, NULL, NULL, NULL, '1', '2017-11-08 10:16:43', '2018-01-20 14:38:03', 0),
	(41, '角色查看', 't_sys_role_view', NULL, '/admin/role/**', 'GET', 4, NULL, NULL, NULL, '1', '2017-11-08 10:14:01', '2018-03-08 10:45:44', 0),
	(42, '角色新增', 't_sys_role_add', NULL, '/admin/role/*', 'POST', 4, NULL, NULL, NULL, '1', '2017-11-08 10:14:18', '2018-02-28 15:37:40', 0),
	(43, '角色修改', 't_sys_role_edit', NULL, '/admin/role/*', 'PUT', 4, NULL, NULL, NULL, '1', '2017-11-08 10:14:41', '2018-02-28 15:37:48', 0),
	(44, '角色删除', 't_sys_role_del', NULL, '/admin/role/*', 'DELETE', 4, NULL, NULL, NULL, '1', '2017-11-08 10:14:59', '2018-02-28 15:37:56', 0),
	(45, '部门查看', 't_sys_dept_view', NULL, '/admin/dept/*', 'GET', 7, NULL, NULL, NULL, '1', '2017-11-08 10:14:59', '2018-02-28 15:37:56', 0),
	(46, '部门新增', 't_sys_dept_add', NULL, '/admin/dept/*', 'POST', 7, NULL, NULL, NULL, '1', '2017-11-08 10:14:59', '2018-02-28 15:37:56', 0),
	(47, '部门修改', 't_sys_dept_edit', NULL, '/admin/dept/*', 'PUT', 7, NULL, NULL, NULL, '1', '2017-11-08 10:14:59', '2018-02-28 15:37:56', 0),
	(48, '部门删除', 't_sys_dept_del', NULL, '/admin/dept/*', 'DELETE', 7, NULL, NULL, NULL, '1', '2017-11-08 10:14:59', '2018-02-28 15:37:56', 0);
/*!40000 ALTER TABLE `t_sys_menu` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_role 结构
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE IF NOT EXISTS `t_sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) COLLATE utf8mb4_bin NOT NULL,
  `role_code` varchar(64) COLLATE utf8mb4_bin NOT NULL,
  `role_desc` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_idx1_role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 正在导出表  fw-cloud.t_sys_role 的数据：~2 rows (大约)
DELETE FROM `t_sys_role`;
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` (`role_id`, `role_name`, `role_code`, `role_desc`, `create_time`, `update_time`, `del_flag`) VALUES
	(1, 'admin', 'ROLE_ADMIN', '超级管理员', '2017-10-29 15:45:51', '2018-02-07 15:12:38', '0'),
	(2, 'test', 'ROLE_TEST', '测试', '2018-02-27 18:25:38', '2018-02-28 09:57:16', '0');
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
  `sid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- 正在导出表  fw-cloud.t_sys_role_menu 的数据：~26 rows (大约)
DELETE FROM `t_sys_role_menu`;
/*!40000 ALTER TABLE `t_sys_role_menu` DISABLE KEYS */;
INSERT INTO `t_sys_role_menu` (`sid`, `role_id`, `menu_id`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 1, 3),
	(4, 1, 4),
	(5, 1, 5),
	(6, 1, 6),
	(7, 1, 7),
	(15, 1, 21),
	(16, 1, 22),
	(17, 1, 23),
	(18, 1, 24),
	(19, 1, 31),
	(20, 1, 32),
	(21, 1, 33),
	(22, 1, 34),
	(23, 1, 41),
	(24, 1, 42),
	(25, 1, 43),
	(26, 1, 44),
	(27, 1, 45),
	(28, 1, 46),
	(29, 1, 47),
	(30, 1, 48);
/*!40000 ALTER TABLE `t_sys_role_menu` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_user 结构
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE IF NOT EXISTS `t_sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL,
  `open_id` varchar(255) DEFAULT NULL COMMENT 'openid',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门Id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` smallint(1) DEFAULT '0' COMMENT '0-正常，1-删除',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_idx1_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 正在导出表  fw-cloud.t_sys_user 的数据：~2 rows (大约)
DELETE FROM `t_sys_user`;
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` (`user_id`, `username`, `password`, `open_id`, `mobile`, `avatar`, `dept_id`, `create_time`, `update_time`, `del_flag`) VALUES
	(1, 'admin', '$2a$10$YAdC4T/fF9emBtmlGrZj4uxVj6yZmyGXzaNBO3W2PBN.PcGK6IWPC', NULL, '', 'https://avatars0.githubusercontent.com/u/21272196?s=40&v=4', 1, '2017-10-29 15:45:13', '2018-03-08 10:46:59', 0),
	(2, 'test', '$2a$10$bvIjvNMsFP0d.wkF2yb9puXn00.q086DInosQsCjXIA9zDINbvIBq', NULL, NULL, 'https://avatars0.githubusercontent.com/u/21272196?s=40&v=4', 1, '2018-02-27 18:24:58', '2018-03-07 21:46:00', 0),
	(3, '添加用户2', '$2a$10$1QLEolaGWQmXGf7woa8G1.UYT17YV3TWPG/WK9Xlc8xP70prErpsC', NULL, NULL, NULL, 1, '2018-03-07 18:12:39', '2018-03-07 21:45:20', 0);
/*!40000 ALTER TABLE `t_sys_user` ENABLE KEYS */;


-- 导出  表 fw-cloud.t_sys_user_role 结构
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- 正在导出表  fw-cloud.t_sys_user_role 的数据：~3 rows (大约)
DELETE FROM `t_sys_user_role`;
/*!40000 ALTER TABLE `t_sys_user_role` DISABLE KEYS */;
INSERT INTO `t_sys_user_role` (`id`, `user_id`, `role_id`) VALUES
	(4, 3, 1),
	(5, 2, 1),
	(6, 1, 1);
/*!40000 ALTER TABLE `t_sys_user_role` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
