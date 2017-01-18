-- ----------------------------
-- Table structure for kb_user_profile
-- ----------------------------
DROP TABLE IF EXISTS kb_user_profile;
CREATE TABLE kb_user_profile
(
  id                VARCHAR(40)  NOT NULL
  COMMENT '编号',
  email             VARCHAR(200) COMMENT '邮箱',
  user_name         VARCHAR(200) NOT NULL,
  nick_name         VARCHAR(40),
  phone             VARCHAR(40),
  avatar            VARCHAR(200),
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
  name              VARCHAR(50),
  team_id           VARCHAR(50),
  owner             VARCHAR(40),
  author            VARCHAR(40) NOT NULL,
  delete_status     INT(2)               DEFAULT 0,
  sort_number       INT(2)      NOT NULL DEFAULT 0,
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
  author            VARCHAR(40) NOT NULL,
  description       VARCHAR(100)         DEFAULT '',
  type              INT(2)               DEFAULT 0,
  status            INT(2)               DEFAULT 0,
  delete_status     INT(2)               DEFAULT 0,
  sort_number       INT(2)      NOT NULL DEFAULT 0,
  board_id          VARCHAR(40) NOT NULL,
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
  id                VARCHAR(40)  NOT NULL,
  summary           VARCHAR(200) NOT NULL,
  code              VARCHAR(50),
  content           TEXT,
  sort_number       INT(2)       NOT NULL  DEFAULT 0,
  author            VARCHAR(40)            DEFAULT NULL,
  procedure_id      VARCHAR(40)  NOT NULL,
  creation_time     DATETIME               DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)                 DEFAULT 0,
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
  author            VARCHAR(40) DEFAULT NULL,
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
  author            VARCHAR(40) DEFAULT NULL,
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
  author            VARCHAR(40) DEFAULT NULL,
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
  user_name         VARCHAR(50) NOT NULL,
  verification_code VARCHAR(50) NOT NULL,
  is_verify_passed  INT      DEFAULT 0,
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
  user_name         VARCHAR(50) NOT NULL,
  is_reset          INT      DEFAULT 0,
  creation_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)   DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_member_invitation
-- ----------------------------
DROP TABLE IF EXISTS kb_team_member_invitation;

CREATE TABLE kb_team_member_invitation (
  id                VARCHAR(40) NOT NULL,
  team_id           VARCHAR(50) NOT NULL,
  inviter           VARCHAR(50) NOT NULL,
  invitee           VARCHAR(50) NOT NULL,
  is_accepted       INT      DEFAULT 0,
  creation_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)   DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_notification
-- ----------------------------
DROP TABLE IF EXISTS kb_notification;

CREATE TABLE kb_notification (
  id                VARCHAR(40) NOT NULL,
  receiver          VARCHAR(50) NOT NULL,
  sender            VARCHAR(50) NOT NULL,
  content           VARCHAR(50) NOT NULL,
  link              VARCHAR(500) DEFAULT '',
  is_read           INT          DEFAULT 0,
  type              VARCHAR(50) NOT NULL,
  creation_time     DATETIME     DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)       DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_acceptance_criterias
-- ----------------------------
DROP TABLE IF EXISTS kb_acceptance_criterias;

CREATE TABLE kb_acceptance_criterias (
  id                VARCHAR(40) NOT NULL,
  summary           VARCHAR(200) DEFAULT '',
  card_id           VARCHAR(50) NOT NULL,
  sort_number       INT          DEFAULT 0,
  finished          BOOLEAN      DEFAULT 0,
  author            VARCHAR(40)  DEFAULT NULL,
  creation_time     DATETIME     DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)       DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
-- ----------------------------
-- Table structure for kb_comment
-- ----------------------------
DROP TABLE IF EXISTS kb_comment;

CREATE TABLE kb_comment (
  id                VARCHAR(40) NOT NULL,
  summary           VARCHAR(200) DEFAULT '',
  card_id           VARCHAR(50) NOT NULL,
  author            VARCHAR(40)  DEFAULT NULL,
  creation_time     DATETIME     DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)       DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_tag
-- ----------------------------
DROP TABLE IF EXISTS kb_tag;

CREATE TABLE kb_tag (
  id                VARCHAR(40) NOT NULL,
  name              VARCHAR(50) DEFAULT '',
  color             VARCHAR(20) NOT NULL,
  board_id          VARCHAR(40) DEFAULT NULL,
  author            VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)      DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_cards_tags
-- ----------------------------
DROP TABLE IF EXISTS kb_cards_tags;

CREATE TABLE kb_cards_tags (
  id                VARCHAR(40) NOT NULL,
  card_id           VARCHAR(40) DEFAULT NULL,
  tag_id            VARCHAR(40) DEFAULT NULL,
  author            VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)      DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_activity
-- ----------------------------
DROP TABLE IF EXISTS kb_activity;

CREATE TABLE kb_activity (
  id                  VARCHAR(40) NOT NULL PRIMARY KEY,
  card_id             VARCHAR(40)  DEFAULT NULL,
  prev_procedure_id   VARCHAR(40)  DEFAULT NULL,
  procedure_id        VARCHAR(40)  DEFAULT NULL,
  operation_type_code VARCHAR(40)  DEFAULT NULL,
  operation_type_name VARCHAR(40)  DEFAULT NULL,
  summary             VARCHAR(500) DEFAULT NULL,
  detail              TEXT         DEFAULT NULL,
  userName            VARCHAR(40)  DEFAULT NULL,
  creation_time       DATETIME     DEFAULT CURRENT_TIMESTAMP,
  modification_time   DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status       INT(2)       DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
