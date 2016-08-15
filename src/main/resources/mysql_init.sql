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
  delete_status     INT(2)      DEFAULT 0,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME    DEFAULT CURRENT_TIMESTAMP
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
  reporter          VARCHAR(40) NOT NULL,
  delete_status     INT(2)               DEFAULT 0,
  order_number      INT(2)      NOT NULL DEFAULT 0,
  creation_time     DATETIME             DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_procedure
-- ----------------------------
DROP TABLE IF EXISTS kb_procedure;
CREATE TABLE kb_procedure (
  id                VARCHAR(40) NOT NULL,
  title             VARCHAR(50) NOT NULL,
  reporter          VARCHAR(40) NOT NULL,
  delete_status     INT(2)               DEFAULT 0,
  order_number      INT(2)      NOT NULL DEFAULT 0,
  board_id          VARCHAR(40)          DEFAULT NULL,
  creation_time     DATETIME             DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_card
-- ----------------------------
DROP TABLE IF EXISTS kb_card;
CREATE TABLE kb_card (
  id                VARCHAR(40)   NOT NULL,
  summary           VARCHAR(1023) NOT NULL,
  content           TEXT,
  order_number      INT(2)        NOT NULL  DEFAULT 0,
  reporter          VARCHAR(40)             DEFAULT NULL,
  procedure_id      VARCHAR(40)             DEFAULT NULL,
  creation_time     DATETIME                DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)                  DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_card_assignment
-- ----------------------------
DROP TABLE IF EXISTS kb_card_assignment;

CREATE TABLE kb_card_assignment (
  id                VARCHAR(40) NOT NULL,
  card_id           VARCHAR(40) NOT NULL,
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
  id                VARCHAR(40) NOT NULL,
  name              VARCHAR(50) NOT NULL,
  reporter          VARCHAR(40) DEFAULT NULL,
  delete_status     INT(2)      DEFAULT 0,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
-- ----------------------------
-- Table structure for kb_team_members
-- ----------------------------
DROP TABLE IF EXISTS kb_team_members;

CREATE TABLE kb_team_members (
  id                VARCHAR(40) NOT NULL,
  team_id           VARCHAR(50) NOT NULL,
  member            VARCHAR(50) NOT NULL,
  reporter          VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)      DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_password_retrieval
-- ----------------------------
DROP TABLE IF EXISTS kb_password_retrieval;

CREATE TABLE kb_password_retrieval (
  id                VARCHAR(40) NOT NULL,
  email             VARCHAR(50) NOT NULL,
  verification_code VARCHAR(50) NOT NULL,
  is_verified       INT      DEFAULT 0,
  creation_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)   DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
-- ----------------------------
-- Table structure for kb_password_reset
-- ----------------------------
DROP TABLE IF EXISTS kb_password_reset;

CREATE TABLE kb_password_reset (
  id                VARCHAR(40) NOT NULL,
  email             VARCHAR(50) NOT NULL,
  is_reset          INT      DEFAULT 0,
  creation_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)   DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
