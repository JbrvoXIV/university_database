package com.university.university_database;

import com.university.university_database.schemas.CurrentUser;
import com.university.university_database.schemas.Person;
import com.university.university_database.schemas.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.Objects;

public class SceneHandler {

    enum Files {
        MAIN("main.fxml", "Welcome!"),
        STUDENT_LOGIN("studentLogin.fxml", "Student Login Page"),
        PROFESSOR_LOGIN("professorLogin.fxml", "Professor Login Page"),
        STUDENT_REGISTRATION("studentRegistration.fxml", "Student Registration Form"),
        PROFESSOR_REGISTRATION("professorRegistration.fxml", "Professor Registration Form"),
        STUDENT_PORTAL("studentPortal.fxml", "Student Portal"),
        PROFESSOR_PORTAL("professorPortal.fxml", "Professor Portal"),
        ADD_CLASS_FORM("addClassForm.fxml", "Form"),
        STUDENT_UPDATE_INFO_FORM("studentUpdateForm.fxml", "Update Student"),
        PROFESSOR_UPDATE_INFO_FORM("professorUpdateForm.fxml", "Update Professor"),
        NEW_GRADE_FORM("newGradeForm.fxml", "Update Grade");

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

    public static void loadStudentPortal() {
        loadSceneHelper(Files.STUDENT_PORTAL.getFile(), Files.STUDENT_PORTAL.getTitle());
    }

    public static void loadProfessorPortal() {
        loadSceneHelper(Files.PROFESSOR_PORTAL.getFile(), Files.PROFESSOR_PORTAL.getTitle());
    }

    public static void loadAddClassForm(Table table) {
        AddClassFormController.setUserType(table);
        loadSceneHelper(Files.ADD_CLASS_FORM.getFile(), Files.ADD_CLASS_FORM.getTitle());
    }

    public static void loadUserPortal(Label idDisplay, Label departmentDisplay) throws SQLException {
        Person p = CurrentUser.getUser();
        String idDisplayText = String.format("%s %s", idDisplay.getText(), p.getID());
        String department = SQLController.queryDepartment(p.getDepartmentID());
        String majorDisplayText = String.format("%s %s", departmentDisplay.getText(), department);;

        idDisplay.setText(idDisplayText);
        departmentDisplay.setText(majorDisplayText);
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

     public static boolean handleLoginVerification(Table table, Label messageLabel, TextField usernameField, PasswordField passwordField) {
         String id = usernameField.getText();
         String password = passwordField.getText();
         Person p;
         try {
             p = SQLController.queryLogin(table, id, password);
             CurrentUser.setUser(p);
         } catch(SQLSyntaxErrorException ex) {
             messageLabel.setText("ID is not numerical, please try again");
             printExceptionMessage(ex);
             return false;
         } catch(Exception ex) {
             messageLabel.setText(ex.getMessage());
             printExceptionMessage(ex);
             return false;
         }
         return true;
     }

    public static void loadUserUpdateForm(Table table) {
        UpdateFormController.setUserType(table);
        if(table == Table.STUDENT)
            loadSceneHelper(Files.STUDENT_UPDATE_INFO_FORM.getFile(), Files.STUDENT_UPDATE_INFO_FORM.getTitle());
        else
            loadSceneHelper(Files.PROFESSOR_UPDATE_INFO_FORM.getFile(), Files.PROFESSOR_UPDATE_INFO_FORM.getTitle());
    }

    public static void loadUpdateGradeModal(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneHandler.class.getResource(Files.NEW_GRADE_FORM.getFile()));
            AnchorPane modal = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(modal);
            stage.setTitle(Files.NEW_GRADE_FORM.getTitle());
            stage.setScene(scene);
            stage.show();
        } catch(NullPointerException ex) {
            printErrorFileNull(Files.NEW_GRADE_FORM.getFile());
        } catch(Exception ex) {
            printExceptionMessage(ex);
        }
    }

    public static void triggerAlert(Alert.AlertType alertType, String title, String message, Exception ex) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(ex.getMessage());

        alert.showAndWait();
    }

     private static void printErrorFileNull(String file) {
         System.out.println("The file " + file + " does not exist!");
     }

     private static void printExceptionMessage(Exception ex) {
         System.out.println(ex.getMessage());
     }
}
