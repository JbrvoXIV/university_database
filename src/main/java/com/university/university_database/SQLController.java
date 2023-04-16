package com.university.university_database;

import com.university.university_database.schemas.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.util.NoSuchElementException;

public class SQLController {

    private final static String URI = System.getenv("MYSQL_URI");
    private final static String USERNAME = System.getenv("MYSQL_USER");
    private final static String PASSWORD = System.getenv("MYSQL_PASSWORD");

    public static Connection connection;

    public static void establishConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(Exception e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(
                    URI,
                    USERNAME,
                    PASSWORD);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /* main insert function, callable */
    public static int insert(Table table, Object insertObj) throws SQLException {
        int rowsAffected = 0;
        switch(table) {
            case STUDENT -> {
                Student student = (Student)insertObj;
                rowsAffected = executeStudentInsert(table, student);
            }
            case PROFESSOR -> {
                Professor professor = (Professor)insertObj;
                rowsAffected = executeProfessorInsert(table, professor);
            }
        }
        return rowsAffected;
    }

    /* student insert helper */
    private static int executeStudentInsert(Table table, Student student) throws SQLException {
        PreparedStatement preparedStatement;
        String insertStatement;

        insertStatement = String.format(
                "INSERT INTO %s" +
                "(student_id, password, major_id, first_name, last_name, address, phone_number, email, dob)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", table.getTable()
        );
        preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setInt(1, student.getID());
        preparedStatement.setString(2, student.getPassword());
        preparedStatement.setInt(3, student.getDepartmentID());
        preparedStatement.setString(4, student.getFirstName());
        preparedStatement.setString(5, student.getLastName());
        preparedStatement.setString(6, student.getAddress());
        preparedStatement.setString(7, student.getPhone());
        preparedStatement.setString(8, student.getEmail());
        preparedStatement.setDate(9, getLocalDateAsSQLDate(student.getDob()));

        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();

        return rowsAffected;
    }

    /* professor insert helper */
    private static int executeProfessorInsert(Table table, Professor professor) throws SQLException {
        PreparedStatement preparedStatement;
        String insertStatement;

        insertStatement = String.format(
                "INSERT INTO %s" +
                        "(professor_id, password, first_name, last_name, address, phone_number, email, department_id)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", table.getTable()
        );
        preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setInt(1, professor.getID());
        preparedStatement.setString(2, professor.getPassword());
        preparedStatement.setString(3, professor.getFirstName());
        preparedStatement.setString(4, professor.getLastName());
        preparedStatement.setString(5, professor.getAddress());
        preparedStatement.setString(6, professor.getPhone());
        preparedStatement.setString(7, professor.getEmail());
        preparedStatement.setInt(8, professor.getDepartmentID());

        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();

        return rowsAffected;
    }

    /* query to check if user exists (used for login verification) */
    public static boolean queryForUserIDCheck(Table table, Person p) throws SQLException {
        String tableToQuery = table.getTable();
        String idAttribute = tableToQuery.equals(Table.STUDENT.getTable()) ? "student_id" :"professor_id";
        String queryString = String.format(
                "SELECT %s FROM %s WHERE %s=%s", idAttribute, tableToQuery, idAttribute, p.getID()
                );
        Statement statement;
        ResultSet resultSet;

        statement = connection.createStatement();
        resultSet = statement.executeQuery(queryString);

        return resultSet.next(); // user exists = true, otherwise = false;
    }

    /* like the queryForUserIDCheck but returns Person object of user if found instead of boolean */
    public static Person queryLogin(Table table, String id, String password) throws SQLException, SQLSyntaxErrorException {
        Person p = null;
        String tableToQuery = table.getTable();
        String idAttribute = tableToQuery.equals(Table.STUDENT.getTable()) ? "student_id" :"professor_id";
        String queryString = String.format("SELECT * FROM %s WHERE %s = %s LIMIT 1", tableToQuery, idAttribute, id);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(queryString);

        if(resultSet.next()) {
            boolean passwordCorrect = password.equals(resultSet.getString("password"));
            if (!passwordCorrect)
                throw new SQLException("Password is incorrect! Please try again!");
            switch (table) {
                case STUDENT ->
                        p = new Student(
                            id,
                            password,
                            resultSet.getInt("major_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("address"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getDate("dob").toLocalDate()
                    );
                case PROFESSOR ->
                    p = new Professor(
                            id,
                            password,
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("address"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getInt("department_id")
                    );
            }
        } else { // query returns nothing
            throw new SQLException("User does not exist! Please register.");
        }

        statement.close();
        resultSet.close();

        return p;
    }

    /* query for department based on department_id */
    public static String queryDepartment(int departmentID) throws SQLException {
        Statement statement;
        ResultSet resultSet;
        String queryString = String.format("SELECT * FROM DEPARTMENT WHERE department_id = %d LIMIT 1", departmentID);
        String departmentName = "";

        statement = connection.createStatement();
        resultSet = statement.executeQuery(queryString);

        if(resultSet.next()) {
            departmentName = resultSet.getString("department_name");
        }

        statement.close();
        resultSet.close();

        return departmentName;
    }

    /* retrieve all courses associated with a user (professor or student) */
    public static ObservableList<Course> getCoursesForUser(Table table, Person p) throws SQLException {
        boolean isStudent = table == Table.STUDENT;
        ObservableList<Course> list = FXCollections.observableArrayList();
        Statement statement;
        ResultSet resultSet;
        String queryString = "";
        if(isStudent) {
            queryString = String.format(
                    "SELECT *\n" +
                            "FROM COURSE\n" +
                            "JOIN ENROLLMENT ON COURSE.course_id = ENROLLMENT.course_id\n" +
                            "JOIN STUDENT ON ENROLLMENT.student_id = STUDENT.student_id\n" +
                            "WHERE STUDENT.student_id = %d"
                    , p.getID());
        } else {
            queryString = String.format("SELECT * FROM COURSE WHERE professor_id=%d", p.getID());
        }

        statement = connection.createStatement();
        resultSet = statement.executeQuery(queryString);

        while(resultSet.next()) {
            String courseID = resultSet.getString("course_id");
            int professorID = resultSet.getInt("professor_id");
            String courseName = resultSet.getString("course_name");
            String professorName = resultSet.getString("instructor_name");
            Time startTime = resultSet.getTime("start_time");
            Time endTime = resultSet.getTime("end_time");
            String roomNumber = resultSet.getString("room_number");
            String grade = isStudent ? resultSet.getString("grade") : null;

            list.add(new Course(
                    courseID,
                    professorID,
                    courseName,
                    professorName,
                    startTime,
                    endTime,
                    roomNumber,
                    grade
            ));
        }

        statement.close();
        resultSet.close();

        return list;
    }


    /* helper for converting LocalDate object to valid SQL Date type */
    private static Date getLocalDateAsSQLDate(LocalDate date) {
        return Date.valueOf(date);
    }

    /* queries for available courses that a student can take that are not currently enrolled and belong to the same department */
    public static ObservableList<Course> getAvailableCoursesForUser() throws SQLException {
        ObservableList<Course> list = FXCollections.observableArrayList();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int studentID = CurrentUser.getUser().getID();
        String queryString = "SELECT *\n" +
                "FROM COURSE\n" +
                "WHERE department_id = (\n" +
                "    SELECT major_id\n" +
                "    FROM STUDENT\n" +
                "    WHERE student_id = ?\n" +
                ")\n" +
                "AND course_id NOT IN (\n" +
                "    SELECT course_id\n" +
                "    FROM ENROLLMENT\n" +
                "    WHERE student_id = ?\n" +
                ")";

        preparedStatement = connection.prepareStatement(queryString);
        preparedStatement.setInt(1, studentID);
        preparedStatement.setInt(2, studentID);
        resultSet = preparedStatement.executeQuery();

        if(!resultSet.isBeforeFirst()) {
            SceneHandler.triggerAlert(
                    Alert.AlertType.ERROR,
                    "No Results Found!",
                    "Please refer to the description for more information.",
                    new NoSuchElementException("There are no classes available for you or you've already maxed out class list.")
                    );
            preparedStatement.close();
            resultSet.close();
            return null;
        }

        while(resultSet.next()) {
            String courseID = resultSet.getString("course_id");
            int professorID = resultSet.getInt("professor_id");
            String courseName = resultSet.getString("course_name");
            String instructorName = resultSet.getString("instructor_name");
            Time startTime = resultSet.getTime("start_time");
            Time endTime = resultSet.getTime("end_time");
            String roomNumber = resultSet.getString("room_number");
            String defaultGrade = "A";

            list.add(new Course(
                    courseID,
                    professorID,
                    courseName,
                    instructorName,
                    startTime,
                    endTime,
                    roomNumber,
                    defaultGrade
            ));
        }

        preparedStatement.close();
        resultSet.close();

        return list;
    }

    /* add a class for the student */
    public static boolean addClass(Course selectedCourse) throws SQLException {
        String queryString =
                "INSERT INTO ENROLLMENT (student_id, course_id, grade)\n" +
                "VALUES (?, ?, \"A\")"; //default grade is A, can be changed through update in portal
        PreparedStatement preparedStatement;

        preparedStatement = connection.prepareStatement(queryString);
        preparedStatement.setInt(1, CurrentUser.getUser().getID());
        preparedStatement.setString(2, selectedCourse.getCourseID());
        int rowsUpdated = preparedStatement.executeUpdate();

        preparedStatement.close();

        return rowsUpdated == 1;
    }

    /* update grade for student */
    public static boolean updateGrade(String grade) {
        Student s = (Student)CurrentUser.getUser();
        Course course = NewGradeFormController.getSelectedCourse();
        String queryString =
                "UPDATE ENROLLMENT\n" +
                "SET grade = ?\n" +
                "WHERE course_id = ?\n" +
                "AND student_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1, grade);
            preparedStatement.setString(2, course.getCourseID());
            preparedStatement.setInt(3, s.getID());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated == 1;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /* remove a course assigned to a student */
    public static boolean removeClass(Course selectedCourse) throws SQLException {
        String queryString = "DELETE FROM ENROLLMENT WHERE student_id = ? AND course_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(queryString);
        preparedStatement.setInt(1, CurrentUser.getUser().getID());
        preparedStatement.setString(2, selectedCourse.getCourseID());

        int rowsRemoved = preparedStatement.executeUpdate();

        return rowsRemoved == 1;
    }

    /* update user info depending on if request came from professor side or student side */
    public static boolean updateUser(Table userType, Person p) throws SQLException {
        StringBuilder queryString;
        boolean isStudent = userType == Table.STUDENT;
        PreparedStatement preparedStatement;

        /* create query string based on userType */
        if(isStudent)
            queryString = new StringBuilder("UPDATE STUDENT\n");
        else
            queryString = new StringBuilder("UPDATE PROFESSOR\n");

        queryString.append("SET password = ?, first_name = ?, last_name = ?, address = ?, phone_number = ?, email = ?");

        if(isStudent) {
            queryString.append(", dob = ?");
            queryString.append("\nWHERE student_id = ?;");
        } else {
            queryString.append("\nWHERE professor_id = ?;");
        }

        preparedStatement = connection.prepareStatement(queryString.toString());
        preparedStatement.setString(1, p.getPassword());
        preparedStatement.setString(2, p.getFirstName());
        preparedStatement.setString(3, p.getLastName());
        preparedStatement.setString(4, p.getAddress());
        preparedStatement.setString(5, p.getPhone());
        preparedStatement.setString(6, p.getEmail());
        if(isStudent) {
            final Student s = (Student)p;
            preparedStatement.setDate(7, Date.valueOf(s.getDob()));
            preparedStatement.setInt(8, p.getID());
        } else {
            preparedStatement.setInt(7, p.getID());
        }

        int rowsUpdated = preparedStatement.executeUpdate();

        return rowsUpdated == 1;
    }
}
