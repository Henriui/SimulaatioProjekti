package com.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * 
 * JavaFX App
 */
public class MainApp extends Application {

    // Hiiren variablet

    private double xOffset = 0;
    private double yOffset = 0;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/mainView.fxml"));
        scene = new Scene(root);
        
        stage.setScene(scene);

        // Transparent upper bar.
        // stage.initStyle(StageStyle.TRANSPARENT);

        // Grab your root here

        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // Can move window when mouse down and drag.

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        stage.setMaximized(false);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void startSimulation(String[] args) {
        launch();
    }
}