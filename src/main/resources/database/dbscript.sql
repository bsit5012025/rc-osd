-- DROP & CREATE USER
DROP USER rcosd CASCADE;

CREATE USER rcosd IDENTIFIED BY Changeme0;
ALTER USER rcosd QUOTA UNLIMITED ON DATA;
ALTER USER rcosd QUOTA UNLIMITED ON USERS;
GRANT CREATE SESSION TO rcosd WITH ADMIN OPTION;
GRANT CONNECT TO rcosd;
ALTER SESSION SET current_schema = rcosd;

-- DROP TABLES
DROP TABLE login CASCADE CONSTRAINTS;
--DROP TABLE person CASCADE CONSTRAINTS;
--DROP TABLE student CASCADE CONSTRAINTS;
--DROP TABLE prefect CASCADE CONSTRAINTS;
--DROP TABLE department_head CASCADE CONSTRAINTS;
--DROP TABLE department CASCADE CONSTRAINTS;
--DROP TABLE guardian CASCADE CONSTRAINTS;
--DROP TABLE offense CASCADE CONSTRAINTS;
--DROP TABLE disciplinaryAction CASCADE CONSTRAINTS;
--DROP TABLE violation CASCADE CONSTRAINTS;
--DROP TABLE appeal CASCADE CONSTRAINTS;

COMMIT;

-- LOGIN ENTITY
CREATE TABLE login (
    id number(20,0) generated as identity
        constraint LOGIN_NOT_NULL not null,
--    person_id number(20,0) unique,
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
    primary key (id));


-- PERSON
--CREATE TABLE person (
--    person_id varchar2(10) PRIMARY KEY,
--    last_name varchar2(50) NOT NULL,
--    first_name varchar2(100) NOT NULL,
--    middle_name varchar2(30) NOT NULL
--);
--
---- DEPARTMENT & HEAD
--CREATE TABLE department_head (
--    departmentheadID VARCHAR(10) PRIMARY KEY,
--    userID VARCHAR(10),
--    personID VARCHAR(10),
--    departmentID VARCHAR(10)
--);
--
----DEPARTMENT
--CREATE TABLE department (
--    departmentID VARCHAR(10) PRIMARY KEY,
--    departmentname VARCHAR(100),
--    departmentheadID VARCHAR(10)
--);
--
---- STUDENT
--CREATE TABLE student (
--    studentID VARCHAR(10) PRIMARY KEY,
--    userID VARCHAR(10),
--    personID VARCHAR(10),
--    studentLevel VARCHAR(30),
--    section VARCHAR(50),
--    departmentID VARCHAR(10)
--);
--
---- PREFECT
--CREATE TABLE prefect (
--    prefectID VARCHAR(10) PRIMARY KEY,
--    userID VARCHAR(10),
--    personID VARCHAR(10),
--    departmentID VARCHAR(10)
--);
--
---- GUARDIAN
--CREATE TABLE guardian (
--    guardianID VARCHAR(10) PRIMARY KEY,
--    personID VARCHAR(10),
--    contactnumber VARCHAR(20),
--    relationship VARCHAR(50),
--    studentID VARCHAR(10)
--);
--
---- OFFENSE
--CREATE TABLE offense (
--    offenseID VARCHAR(10) PRIMARY KEY,
--    offense VARCHAR(100),
--    type VARCHAR(50),
--    remarks VARCHAR(500)
--);
--
---- DISCIPLINARY ACTION
--CREATE TABLE disciplinaryaction (
--    actionID VARCHAR(10) PRIMARY KEY,
--    action VARCHAR(100),
--    description VARCHAR(500)
--);
--
---- VIOLATION
--CREATE TABLE violation (
--    violationID VARCHAR(10) PRIMARY KEY,
--    studentID VARCHAR(10),
--    prefectID VARCHAR(10),
--    offenseID VARCHAR(10),
--    Dateofviolation DATE,
--    actionID VARCHAR(10),
--    Dateofresolution DATE,
--    Remarks VARCHAR(500),
--    status VARCHAR(30)
--);
--
---- APPEAL
--CREATE TABLE appeal (
--    appealID VARCHAR(10) PRIMARY KEY,
--    violationID VARCHAR(10),
--    studentID VARCHAR(10),
--    message VARCHAR(500),
--    datefiled DATE,
--    status VARCHAR(20)
--);

-- FOREIGN KEYS
--ALTER TABLE department_head ADD CONSTRAINT FK_DEPTHEAD_USER FOREIGN KEY (userID) REFERENCES login(userID);
--ALTER TABLE department_head ADD CONSTRAINT FK_DEPTHEAD_PERSON FOREIGN KEY (personID) REFERENCES person(personID);
--ALTER TABLE department ADD CONSTRAINT FK_DEPARTMENT_HEAD FOREIGN KEY (departmentheadID) REFERENCES department_head(departmentheadID);
--ALTER TABLE student ADD CONSTRAINT FK_STUDENT_USER FOREIGN KEY (userID) REFERENCES login(userID);
--ALTER TABLE student ADD CONSTRAINT FK_STUDENT_PERSON FOREIGN KEY (personID) REFERENCES person(personID);
--ALTER TABLE student ADD CONSTRAINT FK_STUDENT_DEPARTMENT FOREIGN KEY (departmentID) REFERENCES department(departmentID);
--ALTER TABLE prefect ADD CONSTRAINT FK_PREFECT_USER FOREIGN KEY (userID) REFERENCES login(userID);
--ALTER TABLE prefect ADD CONSTRAINT FK_PREFECT_PERSON FOREIGN KEY (personID) REFERENCES person(personID);
--ALTER TABLE prefect ADD CONSTRAINT FK_PREFECT_DEPARTMENT FOREIGN KEY (departmentID) REFERENCES department(departmentID);
--ALTER TABLE guardian ADD CONSTRAINT FK_GUARDIAN_STUDENT FOREIGN KEY (studentID) REFERENCES student(studentID);
--ALTER TABLE guardian ADD CONSTRAINT FK_GUARDIAN_PERSON FOREIGN KEY (personID) REFERENCES person(personID);
--ALTER TABLE violation ADD CONSTRAINT FK_VIOLATION_STUDENT FOREIGN KEY (studentID) REFERENCES student(studentID);
--ALTER TABLE violation ADD CONSTRAINT FK_VIOLATION_PREFECT FOREIGN KEY (prefectID) REFERENCES prefect(prefectID);
--ALTER TABLE violation ADD CONSTRAINT FK_VIOLATION_OFFENSE FOREIGN KEY (offenseID) REFERENCES offense(offenseID);
--ALTER TABLE violation ADD CONSTRAINT FK_VIOLATION_ACTION FOREIGN KEY (actionID) REFERENCES disciplinaryaction(actionID);
--ALTER TABLE appeal ADD CONSTRAINT FK_APPEAL_VIOLATION FOREIGN KEY (violationID) REFERENCES violation(violationID);
--ALTER TABLE appeal ADD CONSTRAINT FK_APPEAL_STUDENT FOREIGN KEY (studentID) REFERENCES student(studentID);


insert into login (username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('admin', 'admin', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_ADMIN', 'user:read,user:create,user:update,user:delete', 1, 0);
insert into login (username, password, person_id, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('prefect', '1234', 2, to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_PREFECT', 'user:read,user:create,user:update,user:delete', 1, 0);
insert into login (username, password, person_id, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('staff','1234', 3, to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_STAFF', 'user:read,user:create,user:update', 1, 0);
insert into login (username, password, person_id, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('user1','1234', 4, to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_USER', 'user:read,user:create,user:update', 1, 0);
insert into login (username, password, person_id, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('user2','1234', 5, to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_USER', 'user:read,user:create,user:update', 1, 0);

--
---- PERSON VALUES
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P001', 'Bayona', 'Wilrow', 'Reosa');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P002', 'Cain', 'Carl Justine', 'Dela Cruz');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P003', 'Camama', 'Mark Joshua', 'Reyes');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P004', 'Cruz', 'John Zenith', 'Pasion');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P005', 'De Gala', 'Angel Lowyza', 'Resurreccion');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P006', 'De Rojas', 'Geoffrey Allen', 'Villaueva');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P007', 'Quevada', 'Keith Jasper', 'Brioso');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P008', 'Reyes', 'Leeane Glazel', 'Nialda');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P009', 'Cadorna', 'Jun', 'Pineda');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P010', 'Pantilanan', 'Anano', 'Riva');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P011', 'Pacifico', 'Allana', 'Klein');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P012', 'Belardo', 'Jed', 'Madela');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P013', 'Demetillo', 'Winibelle', 'Torres');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P014', 'Montano', 'Danny', 'Belle');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P015', 'Bayona', 'Wilson', 'Reosa');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P016', 'Cain', 'Emilyn', 'Dela Cruz');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P017', 'Camama', 'Belinda', 'Reyes');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P018', 'Pasion', 'Edith', 'Pasion');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P019', 'De Gala', 'Vilma', 'Resurreccion');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P020', 'James', 'Lebron', 'Jim');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P021', 'Quevada', 'Rochelle', 'Brioso');
--INSERT INTO person (personID, lastName, firstName, middleName) VALUES ('P022', 'Reyes', 'Ghe', 'Nialda');
--
---- department & HEAD VALUES
--INSERT INTO department_head (departmentHeadID, userID, personID, departmentID) VALUES ('HD-001', '1009', 'P011', 'jhs-3001');
--INSERT INTO department_head (departmentHeadID, userID, personID, departmentID) VALUES ('HD-002', '1010', 'P012', 'shs-3002');
--INSERT INTO department_head (departmentHeadID, userID, personID, departmentID) VALUES ('HD-003', '1011', 'P013', 'ct-3003');
--
---- DEPARTMENT VALUES
--INSERT INTO department (departmentID, departmentName, departmentheadID) VALUES ('jhs-3001', 'Junior HighSchool Department', 'HD-001');
--INSERT INTO department (departmentID, departmentName, departmentheadID) VALUES ('shs-3002', 'Senior HighSchool Department', 'HD-002');
--INSERT INTO department (departmentID, departmentName, departmentheadID) VALUES ('ct-3003', 'College Department', 'HD-003');
--
---- STUDENT VALUES
--INSERT INTO student (studentID, userID, personID, studentlevel, section, departmentID) VALUES ('S-001', '1001', 'P001', 'Grade-8', 'St.Hannibal', 'jhs-3001');
--INSERT INTO student (studentID, userID, personID, studentlevel, section, departmentID) VALUES ('S-002', '1002', 'P002', 'Grade-7', 'St. Anthony', 'jhs-3001');
--INSERT INTO student (studentID, userID, personID, studentlevel, section, departmentID) VALUES ('S-003', '1003', 'P003', 'Grade-11', 'St. Rita', 'shs-3002');
--INSERT INTO student (studentID, userID, personID, studentlevel, section, departmentID) VALUES ('S-004', '1004', 'P004', 'Grade-11', 'St. Rita', 'shs-3002');
--INSERT INTO student (studentID, userID, personID, studentlevel, section, departmentID) VALUES ('S-005', '1005', 'P005', 'Grade-12', 'St. Anne', 'shs-3002');
--INSERT INTO student (studentID, userID, personID, studentlevel, section, departmentID) VALUES ('S-006', '1006', 'P006', '1st Year', 'IT101', 'ct-3003');
--INSERT INTO student (studentID, userID, personID, studentlevel, section, departmentID) VALUES ('S-007', '1007', 'P007', '2nd Year', 'IT301', 'ct-3003');
--INSERT INTO student (studentID, userID, personID, studentlevel, section, departmentID) VALUES ('S-008', '1008', 'P008', '4th Year', 'IT701', 'ct-3003');
--
---- PREFECT VALUES
--INSERT INTO prefect (prefectID, userID, personID, departmentID) VALUES ('DO-001', '1012', 'P009', 'jhs-3001');
--INSERT INTO prefect (prefectID, userID, personID, departmentID) VALUES ('DO-002', '1013', 'P010', 'shs-3002');
--INSERT INTO prefect (prefectID, userID, personID, departmentID) VALUES ('DO-003', '1014', 'P011', 'ct-3003');
--
---- GUARDIAN VALUES
--INSERT INTO guardian (guardianID, personID, contactnumber, relationship, studentID) VALUES ('G-001', 'P015', '0912-345-6789', 'Father', 'S-001');
--INSERT INTO guardian (guardianID, personID, contactnumber, relationship, studentID) VALUES ('G-002', 'P016', '0912-555-6789', 'Mother', 'S-002');
--INSERT INTO guardian (guardianID, personID, contactnumber, relationship, studentID) VALUES ('G-003', 'P017', '0911-222-5555', 'Mother', 'S-003');
--INSERT INTO guardian (guardianID, personID, contactnumber, relationship, studentID) VALUES ('G-004', 'P018', '0922-777-9988', 'Mother', 'S-004');
--INSERT INTO guardian (guardianID, personID, contactnumber, relationship, studentID) VALUES ('G-005', 'P019', '0933-333-3333', 'Mother', 'S-005');
--INSERT INTO guardian (guardianID, personID, contactnumber, relationship, studentID) VALUES ('G-006', 'P020', '0978-343-1212', 'Father', 'S-006');
--INSERT INTO guardian (guardianID, personID, contactnumber, relationship, studentID) VALUES ('G-007', 'P021', '0988-999-3453', 'Mother', 'S-007');
--INSERT INTO guardian (guardianID, personID, contactnumber, relationship, studentID) VALUES ('G-008', 'P022', '0967-876-5152', 'Mother', 'S-008');
--
----OFFENSE VALUES
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-001', 'Vaping', 'Major Offense', 'Bringing vape');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-002', 'Punching', 'Major Offense', 'Punching another student');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-003', 'Stealing', 'Major Offense', 'Stealing things from another student');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-004', 'Bullying', 'Major Offense', 'Student delivers disrespectful messages to another student that includes threats and intimidation');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-005', 'PDA', 'Major Offense', 'Student engages in inappropriate consensual verbal and/or physical gestures/contact, of a sexual nature to another student.');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-006', 'Cheating', 'Major Offense', 'Student deliberately violates rules or engages in plagiarism or copying anothers work');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-007', 'Skip Class', 'Major Offense', 'Student leaves or misses class without permission');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-008', 'Tardiness', 'Major Offense', 'Student is repeatedly late to class');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-009', 'Technology Violation', 'Major Offense', 'Inappropriate use of gadgets');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-010', 'Use/Posession of Alcohol', 'Major Offense', 'Student is in possession of or is using alcohol');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-011', 'Use/Possession of Drugs', 'Major Offense', 'Student is in possession of or is using illegal drugs');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-012', 'Use/Possession of Tobacco', 'Major Offense', 'Student is in possession of or is using tobacco');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-013', 'Use/Possession of Weapons', 'Major Offense', 'Student is in possession of knives or gun or other object readily capable of causing bodily harm');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-014', 'Use/Possession of Drugs', 'Major Offense', 'Student is in possession of or is using illegal drugs');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-015', 'Disrespect', 'Minor Offense', 'Student engages in brief or low-intensity failure to respond to adult requests');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-016', 'Dress Code', 'Minor Offense', 'Student wears clothing that not within the dress code guidelines');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-017', 'Inappropriate Language', 'Minor Offense', 'Student engages in low-intensity instance of appropriate language');
--INSERT INTO offense (offenseID, offense, type, remarks) VALUES ('OFF-018', 'Dress Code', 'Minor Offense', 'Student wears clothing that not within the dress code guidelines');
--
----DISCIPLINARY ACTION VALUES
--INSERT INTO disciplinaryAction (actionID, action, description) VALUES ('D-001', 'Community Service', 'A service component where the student spends time serving in the community meeting actual needs');
--INSERT INTO disciplinaryAction (actionID, action, description) VALUES ('D-002', 'Probation', 'a warning status given to a student whose academic performance or behavior falls below the institutions standards');
--
---- VIOLATION VALUES
--INSERT INTO violation (violationID, studentID, prefectID, offenseID, Dateofviolation, actionID, Dateofresolution, Remarks, status)
--VALUES ('V-001', 'S-001', 'DO-001', 'OFF-002', TO_DATE('2025-07-28','YYYY-MM-DD'), 'D-001', TO_DATE('2025-08-04','YYYY-MM-DD'), '8 hours of Community service', 'IN PROGRESS');
--
--INSERT INTO violation (violationID, studentID, prefectID, offenseID, Dateofviolation, actionID, Dateofresolution, Remarks, status)
--VALUES ('V-002', 'S-002', 'DO-001', 'OFF-005', TO_DATE('2025-10-01','YYYY-MM-DD'), 'D-001', TO_DATE('2025-10-04','YYYY-MM-DD'), '4 hours of Community service', 'IN PROGRESS');
--
--INSERT INTO violation (violationID, studentID, prefectID, offenseID, Dateofviolation, actionID, Dateofresolution, Remarks, status)
--VALUES ('V-003', 'S-003', 'DO-001', 'OFF-007', TO_DATE('2025-10-20','YYYY-MM-DD'), 'D-001', TO_DATE('2025-10-25','YYYY-MM-DD'), '10 hours of Community service', 'IN PROGRESS');
--
---- APPEAL VALUES
--INSERT INTO appeal (appealID, violationID, studentID, message, datefiled, status)
--VALUES ('APP-001', 'V-001', 'S-001', 'I did not punch my classmate', TO_DATE('2025-07-29','YYYY-MM-DD'), 'Not Resolve');
--
--INSERT INTO appeal (appealID, violationID, studentID, message, datefiled, status)
--VALUES ('APP-002', 'V-002', 'S-002', 'We are simply holding hands', TO_DATE('2025-10-03','YYYY-MM-DD'), 'Resolve');
--
--INSERT INTO appeal (appealID, violationID, studentID, message, datefiled, status)
--VALUES ('APP-003', 'V-003', 'S-003', 'I thought that our teacher will not attend the class', TO_DATE('2025-10-22','YYYY-MM-DD'), 'Not Resolve');

COMMIT;
