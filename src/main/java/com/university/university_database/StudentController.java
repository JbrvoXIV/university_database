package com.university.university_database;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class StudentController {

    @FXML
    private Label studentLoginError;
    @FXML
    private TextField studentUsernameField;
    @FXML
    private PasswordField studentPasswordField;

    public void studentLogin(ActionEvent e) throws InterruptedException {
        String username = studentUsernameField.getText();
        String password = studentPasswordField.getText();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));

        studentLoginError.setText(String.format("%s %s logging in...", username, password));
        pauseTransition.setOnFinished(event -> studentLoginError.setText("LOGGED IN!"));
        pauseTransition.play();
    }
}
