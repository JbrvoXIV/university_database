package com.university.university_database;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class ProfessorController {
    @FXML
    private Label professorLoginError;
    @FXML
    private TextField professorUsernameField;
    @FXML
    private PasswordField professorPasswordField;

    public void professorLogin(ActionEvent e) throws InterruptedException {
        String username = professorUsernameField.getText();
        String password = professorPasswordField.getText();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));

        professorLoginError.setText(String.format("%s %s logging in...", username, password));
        pauseTransition.setOnFinished(event -> professorLoginError.setText("LOGGED IN!"));
        pauseTransition.play();
    }
}
