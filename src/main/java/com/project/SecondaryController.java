package com.project;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SecondaryController {
    @FXML
    private Label yksityisJonossa;
    @FXML
    private Label YritysJonossa;
    @FXML
    private Label yksityisPalvelupisteita;
    @FXML
    private Label palvelupisteellaYksityis;
    @FXML
    private Label yritysPalvelupisteita;
    @FXML
    private Label palvelupisteellaYritys;
    @FXML
    private Label suorittaneetMaara;


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