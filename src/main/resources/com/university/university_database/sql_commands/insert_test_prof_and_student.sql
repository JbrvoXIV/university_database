USE universityxyz_database;
    
INSERT INTO STUDENT (student_id, password, major_id, first_name, last_name, address, phone_number, email, dob)
VALUES (123456, "password", 1, "test", "student", "123 test st", "1234567890", "teststudentemail@email.com", DATE("2000-01-01"));

INSERT INTO PROFESSOR (professor_id, password, department_id, first_name, last_name, address, phone_number, email)
VALUES (654321, "password", 2, "test", "professor", "123 test st", "9987654321", "testprofessoremail@email.com");