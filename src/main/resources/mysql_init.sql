-- ----------------------------
-- Table structure for kb_user_profile
-- ----------------------------
DROP TABLE IF EXISTS kb_user_profile;
CREATE TABLE kb_user_profile
(
  id                VARCHAR(40) NOT NULL
  COMMENT '编号',
  email             VARCHAR(200) COMMENT '邮箱',
  name              VARCHAR(40),
  nick              VARCHAR(40),
  phone             VARCHAR(40),
  delete_status     INT(2)   DEFAULT 0,
  creation_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
-- ----------------------------
-- Table structure for kb_user_registration
-- ----------------------------
DROP TABLE IF EXISTS kb_user_registration;
CREATE TABLE kb_user_registration
(
  id                VARCHAR(40)  NOT NULL PRIMARY KEY,
  password          VARCHAR(500) NOT NULL,
  name              VARCHAR(40)  NOT NULL,
  email             VARCHAR(127) NOT NULL,
  salt              VARCHAR(40) DEFAULT NULL,
  delete_status     INT         DEFAULT 0,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME    DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_board
-- ----------------------------
DROP TABLE IF EXISTS kb_board;
CREATE TABLE kb_board (
  id                VARCHAR(40) NOT NULL,
  name              TEXT,
  reporter          INT(2)      NOT NULL,
  delete_status     INT(2)               DEFAULT 0,
  order_number      INT(2)      NOT NULL DEFAULT 0,
  creation_time     DATETIME             DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_entry
-- ----------------------------
DROP TABLE IF EXISTS kb_entry;
CREATE TABLE kb_entry (
  id                VARCHAR(40) NOT NULL,
  title             VARCHAR(50) NOT NULL,
  reporter          VARCHAR(40) NOT NULL,
  delete_status     INT                  DEFAULT 0,
  order_number      INT(2)      NOT NULL DEFAULT 0,
  board_id          VARCHAR(40)          DEFAULT NULL,
  creation_time     DATETIME             DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_task
-- ----------------------------
DROP TABLE IF EXISTS kb_task;
CREATE TABLE kb_task (
  id                VARCHAR(40)   NOT NULL,
  summary           VARCHAR(1023) NOT NULL,
  content           TEXT,
  order_number      INT(2)        NOT NULL  DEFAULT 0,
  reporter          VARCHAR(40)             DEFAULT NULL,
  entry_id          VARCHAR(40)             DEFAULT NULL,
  creation_time     DATETIME                DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)                  DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_task_assignment
-- ----------------------------
DROP TABLE IF EXISTS kb_task_assignment;

CREATE TABLE kb_task_assignment (
  id                VARCHAR(40) NOT NULL,
  task_id           VARCHAR(40) NOT NULL,
  assigner          VARCHAR(40) NOT NULL,
  assignee          VARCHAR(40) NOT NULL,
  reporter          VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)      DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
  -- ----------------------------
  -- Table structure for kb_team
  -- ----------------------------
  DROP TABLE IF EXISTS kb_team;
  CREATE TABLE kb_team (
    id                VARCHAR(40)   NOT NULL,
    name              VARCHAR(50)   NOT NULL,
    reporter          VARCHAR(40)   DEFAULT NULL,
    delete_status     INT(2)        DEFAULT 0,

    PRIMARY KEY (id)
  )
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;