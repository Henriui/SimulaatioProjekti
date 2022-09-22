package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
// Hiiren variablet

private double xOffset = 0;
private double yOffset = 0;

@Override
public void start(Stage primaryStage) 
{
    try {
         Parent root = FXMLLoader.load(getClass().getResource("mainView.fxml"));

         // Tekee yläpalkista läpinäkyvän.

         primaryStage.initStyle(StageStyle.TRANSPARENT);

            //Hiiren klikkaus kohta

            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            //Hiirtä voi liikuttaa pois valinnasta pohjassa.

            root.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            });

            // Create scene.

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();
        
    } catch(Exception e) {
        e.printStackTrace();
    }
}

public static void main(String[] args) {
    launch(args);
}

}