drop table if exists kb_entry;
CREATE TABLE kb_entry
(
    id INT(11) PRIMARY KEY NOT NULL auto_increment,
    title TEXT,
    deleteStatus INT(11) DEFAULT '0'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
