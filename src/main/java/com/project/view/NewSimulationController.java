package com.project.view;

import java.io.IOException;

import com.project.MainApp;
import com.project.simu.framework.Moottori;
import com.project.simu.framework.Trace;
import com.project.simu.framework.Trace.Level;
import com.project.simu.model.OmaMoottori;
import com.project.simu.model.UserParametrit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    public void setSuureet() throws IOException {
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
    public void asiakkaitaPalveluPisteella(int type, int koko) {
        Platform.runLater(new Runnable() {
            public void run() {
                String tulos = String.valueOf(koko);
                if (type == 10) {
                    palvelupisteellaYritys.setText("Palveltu: " + tulos);
                } else {
                    palvelupisteellaYksityis.setText("Palveltu: " + tulos);
                }
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