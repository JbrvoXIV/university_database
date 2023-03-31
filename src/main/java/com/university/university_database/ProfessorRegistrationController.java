package com.university.university_database;

import com.university.university_database.schemas.Department;
import com.university.university_database.schemas.Professor;
import com.university.university_database.schemas.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfessorRegistrationController implements Initializable {

    @FXML
    private ChoiceBox<String> professorDepartmentRegistration;
    @FXML
    private TextField professorIDRegistration;
    @FXML
    private TextField professorPasswordRegistration;
    @FXML
    private TextField professorFirstNameRegistration;
    @FXML
    private TextField professorLastNameRegistration;
    @FXML
    private TextField professorAddressRegistration;
    @FXML
    private TextField professorPhoneRegistration;
    @FXML
    private TextField professorEmailRegistration;
    @FXML
    private BorderPane professorRegistrationForm;


    public void submitProfessorRegistrationForm(ActionEvent e) {
        Professor p;
        String ID = professorIDRegistration.getText();
        String password = professorPasswordRegistration.getText();
        String firstName = professorFirstNameRegistration.getText();
        String lastName = professorLastNameRegistration.getText();
        String address = professorAddressRegistration.getText();
        String phone = professorPhoneRegistration.getText();
        String email = professorEmailRegistration.getText();
        int departmentID = Department.valueOf(professorDepartmentRegistration.getValue()).getDepartmentID(); // parse string to Department Enum, get ID

        try {
            p = new Professor(ID, password, firstName, lastName, address, phone, email, departmentID);
        } catch(Exception ex) {
            triggerAlert("Incorrect Input", "You've entered incorrect input, see below for more information.", ex);
            return; // user input is incorrect, keep old inputs and prompt user to reenter or fix info
        }
        finally {
            professorIDRegistration.setText("");
            professorFirstNameRegistration.setText("");
            professorLastNameRegistration.setText("");
            professorAddressRegistration.setText("");
            professorPhoneRegistration.setText("");
            professorEmailRegistration.setText("");
        }

        try {
            insertNewProfessor(p);
        } catch(Exception ex) {
            triggerAlert("INSERT Failed", "The insert failed! See below for more details.", ex);
        }
    }

    private void triggerAlert(String title, String message, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String exceptionName = ex.getClass().getSimpleName();
        String exceptionMessage = ex.getMessage();

        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(exceptionName + ": " + exceptionMessage);

        if(alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage)professorRegistrationForm.getScene().getWindow();
            stage.close();
        }
    }

    public void revertToProfessorLogin(ActionEvent e) {
        Controller c = new Controller();
        c.switchToProfessorLogin(e);
    }

    private void insertNewProfessor(Professor p) throws SQLException {
        SQLController.insert(Table.PROFESSOR, p);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Department[] departments = Department.values();
        for(Department department : departments)
            professorDepartmentRegistration.getItems().add(department.name());
        professorDepartmentRegistration.setValue(departments[0].name()); // set default value to first department
    }
}
