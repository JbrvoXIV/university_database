package com.university.university_database;

import com.university.university_database.schemas.Student;
import com.university.university_database.schemas.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

public class StudentRegistrationController {

    @FXML
    private TextField studentIDRegistration;
    @FXML
    private TextField studentPasswordRegistration;
    @FXML
    private TextField studentFirstNameRegistration;
    @FXML
    private TextField studentLastNameRegistration;
    @FXML
    private TextField studentAddressRegistration;
    @FXML
    private TextField studentPhoneRegistration;
    @FXML
    private TextField studentEmailRegistration;
    @FXML
    private DatePicker studentDOBRegistration;
    @FXML
    private BorderPane studentRegistrationForm;

    public void submitStudentRegistrationForm(ActionEvent e) {
        Student s;
        String ID = studentIDRegistration.getText();
        String password = studentPasswordRegistration.getText();
        String firstName = studentFirstNameRegistration.getText();
        String lastName = studentLastNameRegistration.getText();
        String address = studentAddressRegistration.getText();
        String phone = studentPhoneRegistration.getText();
        String email = studentEmailRegistration.getText();
        LocalDate dob = studentDOBRegistration.getValue();

        try {
            s = new Student(ID, password, firstName, lastName, address, phone, email, dob);
        } catch(Exception ex) {
            triggerAlert("Incorrect Input", "You've entered incorrect input, see below for more information.", ex);
            return;
        } finally {
            studentIDRegistration.setText("");
            studentFirstNameRegistration.setText("");
            studentLastNameRegistration.setText("");
            studentAddressRegistration.setText("");
            studentPhoneRegistration.setText("");
            studentEmailRegistration.setText("");
            studentDOBRegistration.setValue(null);
        }

        try {
            insertNewStudent(s);
        } catch(Exception ex) {
            triggerAlert("", "", ex);
        }
    }

    private void triggerAlert(String title, String message, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(ex.getMessage());

        if(alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage)studentRegistrationForm.getScene().getWindow();
            stage.close();
        }
    }

    private void insertNewStudent(Student s) throws SQLException {
        SQLController.insert(Table.STUDENT, s);
    }

    public void revertToStudentLogin(ActionEvent e) {
        Controller c = new Controller();
        c.switchToStudentLogin(e);
    }

}
