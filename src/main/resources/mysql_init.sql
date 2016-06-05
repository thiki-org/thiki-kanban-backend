-- ----------------------------
-- Table structure for kb_user
-- ----------------------------
DROP TABLE IF EXISTS `kb_user`;
CREATE TABLE kb_user
(
  id varchar(64) NOT NULL COMMENT '编号',
  login_name varchar(100) NOT NULL COMMENT '登录名',
  password varchar(100) NOT NULL COMMENT '密码',
  name varchar(100) NOT NULL COMMENT '姓名',
  email varchar(200) COMMENT '邮箱',
  delete_status INT(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
  creation_time     DATETIME             DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) COMMENT = '用户表';


-- ----------------------------
-- Table structure for kb_board
-- ----------------------------
DROP TABLE IF EXISTS `kb_board`;
CREATE TABLE `kb_board` (
  `id`              VARCHAR(40) NOT NULL,
  `name`            TEXT,
  `reporter`        INT(11)     NOT NULL,
  `delete_status`   INT(11)              DEFAULT '0',
  order_number      INT(11)     NOT NULL DEFAULT '0',
  creation_time     DATETIME             DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_entry
-- ----------------------------
DROP TABLE IF EXISTS `kb_entry`;
CREATE TABLE `kb_entry` (
  `id`              VARCHAR(40) NOT NULL,
  `title`           TEXT,
  `reporter`        INT(11)     NOT NULL,
  `delete_status`   INT(11)              DEFAULT '0',
  order_number      INT(11)     NOT NULL DEFAULT '0',
  `board_id`        VARCHAR(40)          DEFAULT NULL,
  creation_time     DATETIME             DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_task
-- ----------------------------
DROP TABLE IF EXISTS `kb_task`;
CREATE TABLE `kb_task` (
  `id`              VARCHAR(40)   NOT NULL,
  `summary`         VARCHAR(1023) NOT NULL,
  `content`         TEXT,
  order_number      INT(11)       NOT NULL  DEFAULT '0',
  `reporter`        INT(11)                 DEFAULT NULL,
  `entry_id`        VARCHAR(40)             DEFAULT NULL,
  creation_time     DATETIME                DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  `delete_status`   INT(11)                 DEFAULT '0',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
