CREATE DATABASE universityxyz_database;

USE universityxyz_database;

CREATE TABLE DEPARTMENT (
	department_id INT UNIQUE NOT NULL,
    department_name VARCHAR(50) UNIQUE NOT NULL,
    PRIMARY KEY (department_id)
);

CREATE TABLE STUDENT (
	student_id INT UNIQUE NOT NULL,
    password VARCHAR(50) UNIQUE NOT NULL,
    major_id VARCHAR(7) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    phone_number VARCHAR(10) UNIQUE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    dob DATE NOT NULL,
    PRIMARY KEY (student_id),
    FOREIGN KEY (major_id) REFERENCES DEPARTMENT (department_id)
);

CREATE TABLE PROFESSOR (
	professor_id INT UNIQUE NOT NULL,
    password VARCHAR(50) UNIQUE NOT NULL,
    department_id INT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    phone_number VARCHAR(10) UNIQUE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    PRIMARY KEY (professor_id),
    FOREIGN KEY (department_id) REFERENCES DEPARTMENT (department_id)
);

CREATE TABLE COURSE (
	course_id VARCHAR(7) UNIQUE NOT NULL,
    professor_id INT NOT NULL,
    department_id INT NOT NULL,
    course_name VARCHAR(100) UNIQUE NOT NULL,
    instructor_name VARCHAR(50) NOT NULL,
    start_time TIME,
    end_time TIME,
    room_number VARCHAR(10),
    PRIMARY KEY (course_id),
    FOREIGN KEY (professor_id) REFERENCES PROFESSOR (professor_id),
    FOREIGN KEY (department_id) REFERENCES DEPARTMENT (department_id)
);

CREATE TABLE ENROLLMENT (
	enrollment_id INT AUTO_INCREMENT UNIQUE NOT NULL,
    student_id INT NOT NULL,
    course_id VARCHAR(7) NOT NULL,
    grade ENUM("A", "B", "C", "D", "F"),
    FOREIGN KEY (student_id) REFERENCES STUDENT (student_id),
    FOREIGN KEY (course_id) REFERENCES COURSE (course_id)
);

CREATE TABLE SCHEDULE (
	student_id INT NOT NULL,
    course_id VARCHAR(7) NOT NULL,
    start_time TIME,
    end_time TIME,
    room_number VARCHAR(10),
    FOREIGN KEY (student_id) REFERENCES STUDENT (student_id),
    FOREIGN KEY (course_id) REFERENCES COURSE (course_id)
);