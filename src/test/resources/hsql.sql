-- ----------------------------
-- Table structure for kb_board
-- ----------------------------
drop table if exists kb_board;
CREATE TABLE kb_board (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  name VARCHAR(50) ,
  team_id VARCHAR(50) ,
  author VARCHAR(40) NOT NULL,
  owner VARCHAR(40),
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
  author VARCHAR(40) NOT NULL,
  board_id VARCHAR(40)  NOT NULL,
  sort_number int DEFAULT 0,
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
  summary VARCHAR(200) NOT NULL,
  code VARCHAR(50),
  content VARCHAR(50),
  sort_number int DEFAULT 0,
  author VARCHAR(40) DEFAULT NULL,
  procedure_id VARCHAR(40)  NOT NULL,
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
  id VARCHAR(40) NOT NULL PRIMARY KEY ,
  user_name             VARCHAR(200) NOT NULL,
  nick_name           VARCHAR(40) ,
  phone            VARCHAR(40) ,
  avatar            VARCHAR(200) ,
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
  author VARCHAR(40) DEFAULT NULL,
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
  author VARCHAR(40) DEFAULT NULL,
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
  author VARCHAR(40) DEFAULT NULL,
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
  user_name VARCHAR(50)NOT NULL,
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
  user_name VARCHAR(50)NOT NULL,
  is_reset int DEFAULT 0,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
);

-- ----------------------------
-- Table structure for kb_member_invitation
-- ----------------------------
drop table if exists kb_member_invitation;

CREATE TABLE kb_team_member_invitation(
  id VARCHAR(40)NOT NULL PRIMARY KEY,
  team_id VARCHAR(50)NOT NULL,
  inviter VARCHAR(50)NOT NULL,
  invitee VARCHAR(50)NOT NULL,
  is_accepted int DEFAULT 0,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
);

-- ----------------------------
-- Table structure for kb_notification
-- ----------------------------
drop table if exists kb_notification;

CREATE TABLE kb_notification(
  id VARCHAR(40)NOT NULL PRIMARY KEY,
  receiver VARCHAR(50)NOT NULL,
  sender VARCHAR(50)NOT NULL,
  content VARCHAR(50)NOT NULL,
  link VARCHAR(500) DEFAULT '',
  is_read int DEFAULT 0,
  type VARCHAR(50)NOT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
);

-- ----------------------------
-- Table structure for kb_acceptance_criterias
-- ----------------------------
drop table if exists kb_acceptance_criterias;

CREATE TABLE kb_acceptance_criterias(
  id VARCHAR(40)NOT NULL PRIMARY KEY,
  summary VARCHAR(200) DEFAULT '',
  card_id VARCHAR(50)NOT NULL,
  sort_number int DEFAULT 9999,
  finished BOOLEAN DEFAULT FALSE ,
  author VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
);

-- ----------------------------
-- Table structure for kb_comment
-- ----------------------------
drop table if exists kb_comment;

CREATE TABLE kb_comment(
  id VARCHAR(40)NOT NULL PRIMARY KEY,
  summary VARCHAR(200) DEFAULT '',
  card_id VARCHAR(50)NOT NULL,
  author VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
);
-- ----------------------------
-- Table structure for kb_tag
-- ----------------------------
drop table if exists kb_tag;

CREATE TABLE kb_tag(
  id VARCHAR(40)NOT NULL PRIMARY KEY,
  name VARCHAR(50) DEFAULT '',
  color VARCHAR(20)NOT NULL,
  board_id VARCHAR(40) DEFAULT NULL,
  author VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
);

-- ----------------------------
-- Table structure for kb_cards_tags
-- ----------------------------
drop table if exists kb_cards_tags;

CREATE TABLE kb_cards_tags(
  id VARCHAR(40)NOT NULL PRIMARY KEY,
  card_id VARCHAR(40) DEFAULT NULL,
  tag_id VARCHAR(40) DEFAULT NULL,
  author VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
);
