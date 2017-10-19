/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 5.5.28 : Database - daiba
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`daiba` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `daiba`;

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `school` varchar(30) NOT NULL DEFAULT '长春工业大学' COMMENT '学校',
  `campus` varchar(20) NOT NULL COMMENT '校区',
  `build` varchar(20) NOT NULL COMMENT '楼栋',
  `room` varchar(10) NOT NULL COMMENT '房间号',
  PRIMARY KEY (`id`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `address` */

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(4) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '管理员',
  `name` varchar(20) NOT NULL COMMENT '管理员姓名',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `wx` varchar(20) NOT NULL COMMENT '微信',
  `qq` varchar(20) NOT NULL COMMENT 'qq',
  `email` varchar(30) NOT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

/*Table structure for table `bringer` */

DROP TABLE IF EXISTS `bringer`;

CREATE TABLE `bringer` (
  `id` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '带客号',
  `accept_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '接单数',
  `creditworthiness` double NOT NULL DEFAULT '0' COMMENT '信誉程度',
  `bad_rep_count` int(10) NOT NULL DEFAULT '0' COMMENT '差评数',
  `medium_rep_count` int(10) NOT NULL DEFAULT '0' COMMENT '中评数',
  `good_rep_count` int(10) NOT NULL DEFAULT '0' COMMENT '好评数',
  `income` double NOT NULL DEFAULT '0' COMMENT '收入',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bringer` */

/*Table structure for table `consignee_message` */

DROP TABLE IF EXISTS `consignee_message`;

CREATE TABLE `consignee_message` (
  `conmsg_id` int(11) NOT NULL AUTO_INCREMENT,
  `use_id` int(10) NOT NULL,
  `call_name` varchar(20) NOT NULL COMMENT '称呼',
  `phonenum` varchar(20) NOT NULL COMMENT '电话',
  `school` varchar(30) NOT NULL DEFAULT '长春工业大学' COMMENT '地址(学校)',
  `campus` varchar(20) DEFAULT NULL COMMENT '地址(校区)',
  `build` varchar(20) DEFAULT NULL COMMENT '地址(楼栋)',
  `room` varchar(10) DEFAULT NULL COMMENT '地址(房间号)',
  PRIMARY KEY (`conmsg_id`),
  KEY `use_id` (`use_id`),
  CONSTRAINT `consignee_message_ibfk_1` FOREIGN KEY (`use_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `consignee_message` */

/*Table structure for table `firm` */

DROP TABLE IF EXISTS `firm`;

CREATE TABLE `firm` (
  `id` varchar(32) NOT NULL COMMENT '业务流水id',
  `bri_id` int(10) unsigned zerofill DEFAULT NULL COMMENT '带客id',
  `use_id` int(10) NOT NULL COMMENT '用户id',
  `ord_id` int(10) unsigned zerofill NOT NULL COMMENT '订单id',
  `give_time` datetime NOT NULL COMMENT '发单时间',
  `accept_time` datetime DEFAULT NULL COMMENT '接单时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `order_state` int(2) NOT NULL COMMENT '订单状态(未接单、已接单、已完成、已取消)',
  `address` varchar(80) NOT NULL COMMENT '送达地点',
  `ask_time` datetime NOT NULL COMMENT '要求送达时间',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `order_money` double NOT NULL COMMENT '订单金额',
  `accept_campus` varchar(20) NOT NULL COMMENT '取件校区',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_15` (`bri_id`),
  KEY `FK_Reference_5` (`use_id`),
  KEY `FK_Reference_6` (`ord_id`),
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`bri_id`) REFERENCES `bringer` (`id`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`use_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`ord_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `firm` */

/*Table structure for table `firm_status` */

DROP TABLE IF EXISTS `firm_status`;

CREATE TABLE `firm_status` (
  `fir_id` varchar(32) NOT NULL COMMENT '流水号',
  `adm_id` int(4) unsigned zerofill DEFAULT NULL COMMENT '管理员id',
  `sta_id` int(2) unsigned zerofill NOT NULL COMMENT '业务状态（正常、锁定、无效）',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `remake` varchar(100) DEFAULT NULL COMMENT '备注 必填',
  KEY `FK_Reference_13` (`fir_id`),
  KEY `FK_Reference_14` (`adm_id`),
  KEY `FK_Reference_16` (`sta_id`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`fir_id`) REFERENCES `firm` (`id`),
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`adm_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `FK_Reference_16` FOREIGN KEY (`sta_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `firm_status` */

/*Table structure for table `option` */

DROP TABLE IF EXISTS `option`;

CREATE TABLE `option` (
  `id` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '意见编号',
  `use_id` int(10) NOT NULL COMMENT '反馈用户id',
  `option_title` varchar(20) NOT NULL COMMENT '标题',
  `option_content` varchar(100) NOT NULL COMMENT '内容',
  `resp_time` datetime NOT NULL COMMENT '反馈时间',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_2` (`use_id`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`use_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `option` */

/*Table structure for table `option_status` */

DROP TABLE IF EXISTS `option_status`;

CREATE TABLE `option_status` (
  `opt_id` int(10) unsigned zerofill NOT NULL COMMENT '意见编号',
  `adm_id` int(4) unsigned zerofill DEFAULT NULL COMMENT '管理员id',
  `sta_id` int(2) unsigned zerofill NOT NULL COMMENT '状态(未处理,处理中,已处理,不予处理)',
  `finish_time` datetime DEFAULT NULL COMMENT '完结时间',
  `remake` varchar(200) DEFAULT NULL COMMENT '操作说明',
  KEY `FK_Reference_11` (`opt_id`),
  KEY `FK_Reference_12` (`adm_id`),
  KEY `FK_Reference_17` (`sta_id`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`opt_id`) REFERENCES `option` (`id`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`adm_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `FK_Reference_17` FOREIGN KEY (`sta_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `option_status` */

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '订单号',
  `sta_id` int(2) unsigned zerofill NOT NULL COMMENT '类型码',
  `company` varchar(50) NOT NULL COMMENT '公司(快递公司，店铺等)',
  `receiver` varchar(20) NOT NULL COMMENT '收件人',
  `accept_add` varchar(100) NOT NULL COMMENT '取件地址',
  `take_num` varchar(10) NOT NULL COMMENT '取件号',
  `phonenum` varchar(20) DEFAULT NULL COMMENT '预留手机号',
  PRIMARY KEY (`id`),
  KEY `type_status` (`sta_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`sta_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

/*Table structure for table `qualification` */

DROP TABLE IF EXISTS `qualification`;

CREATE TABLE `qualification` (
  `use_id` int(10) DEFAULT NULL COMMENT '用户id',
  `bri_id` int(10) unsigned zerofill NOT NULL COMMENT '带客id',
  `adm_id` int(4) unsigned zerofill NOT NULL COMMENT '管理员id',
  `student_num` varchar(20) NOT NULL COMMENT '学号',
  `human_id` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `apply_time` datetime NOT NULL COMMENT '申请时间',
  `sta_id` int(2) unsigned zerofill NOT NULL COMMENT '审核状态',
  `student_card_front` varchar(200) NOT NULL COMMENT '学生证正面',
  `student_card_reverse` varchar(200) NOT NULL COMMENT '学生证反面',
  `id_card_front` varchar(200) DEFAULT NULL COMMENT '身份证正面',
  `id_card_reverse` varchar(200) DEFAULT NULL COMMENT '身份证反面',
  `check_time` datetime DEFAULT NULL COMMENT '认证时间',
  KEY `FK_Reference_19` (`sta_id`),
  KEY `FK_Reference_3` (`use_id`),
  KEY `FK_Reference_7` (`bri_id`),
  KEY `FK_Reference_9` (`adm_id`),
  CONSTRAINT `FK_Reference_19` FOREIGN KEY (`sta_id`) REFERENCES `status` (`id`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`use_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`bri_id`) REFERENCES `bringer` (`id`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`adm_id`) REFERENCES `admin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qualification` */

/*Table structure for table `status` */

DROP TABLE IF EXISTS `status`;

CREATE TABLE `status` (
  `id` int(2) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '状态码',
  `fields` varchar(10) NOT NULL COMMENT '字段',
  `description` varchar(20) NOT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `status` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `phone_num` varchar(20) NOT NULL COMMENT '手机号，用作登录名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `name` varchar(50) NOT NULL COMMENT '默认手机号',
  `portrait` varchar(200) NOT NULL COMMENT '头像',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `role` varchar(20) NOT NULL DEFAULT '普通用户' COMMENT '角色',
  `recetly_login_time` datetime NOT NULL COMMENT '最近登录时间(默认注册时间)',
  `order_num` int(10) NOT NULL DEFAULT '0' COMMENT '发单数',
  `spending` double NOT NULL DEFAULT '0' COMMENT '总支出',
  `sex` int(2) NOT NULL DEFAULT '0' COMMENT '性别',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

/*Table structure for table `user_status` */

DROP TABLE IF EXISTS `user_status`;

CREATE TABLE `user_status` (
  `use_id` int(10) NOT NULL COMMENT '用户id',
  `adm_id` int(4) unsigned zerofill DEFAULT NULL COMMENT '管理员id',
  `sta_id` int(2) unsigned zerofill NOT NULL COMMENT '状态码id',
  `lock_time` datetime DEFAULT NULL COMMENT '锁定时间',
  `clear_time` datetime DEFAULT NULL COMMENT '解锁时间',
  `lock_msg` varchar(50) DEFAULT NULL COMMENT '锁定说明',
  `user_appeal_msg` varchar(200) DEFAULT NULL COMMENT '用户申诉说明',
  KEY `FK_Reference_10` (`adm_id`),
  KEY `FK_Reference_18` (`sta_id`),
  KEY `FK_Reference_4` (`use_id`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`adm_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `FK_Reference_18` FOREIGN KEY (`sta_id`) REFERENCES `status` (`id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`use_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_status` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
