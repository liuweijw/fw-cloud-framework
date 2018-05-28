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

-- 导出 fw-cloud-wechat 的数据库结构
DROP DATABASE IF EXISTS `fw-cloud-wechat`;
CREATE DATABASE IF NOT EXISTS `fw-cloud-wechat` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fw-cloud-wechat`;

-- 导出  表 fw-cloud-wechat.t_wechat_auth_info 结构
DROP TABLE IF EXISTS `t_wechat_auth_info`;
CREATE TABLE IF NOT EXISTS `t_wechat_auth_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(64) NOT NULL,
  `wechat_id` varchar(32) NOT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `sex_desc` varchar(11) DEFAULT NULL,
  `sex_int` int(11) DEFAULT '0',
  `language` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `country` varchar(64) DEFAULT NULL,
  `head_imgurl` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  fw-cloud-wechat.t_wechat_auth_info 的数据：~0 rows (大约)
DELETE FROM `t_wechat_auth_info`;
/*!40000 ALTER TABLE `t_wechat_auth_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_wechat_auth_info` ENABLE KEYS */;


-- 导出  表 fw-cloud-wechat.t_wechat_info 结构
DROP TABLE IF EXISTS `t_wechat_info`;
CREATE TABLE IF NOT EXISTS `t_wechat_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `wechat_id` varchar(32) NOT NULL COMMENT '公众号ID',
  `wechat_name` varchar(32) NOT NULL COMMENT '公众号名称',
  `req_key` varchar(128) NOT NULL COMMENT '请求私钥',
  `res_key` varchar(128) NOT NULL COMMENT '响应私钥',
  `statu` smallint(1) NOT NULL DEFAULT '1' COMMENT '状态,0-停止使用,1-使用中',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_t_wechat_info_wechat_id` (`wechat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='公众号信息表';

-- 正在导出表  fw-cloud-wechat.t_wechat_info 的数据：~1 rows (大约)
DELETE FROM `t_wechat_info`;
/*!40000 ALTER TABLE `t_wechat_info` DISABLE KEYS */;
INSERT INTO `t_wechat_info` (`id`, `wechat_id`, `wechat_name`, `req_key`, `res_key`, `statu`, `create_time`, `update_time`) VALUES
	(1, '10000001', '广东xxxx', 'REQKEY_xxxx', 'RESKEY_xxxxx', 1, '2018-04-03 14:40:20', '2018-04-03 14:41:17');
/*!40000 ALTER TABLE `t_wechat_info` ENABLE KEYS */;


-- 导出  表 fw-cloud-wechat.t_wechat_url 结构
DROP TABLE IF EXISTS `t_wechat_url`;
CREATE TABLE IF NOT EXISTS `t_wechat_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(32) DEFAULT NULL,
  `url` varchar(510) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_uuid` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  fw-cloud-wechat.t_wechat_url 的数据：~0 rows (大约)
DELETE FROM `t_wechat_url`;
/*!40000 ALTER TABLE `t_wechat_url` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_wechat_url` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
