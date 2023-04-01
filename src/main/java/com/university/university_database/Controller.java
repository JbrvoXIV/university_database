package com.university.university_database;

import javafx.event.ActionEvent;

public class Controller {

    public void switchToStudentLogin(ActionEvent e) {
        SceneHandler.loadStudentLogin();
    }

    public void switchToProfessorLogin(ActionEvent e) {
        SceneHandler.loadProfessorLogin();
    }

    public void exit(ActionEvent e) {
        System.exit(1);
    }
}
