package com.university.university_database;

import com.university.university_database.schemas.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

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

    /************************************INSERT**********************************************************/

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
//            case COURSES -> {
//
//            }
//            case SCHEDULE -> {
//
//            }
//            case DEPARTMENT -> {
//
//            }
//            case ENROLLMENT -> {
//
//            }
        }
        return rowsAffected;
    }

    /* student insert helper */
    private static int executeStudentInsert(Table table, Student student) throws SQLException {
        PreparedStatement preparedStatement;
        String insertStatement;

        insertStatement = String.format(
                "INSERT INTO %s" +
                "(student_id, password, first_name, last_name, address, phone_number, email, dob)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", table.getTable()
        );
        preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setInt(1, student.getID());
        preparedStatement.setString(2, student.getPassword());
        preparedStatement.setString(3, student.getFirstName());
        preparedStatement.setString(4, student.getLastName());
        preparedStatement.setString(5, student.getAddress());
        preparedStatement.setString(6, student.getPhone());
        preparedStatement.setString(7, student.getEmail());
        preparedStatement.setDate(8, getLocalDateAsSQLDate(student.getDob()));

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

    /************************************SELECT**********************************************************/

    /* query student or professor login (WIP) */
    public static Person queryLogin(Table table, String id, String password) throws SQLException, SQLSyntaxErrorException {
        Statement statement;
        ResultSet resultSet;
        String queryString;
        switch(table) {
            case STUDENT -> {
                queryString = String.format("SELECT * FROM STUDENT WHERE student_id = %s LIMIT 1", id);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(queryString);
                if(resultSet.next()) {
                    boolean passwordCorrect = password.equals(resultSet.getString("password"));
                    if(!passwordCorrect)
                        throw new SQLException("Password is incorrect! Please try again!");
                    Student s = new Student(
                            resultSet.getString("student_id"),
                            password,
                            resultSet.getInt("major_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("address"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getDate("dob").toLocalDate()
                    );
                    return s;
                } else { // query returns nothing
                    throw new SQLException("User does not exist! Please register.");
                }
            }
        }
        return null;
    }

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

    public static ObservableList<Course> getCoursesForUser(Person p) throws SQLException {
        ObservableList<Course> list = FXCollections.observableArrayList();
        Statement statement;
        ResultSet resultSet;
        String queryString = String.format(
                "SELECT *\n" +
                "FROM COURSE\n" +
                "JOIN ENROLLMENT ON COURSE.course_id = ENROLLMENT.course_id\n" +
                "JOIN STUDENT ON ENROLLMENT.student_id = STUDENT.student_id\n" +
                "WHERE STUDENT.student_id = %d"
            , p.getID());

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

            list.add(new Course(
                    courseID,
                    professorID,
                    courseName,
                    professorName,
                    startTime,
                    endTime,
                    roomNumber
            ));
        }

        statement.close();
        resultSet.close();

        return list;
    }

    private static Date getLocalDateAsSQLDate(LocalDate date) {
        return Date.valueOf(date);
    }

}
