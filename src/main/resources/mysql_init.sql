-- ----------------------------
-- Table structure for kb_entry
-- ----------------------------
DROP TABLE IF EXISTS `kb_entry`;
CREATE TABLE `kb_entry` (
  `id`              VARCHAR(40) NOT NULL,
  `title`           TEXT,
  `reporter`        INT(11)     NOT NULL,
  `delete_status`   INT(11)  DEFAULT '0',
  creation_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
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
  `assignee`        INT(11)     DEFAULT NULL,
  `reporter`        INT(11)     DEFAULT NULL,
  `entry_id`        VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

  USE kanban;

CREATE TABLE kb_client (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  clientname varchar(100) DEFAULT NULL,
  clientid varchar(50) DEFAULT NULL,
  clientsecret varchar(100) DEFAULT NULL,
  expires bigint(20) DEFAULT NULL,
  clientdessecret varchar(100) DEFAULT NULL,
  desexpires bigint(20) DEFAULT NULL,
  deletestatus char(1) DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX idx_oauth2_client_client_id (clientid)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE kb_user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username varchar(100) DEFAULT NULL,
  password varchar(100) DEFAULT NULL,
  salt varchar(100) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX idx_oauth2_user_username (username)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;