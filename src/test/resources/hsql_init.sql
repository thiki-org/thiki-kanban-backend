-- ----------------------------
-- Table structure for kb_board
-- ----------------------------
drop table if exists kb_board;
CREATE TABLE kb_board (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  name VARCHAR(50) ,
  reporter int NOT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME  DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
  ) ;

-- ----------------------------
-- Table structure for kb_entry
-- ----------------------------
drop table if exists kb_entry;
CREATE TABLE kb_entry (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  title VARCHAR(50) ,
  reporter int NOT NULL,
  board_id VARCHAR(40)  DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME  DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
  ) ;

-- ----------------------------
-- Table structure for kb_task
-- ----------------------------
drop table if exists kb_task;

CREATE TABLE kb_task (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  summary VARCHAR(50) NOT NULL,
  content VARCHAR(50),
  order_number int DEFAULT 0,
  reporter int DEFAULT NULL,
  entry_id VARCHAR(40)  DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
  ) ;

  -- ----------------------------
-- Table structure for kb_user
-- ----------------------------
DROP TABLE IF EXISTS kb_user;
CREATE TABLE kb_user
(
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  email             VARCHAR(200) NOT NULL,
  name             VARCHAR(40) NOT NULL,
  delete_status int DEFAULT 0,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP
  );
-- ----------------------------
-- Table structure for kb_task_assignment
-- ----------------------------
drop table if exists kb_task_assignment;

CREATE TABLE kb_task_assignment (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  task_id VARCHAR(40) NOT NULL,
  assigner VARCHAR(40) NOT NULL,
  assignee VARCHAR(40) NOT NULL,
  reporter VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  delete_status int DEFAULT 0
  ) ;
