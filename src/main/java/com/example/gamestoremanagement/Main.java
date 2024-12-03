package com.example.gamestoremanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            URL resource = getClass().getResource("/com/example/gamestoremanagement/fxml/LoginView.fxml");
            if (resource == null) {
                throw new IllegalArgumentException("LoginView.fxml not found");
            }
            Parent root = FXMLLoader.load(resource);
            primaryStage.setTitle("Game Store Management");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}