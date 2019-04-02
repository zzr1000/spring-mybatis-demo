 CREATE TABLE student_info (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  sex varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  major varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  height varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (id)
)