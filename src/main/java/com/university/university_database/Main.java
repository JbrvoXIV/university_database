package com.university.university_database;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        SceneHandler.setStage(stage);
        SceneHandler.loadMainScene();
    }

    public static void main(String[] args) {
        SQLController.establishConnection();
        launch();
    }
}