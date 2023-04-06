package com.university.university_database;

import com.university.university_database.schemas.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NewGradeFormController implements Initializable {

    private final String[] grades = { "A", "B", "C", "D", "F" };

    private static Course selectedCourse;

    @FXML
    private ChoiceBox<String> gradeChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadChoiceBox();
    }

    public void submitAndReturnToPortal(ActionEvent e) {
        String grade = gradeChoiceBox.getValue();
        boolean gradeUpdated = SQLController.updateGrade(grade);
        if(gradeUpdated) {
            SceneHandler.triggerAlert(
                    Alert.AlertType.CONFIRMATION,
                    "Success",
                    "User info updated",
                    new Exception("The grade for the selected course was updated")
            );
        } else {
            SceneHandler.triggerAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "User info NOT updated",
                    new Exception("The grade for the selected course was NOT updated")
            );
        }
        SceneHandler.loadStudentPortal();
    }

    private void loadChoiceBox() {
        for(String grade : grades)
            gradeChoiceBox.getItems().add(grade);
        gradeChoiceBox.setValue(grades[0]);
    }

    public static Course getSelectedCourse() {
        return selectedCourse;
    }

    public static void setSelectedCourse(Course selectedCourse) {
        NewGradeFormController.selectedCourse = selectedCourse;
    }
}
