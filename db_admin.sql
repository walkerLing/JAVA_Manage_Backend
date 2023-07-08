/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.33 : Database - db_admin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_admin` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `db_admin`;

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单主键ID',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `parent_id` bigint DEFAULT NULL COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `perms` varchar(100) DEFAULT '' COMMENT '权限标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`name`,`icon`,`parent_id`,`order_num`,`path`,`component`,`menu_type`,`perms`,`create_time`,`update_time`,`remark`) values (1,'系统管理','system',0,1,'/sys','','M','','2022-07-04 14:56:29','2022-07-04 14:56:31','系统管理目录'),(2,'业务管理','monitor',0,2,'/bsns','','M','','2022-07-04 14:59:43','2022-07-04 14:59:45','业务管理目录'),(3,'用户管理','user',1,1,'/sys/user','sys/user/index','C','system:user:list','2022-07-04 15:20:51','2022-07-04 15:20:53','用户管理菜单'),(4,'角色管理','peoples',1,2,'/sys/role','sys/role/index','C','system:role:list','2022-07-04 15:23:35','2022-07-04 15:23:39','角色管理菜单'),(5,'菜单管理','tree-table',1,3,'/sys/menu','sys/menu/index','C','system:menu:list','2022-07-04 15:23:41','2022-07-04 15:23:43','菜单管理菜单'),(6,'部门管理','tree',2,1,'/bsns/department','bsns/Department','C','','2022-07-04 15:24:40','2022-07-04 15:24:44','部门管理菜单'),(7,'岗位管理','post',2,2,'/bsns/post','bsns/Post','C','','2022-07-04 15:24:42','2022-07-04 15:24:46','岗位管理菜单'),(8,'用户新增','#',3,2,'','','F','system:user:add','2022-07-04 15:24:42','2022-07-04 15:24:46','添加用户按钮'),(9,'用户修改','#',3,3,'','','F','system:user:edit','2022-07-04 15:24:42','2022-07-04 15:24:46','修改用户按钮'),(10,'用户删除','#',3,4,'','','F','system:user:delete','2022-07-04 15:24:42','2022-07-04 15:24:46','删除用户按钮'),(11,'分配角色','#',3,5,'','','F','system:user:role','2022-07-04 15:24:42','2022-07-04 15:24:46','分配角色按钮'),(12,'重置密码','#',3,6,'','','F','system:user:resetPwd','2022-07-04 15:24:42','2022-07-04 15:24:46','重置密码按钮'),(13,'角色新增','#',4,2,'','','F','system:role:add','2022-07-04 15:24:42','2022-07-04 15:24:46','添加用户按钮'),(14,'角色修改','#',4,3,'','','F','system:role:edit','2022-07-04 15:24:42','2022-07-04 15:24:46','修改用户按钮'),(15,'角色删除','#',4,4,'',NULL,'F','system:role:delete','2022-07-04 15:24:42','2022-07-04 15:24:46','删除用户按钮'),(16,'分配权限','#',4,5,'','','F','system:role:menu','2022-07-04 15:24:42','2022-07-04 15:24:46','分配权限按钮'),(17,'菜单新增','#',5,2,'',NULL,'F','system:menu:add','2022-07-04 15:24:42','2022-07-04 15:24:46','添加菜单按钮'),(18,'菜单修改','#',5,3,'',NULL,'F','system:menu:edit','2022-07-04 15:24:42','2022-07-04 15:24:46','修改菜单按钮'),(19,'菜单删除','#',5,4,'',NULL,'F','system:menu:delete','2022-07-04 15:24:42','2022-07-04 15:24:46','删除菜单按钮'),(20,'用户查询','#',3,1,'',NULL,'F','system:user:query','2022-07-04 15:24:42','2022-07-04 15:24:46','用户查询按钮'),(21,'角色查询','#',4,1,'',NULL,'F','system:role:query','2022-07-04 15:24:42','2022-07-04 15:24:46','角色查询按钮'),(22,'菜单查询','#',5,1,'',NULL,'F','system:menu:query','2022-07-04 15:24:42','2022-07-04 15:24:46','菜单查询按钮'),(33,'测速22','122',3,3,'','34','M','33','2022-08-19 03:11:20','2022-08-18 19:11:33',NULL);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色主键ID',
  `name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `code` varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`code`,`create_time`,`update_time`,`remark`) values (1,'超级管理员','admin','2022-07-04 14:40:44','2022-07-04 14:40:47','拥有系统最高权限'),(21,'人事部成员','Admin','2022-08-30 06:01:11','2023-07-08 18:07:02','拥有仅次于人事部部长的权限，新规登录、修改员工信息、查询信息权限，权限管理模块以外都可以使用。'),(22,'人事部部长','superAdmin','2023-07-08 18:06:03',NULL,'拥有最高权限，除人事部成员权限以外，还可以设定员工权限，可使用权限管理模块。'),(23,'部门经理','superUser','2023-07-09 10:07:52','2023-07-08 18:08:58','系统的数据查询权限'),(24,'普通员工','User','2023-07-08 18:09:39',NULL,'普通员工，拥有查询模块。');

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色菜单主键ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=321 DEFAULT CHARSET=utf8mb3;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`id`,`role_id`,`menu_id`) values (21,7,1),(22,7,2),(23,7,6),(24,7,7),(25,6,1),(26,6,3),(27,6,9),(28,6,10),(29,19,1),(30,19,3),(31,19,2),(32,19,6),(33,1,1),(34,1,3),(35,1,20),(36,1,8),(37,1,9),(38,1,10),(39,1,11),(40,1,12),(41,1,4),(42,1,21),(43,1,13),(44,1,14),(45,1,15),(46,1,16),(47,1,23),(48,1,5),(49,1,22),(50,1,17),(51,1,18),(52,1,19),(53,1,2),(54,1,6),(55,1,7),(208,20,1),(209,20,3),(210,20,20),(211,20,8),(212,20,9),(213,20,33),(214,20,10),(215,20,11),(216,20,4),(217,20,21),(218,20,13),(219,20,5),(220,20,22),(221,20,17),(222,20,18),(223,20,2),(224,20,6),(225,20,7),(253,2,1),(254,2,4),(255,2,21),(256,2,2),(257,2,6),(273,3,1),(274,3,4),(275,3,21),(276,3,2),(277,3,7),(278,23,1),(279,23,3),(280,23,20),(281,22,1),(282,22,3),(283,22,20),(284,22,8),(285,22,9),(286,22,33),(287,22,10),(288,22,11),(289,22,12),(290,22,4),(291,22,21),(292,22,13),(293,22,14),(294,22,15),(295,22,16),(296,22,5),(297,22,22),(298,22,17),(299,22,18),(300,22,19),(301,22,2),(302,22,6),(303,22,7),(304,21,1),(305,21,3),(306,21,20),(307,21,8),(308,21,9),(309,21,33),(310,21,10),(311,21,11),(312,21,12),(313,21,5),(314,21,22),(315,21,17),(316,21,18),(317,21,19),(318,21,2),(319,21,6),(320,21,7);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `avatar` varchar(255) DEFAULT 'default.jpg' COMMENT '用户头像',
  `email` varchar(100) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`avatar`,`email`,`phonenumber`,`login_date`,`status`,`create_time`,`update_time`,`remark`) values (1,'java1234','$2a$10$Kib4zuVhTzg3I1CoqJfd0unuY9G9ysI7cfbhyT3fi7k7Z/4pr3bGW','20220727112556000000325.jpg','caofeng4017@126.com','18862857417','2023-07-08 17:50:04','0','2022-06-09 08:47:52','2022-06-22 08:47:54','备注'),(3,'test','$2a$10$/0NqpDPsRhzqKMBEwqNL..gJqwaso.0m08HJJ5or2z.pSCJ5lDM56','333.jpg','','','2022-07-24 17:36:07','0',NULL,'2023-07-08 18:31:37',NULL),(33,'Yiming','$2a$10$dy8v8LLdH/F6uJyK9jDBnuMvw9.2svq37hVcUIYAY4hbW8xO/n1Ty','default.jpg','410796551@qq.com','17669520529',NULL,'0','2023-07-08 18:32:57',NULL,''),(34,'Fanxing','$2a$10$rynDHhCDD.NAFbY9FQO.wOP7EvSHsbnS0tx4ITA9IOh8jz3xqcxTO','default.jpg','784596521@qq.com','17748859965','2023-07-08 18:38:40','0','2023-07-08 18:34:21',NULL,''),(35,'Qihang','$2a$10$41PuHqKnwdb40UDYFRYQneJfzvrAF6smZ1cbyYuVN.JhLGBjVIxIm','default.jpg','788544122@qq.com','14458796524',NULL,'0','2023-07-08 18:35:22',NULL,''),(36,'Wangcheng','$2a$10$/fUUUQnIljYma88csZdTwOUvv9v08fDbbFBIsCjE/FsqzFYwouX0.','default.jpg','155266399@qq.com','15542263958',NULL,'0','2023-07-08 18:37:52',NULL,'');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户角色主键ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb3;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`) values (27,1,1),(32,3,24),(34,33,21),(35,34,23),(36,35,22),(37,36,21);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
