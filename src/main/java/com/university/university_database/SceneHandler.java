package com.university.university_database;

import com.university.university_database.schemas.Person;
import com.university.university_database.schemas.Table;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLSyntaxErrorException;
import java.util.Objects;

public class SceneHandler {

    enum Files {
        MAIN("main.fxml", "Welcome!"),
        STUDENT_LOGIN("studentLogin.fxml", "Student Login Page"),
        PROFESSOR_LOGIN("professorLogin.fxml", "Professor Login Page"),
        STUDENT_REGISTRATION("studentRegistration.fxml", "Student Registration Form"),
        PROFESSOR_REGISTRATION("professorRegistration.fxml", "Professor Registration Form");

        private final String file;
        private final String title;

        Files(String file, String title) {
            this.file = file;
            this.title = title;
        }

        public String getFile() {
            return file;
        }

        public String getTitle() {
            return title;
        }
    }

    private static Stage stage;
    private static Parent root;
    private static Scene scene;

    public static void setStage(Stage stage) {
        SceneHandler.stage = stage;
    }

     public static void loadMainScene() {
         loadSceneHelper(Files.MAIN.getFile(), Files.MAIN.getTitle());
     }

     public static void loadStudentLogin() {
         loadSceneHelper(Files.STUDENT_LOGIN.getFile(), Files.STUDENT_LOGIN.getTitle());
     }

     public static void loadProfessorLogin() {
         loadSceneHelper(Files.PROFESSOR_LOGIN.getFile(), Files.PROFESSOR_LOGIN.getTitle());
     }

    public static void loadStudentRegisterForm() {
         loadSceneHelper(Files.STUDENT_REGISTRATION.getFile(), Files.STUDENT_REGISTRATION.getTitle());
    }

    public static void loadProfessorRegisterForm() {
         loadSceneHelper(Files.PROFESSOR_REGISTRATION.getFile(), Files.PROFESSOR_REGISTRATION.getTitle());
    }

//    private static void loadUserInformation(Person p) {
//        loadUserLoginScene(p);
//        loadSceneHelper();
//    }

    private static void loadUserLoginScene() {

    }

     private static void loadSceneHelper(String file, String title) {
         try {
             URL fileToLoad = Objects.requireNonNull(SceneHandler.class.getResource(file));
             SceneHandler.root = FXMLLoader.load(fileToLoad);
             SceneHandler.scene = new Scene(root);
             SceneHandler.stage.setTitle(title);
             SceneHandler.stage.setScene(scene);
             SceneHandler.stage.show();
         } catch(NullPointerException ex) {
             printErrorFileNull(file);
         } catch(Exception ex) {
             printExceptionMessage(ex);
         }
     }

     public static void handleLoginVerification(Table table, Label messageLabel, TextField usernameField, PasswordField passwordField) {
         String id = usernameField.getText();
         String password = passwordField.getText();
         PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));

         messageLabel.setText(String.format("%s %s logging in...", id, password));
         try {
             Person p = SQLController.queryLogin(table, id, password);
             pauseTransition.setOnFinished(event -> messageLabel.setText("LOGGED IN! Redirecting now..."));
             //loadUserInformation(p);
         } catch(SQLSyntaxErrorException ex) {
             pauseTransition.setOnFinished(event -> messageLabel.setText("ID is not numerical, please try again"));
         } catch(Exception ex) {
             pauseTransition.setOnFinished(event -> messageLabel.setText(ex.getMessage()));
         } finally {
             pauseTransition.play();
         }
     }

     private static void printErrorFileNull(String file) {
         System.out.println("The file " + file + " does not exist!");
     }

     private static void printExceptionMessage(Exception ex) {
         System.out.println(ex.getMessage());
     }
}