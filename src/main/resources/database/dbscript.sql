DROP USER rcosd CASCADE;

CREATE USER rcosd IDENTIFIED BY Changeme0;
ALTER USER rcosd QUOTA UNLIMITED ON DATA;
ALTER USER rcosd QUOTA UNLIMITED ON USERS;
GRANT CREATE SESSION TO rcosd WITH ADMIN OPTION;
GRANT CONNECT TO rcosd;
ALTER SESSION SET current_schema = rcosd;

DROP TABLE login CASCADE CONSTRAINTS;
DROP TABLE offense CASCADE CONSTRAINTS;

-- LOGIN ENTITY
CREATE TABLE login (
   id number(20,0) generated as identity
       constraint LOGIN_NOT_NULL not null,
--   personID number(20,0) unique,
   username varchar2(25 char)
       constraint LOGIN_USERNAME_NOT_NULL not null,
   password varchar2(255 char)
       constraint LOGIN_PASSWORD_NOT_NULL not null,
   join_date timestamp(6),
   last_login_date timestamp(6),
   role varchar2(255 char),
   authorities varchar2(255 char),
   is_active number(1,0),
   is_locked number(1,0),
   primary key (id)
);

-- OFFENSE ENTITY
CREATE TABLE offense (
   offenseID VARCHAR(10) PRIMARY KEY,
   offense VARCHAR(100),
   type VARCHAR(50),
   description VARCHAR(500)
);

insert into login (username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('admin', 'admin', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_ADMIN', 'user:read,user:create,user:update,user:delete', 1, 0);
insert into login (username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('prefect', '1234', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_PREFECT', 'user:read,user:create,user:update,user:delete', 1, 0);
insert into login (username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('staff','1234', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_STAFF', 'user:read,user:create,user:update', 1, 0);
insert into login (username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('user1','1234', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_USER', 'user:read,user:create,user:update', 1, 0);
insert into login (username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('user2','1234', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_USER', 'user:read,user:create,user:update', 1, 0);


INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-001', 'Vaping', 'Major Offense', 'Bringing vape');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-002', 'Punching', 'Major Offense', 'Punching another student');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-003', 'Stealing', 'Major Offense', 'Stealing things from another student');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-004', 'Bullying', 'Major Offense', 'Student delivers disrespectful messages to another student that includes threats and intimidation');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-005', 'PDA', 'Major Offense', 'Student engages in inappropriate consensual verbal and/or physical gestures/contact, of a sexual nature to another student.');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-006', 'Cheating', 'Major Offense', 'Student deliberately violates rules or engages in plagiarism or copying anothers work');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-007', 'Skip Class', 'Major Offense', 'Student leaves or misses class without permission');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-008', 'Tardiness', 'Major Offense', 'Student is repeatedly late to class');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-009', 'Technology Violation', 'Major Offense', 'Inappropriate use of gadgets');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-010', 'Use/Posession of Alcohol', 'Major Offense', 'Student is in possession of or is using alcohol');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-011', 'Use/Possession of Drugs', 'Major Offense', 'Student is in possession of or is using illegal drugs');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-012', 'Use/Possession of Tobacco', 'Major Offense', 'Student is in possession of or is using tobacco');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-013', 'Use/Possession of Weapons', 'Major Offense', 'Student is in possession of knives or gun or other object readily capable of causing bodily harm');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-014', 'Use/Possession of Drugs', 'Major Offense', 'Student is in possession of or is using illegal drugs');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-015', 'Disrespect', 'Minor Offense', 'Student engages in brief or low-intensity failure to respond to adult requests');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-016', 'Dress Code', 'Minor Offense', 'Student wears clothing that not within the dress code guidelines');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-017', 'Inappropriate Language', 'Minor Offense', 'Student engages in low-intensity instance of appropriate language');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-018', 'Dress Code', 'Minor Offense', 'Student wears clothing that not within the dress code guidelines');

COMMIT;