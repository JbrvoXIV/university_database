package com.university.university_database;

import com.university.university_database.schemas.Table;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class StudentLoginController {

    @FXML
    private Label studentLoginMessage;
    @FXML
    private TextField studentUsernameField;
    @FXML
    private PasswordField studentPasswordField;

    public void studentLogin(ActionEvent e) {
        SceneHandler.handleLoginVerification(
                Table.STUDENT,
                studentLoginMessage,
                studentUsernameField,
                studentPasswordField
        );
    }

    public void switchToStudentRegisterForm(ActionEvent e) {
        SceneHandler.loadStudentRegisterForm();
    }

    public void revertToOriginalPage(ActionEvent e) {
        SceneHandler.loadMainScene();
    }
}
