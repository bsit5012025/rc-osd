DROP USER rcosd CASCADE;

CREATE USER rcosd IDENTIFIED BY Changeme0;
ALTER USER rcosd QUOTA UNLIMITED ON DATA;
ALTER USER rcosd QUOTA UNLIMITED ON USERS;
GRANT CREATE SESSION TO rcosd WITH ADMIN OPTION;
GRANT CONNECT TO rcosd;
ALTER SESSION SET current_schema = rcosd;


DROP TABLE person CASCADE CONSTRAINTS;
DROP TABLE login CASCADE CONSTRAINTS;
DROP TABLE offense CASCADE CONSTRAINTS;
DROP TABLE disciplinaryAction CASCADE CONSTRAINTS;
DROP TABLE department_head CASCADE CONSTRAINTS;
DROP TABLE department CASCADE CONSTRAINTS;
DROP TABLE prefect CASCADE CONSTRAINTS;
DROP TABLE student CASCADE CONSTRAINTS;
DROP TABLE disciplinaryStatus CASCADE CONSTRAINTS;
DROP TABLE enrollment CASCADE CONSTRAINTS;
DROP TABLE record CASCADE CONSTRAINTS;


-- PERSON ENTITY
CREATE TABLE person(
   personID number(20,0) generated as identity
       constraint PERSON_NOT_NULL not null,
   lastName VARCHAR(50) NOT NULL,
   firstName VARCHAR(100) NOT NULL,
   middleName VARCHAR(30),
   primary key (personID)
);

-- LOGIN ENTITY
CREATE TABLE login (
   id number(20,0) generated as identity
       constraint LOGIN_NOT_NULL not null,
   personID number(20,0) unique,
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

-- DISCIPLINARY ACTION ENTITY
CREATE TABLE disciplinaryaction (
   actionID VARCHAR(10) PRIMARY KEY,
   action VARCHAR(100),
   description VARCHAR(500)
);

-- DEPARTMENT HEAD ENTITY
CREATE TABLE department_head (
   departmentheadID VARCHAR(10) PRIMARY KEY,
   userID NUMBER(20,0),
   departmentID VARCHAR(10)
);

--DEPARTMENT ENTITY
CREATE TABLE department (
   departmentID VARCHAR(10) PRIMARY KEY,
   departmentname VARCHAR(100)
);

-- PREFECT ENTITY
CREATE TABLE prefect (
   prefectID VARCHAR(10) PRIMARY KEY,
   userID NUMBER(20,0),
   departmentID VARCHAR(10)
);

-- STUDENT ENTITY
CREATE TABLE student (
   studentID VARCHAR(10) PRIMARY KEY,
   userID NUMBER(20,0),
   address VARCHAR(255),
   studentType VARCHAR(20),
   departmentID VARCHAR(10)
);

-- DISCIPLINARY STATUS ENTITY
CREATE TABLE disciplinaryStatus (
   disciplinaryStatusID VARCHAR(10) PRIMARY KEY,
   status VARCHAR(30),
   description VARCHAR(255)
);

-- ENROLLMENT ENTITY
CREATE TABLE enrollment (
   enrollmentID VARCHAR(10) PRIMARY KEY,
   studentID VARCHAR(10),
   schoolYear VARCHAR(9),
   studentLevel VARCHAR(30),
   section VARCHAR(50),
   departmentID VARCHAR(10),
   disciplinaryStatusID VARCHAR(10)
);

-- RECORD ENTITY
CREATE TABLE record (
   recordID VARCHAR(10) PRIMARY KEY,
   enrollmentID VARCHAR(10),
   prefectID VARCHAR(10),
   offenseID VARCHAR(10),
   dateOfViolation DATE,
   actionID VARCHAR(10),
   dateOfResolution DATE,
   remarks VARCHAR(500),
   status VARCHAR(30)
);


-- FOREIGN KEYS
ALTER TABLE login ADD CONSTRAINT FK_LOGIN_PERSON FOREIGN KEY (personID) REFERENCES person(personID);
ALTER TABLE department_head ADD CONSTRAINT FK_DEPTHEAD_LOGIN FOREIGN KEY (userID) REFERENCES login(id);
ALTER TABLE department_head ADD CONSTRAINT FK_DEPTHEAD_DEPT FOREIGN KEY (departmentID) REFERENCES department(departmentID);
ALTER TABLE prefect ADD CONSTRAINT FK_PREFECT_LOGIN FOREIGN KEY (userID) REFERENCES login(id);
ALTER TABLE student ADD CONSTRAINT FK_STUDENT_LOGIN FOREIGN KEY (userID) REFERENCES login(id);
ALTER TABLE student ADD CONSTRAINT FK_STUDENT_DEPARTMENT FOREIGN KEY (departmentID) REFERENCES department(departmentID);
ALTER TABLE enrollment ADD CONSTRAINT FK_ENROLL_STUDENT FOREIGN KEY (studentID) REFERENCES student(studentID);
ALTER TABLE enrollment ADD CONSTRAINT FK_ENROLL_DEPT FOREIGN KEY (departmentID) REFERENCES department(departmentID);
ALTER TABLE enrollment ADD CONSTRAINT FK_ENROLL_STATUS FOREIGN KEY (disciplinaryStatusID) REFERENCES disciplinaryStatus(disciplinaryStatusID);
ALTER TABLE record ADD CONSTRAINT FK_RECORD_ENROLLMENT FOREIGN KEY (enrollmentID) REFERENCES enrollment(enrollmentID);
ALTER TABLE record ADD CONSTRAINT FK_RECORD_PREFECT FOREIGN KEY (prefectID) REFERENCES prefect(prefectID);
ALTER TABLE record ADD CONSTRAINT FK_RECORD_OFFENSE FOREIGN KEY (offenseID) REFERENCES offense(offenseID);
ALTER TABLE record ADD CONSTRAINT FK_RECORD_ACTION FOREIGN KEY (actionID) REFERENCES disciplinaryAction(actionID);


-- TEST DATA
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Bayona', 'Wilrow', 'Reosa');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cain', 'Carl Justine', 'Dela Cruz');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Camama', 'Mark Joshua', 'Reyes');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cruz', 'John Zenith', 'Pasion');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('De Gala', 'Angel Lowyza', 'Resurreccion');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('De Rojas', 'Geoffrey Allen', 'Villanueva');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Quevada', 'Keith Jasper', 'Brioso');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Reyes', 'Leeane Glazel', 'Nialda');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cadorna', 'Jun', 'Pineda');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Pantilanan', 'Anano', 'Riva');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Pacifico', 'Allana', 'Klein');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Belardo', 'Jed', 'Madela');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Demetillo', 'Winibelle', 'Torres');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Montana', 'Danny', 'Belle');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Bayona', 'Wilson', 'Reosa');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cain', 'Emilyn', 'Dela Cruz');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Camama', 'Belinda', 'Reyes');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Pasion', 'Edith', 'Dugayo');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('De Gala', 'Vilma', 'Resurreccion');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('James', 'Lebron', 'Jim');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Quevada', 'Rochelle', 'Brioso');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Reyes', 'Ghe', 'Nialda');

insert into login (personID, username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values (1, 'admin', 'admin', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_ADMIN', 'user:read,user:create,user:update,user:delete', 1, 0);
insert into login (personID,username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values (9, 'prefect', '1234', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_PREFECT', 'user:read,user:create,user:update,user:delete', 1, 0);
insert into login (personID, username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values (12, 'staff','1234', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_STAFF', 'user:read,user:create,user:update', 1, 0);
insert into login (personID, username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values (2, 'user1','1234', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_USER', 'user:read,user:create,user:update', 1, 0);
insert into login (personID, username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values (3, 'user2','1234', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_USER', 'user:read,user:create,user:update', 1, 0);

INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-001', 'Vaping', 'Major Offense', 'Bringing vape');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-002', 'Punching', 'Major Offense', 'Punching another student');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-003', 'Stealing', 'Major Offense', 'Stealing things from another student');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-004', 'Bullying', 'Major Offense', 'Student delivers disrespectful messages to another student that includes threats and intimidation');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-005', 'PDA', 'Major Offense', 'Student engages in inappropriate consensual verbal and/or physical gestures/contact, of a sexual nature to another student.');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-006', 'Cheating', 'Major Offense', 'Student deliberately violates rules or engages in plagiarism or copying anothers work');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-007', 'Skip Class', 'Major Offense', 'Student leaves or misses class without permission');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-008', 'Tardiness', 'Major Offense', 'Student is repeatedly late to class');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-009', 'Technology Violation', 'Major Offense', 'Inappropriate use of gadgets');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-010', 'Use/Possession of Alcohol', 'Major Offense', 'Student is in possession of or is using alcohol');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-011', 'Use/Possession of Drugs', 'Major Offense', 'Student is in possession of or is using illegal drugs');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-012', 'Use/Possession of Tobacco', 'Major Offense', 'Student is in possession of or is using tobacco');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-013', 'Use/Possession of Weapons', 'Major Offense', 'Student is in possession of knives or gun or other object readily capable of causing bodily harm');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-014', 'Use/Possession of Drugs', 'Major Offense', 'Student is in possession of or is using illegal drugs');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-015', 'Disrespect', 'Minor Offense', 'Student engages in brief or low-intensity failure to respond to adult requests');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-016', 'Dress Code', 'Minor Offense', 'Student wears clothing that not within the dress code guidelines');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-017', 'Inappropriate Language', 'Minor Offense', 'Student engages in low-intensity instance of appropriate language');
INSERT INTO offense (offenseID, offense, type, description) VALUES ('OFF-018', 'Dress Code', 'Minor Offense', 'Student wears clothing that not within the dress code guidelines');

INSERT INTO disciplinaryAction (actionID, action, description) VALUES ('D-001', 'Community Service', 'A service component where the student spends time serving in the community meeting actual needs');
INSERT INTO disciplinaryAction (actionID, action, description) VALUES ('D-002', 'Probation', 'a warning status given to a student whose academic performance or behavior falls below the institutions standards');

INSERT INTO department (departmentID, departmentName) VALUES ('jhs-3001', 'Junior High School Department');
INSERT INTO department (departmentID, departmentName) VALUES ('shs-3002', 'Senior High School Department');
INSERT INTO department (departmentID, departmentName) VALUES ('ct-3003', 'College Department');

INSERT INTO department_head (departmentHeadID, userID,  departmentID) VALUES ('HD-001', 3, 'jhs-3001');

INSERT INTO prefect (prefectID, userID,  departmentID) VALUES ('DO-001', 2, 'jhs-3001');

INSERT INTO student (studentID, userID, address, studentType, departmentID) VALUES ('JHS-0001', 4, 'Buho', 'Intern', 'jhs-3001');
INSERT INTO student (studentID, userID, address, studentType, departmentID) VALUES ('CT23-0001', 5, 'Malabag', 'Extern', 'ct-3003');

INSERT INTO disciplinaryStatus (disciplinaryStatusID, status, description) VALUES ('STATUS01', 'Good Standing', 'Student has no disciplinary issues and maintains good behavior.');
INSERT INTO disciplinaryStatus (disciplinaryStatusID, status, description) VALUES ('STATUS02', 'Conduct Monitoring', 'Student is under observation due to minor conduct issues.');
INSERT INTO disciplinaryStatus (disciplinaryStatusID, status, description) VALUES ('STATUS03', 'Conduct Probation', 'Student is on probation due to repeated or serious conduct violations.');
INSERT INTO disciplinaryStatus (disciplinaryStatusID, status, description) VALUES ('STATUS04', 'Attendance Monitoring', 'Student is under observation due to attendance issues.');
INSERT INTO disciplinaryStatus (disciplinaryStatusID, status, description) VALUES ('STATUS05', 'Attendance Probation', 'Student is on probation due to repeated attendance violations.');

INSERT INTO enrollment (enrollmentID, studentID, schoolYear, studentLevel, section, departmentID, disciplinaryStatusID) VALUES ('E-001', 'JHS-0001', '2024-2025', 'Grade-8', 'St. Hannibal', 'jhs-3001', 'STATUS01');
INSERT INTO enrollment (enrollmentID, studentID, schoolYear, studentLevel, section, departmentID, disciplinaryStatusID) VALUES ('E-002', 'JHS-0001', '2025-2026', 'Grade-9', 'St. Anthony', 'jhs-3001', 'STATUS02');
INSERT INTO enrollment (enrollmentID, studentID, schoolYear, studentLevel, section, departmentID, disciplinaryStatusID) VALUES ('E-003', 'CT23-0001', '2025-2026', '2nd Year', 'IT301', 'ct-3003', 'STATUS04');

INSERT INTO record (recordID, enrollmentID, prefectID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES ('R-001', 'E-001', 'DO-001', 'OFF-001', TO_DATE('2024-09-15', 'YYYY-MM-DD'), 'D-001', TO_DATE('2024-09-20', 'YYYY-MM-DD'), 'Student caught vaping in school', 'Pending');
INSERT INTO record (recordID, enrollmentID, prefectID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES ('R-002', 'E-002', 'DO-001', 'OFF-008', TO_DATE('2025-01-12', 'YYYY-MM-DD'), 'D-002', TO_DATE('2025-01-20', 'YYYY-MM-DD'), 'Repeatedly late to class', 'Resolved');
INSERT INTO record (recordID, enrollmentID, prefectID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES ('R-003', 'E-003', 'DO-001', 'OFF-007', TO_DATE('2025-02-05', 'YYYY-MM-DD'), 'D-001', TO_DATE('2025-02-10', 'YYYY-MM-DD'), 'Skipped class without permission', 'Resolved');
INSERT INTO record (recordID, enrollmentID, prefectID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES ('R-004', 'E-003', 'DO-001', 'OFF-004', TO_DATE('2025-03-03', 'YYYY-MM-DD'), 'D-002', TO_DATE('2025-03-08', 'YYYY-MM-DD'), 'Bullying incident reported', 'Resolved');

COMMIT;