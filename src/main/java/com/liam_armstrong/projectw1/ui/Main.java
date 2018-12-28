package com.liam_armstrong.projectw1.ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        mainController controller = loader.getController();
        primaryStage.setTitle("Calendar");
        Scene scene = new Scene(root, 700, 600);
        primaryStage.setScene(scene);
        controller.boot();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
