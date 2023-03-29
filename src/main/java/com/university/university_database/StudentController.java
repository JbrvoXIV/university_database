package com.university.university_database;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StudentController {

    @FXML
    private Label studentLoginError;
    @FXML
    private TextField studentUsernameField;
    @FXML
    private PasswordField studentPasswordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void studentLogin(ActionEvent e) {
        String username = studentUsernameField.getText();
        String password = studentPasswordField.getText();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));

        studentLoginError.setText(String.format("%s %s logging in...", username, password));
        pauseTransition.setOnFinished(event -> studentLoginError.setText("LOGGED IN!"));
        pauseTransition.play();
    }

    public void switchToStudentRegisterForm(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("studentRegistration.fxml"));
            stage = (Stage)(((Node)(event.getSource())).getScene().getWindow());
            scene = new Scene(root);
            stage.setTitle("Student Register");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
