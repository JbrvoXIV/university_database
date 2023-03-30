package com.university.university_database;

import com.university.univerity_database.schemas.Department;
import com.university.univerity_database.schemas.Professor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfessorRegistrationController implements Initializable {

    @FXML
    private ChoiceBox<String> professorDepartmentRegistration;
    @FXML
    private TextField professorIDRegistration;
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
        String firstName = professorFirstNameRegistration.getText();
        String lastName = professorLastNameRegistration.getText();
        String address = professorAddressRegistration.getText();
        String phone = professorPhoneRegistration.getText();
        String email = professorEmailRegistration.getText();
        int departmentID = Department.valueOf(professorDepartmentRegistration.getValue()).getDepartmentID(); // parse string to Department Enum, get ID

        try {
            p = new Professor(ID, firstName, lastName, address, phone, email, departmentID);
        } catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Input");
            alert.setHeaderText("You've entered incorrect input, see below for more information.");
            alert.setContentText(ex.getMessage());

            if(alert.showAndWait().get() == ButtonType.OK) {
                Stage stage = (Stage)professorRegistrationForm.getScene().getWindow();
                stage.close();
            }
        } finally {
            professorIDRegistration.setText("");
            professorFirstNameRegistration.setText("");
            professorLastNameRegistration.setText("");
            professorAddressRegistration.setText("");
            professorPhoneRegistration.setText("");
            professorEmailRegistration.setText("");
        }
    }

    public void revertToProfessorLogin(ActionEvent e) {
        Controller c = new Controller();
        c.switchToProfessorLogin(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Department[] departments = Department.values();
        for(Department department : departments)
            professorDepartmentRegistration.getItems().add(department.name());
        professorDepartmentRegistration.setValue(departments[0].name()); // set default value to first department
    }
}
