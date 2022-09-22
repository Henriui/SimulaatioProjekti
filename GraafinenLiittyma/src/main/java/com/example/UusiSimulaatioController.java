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
        //Trace.setTraceLevel(Level.INFO);
		//Moottori m = new OmaMoottori();
		//m.setSimulointiaika(1000);
		//m.start();
    }
    public void ilmoitaJononKoko(int koko){
        String tulos = String.valueOf(koko);
        yksityisJonossa.setText(tulos);
    }
}