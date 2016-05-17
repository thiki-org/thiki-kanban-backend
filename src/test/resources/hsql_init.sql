-- ----------------------------
-- Table structure for kb_entry
-- ----------------------------
drop table if exists kb_entry;
CREATE TABLE kb_entry (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  title VARCHAR(50) ,
  reporter int NOT NULL,
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
  assignee int DEFAULT NULL,
  reporter int DEFAULT NULL,
  entry_id int DEFAULT NULL,
  delete_status int DEFAULT 0
  ) ;
