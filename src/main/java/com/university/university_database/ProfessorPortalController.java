package com.university.university_database;

import com.university.university_database.schemas.Course;
import com.university.university_database.schemas.CurrentUser;
import com.university.university_database.schemas.Person;
import com.university.university_database.schemas.Table;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

public class ProfessorPortalController implements Initializable {

    @FXML
    private Label professorIDDisplay;
    @FXML
    private Label professorMajorDisplay;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userID = String.valueOf(CurrentUser.getUser().getID());
        String userPassword = CurrentUser.getUser().getPassword();
        try {
            Person p = SQLController.queryLogin(Table.PROFESSOR, userID, userPassword);
            if(p == null)
                throw new SQLException("User not found!");
            SceneHandler.loadUserPortal(p, professorIDDisplay, professorMajorDisplay);
            populateTable(p);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        courseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
    }

    /* WIP */
    public void addClass(ActionEvent e) {
    }

    /* WIP */
    public void removeClass(ActionEvent e) {
    }

    /* WIP */
    public void updateUserInfo(ActionEvent e) {
    }

    /* WIP */
    public void logout(ActionEvent e) {
        CurrentUser.setUser(null);
        SceneHandler.loadProfessorLogin();
    }

    private void populateTable(Person professor) {
        try {
            ObservableList<Course> list = SQLController.getCoursesForUser(Table.PROFESSOR, professor);
            courseTable.setItems(list);
            courseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
