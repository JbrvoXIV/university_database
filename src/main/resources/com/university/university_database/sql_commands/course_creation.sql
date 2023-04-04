USE universityxyz_database;

# INITIAL CREATION OF DEPARTMENT_ID = 1 COURSES
INSERT INTO COURSE (course_id, professor_id, course_name, instructor_name, start_time, end_time, room_number)
VALUES
	("COP3530", 546372, "Data Structures", 
		(CONCAT(
			(SELECT first_name FROM PROFESSOR WHERE professor_id=546372),
			" ",
			(SELECT last_name FROM PROFESSOR WHERE professor_id=546372)
			)
		), 
    TIME("1:00:00"), TIME("2:30:00"), "PG6 106"),
    ("COP4338", 135790, "Systems Programming", 
		(CONCAT(
			(SELECT first_name FROM PROFESSOR WHERE professor_id=135790),
			" ",
			(SELECT last_name FROM PROFESSOR WHERE professor_id=135790)
			)
		), 
    TIME("4:15:00"), TIME("5:30:00"), "PG6 102"),
    ("COP4610", 970685, "Operating Systems", 
		(CONCAT(
			(SELECT first_name FROM PROFESSOR WHERE professor_id=970685),
			" ",
			(SELECT last_name FROM PROFESSOR WHERE professor_id=970685)
			)
		), 
    NULL, NULL, "ONLINE");
    