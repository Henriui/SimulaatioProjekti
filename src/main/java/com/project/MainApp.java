package com.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import com.project.view.MainViewController;

/**
 * JavaFX App
 */
<<<<<<< HEAD:src/main/java/com/project/App.java
public class App extends Application {
    // Hiiren variablet
=======
public class MainApp extends Application {

// Hiiren variablet
>>>>>>> main:src/main/java/com/project/MainApp.java

    private double xOffset = 0;
    private double yOffset = 0;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException 
    {
        scene = new Scene(loadFXML("mainView"));
        stage.setScene(scene);
<<<<<<< HEAD:src/main/java/com/project/App.java
        // stage.initStyle(StageStyle.TRANSPARENT);

        // grab your root here
=======

        // Transparent upper bar.

        //stage.initStyle(StageStyle.TRANSPARENT);

         // Grab your root here
>>>>>>> main:src/main/java/com/project/MainApp.java

        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

<<<<<<< HEAD:src/main/java/com/project/App.java
        // move around here
=======
        // Can move window when mouse down and drag.
>>>>>>> main:src/main/java/com/project/MainApp.java

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        stage.setMaximized(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}