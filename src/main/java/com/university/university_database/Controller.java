package com.university.university_database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private TextField studentUsernameField;
    @FXML
    private PasswordField studentPasswordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToStudentLogin(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("studentLogin.fxml"));
            stage = (Stage)(((Node)(event.getSource())).getScene().getWindow());
            scene = new Scene(root);
            stage.setTitle("Login Page");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToProfessorLogin(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("professorLogin.fxml"));
            stage = (Stage)(((Node)(event.getSource())).getScene().getWindow());
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void exit(ActionEvent e) {
        System.exit(1);
    }
}
