package com.university.university_database;

import com.university.univerity_database.schemas.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.IllegalFormatException;

public class StudentRegistrationController {

    @FXML
    private TextField studentIDRegistration;
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
        Student s = null;
        String ID = studentIDRegistration.getText();
        String firstName = studentFirstNameRegistration.getText();
        String lastName = studentLastNameRegistration.getText();
        String address = studentAddressRegistration.getText();
        String phone = studentPhoneRegistration.getText();
        String email = studentEmailRegistration.getText();
        LocalDate dob = studentDOBRegistration.getValue();

        try {
            s = new Student(ID, firstName, lastName, address, phone, email, dob);
        } catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Input");
            alert.setHeaderText("You've entered incorrect input, see below for more information.");
            alert.setContentText(ex.getMessage());

            if(alert.showAndWait().isPresent() && alert.showAndWait().get() == ButtonType.OK) {
                Stage stage = (Stage)studentRegistrationForm.getScene().getWindow();
                stage.close();
            }
        } finally {
            studentIDRegistration.setText("");
            studentFirstNameRegistration.setText("");
            studentLastNameRegistration.setText("");
            studentAddressRegistration.setText("");
            studentPhoneRegistration.setText("");
            studentEmailRegistration.setText("");
            studentDOBRegistration.setValue(null);
        }
    }

}
