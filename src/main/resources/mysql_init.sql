-- ----------------------------
-- Table structure for kb_entry
-- ----------------------------
DROP TABLE IF EXISTS `kb_entry`;
CREATE TABLE `kb_entry` (
  `id`            VARCHAR(40) NOT NULL,
  `title`         TEXT,
  `reporter`      INT(11)     NOT NULL,
  `delete_status` INT(11) DEFAULT '0',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_task
-- ----------------------------
DROP TABLE IF EXISTS `kb_task`;
CREATE TABLE `kb_task` (
  `id`       VARCHAR(40)   NOT NULL,
  `summary`  VARCHAR(1023) NOT NULL,
  `content`  TEXT,
  `assignee` INT(11) DEFAULT NULL,
  `reporter` INT(11) DEFAULT NULL,
  `entry_id` VARCHAR(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
