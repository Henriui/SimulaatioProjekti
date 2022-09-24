package com.project;

import java.io.IOException;

import com.project.simu.framework.Moottori;
import com.project.simu.framework.Trace;
import com.project.simu.framework.Trace.Level;
import com.project.simu.model.OmaMoottori;
import com.project.simu.model.SimulaationSuureet;
import com.project.simu.model.Tyyppi;
import com.project.simu.model.UserParametrit;
import com.project.simu.utilities.ParametriUtilities;

import javafx.application.Platform;
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
    public void aloitaSimulaatio() {
        UserParametrit uP = UserParametrit.getInstance();
        Trace.setTraceLevel(Level.INFO);
        Moottori m = new OmaMoottori(this);
        m.setSimulointiaika(uP.getSimulaationAika());
        ((Thread) m).start();
    }

    public void ilmoitaJononKoko(int koko) {

    }
}