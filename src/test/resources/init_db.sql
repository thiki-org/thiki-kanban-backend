-- ----------------------------
-- Table structure for kb_entry
-- ----------------------------
DROP TABLE IF EXISTS `kb_entry`;
CREATE TABLE `kb_entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text,
  `reporter` int(11) NOT NULL,
  `deleteStatus` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for kb_task
-- ----------------------------
DROP TABLE IF EXISTS `kb_task`;
CREATE TABLE `kb_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `summary` varchar(1023) NOT NULL,
  `content` text,
  `assignee` int(11) DEFAULT NULL,
  `reporter` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
