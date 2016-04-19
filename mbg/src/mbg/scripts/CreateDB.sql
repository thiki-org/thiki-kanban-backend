/*
Navicat MySQL Data Transfer

Source Server         : root_localhost
Source Server Version : 50511
Source Host           : localhost:3306
Source Database       : thiki

Target Server Type    : MYSQL
Target Server Version : 50511
File Encoding         : 65001

Date: 2016-04-19 12:23:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for th_entry
-- ----------------------------
DROP TABLE IF EXISTS `th_entry`;
CREATE TABLE `th_entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(127) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
