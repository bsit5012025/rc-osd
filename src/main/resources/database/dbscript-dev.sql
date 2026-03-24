DROP USER rcosd CASCADE;

CREATE USER rcosd IDENTIFIED BY Changeme0;
ALTER USER rcosd QUOTA UNLIMITED ON USERS;
GRANT CREATE SESSION TO rcosd WITH ADMIN OPTION;
GRANT CONNECT TO rcosd;
ALTER SESSION SET current_schema = rcosd;

DROP TABLE person CASCADE CONSTRAINTS;
DROP TABLE login CASCADE CONSTRAINTS;
DROP TABLE offense CASCADE CONSTRAINTS;
DROP TABLE disciplinaryAction CASCADE CONSTRAINTS;
DROP TABLE employee CASCADE CONSTRAINTS;
DROP TABLE student CASCADE CONSTRAINTS;
DROP TABLE disciplinaryStatus CASCADE CONSTRAINTS;
DROP TABLE enrollment CASCADE CONSTRAINTS;
DROP TABLE record CASCADE CONSTRAINTS;
DROP TABLE appeal CASCADE CONSTRAINTS;
DROP TABLE request CASCADE CONSTRAINTS;
DROP TABLE guardian CASCADE CONSTRAINTS;
DROP TABLE studentGuardian CASCADE CONSTRAINTS;

-- PERSON ENTITY
CREATE TABLE person (
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
   offenseID number(20,0) generated as identity
       constraint OFFENSE_NOT_NULL not null,
   offense VARCHAR(100),
   type VARCHAR(50),
   description VARCHAR(500),
   PRIMARY KEY (offenseID)
);

-- DISCIPLINARY ACTION ENTITY
CREATE TABLE disciplinaryAction (
   actionID number(20,0) PRIMARY KEY,
   action VARCHAR(100),
   description VARCHAR(500)
);

-- EMPLOYEE ENTITY
CREATE TABLE employee (
   employeeID VARCHAR(10) PRIMARY KEY,
   personID number(20,0),
   department varchar(20),
   employeeRole VARCHAR(30)
);

-- STUDENT ENTITY
CREATE TABLE student (
   studentID VARCHAR(10) PRIMARY KEY,
   personID number(20,0),
   address VARCHAR(255),
   studentType VARCHAR(20),
   department VARCHAR(20)
);

-- DISCIPLINARY STATUS ENTITY
CREATE TABLE disciplinaryStatus (
   disciplinaryStatusID number(20,0) PRIMARY KEY,
   status VARCHAR(30),
   description VARCHAR(255)
);

-- ENROLLMENT ENTITY
CREATE TABLE enrollment (
   enrollmentID number(20,0) generated as identity
       constraint ENROLLMENT_NOT_NULL not null,
   studentID VARCHAR(10),
   schoolYear VARCHAR(9),
   studentLevel VARCHAR(30),
   section VARCHAR(50),
   department VARCHAR(20),
   disciplinaryStatusID number(20,0),
   primary key (enrollmentID)
);

-- RECORD ENTITY
CREATE TABLE record (
   recordID number(20,0) generated as identity
       constraint RECORD_NOT_NULL not null,
   enrollmentID number(20,0),
   employeeID VARCHAR(10),
   offenseID number(20,0),
   dateOfViolation DATE,
   actionID number(20,0),
   dateOfResolution DATE,
   remarks VARCHAR(500),
   status VARCHAR(30),
   primary key (recordID)
);

-- APPEAL ENTITY
CREATE TABLE appeal (
   appealID number(20,0) generated as identity
       constraint APPEAL_NOT_NULL not null,
   recordID number(20,0),
   enrollmentID number(20,0),
   message VARCHAR(500),
   dateFiled DATE,
   status VARCHAR(20),
   primary key (appealID)
);

--REQUEST ENTITY
CREATE TABLE request (
   requestID number(20,0) generated as identity
       constraint REQUEST_NOT_NULL not null,
   employeeID VARCHAR(10),
   details VARCHAR(100),
   message VARCHAR(500),
   type VARCHAR(100),
   status VARCHAR(10),
   primary key (requestID)
);

-- GUARDIAN ENTITY
CREATE TABLE guardian (
   guardianID number(20,0) generated as identity
       constraint GUARDIAN_NOT_NULL not null,
   personID number(20,0),
   contactNumber VARCHAR2(20),
   relationship VARCHAR2(50),
   primary key (guardianID)
);

CREATE TABLE studentGuardian (
   studentID VARCHAR2(10),
   guardianID NUMBER(20,0),
   primary key (studentID, guardianID)
);

-- CONSTRAINTS
ALTER TABLE login ADD CONSTRAINT FK_LOGIN_PERSON FOREIGN KEY (personID) REFERENCES person(personID);
ALTER TABLE employee ADD CONSTRAINT FK_EMPLOYEE_PERSON FOREIGN KEY (personID) REFERENCES person(personID);
ALTER TABLE student ADD CONSTRAINT FK_STUDENT_PERSON FOREIGN KEY (personID) REFERENCES person(personID);
ALTER TABLE enrollment ADD CONSTRAINT FK_ENROLL_STUDENT FOREIGN KEY (studentID) REFERENCES student(studentID);
ALTER TABLE enrollment ADD CONSTRAINT FK_ENROLL_STATUS FOREIGN KEY (disciplinaryStatusID) REFERENCES disciplinaryStatus(disciplinaryStatusID);
ALTER TABLE record ADD CONSTRAINT FK_RECORD_ENROLLMENT FOREIGN KEY (enrollmentID) REFERENCES enrollment(enrollmentID);
ALTER TABLE record ADD CONSTRAINT FK_RECORD_EMPLOYEE FOREIGN KEY (employeeID) REFERENCES employee(employeeID);
ALTER TABLE record ADD CONSTRAINT FK_RECORD_OFFENSE FOREIGN KEY (offenseID) REFERENCES offense(offenseID);
ALTER TABLE record ADD CONSTRAINT FK_RECORD_ACTION FOREIGN KEY (actionID) REFERENCES disciplinaryAction(actionID);
ALTER TABLE appeal ADD CONSTRAINT FK_APPEAL_RECORD FOREIGN KEY (recordID) REFERENCES record(recordID);
ALTER TABLE appeal ADD CONSTRAINT FK_APPEAL_ENROLLMENT FOREIGN KEY (enrollmentID) REFERENCES enrollment(enrollmentID);
ALTER TABLE request ADD CONSTRAINT FK_REQUEST_EMPLOYEE FOREIGN KEY (employeeID) REFERENCES employee(employeeID);
ALTER TABLE guardian ADD CONSTRAINT FK_GUARDIAN_PERSON FOREIGN KEY (personID) REFERENCES person(personID);
ALTER TABLE studentGuardian ADD CONSTRAINT FK_SG_STUDENT FOREIGN KEY (studentID) REFERENCES student(studentID);
ALTER TABLE studentGuardian ADD CONSTRAINT FK_SG_GUARDIAN FOREIGN KEY (guardianID) REFERENCES guardian(guardianID);
ALTER TABLE record ADD CONSTRAINT CHK_RECORD_STATUS CHECK (status IN ('PENDING', 'RESOLVED', 'APPEALED'));
ALTER TABLE employee ADD CONSTRAINT CHK_EMPLOYEE_DEPT CHECK (department IN ('JHS', 'SHS', 'COLLEGE'));
ALTER TABLE student ADD CONSTRAINT CHK_STUDENT_DEPT CHECK (department IN ('JHS', 'SHS', 'COLLEGE'));
ALTER TABLE enrollment ADD CONSTRAINT CHK_ENROLL_DEPT CHECK (department IN ('JHS', 'SHS', 'COLLEGE'));
ALTER TABLE guardian ADD CONSTRAINT CHK_GUARDIAN_RELATIONSHIP CHECK (relationship IN ('Father', 'Mother', 'Guardian'));

-- TEST DATA
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Bayona', 'Wilrow', 'Reosa');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cain', 'Carl Justine', 'Dela Cruz');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Camama', 'Mark Joshua', 'Reyes');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cruz', 'John Zenith', 'Pasion');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('De Gala', 'Angel Lowyza', 'Resurreccion');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('De Rojas', 'Geoffrey Allen', 'Villanueva');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Quevada', 'Keith Jasper', 'Brioso');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Reyes', 'Leeane Glazel', 'Nialda');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Santos', 'Juan', 'Dela Cruz');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Reyes', 'Maria', 'Lopez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cruz', 'Angelo', 'Perez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Garcia', 'Sophia', 'Martinez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Mendoza', 'Daniel', 'Rivera');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Flores', 'Camille', 'Torres');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Navarro', 'Joshua', 'Bautista');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Ramos', 'Patricia', 'King');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Villanueva', 'Mark', 'Andres');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Castillo', 'Nicole', 'Santiago');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Delgado', 'Anthony', 'Reyes');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cabrera', 'Elaine', 'Torres');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Molina', 'Francis', 'Alcantara');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Torralba', 'Bianca', 'Lopez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Villanueva', 'Rafael', 'Castillo');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Sarmiento', 'Nicole', 'Garcia');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Dizon', 'Kevin', 'Bautista');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Alcantara', 'Jasmine', 'Perez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Ramirez', 'Carlos', 'Morales');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Lozada', 'Patricia', 'Santos');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Estrella', 'Leah', 'Montero');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Padilla', 'Miguel', 'Sarmiento');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Torres', 'Isabelle', 'Delos Santos');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Reyes', 'Christian', 'Alvarez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Salvador', 'Ariana', 'Villanueva');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cruz', 'Nathan', 'Lopez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Velasco', 'Sophia', 'Garcia');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Martinez', 'Joshua', 'Perez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Ortega', 'Patricia', 'Dela Rosa');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Javier', 'Rico', 'Perez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Aguilar', 'Bryan', 'Castro');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Bautista', 'Angela', 'Reyes');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Castro', 'John Paul', 'Santos');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Domingo', 'Liza', 'Garcia');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Espino', 'Carlo', 'Mendoza');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Fajardo', 'Katrina', 'Lopez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Galvez', 'Nathan', 'Torres');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Herrera', 'Bea', 'Villanueva');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Isidro', 'Mark Anthony', 'Ramos');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Jimenez', 'Claire', 'Navarro');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Aquino', 'Renz', 'Dela Cruz');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Balagtas', 'Mikaela', 'Santos');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cunanan', 'Paolo', 'Garcia');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('De Guzman', 'Aira', 'Lopez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Eusebio', 'Christian', 'Reyes');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Francisco', 'Joy', 'Torres');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Gatchalian', 'Kyle', 'Mendoza');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Hilario', 'Trisha', 'Villanueva');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Ignacio', 'Jerome', 'Ramos');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Lacsamana', 'Danica', 'Navarro');
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
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Santos', 'Pedro', 'Dela Cruz');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Reyes', 'Ana', 'Lopez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cruz', 'Roberto', 'Perez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Garcia', 'Elena', 'Martinez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Mendoza', 'Carlos', 'Rivera');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Flores', 'Liza', 'Torres');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Navarro', 'Ramon', 'Bautista');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Ramos', 'Cynthia', 'King');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Villanueva', 'Jose', 'Andres');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Castillo', 'Marilyn', 'Santiago');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Delgado', 'Manuel', 'Reyes');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cabrera', 'Rosa', 'Torres');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Molina', 'Ernesto', 'Alcantara');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Torralba', 'Grace', 'Lopez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Villanueva', 'Antonio', 'Castillo');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Sarmiento', 'Lourdes', 'Garcia');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Dizon', 'Ricardo', 'Bautista');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Alcantara', 'Teresita', 'Perez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Ramirez', 'Danilo', 'Morales');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Lozada', 'Evelyn', 'Santos');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Estrella', 'Carlos', 'Montero');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Padilla', 'Rosa', 'Sarmiento');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Torres', 'Antonio', 'Delos Santos');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Reyes', 'Luz', 'Alvarez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Salvador', 'Eduardo', 'Villanueva');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cruz', 'Maribel', 'Lopez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Velasco', 'Fernando', 'Garcia');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Martinez', 'Elena', 'Perez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Ortega', 'Ricardo', 'Dela Rosa');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Javier', 'Alfredo', 'Perez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Aguilar', 'Hector', 'Castro');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Bautista', 'Josephine', 'Reyes');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Castro', 'Felix', 'Santos');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Domingo', 'Susan', 'Garcia');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Espino', 'Rolando', 'Mendoza');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Fajardo', 'Daisy', 'Lopez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Galvez', 'Benigno', 'Torres');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Herrera', 'Cristina', 'Villanueva');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Isidro', 'Oscar', 'Ramos');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Jimenez', 'Teresa', 'Navarro');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Aquino', 'Benito', 'Dela Cruz');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Balagtas', 'Veronica', 'Santos');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Cunanan', 'Mario', 'Garcia');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('De Guzman', 'Angelita', 'Lopez');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Eusebio', 'Norberto', 'Reyes');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Francisco', 'Belinda', 'Torres');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Gatchalian', 'Rogelio', 'Mendoza');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Hilario', 'Rowena', 'Villanueva');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Ignacio', 'Edgardo', 'Ramos');
INSERT INTO person ( lastName, firstName, middleName) VALUES ('Lacsamana', 'Norma', 'Navarro');

insert into login (personID, username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values (1, 'admin', 'admin', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_ADMIN', 'user:read,user:create,user:update,user:delete', 1, 0);
insert into login (personID,username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values (9, 'prefect', '1234', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_PREFECT', 'user:read,user:create,user:update,user:delete', 1, 0);
insert into login (personID, username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values (12, 'deptHead','1234', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_STAFF', 'user:read,user:create,user:update', 1, 0);
insert into login (personID, username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values (2, 'user1','1234', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_USER', 'user:read,user:create,user:update', 1, 0);
insert into login (personID, username, password, join_date, last_login_date, role, authorities, is_active, is_locked)
values (3, 'user2','1234', to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_USER', 'user:read,user:create,user:update', 1, 0);

INSERT INTO offense (offense, type, description) VALUES ('Vaping', 'Major Offense', 'Bringing vape');
INSERT INTO offense (offense, type, description) VALUES ('Punching', 'Major Offense', 'Punching another student');
INSERT INTO offense (offense, type, description) VALUES ('Stealing', 'Major Offense', 'Stealing things from another student');
INSERT INTO offense (offense, type, description) VALUES ('Bullying', 'Major Offense', 'Student delivers disrespectful messages to another student that includes threats and intimidation');
INSERT INTO offense (offense, type, description) VALUES ('PDA', 'Major Offense', 'Student engages in inappropriate consensual verbal and/or physical gestures/contact, of a sexual nature to another student.');
INSERT INTO offense (offense, type, description) VALUES ('Cheating', 'Major Offense', 'Student deliberately violates rules or engages in plagiarism or copying anothers work');
INSERT INTO offense (offense, type, description) VALUES ('Skip Class', 'Major Offense', 'Student leaves or misses class without permission');
INSERT INTO offense (offense, type, description) VALUES ('Tardiness', 'Major Offense', 'Student is repeatedly late to class');
INSERT INTO offense (offense, type, description) VALUES ('Technology Violation', 'Major Offense', 'Inappropriate use of gadgets');
INSERT INTO offense (offense, type, description) VALUES ('Use/Possession of Alcohol', 'Major Offense', 'Student is in possession of or is using alcohol');
INSERT INTO offense (offense, type, description) VALUES ('Use/Possession of Drugs', 'Major Offense', 'Student is in possession of or is using illegal drugs');
INSERT INTO offense (offense, type, description) VALUES ('Use/Possession of Tobacco', 'Major Offense', 'Student is in possession of or is using tobacco');
INSERT INTO offense (offense, type, description) VALUES ('Use/Possession of Weapons', 'Major Offense', 'Student is in possession of knives or gun or other object readily capable of causing bodily harm');
INSERT INTO offense (offense, type, description) VALUES ('Use/Possession of Drugs', 'Major Offense', 'Student is in possession of or is using illegal drugs');
INSERT INTO offense (offense, type, description) VALUES ('Disrespect', 'Minor Offense', 'Student engages in brief or low-intensity failure to respond to adult requests');
INSERT INTO offense (offense, type, description) VALUES ('Dress Code', 'Minor Offense', 'Student wears clothing that not within the dress code guidelines');
INSERT INTO offense (offense, type, description) VALUES ('Inappropriate Language', 'Minor Offense', 'Student engages in low-intensity instance of appropriate language');
INSERT INTO offense (offense, type, description) VALUES ('Dress Code', 'Minor Offense', 'Student wears clothing that not within the dress code guidelines');

INSERT INTO disciplinaryAction (actionID, action, description) VALUES (1, 'Community Service', 'A service component where the student spends time serving in the community meeting actual needs');
INSERT INTO disciplinaryAction (actionID, action, description) VALUES (2, 'Probation', 'a warning status given to a student whose academic performance or behavior falls below the institutions standards');

INSERT INTO employee (employeeID, personID, department, employeeRole) VALUES ('EMP-001', 61, 'JHS', 'DEPT_HEAD');
INSERT INTO employee (employeeID, personID, department, employeeRole) VALUES ('EMP-002', 60, 'JHS', 'PREFECT');
INSERT INTO employee (employeeID, personID, department, employeeRole) VALUES ('EMP-003', 59, 'COLLEGE', 'PREFECT');

INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0001', 1, 'Malabag', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0002', 2, 'Malabag', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0003', 3, 'Malabag', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0004', 4, 'Malabag', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0005', 5, 'Alfonso', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0006', 6, 'Malabag', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0007', 7, 'Buho', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0008', 8, 'Malabag', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0009', 9, 'Bacoor', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0010', 10, 'Bacoor', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0011', 11, 'Dasmariñas', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0012', 12, 'Dasmariñas', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0013', 13, 'Dasmariñas', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0014', 14, 'Batangas', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0015', 15, 'Tagaytay', 'Intern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0016', 16, 'Tagaytay', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0017', 17, 'Buho', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('CT23-0018', 18, 'Silang', 'Extern', 'COLLEGE');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0019', 19, 'Alfonso', 'Intern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0020', 20, 'Nasugbu', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0021', 21, 'Amuyong', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0022', 22, 'Tagaytay', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0023', 23, 'Tagaytay', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0024', 24, 'Buho', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0025', 25, 'Buho', 'Intern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0026', 26, 'Malabag', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0027', 27, 'Malabag', 'Intern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0028', 28, 'Malabag', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0029', 29, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0030', 30, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0031', 31, 'Buho', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0032', 32, 'Tagaytay', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0033', 33, 'Tagaytay', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0034', 34, 'Alfonso', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0035', 35, 'Dasmariñas', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0036', 36, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0037', 37, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0038', 38, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0039', 39, 'Malabag', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0040', 40, 'Malabag', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0041', 41, 'Alfonso', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0042', 42, 'Malabag', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0043', 43, 'Tagaytay', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0044', 44, 'Lalaan II', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0045', 45, 'Buho', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0046', 46, 'Tagaytay', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0047', 47, 'Malabag', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('JHS-0048', 48, 'Malabag', 'Extern', 'JHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0049', 49, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0050', 50, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0051', 51, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0052', 52, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0053', 53, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0054', 54, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0055', 55, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0056', 56, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0057', 57, 'Malabag', 'Extern', 'SHS');
INSERT INTO student (studentID, personID, address, studentType, department) VALUES ('SHS-0058', 58, 'Malabag', 'Extern', 'SHS');

INSERT INTO disciplinaryStatus (disciplinaryStatusID, status, description) VALUES (1, 'Good Standing', 'Student has no disciplinary issues and maintains good behavior.');
INSERT INTO disciplinaryStatus (disciplinaryStatusID, status, description) VALUES (2, 'Conduct Monitoring', 'Student is under observation due to minor conduct issues.');
INSERT INTO disciplinaryStatus (disciplinaryStatusID, status, description) VALUES (3, 'Conduct Probation', 'Student is on probation due to repeated or serious conduct violations.');
INSERT INTO disciplinaryStatus (disciplinaryStatusID, status, description) VALUES (4, 'Attendance Monitoring', 'Student is under observation due to attendance issues.');
INSERT INTO disciplinaryStatus (disciplinaryStatusID, status, description) VALUES (5, 'Attendance Probation', 'Student is on probation due to repeated attendance violations.');

INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0001', '2025-2026', '3rd Year', 'IT501', 'COLLEGE', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0002', '2025-2026', '3rd Year', 'IT501', 'COLLEGE', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0003', '2025-2026', '3rd Year', 'IT501', 'COLLEGE', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0004', '2025-2026', '3rd Year', 'IT501', 'COLLEGE', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0005', '2025-2026', '3rd Year', 'IT501', 'COLLEGE', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0006', '2025-2026', '3rd Year', 'IT501', 'COLLEGE', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0007', '2025-2026', '3rd Year', 'IT501', 'COLLEGE', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0008', '2025-2026', '3rd Year', 'IT501', 'COLLEGE', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0009', '2025-2026', '2nd Year', 'ECE301', 'COLLEGE', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0010', '2025-2026', '2nd Year', 'ECE301', 'COLLEGE', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0011', '2025-2026', '2nd Year', 'ECE301', 'COLLEGE', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0012', '2025-2026', '2nd Year', 'ECE301', 'COLLEGE', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0013', '2025-2026', '2nd Year', 'ECE301', 'COLLEGE', 2);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0014', '2025-2026', '4th Year', 'ECE701', 'COLLEGE', 5);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0015', '2025-2026', '4th Year', 'ECE701', 'COLLEGE', 5);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0016', '2025-2026', '4th Year', 'ECE701', 'COLLEGE', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0017', '2025-2026', '4th Year', 'ECE701', 'COLLEGE', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('CT23-0018', '2025-2026', '4th Year', 'ECE701', 'COLLEGE', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0019', '2025-2026', 'Grade-8', 'St. Hannibal', 'JHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0020', '2025-2026', 'Grade-8', 'St. Hannibal', 'JHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0021', '2025-2026', 'Grade-8', 'St. Hannibal', 'JHS', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0022', '2025-2026', 'Grade-8', 'St. Hannibal', 'JHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0023', '2025-2026', 'Grade-8', 'St. Hannibal', 'JHS', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0024', '2025-2026', 'Grade-9', 'St. Anthony', 'JHS', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0025', '2025-2026', 'Grade-9', 'St. Anthony', 'JHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0026', '2025-2026', 'Grade-9', 'St. Anthony', 'JHS', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0027', '2025-2026', 'Grade-9', 'St. Anthony', 'JHS', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0028', '2025-2026', 'Grade-9', 'St. Anthony', 'JHS', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0029', '2025-2026', 'Grade-12', 'St. Mary Magdalene', 'SHS', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0030', '2025-2026', 'Grade-12', 'St. Mary Magdalene', 'SHS', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0031', '2025-2026', 'Grade-12', 'St. Mary Magdalene', 'SHS', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0032', '2025-2026', 'Grade-12', 'St. Mary Magdalene', 'SHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0033', '2025-2026', 'Grade-12', 'St. Mary Magdalene', 'SHS', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0034', '2025-2026', 'Grade-12', 'St. Teresa of Calcutta', 'SHS', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0035', '2025-2026', 'Grade-12', 'St. Teresa of Calcutta', 'SHS', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0036', '2025-2026', 'Grade-12', 'St. Teresa of Calcutta', 'SHS', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0037', '2025-2026', 'Grade-12', 'St. Teresa of Calcutta', 'SHS', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0038', '2025-2026', 'Grade-12', 'St. Teresa of Calcutta', 'SHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0039', '2025-2026', 'Grade-7', 'St. Raphael', 'JHS', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0040', '2025-2026', 'Grade-7', 'St. Raphael', 'JHS', 2);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0041', '2025-2026', 'Grade-7', 'St. Raphael', 'JHS', 2);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0042', '2025-2026', 'Grade-7', 'St. Raphael', 'JHS', 2);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0043', '2025-2026', 'Grade-7', 'St. Raphael', 'JHS', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0044', '2025-2026', 'Grade-10', 'St. Augustine', 'JHS', 4);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0045', '2025-2026', 'Grade-10', 'St. Augustine', 'JHS', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0046', '2025-2026', 'Grade-10', 'St. Augustine', 'JHS', 1);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0047', '2025-2026', 'Grade-10', 'St. Augustine', 'JHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('JHS-0048', '2025-2026', 'Grade-10', 'St. Augustine', 'JHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0049', '2025-2026', 'Grade-11', 'St. Charles Borromeo', 'SHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0050', '2025-2026', 'Grade-11', 'St. Charles Borromeo', 'SHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0051', '2025-2026', 'Grade-11', 'St. Charles Borromeo', 'SHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0052', '2025-2026', 'Grade-11', 'St. Charles Borromeo', 'SHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0053', '2025-2026', 'Grade-11', 'St. Charles Borromeo', 'SHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0054', '2025-2026', 'Grade-11', 'St. Joseph', 'SHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0055', '2025-2026', 'Grade-11', 'St. Joseph', 'SHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0056', '2025-2026', 'Grade-11', 'St. Joseph', 'SHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0057', '2025-2026', 'Grade-11', 'St. Joseph', 'SHS', 3);
INSERT INTO enrollment (studentID, schoolYear, studentLevel, section, department, disciplinaryStatusID) VALUES ('SHS-0058', '2025-2026', 'Grade-11', 'St. Joseph', 'SHS', 3);

INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (20, 'EMP-002', 8, TO_DATE('2025-09-15', 'YYYY-MM-DD'), 1, TO_DATE('2025-09-17', 'YYYY-MM-DD'), 'Repeatedly late to class', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (18, 'EMP-003', 5, TO_DATE('2025-01-12', 'YYYY-MM-DD'), 2, TO_DATE('2025-01-20', 'YYYY-MM-DD'), 'Caught holding hands', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (12, 'EMP-003', 10, TO_DATE('2025-04-28', 'YYYY-MM-DD'), 2, TO_DATE('2025-04-30', 'YYYY-MM-DD'), 'She was seen bringing alcohol to the acquintace party.', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (37, 'EMP-002', 15, TO_DATE('2025-01-20', 'YYYY-MM-DD'), 2, TO_DATE('2025-01-25', 'YYYY-MM-DD'), 'He raised his voice at his teacher.', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (13, 'EMP-003', 10, TO_DATE('2025-04-28', 'YYYY-MM-DD'), 2, TO_DATE('2025-04-30', 'YYYY-MM-DD'), 'He was seen bringing alcohol to the acquintace party.', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (19, 'EMP-002', 18, TO_DATE('2025-09-01', 'YYYY-MM-DD'), 2, TO_DATE('2025-09-10', 'YYYY-MM-DD'), 'She is not in her uniform', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (31, 'EMP-002', 2, TO_DATE('2025-10-11', 'YYYY-MM-DD'), 2, TO_DATE('2025-10-18', 'YYYY-MM-DD'), 'He punched his classmate', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (28, 'EMP-002', 6, TO_DATE('2025-11-11', 'YYYY-MM-DD'), 2, TO_DATE('2025-11-18', 'YYYY-MM-DD'), 'Caught cheating during exam', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (24, 'EMP-002', 9, TO_DATE('2025-02-14', 'YYYY-MM-DD'), 2, TO_DATE('2025-02-14', 'YYYY-MM-DD'), 'Seen using phone during break', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (38, 'EMP-002', 17, TO_DATE('2025-03-17', 'YYYY-MM-DD'), 2, TO_DATE('2025-02-14', 'YYYY-MM-DD'), 'Heard cursing', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (34, 'EMP-002', 9, TO_DATE('2025-01-14', 'YYYY-MM-DD'), 2, TO_DATE('2025-02-14', 'YYYY-MM-DD'), 'Seen using phone during break', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (22, 'EMP-002', 7, TO_DATE('2025-02-28', 'YYYY-MM-DD'), 2, TO_DATE('2025-03-05', 'YYYY-MM-DD'), 'The guard saw the student trying to sneak out of the school', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (29, 'EMP-002', 7, TO_DATE('2025-01-13', 'YYYY-MM-DD'), 2, TO_DATE('2025-01-17', 'YYYY-MM-DD'), 'The guard saw the student trying to sneak out of the school', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (33, 'EMP-002', 8, TO_DATE('2025-01-13', 'YYYY-MM-DD'), 2, TO_DATE('2025-01-17', 'YYYY-MM-DD'), 'Repeatedly late to class', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (1, 'EMP-003', 8, TO_DATE('2025-01-13', 'YYYY-MM-DD'), 2, TO_DATE('2025-01-17', 'YYYY-MM-DD'), 'Repeatedly late to class', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (11, 'EMP-003', 18, TO_DATE('2025-01-13', 'YYYY-MM-DD'), 2, TO_DATE('2025-01-17', 'YYYY-MM-DD'), 'She is not in her uniform', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (16, 'EMP-003', 6, TO_DATE('2024-11-13', 'YYYY-MM-DD'), 2, TO_DATE('2024-11-17', 'YYYY-MM-DD'), 'Caught cheating during exam', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (2, 'EMP-003', 2, TO_DATE('2024-08-15', 'YYYY-MM-DD'), 2, TO_DATE('2024-08-16', 'YYYY-MM-DD'), 'Punched his classmate', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (3, 'EMP-003', 17, TO_DATE('2024-10-01', 'YYYY-MM-DD'), 2, TO_DATE('2024-10-05', 'YYYY-MM-DD'), 'Not wearing proper uniform', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (4, 'EMP-003', 17, TO_DATE('2024-08-20', 'YYYY-MM-DD'), 2, TO_DATE('2024-08-20', 'YYYY-MM-DD'), 'Heard cursing', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (5, 'EMP-003', 9, TO_DATE('2025-01-20', 'YYYY-MM-DD'), 2, TO_DATE('2025-01-23', 'YYYY-MM-DD'), 'Using her phone during discussion', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (6, 'EMP-003', 8, TO_DATE('2024-11-14', 'YYYY-MM-DD'), 2, TO_DATE('2024-11-17', 'YYYY-MM-DD'), 'Always late to class', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (7, 'EMP-003', 3, TO_DATE('2025-04-09', 'YYYY-MM-DD'), 2, TO_DATE('2025-04-09', 'YYYY-MM-DD'), 'Stealing money', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (8, 'EMP-003', 15, TO_DATE('2025-10-01', 'YYYY-MM-DD'), 2, TO_DATE('2025-10-01', 'YYYY-MM-DD'), 'Did not follow her proffessor instruction', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (42, 'EMP-002', 7, TO_DATE('2024-11-18', 'YYYY-MM-DD'), 2, TO_DATE('2024-11-19', 'YYYY-MM-DD'), 'The guard saw the student trying to sneak out of the school', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (39, 'EMP-002', 17, TO_DATE('2025-09-18', 'YYYY-MM-DD'), 2, TO_DATE('2025-09-19', 'YYYY-MM-DD'), 'Heard cursing', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (42, 'EMP-002', 5, TO_DATE('2025-02-14', 'YYYY-MM-DD'), 2, TO_DATE('2025-02-17', 'YYYY-MM-DD'), 'Caught holding hands', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (27, 'EMP-003', 3, TO_DATE('2024-11-18', 'YYYY-MM-DD'), 2, TO_DATE('2024-11-19', 'YYYY-MM-DD'), 'Stealing money', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (58, 'EMP-003', 7, TO_DATE('2024-11-18', 'YYYY-MM-DD'), 2, TO_DATE('2024-11-19', 'YYYY-MM-DD'), 'The guard saw the student trying to sneak out of the school', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (54, 'EMP-003', 3, TO_DATE('2024-11-18', 'YYYY-MM-DD'), 2, TO_DATE('2024-11-19', 'YYYY-MM-DD'), 'Stealing money', 'PENDING');
INSERT INTO record (enrollmentID, employeeID, offenseID, dateOfViolation, actionID, dateOfResolution, remarks, status) VALUES (29, 'EMP-003', 17, TO_DATE('2024-11-18', 'YYYY-MM-DD'), 2, TO_DATE('2024-11-19', 'YYYY-MM-DD'), 'Not wearing proper uniform', 'PENDING');

INSERT INTO appeal (recordID, enrollmentID, message, dateFiled, status) VALUES (15, 1, 'I swear not to be late to class again', TO_DATE('2025-07-29','YYYY-MM-DD'), 'PENDING');
INSERT INTO appeal (recordID, enrollmentID, message, dateFiled, status) VALUES (18, 2, 'I did not punch my classmate', TO_DATE('2025-07-29','YYYY-MM-DD'), 'PENDING');
INSERT INTO appeal (recordID, enrollmentID, message, dateFiled, status) VALUES (19, 3, 'We do not have classes that day', TO_DATE('2025-07-29','YYYY-MM-DD'), 'PENDING');
INSERT INTO appeal (recordID, enrollmentID, message, dateFiled, status) VALUES (20, 4, 'Sorry, I swear not to do it again', TO_DATE('2025-07-29','YYYY-MM-DD'), 'PENDING');
INSERT INTO appeal (recordID, enrollmentID, message, dateFiled, status) VALUES (21, 5, 'I used my phone during class because my mom text me', TO_DATE('2025-07-29','YYYY-MM-DD'), 'PENDING');
INSERT INTO appeal (recordID, enrollmentID, message, dateFiled, status) VALUES (2, 18, 'I am just holding her/his hand', TO_DATE('2025-07-29','YYYY-MM-DD'), 'PENDING');
INSERT INTO appeal (recordID, enrollmentID, message, dateFiled, status) VALUES (29, 58, 'I am not skipping class', TO_DATE('2025-07-29','YYYY-MM-DD'), 'PENDING');
INSERT INTO appeal (recordID, enrollmentID, message, dateFiled, status) VALUES (22, 6, 'I swear not to be late to class again', TO_DATE('2025-07-29','YYYY-MM-DD'), 'PENDING');
INSERT INTO appeal (recordID, enrollmentID, message, dateFiled, status) VALUES (23, 7, 'It was my money', TO_DATE('2025-07-29','YYYY-MM-DD'), 'PENDING');
INSERT INTO appeal (recordID, enrollmentID, message, dateFiled, status) VALUES (14, 8, 'I swear to listen to my teacher', TO_DATE('2025-07-29','YYYY-MM-DD'), 'PENDING');

INSERT INTO request (employeeID, details, type, message, status) VALUES ('EMP-001', 'St. Andrew', 'By Section', 'Requesting for the conduct record of all students in St. Andrew', 'PENDING');
INSERT INTO request (employeeID, details, type, message, status) VALUES ('EMP-001', 'Grade 10', 'By Batch', 'Requesting for the conduct record of the graduating class of 2025-2026', 'PENDING');
INSERT INTO request (employeeID, details, type, message, status) VALUES ('EMP-001', 'St. Raphael', 'By Section', 'Requesting for the conduct record of all students in St. Raphael', 'PENDING');
INSERT INTO request (employeeID, details, type, message, status) VALUES ('EMP-001', 'IT601', 'By Section', 'Requesting for the conduct record of all students in IT601', 'PENDING');
INSERT INTO request (employeeID, details, type, message, status) VALUES ('EMP-001', 'St. Augustine', 'By Section', 'Requesting for the conduct record of all students in St. Augustine', 'PENDING');
INSERT INTO request (employeeID, details, type, message, status) VALUES ('EMP-001', 'ECE701', 'By Section', 'Requesting for the conduct record of all students in ECE701', 'PENDING');

INSERT INTO guardian (personID, contactNumber, relationship) VALUES (65, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (66, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (67, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (68, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (69, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (70, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (71, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (72, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (73, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (74, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (75, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (76, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (77, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (78, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (79, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (80, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (81, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (82, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (83, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (84, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (85, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (86, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (87, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (88, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (89, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (90, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (91, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (92, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (93, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (94, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (95, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (96, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (97, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (98, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (99, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (100, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (101, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (102, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (103, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (104, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (105, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (106, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (107, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (108, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (109, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (110, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (111, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (112, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (113, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (114, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (115, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (116, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (117, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (118, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (119, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (120, '09000000000', 'Mother');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (121, '09000000000', 'Father');
INSERT INTO guardian (personID, contactNumber, relationship) VALUES (122, '09000000000', 'Mother');

INSERT INTO studentGuardian VALUES ('CT23-0001', 1);
INSERT INTO studentGuardian VALUES ('CT23-0002', 2);
INSERT INTO studentGuardian VALUES ('CT23-0003', 3);
INSERT INTO studentGuardian VALUES ('CT23-0004', 4);
INSERT INTO studentGuardian VALUES ('CT23-0005', 5);
INSERT INTO studentGuardian VALUES ('CT23-0006', 6);
INSERT INTO studentGuardian VALUES ('CT23-0007', 7);
INSERT INTO studentGuardian VALUES ('CT23-0008', 8);
INSERT INTO studentGuardian VALUES ('CT23-0009', 9);
INSERT INTO studentGuardian VALUES ('CT23-0010', 10);
INSERT INTO studentGuardian VALUES ('CT23-0011', 11);
INSERT INTO studentGuardian VALUES ('CT23-0012', 12);
INSERT INTO studentGuardian VALUES ('CT23-0013', 13);
INSERT INTO studentGuardian VALUES ('CT23-0014', 14);
INSERT INTO studentGuardian VALUES ('CT23-0015', 15);
INSERT INTO studentGuardian VALUES ('CT23-0016', 16);
INSERT INTO studentGuardian VALUES ('CT23-0017', 17);
INSERT INTO studentGuardian VALUES ('CT23-0018', 18);
INSERT INTO studentGuardian VALUES ('JHS-0019', 19);
INSERT INTO studentGuardian VALUES ('JHS-0020', 20);
INSERT INTO studentGuardian VALUES ('JHS-0021', 21);
INSERT INTO studentGuardian VALUES ('JHS-0022', 22);
INSERT INTO studentGuardian VALUES ('JHS-0023', 23);
INSERT INTO studentGuardian VALUES ('JHS-0024', 24);
INSERT INTO studentGuardian VALUES ('JHS-0025', 25);
INSERT INTO studentGuardian VALUES ('JHS-0026', 26);
INSERT INTO studentGuardian VALUES ('JHS-0027', 27);
INSERT INTO studentGuardian VALUES ('JHS-0028', 28);
INSERT INTO studentGuardian VALUES ('SHS-0029', 29);
INSERT INTO studentGuardian VALUES ('SHS-0030', 30);
INSERT INTO studentGuardian VALUES ('SHS-0031', 31);
INSERT INTO studentGuardian VALUES ('SHS-0032', 32);
INSERT INTO studentGuardian VALUES ('SHS-0033', 33);
INSERT INTO studentGuardian VALUES ('SHS-0034', 34);
INSERT INTO studentGuardian VALUES ('SHS-0035', 35);
INSERT INTO studentGuardian VALUES ('SHS-0036', 36);
INSERT INTO studentGuardian VALUES ('SHS-0037', 37);
INSERT INTO studentGuardian VALUES ('SHS-0038', 38);
INSERT INTO studentGuardian VALUES ('JHS-0039', 39);
INSERT INTO studentGuardian VALUES ('JHS-0040', 40);
INSERT INTO studentGuardian VALUES ('JHS-0041', 41);
INSERT INTO studentGuardian VALUES ('JHS-0042', 42);
INSERT INTO studentGuardian VALUES ('JHS-0043', 43);
INSERT INTO studentGuardian VALUES ('JHS-0044', 44);
INSERT INTO studentGuardian VALUES ('JHS-0045', 45);
INSERT INTO studentGuardian VALUES ('JHS-0046', 46);
INSERT INTO studentGuardian VALUES ('JHS-0047', 47);
INSERT INTO studentGuardian VALUES ('JHS-0048', 48);
INSERT INTO studentGuardian VALUES ('SHS-0049', 49);
INSERT INTO studentGuardian VALUES ('SHS-0050', 50);
INSERT INTO studentGuardian VALUES ('SHS-0051', 51);
INSERT INTO studentGuardian VALUES ('SHS-0052', 52);
INSERT INTO studentGuardian VALUES ('SHS-0053', 53);
INSERT INTO studentGuardian VALUES ('SHS-0054', 54);
INSERT INTO studentGuardian VALUES ('SHS-0055', 55);
INSERT INTO studentGuardian VALUES ('SHS-0056', 56);
INSERT INTO studentGuardian VALUES ('SHS-0057', 57);
INSERT INTO studentGuardian VALUES ('SHS-0058', 58);

COMMIT;