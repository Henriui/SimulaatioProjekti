package com.project.view;

import java.io.IOException;

import com.project.MainApp;
import com.project.simu.framework.Moottori;
import com.project.simu.framework.Trace;
import com.project.simu.framework.Trace.Level;
import com.project.simu.model.OmaMoottori;
import com.project.simu.model.SimulaationSuureet;
<<<<<<< HEAD:src/main/java/com/project/SecondaryController.java
import com.project.simu.model.Tyyppi;
=======
>>>>>>> main:src/main/java/com/project/view/NewSimulationController.java
import com.project.simu.model.UserParametrit;
import com.project.simu.utilities.ParametriUtilities;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NewSimulationController {
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
        MainApp.setRoot("mainView"); 
    }

    @FXML
    public void aloitaSimulaatio() {
        UserParametrit uP = UserParametrit.getInstance();
        Trace.setTraceLevel(Level.INFO);
        Moottori m = new OmaMoottori(this);
        m.setSimulointiaika(uP.getSimulaationAika());
        ((Thread) m).start();
    }

<<<<<<< HEAD:src/main/java/com/project/SecondaryController.java
    public void ilmoitaJononKoko(int koko) {

    }
=======
    public void ilmoitaJononKoko(int koko){
        String tulos = String.valueOf(koko);
        yksityisJonossa.setText(tulos);
    }

    @FXML
    public void setSuureet() throws IOException{
        Scene scene = new Scene(loadFXML("suureet"));

        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Suureiden asetukset");
        stage.show();
    }

    // Finds fxml file from the resources folder.

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
>>>>>>> main:src/main/java/com/project/view/NewSimulationController.java
}