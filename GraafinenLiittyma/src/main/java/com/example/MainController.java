package com.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    private void uusiSimulaatio() throws IOException {
        App.setRoot("secondary");
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
