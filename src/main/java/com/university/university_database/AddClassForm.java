package com.university.university_database;

import com.university.university_database.schemas.Course;
import com.university.university_database.schemas.CurrentUser;
import com.university.university_database.schemas.Table;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class AddClassForm implements Initializable {

    private static Table userType;
    @FXML
    private Label classesForDepartmentLabel;
    @FXML
    private TableView<Course> classesAvailableTable;
    @FXML
    private TableColumn<Course, String> courseID;
    @FXML
    private TableColumn<Course, Time> startTime;
    @FXML
    private TableColumn<Course, Time> endTime;
    @FXML
    private TableColumn<Course, String> roomNumber;
    @FXML
    private TableColumn<Course, String> professorForCourse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String department = SQLController.queryDepartment(CurrentUser.getUser().getDepartmentID());
            String newLabelText = String.format("%s %s", classesForDepartmentLabel.getText(), department);
            classesForDepartmentLabel.setText(newLabelText);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        courseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        professorForCourse.setCellValueFactory(new PropertyValueFactory<>("professorName"));

        populateAvailableCourseTable();
    }

    private void populateAvailableCourseTable() {
        try {
            ObservableList<Course> list = SQLController.getAvailableCoursesForUser(userType);
            classesAvailableTable.setItems(list);
            classesAvailableTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Table getUserType() {
        return userType;
    }

    public static void setUserType(Table userType) {
        AddClassForm.userType = userType;
    }
}
