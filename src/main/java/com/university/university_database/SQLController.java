package com.university.university_database;

import com.university.univerity_database.schemas.Person;
import com.university.univerity_database.schemas.Table;

import java.sql.*;

public class SQLController {

    private final static String URL = "jdbc:mysql://localhost:3306/universityxyz_database";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "password";

    public static Connection connection;

    public static void establishConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(Exception e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertPerson(Table table, Person person) {
        PreparedStatement preparedStatement;
        String insertStatement;
//        switch(table) {
//            case STUDENT -> {
//                insertStatement = String.format("INSERT INTO %s" +
//                        "(student_id, password, first_name, last_name, address, phone_number, email, dob)" +
//                        "");
//                preparedStatement = connection.prepareStatement();
//            }
//        }
    }
}
