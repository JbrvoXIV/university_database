package com.university.university_database;

import com.university.university_database.schemas.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StudentPortalController {

    @FXML
    private Label studentIDDisplay;
    @FXML
    private Label studentMajorDisplay;

    public void addClass(ActionEvent e) {
    }

    public void populateTable(Student p) {

    }

    public Label getStudentIDDisplay() {
        return studentIDDisplay;
    }

    public Label getStudentMajorDisplay() {
        return studentMajorDisplay;
    }
}
