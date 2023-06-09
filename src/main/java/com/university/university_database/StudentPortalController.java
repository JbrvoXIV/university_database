package com.university.university_database;

import com.university.university_database.schemas.Course;
import com.university.university_database.schemas.CurrentUser;
import com.university.university_database.schemas.Person;
import com.university.university_database.schemas.Table;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class StudentPortalController implements Initializable {

    @FXML
    private Label studentIDDisplay;
    @FXML
    private Label studentMajorDisplay;
    @FXML
    private ScrollPane portalScrollPane;
    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> courseID;
    @FXML
    private TableColumn<Course, String> courseName;
    @FXML
    private TableColumn<Course, Time> startTime;
    @FXML
    private TableColumn<Course, Time> endTime;
    @FXML
    private TableColumn<Course, String> roomNumber;
    @FXML
    private TableColumn<Course, String> professorForCourse;
    @FXML
    private TableColumn<Course, String> grade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        portalScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        String userID = String.valueOf(CurrentUser.getUser().getID());
        String userPassword = CurrentUser.getUser().getPassword();
        try {
            Person p = SQLController.queryLogin(Table.STUDENT, userID, userPassword);
            if(p == null)
                throw new SQLException("User not found!");
            SceneHandler.loadUserPortal(studentIDDisplay, studentMajorDisplay);
            populateTable(p);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        courseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        professorForCourse.setCellValueFactory(new PropertyValueFactory<>("professorName"));
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
    }

    public void switchToAddClassForm(ActionEvent e) {
        SceneHandler.loadAddClassForm(Table.STUDENT);
    }

    /* WIP */
    public void removeClass(ActionEvent e) {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if(selectedCourse != null) {
            try {
                boolean classRemoved = SQLController.removeClass(selectedCourse);
                if(classRemoved) {
                    SceneHandler.triggerAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Success",
                            "The class has been removed",
                            new Exception("The class, " + selectedCourse.getCourseID() + ", is no longer on your schedule")
                    );
                    populateTable(CurrentUser.getUser());
                } else {
                    SceneHandler.triggerAlert(
                            Alert.AlertType.ERROR,
                            "Error",
                            "There was an unexpected error",
                            new Exception("The class, " + selectedCourse.getCourseID() + ", was unable to be removed from your schedule")
                    );
                }
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            SceneHandler.triggerAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "You have not chosen a class!",
                    new InputMismatchException("Please select a course before pressing 'Remove Class'")
            );
        }
    }

    public void switchToUserUpdateForm(ActionEvent e) {
        SceneHandler.loadUserUpdateForm(Table.STUDENT);
    }

    public void logout(ActionEvent e) {
        CurrentUser.setUser(null);
        SceneHandler.loadStudentLogin();
    }

    public void updateGrade(ActionEvent e) {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if(selectedCourse != null) {
            NewGradeFormController.setSelectedCourse(selectedCourse);
            SceneHandler.loadUpdateGradeModal(e);
        }
        else {
            SceneHandler.triggerAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No class selected!",
                    new InputMismatchException("Please select a course to update grade")
            );
        }
    }

    private void populateTable(Person student) {
        try {
            ObservableList<Course> list = SQLController.getCoursesForUser(Table.STUDENT, student);
            courseTable.setItems(list);
            courseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
