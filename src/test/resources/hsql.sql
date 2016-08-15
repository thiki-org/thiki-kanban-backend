-- ----------------------------
-- Table structure for kb_board
-- ----------------------------
drop table if exists kb_board;
CREATE TABLE kb_board (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  name VARCHAR(50) ,
  reporter VARCHAR(40) NOT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME  DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
  ) ;

-- ----------------------------
-- Table structure for kb_procedure
-- ----------------------------
drop table if exists kb_procedure;
CREATE TABLE kb_procedure (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  title  VARCHAR(50) NOT NULL,
  reporter VARCHAR(40) NOT NULL,
  board_id VARCHAR(40)  DEFAULT NULL,
  order_number int DEFAULT 0,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME  DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
  ) ;

-- ----------------------------
-- Table structure for kb_card
-- ----------------------------
drop table if exists kb_card;

CREATE TABLE kb_card (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  summary VARCHAR(50) NOT NULL,
  content VARCHAR(50),
  order_number int DEFAULT 0,
  reporter VARCHAR(40) DEFAULT NULL,
  procedure_id VARCHAR(40)  DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
  ) ;

-- ----------------------------
-- Table structure for kb_user_profile
-- ----------------------------
DROP TABLE IF EXISTS kb_user_profile;
CREATE TABLE kb_user_profile
(
  id VARCHAR(40) NOT NULL PRIMARY KEY , -- as userId
  email             VARCHAR(200) NOT NULL,
  nick             VARCHAR(40) ,
  phone            VARCHAR(40) ,
  delete_status int DEFAULT 0,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP
  );

-- ----------------------------
-- Table structure for kb_user_profile
-- ----------------------------
DROP TABLE IF EXISTS kb_user_registration;
CREATE TABLE kb_user_registration
(
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  password VARCHAR(500) NOT NULL,
  name       VARCHAR(40) NOT NULL,
  email VARCHAR(127) NOT NULL,
  salt VARCHAR(40) DEFAULT '',
  delete_status int DEFAULT 0,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP
  );

-- ----------------------------
-- Table structure for kb_card_assignment
-- ----------------------------
drop table if exists kb_card_assignment;

CREATE TABLE kb_card_assignment (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  card_id VARCHAR(40) NOT NULL,
  assigner VARCHAR(40) NOT NULL,
  assignee VARCHAR(40) NOT NULL,
  reporter VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
  ) ;
-- ----------------------------
-- Table structure for kb_team
-- ----------------------------
drop table if exists kb_team;

CREATE TABLE kb_team(
  id VARCHAR(40)NOT NULL PRIMARY KEY,
  name VARCHAR(50)NOT NULL,
  reporter VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
);

-- ----------------------------
-- Table structure for kb_team_members
-- ----------------------------
drop table if exists kb_team_members;

CREATE TABLE kb_team_members(
  id VARCHAR(40)NOT NULL PRIMARY KEY,
  team_id VARCHAR(50)NOT NULL,
  member VARCHAR(50)NOT NULL,
  reporter VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
);

-- ----------------------------
-- Table structure for kb_password_retrieval
-- ----------------------------
drop table if exists kb_password_retrieval;

CREATE TABLE kb_password_retrieval(
  id VARCHAR(40)NOT NULL PRIMARY KEY,
  email VARCHAR(50)NOT NULL,
  verification_code VARCHAR(50)NOT NULL,
  is_verify_passed int DEFAULT 0,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
);

-- ----------------------------
-- Table structure for kb_password_reset
-- ----------------------------
drop table if exists kb_password_reset;

CREATE TABLE kb_password_reset(
  id VARCHAR(40)NOT NULL PRIMARY KEY,
  email VARCHAR(50)NOT NULL,
  is_reset int DEFAULT 0,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
);
