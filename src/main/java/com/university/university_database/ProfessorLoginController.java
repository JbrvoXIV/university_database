package com.university.university_database;

import com.university.university_database.schemas.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ProfessorLoginController {
    @FXML
    private Label professorLoginMessage;
    @FXML
    private TextField professorUsernameField;
    @FXML
    private PasswordField professorPasswordField;

    public void professorLogin(ActionEvent e) {
        SceneHandler.handleLoginVerification(
                Table.PROFESSOR,
                professorLoginMessage,
                professorUsernameField,
                professorPasswordField
        );
    }

    public void switchToProfessorRegisterForm(ActionEvent event) {
        SceneHandler.loadProfessorRegisterForm();
    }

    public void revertToOriginalPage(ActionEvent e) {
        SceneHandler.loadMainScene();
    }
}
