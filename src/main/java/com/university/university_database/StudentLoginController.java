package com.university.university_database;

import com.university.university_database.schemas.Person;
import com.university.university_database.schemas.Table;
import com.university.university_database.schemas.Student;

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

public class StudentLoginController {

    @FXML
    private Label studentLoginMessage;
    @FXML
    private TextField studentUsernameField;
    @FXML
    private PasswordField studentPasswordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    /* WIP */
    public void studentLogin(ActionEvent e) {
        String id = studentUsernameField.getText();
        String password = studentPasswordField.getText();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));

        studentLoginMessage.setText(String.format("%s %s logging in...", id, password));
        try {
            Person p = SQLController.queryLogin(Table.STUDENT, id, password);
            pauseTransition.setOnFinished(event -> studentLoginMessage.setText("LOGGED IN!"));
        } catch(Exception ex) {
            pauseTransition.setOnFinished(event -> studentLoginMessage.setText(ex.getMessage()));
        }
        pauseTransition.play();
    }

    public void switchToStudentRegisterForm(ActionEvent e) {
        try {
            root = FXMLLoader.load(getClass().getResource("studentRegistration.fxml"));
            stage = (Stage)(((Node)(e.getSource())).getScene().getWindow());
            scene = new Scene(root);
            stage.setTitle("Student Register");
            stage.setScene(scene);
            stage.show();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void revertToOriginalPage(ActionEvent e) {
        try {
            stage = (Stage)(((Node)(e.getSource())).getScene().getWindow()); // grab current stage
            new Main().start(stage); // call start, passing in current stage
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
