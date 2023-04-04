package com.university.university_database;

import com.university.university_database.schemas.Department;
import com.university.university_database.schemas.Student;
import com.university.university_database.schemas.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StudentRegistrationController implements Initializable {

    @FXML
    private TextField studentIDRegistration;
    @FXML
    private TextField studentPasswordRegistration;
    @FXML
    private ChoiceBox<String> studentMajorRegistration;
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
        int departmentID = Department.valueOf(studentMajorRegistration.getValue()).getDepartmentID(); // parse string to Department Enum, get ID
        String firstName = studentFirstNameRegistration.getText();
        String lastName = studentLastNameRegistration.getText();
        String address = studentAddressRegistration.getText();
        String phone = studentPhoneRegistration.getText();
        String email = studentEmailRegistration.getText();
        LocalDate dob = studentDOBRegistration.getValue();

        try {
            s = new Student(ID, password, departmentID, firstName, lastName, address, phone, email, dob);
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
            triggerAlert("INSERT Failed", "The insert failed! See below for more details.", ex);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Department[] departments = Department.values();
        for(Department department : departments)
            studentMajorRegistration.getItems().add(department.name());
        studentMajorRegistration.setValue(departments[0].name()); // set default value to first department
    }
}
