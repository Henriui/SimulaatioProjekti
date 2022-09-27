package com.project.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.project.MainApp;
import com.project.simu.framework.Moottori;
import com.project.simu.framework.Trace;
import com.project.simu.framework.Trace.Level;
import com.project.simu.model.OmaMoottori;
import com.project.simu.model.UserParametrit;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewSimulationController implements INewSimulationControllerVtoM, INewSimulationControllerMtoV {
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
   
    private static Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;
    private static boolean open = false;
 
    @FXML
    private void takaisinMainView() throws IOException {
        MainApp.setRoot("mainView");
    }

    @FXML
    public void aloitaSimulaatio() {
        UserParametrit uP = UserParametrit.getInstance();
        Trace.setTraceLevel(Level.INFO);
        Moottori m = new OmaMoottori(this);
        m.setViive(uP.getViiveAika());
        m.setSimulointiaika(uP.getSimulaationAika());
        ((Thread) m).start();
    }

    public void ilmoitaJononKoko(int koko) {
        String tulos = String.valueOf(koko);
        yksityisJonossa.setText(tulos);
    }

    @FXML
    public void setSuureet() throws IOException 
    {
        System.out.println("!11111111111111111" + open);
        if(!open)
        {
            Scene scene = new Scene(loadFXML("Parametrit"));
            Stage stage = new Stage();
            stage.setScene(scene);
            
            stage.setTitle("Suureiden asetukset");
            stage.initStyle(StageStyle.TRANSPARENT);

            scene.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            // Can move window when mouse down and drag.

            scene.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            stage.show();
            open = true;
        }
    }
    
    public void popupOpen(boolean isOpen){
        open = isOpen;
    }

    // Finds fxml file from the resources folder.

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // LIUTA TESTI METHODEITA -> nää vois siivota järkevämmäks ehkä?
    // Jokanen tekee erillisen runin? jne jne
    // Check: SimulaationSuureet.java updateSuureet()
    @Override
    public void ilmoitaJononKoko(int yksityis, int yritys) {
        Platform.runLater(new Runnable() {
            public void run() {
                YritysJonossa.setText(String.valueOf(yritys));
                yksityisJonossa.setText(String.valueOf(yksityis));
            }
        });

    }

    @Override
    public void ilmoitaPalveluPisteet(int yritys, int yksityis) {
        Platform.runLater(new Runnable() {
            public void run() {
                yksityisPalvelupisteita.setText("Palvelupisteitä: " + String.valueOf(yksityis));
                yritysPalvelupisteita.setText("Palvelupisteitä: " + String.valueOf(yritys));
            }
        });
    }

    @Override
    public void asiakkaitaPalveluPisteella(int yksityis, int yritys) {
        Platform.runLater(new Runnable() {
            public void run() {
                palvelupisteellaYritys.setText("As. Oleskellut: " + String.valueOf(yritys));
                palvelupisteellaYksityis.setText("As. Oleskellut: " + String.valueOf(yksityis));
            }
        });
    }

    @Override
    public void ulkonaAsiakkaita(int maara) {
        Platform.runLater(new Runnable() {
            public void run() {
                suorittaneetMaara.setText("Ulkona: " + String.valueOf(maara));
            }
        });
    }

    @Override
    public void hidastaSimulaatiota() {
        // TODO Auto-generated method stub

    }

    @Override
    public void nopeutaSimulaatiota() {
        // TODO Auto-generated method stub

    }

}