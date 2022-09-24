package com.project.view;

import java.io.IOException;

import com.project.MainApp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainViewController {

    @FXML
    private void uusiSimulaatio() throws IOException {
        MainApp.setRoot("NewSimulationGUI"); 
    }
    
    @FXML
    public void tulokset(){
        System.exit(0);
    }

    @FXML
    public void asetukset(){
        System.exit(0);
    }

    @FXML
    public void Exit(){
        System.exit(0);
    }
}
