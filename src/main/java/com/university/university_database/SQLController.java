package com.university.university_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLController {

    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    public static void establishConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(Exception e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/universityxyz_database";
        String username = "root";
        String password = "password";
        try {
            connection = DriverManager.getConnection(
                    url,
                    username,
                    password);
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
