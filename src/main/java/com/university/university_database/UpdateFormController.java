package com.university.university_database;

import com.university.university_database.schemas.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class UpdateFormController implements Initializable {
    private static Table userType;
    @FXML
    private BorderPane updateForm;
    @FXML
    private TextField passwordUpdateForm;
    @FXML
    private TextField firstNameUpdateForm;
    @FXML
    private TextField lastNameUpdateForm;
    @FXML
    private TextField addressUpdateForm;
    @FXML
    private TextField phoneUpdateForm;
    @FXML
    private TextField emailUpdateForm;
    @FXML
    private DatePicker dobUpdateForm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Person user = CurrentUser.getUser();

        passwordUpdateForm.setText(user.getPassword());
        firstNameUpdateForm.setText(user.getFirstName());
        lastNameUpdateForm.setText(user.getLastName());
        addressUpdateForm.setText(user.getAddress());
        phoneUpdateForm.setText(user.getPhone());
        emailUpdateForm.setText(user.getEmail());

        if(userType == Table.STUDENT) {
            final Student s = (Student)user;
            dobUpdateForm.setValue(s.getDob());
        }
    }

    public void submitUpdateUserForm(ActionEvent e) {
        Person user = CurrentUser.getUser();
        boolean isStudent = userType == Table.STUDENT;
        Student s = null;
        Professor p = null;
        try {
            if(isStudent) {
                s = new Student(
                        String.valueOf(user.getID()),
                        passwordUpdateForm.getText(),
                        user.getDepartmentID(),
                        firstNameUpdateForm.getText(),
                        lastNameUpdateForm.getText(),
                        addressUpdateForm.getText(),
                        phoneUpdateForm.getText(),
                        emailUpdateForm.getText(),
                        dobUpdateForm.getValue()
                );
            } else {
                p = new Professor(
                        String.valueOf(user.getID()),
                        passwordUpdateForm.getText(),
                        firstNameUpdateForm.getText(),
                        lastNameUpdateForm.getText(),
                        addressUpdateForm.getText(),
                        phoneUpdateForm.getText(),
                        emailUpdateForm.getText(),
                        user.getDepartmentID()
                );
            }
            boolean userUpdated;
            if(isStudent)
                userUpdated = SQLController.updateUser(userType, s);
            else
                userUpdated = SQLController.updateUser(userType, p);
            if(!userUpdated)
                throw new InputMismatchException("User was unable to be updated");
            else {
                SceneHandler.triggerAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Success",
                        "User was successfully updated",
                        new Exception("User info has been saved")
                );
                CurrentUser.setUser(isStudent ? s : p); // set current user to same user with new info
            }
        } catch(Exception ex) {
            SceneHandler.triggerAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "An error occurred while trying to update user",
                    ex
            );
        }
    }

    public void switchToUserPortal() {
        if(userType == Table.STUDENT)
            SceneHandler.loadStudentPortal();
        else
            SceneHandler.loadProfessorPortal();
    }

    public static Table getUserType() {
        return userType;
    }

    public static void setUserType(Table userType) {
        UpdateFormController.userType = userType;
    }
}
