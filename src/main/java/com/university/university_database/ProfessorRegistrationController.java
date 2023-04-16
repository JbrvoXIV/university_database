package com.university.university_database;

import com.university.university_database.schemas.Department;
import com.university.university_database.schemas.Professor;
import com.university.university_database.schemas.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.InputMismatchException;
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
        Professor p = null;
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
            if(SQLController.queryForUserIDCheck(Table.PROFESSOR, p))
                throw new InputMismatchException("User with ID " + ID + " already exists, cannot create new user.");
        } catch(InputMismatchException ex) {
            SceneHandler.triggerAlert(Alert.AlertType.ERROR, "Error", "The user ID already exists.", ex);
            return;
        } catch(Exception ex) {
            SceneHandler.triggerAlert(Alert.AlertType.ERROR, "Incorrect Input", "You've entered incorrect input, see below for more information.", ex);
            return; // user input is incorrect, keep old inputs and prompt user to reenter or fix info
        }

        try {
            insertNewProfessor(p);
            SceneHandler.triggerAlert(
                    Alert.AlertType.CONFIRMATION,
                    "INSERT Success",
                    "Professor was created",
                    new Exception("Professor was successfully created, you may now log in!" +
                            " Classes may only be added through admin privileges (MySQL Database).")
            );
        } catch(Exception ex) {
            SceneHandler.triggerAlert(Alert.AlertType.ERROR, "INSERT Failed", "The insert failed! See below for more details.", ex);
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
