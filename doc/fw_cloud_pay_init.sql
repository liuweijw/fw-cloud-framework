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

-- 导出 fw-cloud-pay 的数据库结构
DROP DATABASE IF EXISTS `fw-cloud-pay`;
CREATE DATABASE IF NOT EXISTS `fw-cloud-pay` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fw-cloud-pay`;

-- 导出  表 fw-cloud-pay.t_pay_channel 结构
DROP TABLE IF EXISTS `t_pay_channel`;
CREATE TABLE IF NOT EXISTS `t_pay_channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '渠道主键ID',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道ID',
  `channel_name` varchar(32) NOT NULL COMMENT '渠道名称,如:alipay,wechat',
  `channel_mch_id` varchar(32) NOT NULL COMMENT '渠道商户ID',
  `mch_id` varchar(32) NOT NULL COMMENT '商户ID',
  `statu` smallint(1) NOT NULL DEFAULT '1' COMMENT '渠道状态,0-停止使用,1-使用中',
  `param` varchar(4096) NOT NULL COMMENT '配置参数,json字符串',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_mchid_channelid` (`channel_id`,`mch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='支付渠道表';

-- 正在导出表  fw-cloud-pay.t_pay_channel 的数据：~1 rows (大约)
DELETE FROM `t_pay_channel`;
/*!40000 ALTER TABLE `t_pay_channel` DISABLE KEYS */;
INSERT INTO `t_pay_channel` (`id`, `channel_id`, `channel_name`, `channel_mch_id`, `mch_id`, `statu`, `param`, `remark`, `create_time`, `update_time`) VALUES
	(1, 'WX_JSAPI', 'WX', '111111', '10000001', 1, '{"mchId":"111111", "appId":"xxxsss", "key":"xxxxxx", "certLocalPath":"wx/apiclient_cert.p12", "certPassword":"xxxxxx"}', '', '2017-11-20 10:08:54', '2018-05-16 13:41:34');
/*!40000 ALTER TABLE `t_pay_channel` ENABLE KEYS */;


-- 导出  表 fw-cloud-pay.t_pay_iap_receipt 结构
DROP TABLE IF EXISTS `t_pay_iap_receipt`;
CREATE TABLE IF NOT EXISTS `t_pay_iap_receipt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pay_order_id` varchar(32) NOT NULL COMMENT '支付订单号',
  `mch_id` varchar(32) NOT NULL COMMENT '商户ID',
  `transaction_id` varchar(32) NOT NULL COMMENT 'IAP业务号',
  `status` smallint(1) NOT NULL DEFAULT '0' COMMENT '处理状态:0-未处理,1-处理成功,-1-处理失败',
  `handle_count` smallint(1) NOT NULL DEFAULT '0' COMMENT '处理次数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `receipt_data` text NOT NULL COMMENT '渠道ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_pay_iap_payorderid` (`pay_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='苹果支付凭据表';

-- 正在导出表  fw-cloud-pay.t_pay_iap_receipt 的数据：~0 rows (大约)
DELETE FROM `t_pay_iap_receipt`;
/*!40000 ALTER TABLE `t_pay_iap_receipt` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_pay_iap_receipt` ENABLE KEYS */;


-- 导出  表 fw-cloud-pay.t_pay_mch_info 结构
DROP TABLE IF EXISTS `t_pay_mch_info`;
CREATE TABLE IF NOT EXISTS `t_pay_mch_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `mch_id` varchar(32) NOT NULL COMMENT '商户ID',
  `mch_name` varchar(32) NOT NULL COMMENT '名称',
  `type` varchar(24) NOT NULL COMMENT '类型',
  `req_key` varchar(128) NOT NULL COMMENT '请求私钥',
  `res_key` varchar(128) NOT NULL COMMENT '响应私钥',
  `statu` smallint(1) NOT NULL DEFAULT '1' COMMENT '商户状态,0-停止使用,1-使用中',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_t_mch_info_mch_id` (`mch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='商户信息表';

-- 正在导出表  fw-cloud-pay.t_pay_mch_info 的数据：~1 rows (大约)
DELETE FROM `t_pay_mch_info`;
/*!40000 ALTER TABLE `t_pay_mch_info` DISABLE KEYS */;
INSERT INTO `t_pay_mch_info` (`id`, `mch_id`, `mch_name`, `type`, `req_key`, `res_key`, `statu`, `create_time`, `update_time`) VALUES
	(1, '10000001', '广东xxxxx', '1', 'REQKEY_xxxxxx', 'RESKEY_xxxxx', 1, '2018-03-19 11:07:55', '2018-05-16 13:42:19');
/*!40000 ALTER TABLE `t_pay_mch_info` ENABLE KEYS */;


-- 导出  表 fw-cloud-pay.t_pay_mch_notify 结构
DROP TABLE IF EXISTS `t_pay_mch_notify`;
CREATE TABLE IF NOT EXISTS `t_pay_mch_notify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `mch_id` varchar(32) NOT NULL COMMENT '商户ID',
  `mch_order_no` varchar(32) NOT NULL COMMENT '商户订单号',
  `order_type` varchar(8) NOT NULL COMMENT '订单类型:1-支付,2-转账,3-退款',
  `notify_url` varchar(2048) NOT NULL COMMENT '通知地址',
  `notify_count` smallint(1) NOT NULL DEFAULT '0' COMMENT '通知次数',
  `result` varchar(2048) DEFAULT NULL COMMENT '通知响应结果',
  `status` smallint(1) NOT NULL DEFAULT '1' COMMENT '通知状态,1-通知中,2-通知成功,3-通知失败',
  `last_notify_time` datetime DEFAULT NULL COMMENT '最后一次通知时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_mchid_ordertype_mchorderno` (`mch_id`,`order_type`,`mch_order_no`),
  UNIQUE KEY `index_notify_orderid` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户通知表';

-- 正在导出表  fw-cloud-pay.t_pay_mch_notify 的数据：~0 rows (大约)
DELETE FROM `t_pay_mch_notify`;
/*!40000 ALTER TABLE `t_pay_mch_notify` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_pay_mch_notify` ENABLE KEYS */;


-- 导出  表 fw-cloud-pay.t_pay_order 结构
DROP TABLE IF EXISTS `t_pay_order`;
CREATE TABLE IF NOT EXISTS `t_pay_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pay_order_id` varchar(32) NOT NULL COMMENT '支付订单号',
  `mch_id` varchar(32) NOT NULL COMMENT '商户ID',
  `mch_order_no` varchar(32) NOT NULL COMMENT '商户订单号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道ID',
  `amount` bigint(20) NOT NULL COMMENT '支付金额,单位分',
  `currency` varchar(3) NOT NULL DEFAULT 'cny' COMMENT '三位货币代码,人民币:cny',
  `status` smallint(1) NOT NULL DEFAULT '0' COMMENT '支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成',
  `ip` varchar(32) DEFAULT NULL COMMENT '客户端IP',
  `device` varchar(64) DEFAULT NULL COMMENT '设备',
  `subject` varchar(64) NOT NULL COMMENT '商品标题',
  `body` varchar(256) NOT NULL COMMENT '商品描述信息',
  `extra` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
  `channel_mch_id` varchar(32) NOT NULL COMMENT '渠道商户ID',
  `channel_order_no` varchar(64) DEFAULT NULL COMMENT '渠道订单号',
  `errcode` varchar(64) DEFAULT NULL COMMENT '渠道支付错误码',
  `errmsg` varchar(128) DEFAULT NULL COMMENT '渠道支付错误描述',
  `param1` varchar(64) DEFAULT NULL COMMENT '扩展参数1',
  `param2` varchar(64) DEFAULT NULL COMMENT '扩展参数2',
  `notify_url` varchar(128) NOT NULL COMMENT '通知地址',
  `notify_count` smallint(1) NOT NULL DEFAULT '0' COMMENT '通知次数',
  `last_notify_time` bigint(20) DEFAULT NULL COMMENT '最后一次通知时间',
  `expire_time` bigint(20) DEFAULT NULL COMMENT '订单失效时间',
  `pay_succ_time` bigint(20) DEFAULT NULL COMMENT '订单支付成功时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_payorderid` (`pay_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='支付订单表';

-- 正在导出表  fw-cloud-pay.t_pay_order 的数据：~0 rows (大约)
DELETE FROM `t_pay_order`;
/*!40000 ALTER TABLE `t_pay_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_pay_order` ENABLE KEYS */;


-- 导出  表 fw-cloud-pay.t_pay_refund_order 结构
DROP TABLE IF EXISTS `t_pay_refund_order`;
CREATE TABLE IF NOT EXISTS `t_pay_refund_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `refund_order_id` varchar(32) NOT NULL COMMENT '退款订单号',
  `mch_refund_no` varchar(32) NOT NULL COMMENT '商户退款单号',
  `mch_id` varchar(32) NOT NULL COMMENT '商户ID',
  `pay_order_id` varchar(32) NOT NULL COMMENT '支付订单号',
  `channel_payorderno` varchar(64) DEFAULT NULL COMMENT '渠道支付单号',
  `channel_id` varchar(24) NOT NULL COMMENT '渠道ID',
  `pay_amount` bigint(20) NOT NULL COMMENT '支付金额,单位分',
  `refund_amount` bigint(20) NOT NULL COMMENT '退款金额,单位分',
  `currency` varchar(3) NOT NULL DEFAULT 'cny' COMMENT '三位货币代码,人民币:cny',
  `status` smallint(1) NOT NULL DEFAULT '0' COMMENT '退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成',
  `result` smallint(1) NOT NULL DEFAULT '0' COMMENT '退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败',
  `ip` varchar(32) DEFAULT NULL COMMENT '客户端IP',
  `device` varchar(64) DEFAULT NULL COMMENT '设备',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `channel_user` varchar(32) DEFAULT NULL COMMENT '渠道用户标识,如微信openId,支付宝账号',
  `user_name` varchar(24) DEFAULT NULL COMMENT '用户姓名',
  `channel_mch_id` varchar(32) NOT NULL COMMENT '渠道商户ID',
  `channel_order_no` varchar(32) DEFAULT NULL COMMENT '渠道订单号',
  `channel_errcode` varchar(128) DEFAULT NULL COMMENT '渠道错误码',
  `channel_errmsg` varchar(128) DEFAULT NULL COMMENT '渠道错误描述',
  `extra` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
  `notify_url` varchar(128) NOT NULL COMMENT '通知地址',
  `param1` varchar(64) DEFAULT NULL COMMENT '扩展参数1',
  `param2` varchar(64) DEFAULT NULL COMMENT '扩展参数2',
  `expire_time` datetime DEFAULT NULL COMMENT '订单失效时间',
  `refund_succ_time` datetime DEFAULT NULL COMMENT '订单退款成功时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_mchid_mchrefundno` (`mch_id`,`mch_refund_no`),
  UNIQUE KEY `index_refund_order_id` (`refund_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='退款订单表';

-- 正在导出表  fw-cloud-pay.t_pay_refund_order 的数据：~0 rows (大约)
DELETE FROM `t_pay_refund_order`;
/*!40000 ALTER TABLE `t_pay_refund_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_pay_refund_order` ENABLE KEYS */;


-- 导出  表 fw-cloud-pay.t_pay_trans_order 结构
DROP TABLE IF EXISTS `t_pay_trans_order`;
CREATE TABLE IF NOT EXISTS `t_pay_trans_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trans_order_id` varchar(32) NOT NULL COMMENT '转账订单号',
  `mch_id` varchar(32) NOT NULL COMMENT '商户ID',
  `mch_trans_no` varchar(32) NOT NULL COMMENT '商户转账单号',
  `channel_id` varchar(32) NOT NULL COMMENT '渠道ID',
  `amount` bigint(20) NOT NULL COMMENT '转账金额,单位分',
  `currency` varchar(3) NOT NULL DEFAULT 'cny' COMMENT '三位货币代码,人民币:cny',
  `status` smallint(1) NOT NULL DEFAULT '0' COMMENT '转账状态:0-订单生成,1-转账中,2-转账成功,3-转账失败,4-业务处理完成',
  `result` smallint(1) NOT NULL DEFAULT '0' COMMENT '转账结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败',
  `ip` varchar(32) DEFAULT NULL COMMENT '客户端IP',
  `device` varchar(64) DEFAULT NULL COMMENT '设备',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `channel_user` varchar(32) DEFAULT NULL COMMENT '渠道用户标识,如微信openId,支付宝账号',
  `user_name` varchar(24) DEFAULT NULL COMMENT '用户姓名',
  `channel_mch_id` varchar(32) NOT NULL COMMENT '渠道商户ID',
  `channel_order_no` varchar(32) DEFAULT NULL COMMENT '渠道订单号',
  `channel_errcode` varchar(128) DEFAULT NULL COMMENT '渠道错误码',
  `channel_errmsg` varchar(128) DEFAULT NULL COMMENT '渠道错误描述',
  `extra` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
  `notify_url` varchar(128) NOT NULL COMMENT '通知地址',
  `param1` varchar(64) DEFAULT NULL COMMENT '扩展参数1',
  `param2` varchar(64) DEFAULT NULL COMMENT '扩展参数2',
  `expire_time` datetime DEFAULT NULL COMMENT '订单失效时间',
  `trans_succ_time` datetime DEFAULT NULL COMMENT '订单转账成功时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_trans_order_id` (`trans_order_id`),
  UNIQUE KEY `index_mch_id_mchorderno` (`mch_id`,`mch_trans_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='转账订单表';

-- 正在导出表  fw-cloud-pay.t_pay_trans_order 的数据：~0 rows (大约)
DELETE FROM `t_pay_trans_order`;
/*!40000 ALTER TABLE `t_pay_trans_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_pay_trans_order` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
