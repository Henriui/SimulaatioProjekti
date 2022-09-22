package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UusiSimulaatioController {

    @FXML
    private Label yksityisJonossa;
    @FXML
    private Label YritysJonossa;

    @FXML
    private void takaisinMainView() throws IOException {
        App.setRoot("mainView");
    }

    @FXML
    public void aloitaSimulaatio(){
        for(int i = 0; i<1000;i++){
            ilmoitaJononKoko(i);
        }
    }
    public void ilmoitaJononKoko(int koko){
        String tulos = String.valueOf(koko);
        yksityisJonossa.setText(tulos);
    }
}