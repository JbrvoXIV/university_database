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

public class ProfessorLoginController {
    @FXML
    private Label professorLoginError;
    @FXML
    private TextField professorUsernameField;
    @FXML
    private PasswordField professorPasswordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void professorLogin(ActionEvent e) throws InterruptedException {
        String username = professorUsernameField.getText();
        String password = professorPasswordField.getText();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));

        professorLoginError.setText(String.format("%s %s logging in...", username, password));
        pauseTransition.setOnFinished(event -> professorLoginError.setText("LOGGED IN!"));
        pauseTransition.play();
    }

    public void switchToProfessorRegisterForm(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("professorRegistration.fxml"));
            stage = (Stage)(((Node)(event.getSource())).getScene().getWindow());
            scene = new Scene(root);
            stage.setTitle("Student Register");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
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
