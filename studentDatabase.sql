drop table Marks;
drop table Students;
drop table Groups;
drop table Specializations;
drop table Subjects;


CREATE TABLE subjects
(id SERIAL PRIMARY KEY,
 name VARCHAR(50) NOT NULL);

CREATE TABLE specializations
(id SERIAL PRIMARY KEY,
 name VARCHAR(50) NOT NULL,
 parentid INTEGER REFERENCES specializations);

CREATE TABLE groups
(id SERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL,
specid INTEGER REFERENCES specializations);

CREATE TABLE students
(id SERIAL PRIMARY KEY,
name VARCHAR(10) NOT NULL,
age NUMERIC(4) NOT NULL,
groupid INTEGER REFERENCES groups);

CREATE TABLE marks
(id SERIAL PRIMARY KEY,
 subjectid INTEGER REFERENCES subjects,
 mark NUMERIC(4) NOT NULL,
 studentid INTEGER REFERENCES students);

INSERT INTO Subjects (name) VALUES ('math');
INSERT INTO Subjects (name) VALUES ('english');
INSERT INTO Subjects (name) VALUES ('physic');

INSERT INTO Specializations (name) VALUES ('main faculty');
INSERT INTO Specializations (name,parentid) VALUES ('mathematic faculty',1);
INSERT INTO Specializations (name,parentid) VALUES ('english faculty',1);
INSERT INTO Specializations (name,parentid) VALUES ('physic faculty',1);

INSERT INTO Groups (name,specid) VALUES ('gr. 33',1);
INSERT INTO Groups (name,specid) VALUES ('gr. 23',4);
INSERT INTO Groups (name,specid) VALUES ('gr. 13',3);
INSERT INTO Groups (name,specid) VALUES ('gr. 43',2);

INSERT INTO Students (name,age,groupid) VALUES ('Ivanov',20,1);
INSERT INTO Students (name,age,groupid) VALUES ('Petrov',19,2);
INSERT INTO Students (name,age,groupid) VALUES ('Sidorov',21,3);
INSERT INTO Students (name,age,groupid) VALUES ('Vasechkin',20,4);
INSERT INTO Students (name,age,groupid) VALUES ('James',20,1);

INSERT INTO Marks (subjectid,mark,studentid) VALUES (1,5,1);
INSERT INTO Marks (subjectid,mark,studentid) VALUES (2,4,1);
INSERT INTO Marks (subjectid,mark,studentid) VALUES (3,4,1);

INSERT INTO Marks (subjectid,mark,studentid) VALUES (1,3,2);
INSERT INTO Marks (subjectid,mark,studentid) VALUES (2,4,2);
INSERT INTO Marks (subjectid,mark,studentid) VALUES (3,3,2);

INSERT INTO Marks (subjectid,mark,studentid) VALUES (1,4,3);
INSERT INTO Marks (subjectid,mark,studentid) VALUES (2,5,3);
INSERT INTO Marks (subjectid,mark,studentid) VALUES (3,4,3);

INSERT INTO Marks (subjectid,mark,studentid) VALUES (1,4,4);
INSERT INTO Marks (subjectid,mark,studentid) VALUES (2,4,4);
INSERT INTO Marks (subjectid,mark,studentid) VALUES (3,4,4);