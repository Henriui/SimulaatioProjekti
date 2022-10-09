package com.project.view;

import java.io.IOException;
import com.project.MainApp;
import animatefx.animation.FadeInDownBig;
import animatefx.animation.FadeInUpBig;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainViewController {

    @FXML
    private Button SimulationB;
    @FXML
    private Button TuloksetB;
    @FXML
    private Button AsetuksetB;
    @FXML
    private Button ExitB;
    @FXML
    private Pane SimulationP;
    @FXML
    private Pane TuloksetP;
    @FXML
    private Pane AsetuksetP;
    @FXML
    private Pane ExitP;
    @FXML
    private Pane Stripe;
    @FXML
    private void uusiSimulaatio() throws IOException {
        MainApp.setRoot("NewSimulationGUI");
        //Scene scene = new Scene(loadFXML("NewSimulationGUI"));
    }

    @FXML
    public void tulokset() throws IOException {
        MainApp.setRoot("tulokset");
    }

    @FXML
    public void asetukset() throws IOException {
        MainApp.setRoot("asetukset");
    }

    @FXML
    public void Exit() {
        System.exit(0);
    }

    @FXML
    public void initialize() {
        SimulationB.setOpacity(0.0);
        FadeInUpBig trans1 = new FadeInUpBig(SimulationB);
        FadeInUpBig trans2 = new FadeInUpBig(TuloksetB);
        FadeInUpBig trans3 = new FadeInUpBig(AsetuksetB);
        FadeInUpBig trans4 = new FadeInUpBig(ExitB);

        FadeInDownBig trans5 = new FadeInDownBig(SimulationP);
        FadeInDownBig trans6 = new FadeInDownBig(TuloksetP);
        FadeInUpBig trans7 = new FadeInUpBig(AsetuksetP);
        FadeInUpBig trans8 = new FadeInUpBig(ExitP);
        FadeInDownBig trans9 = new FadeInDownBig(Stripe);

        new animatefx.util.SequentialAnimationFX();
        new animatefx.util.ParallelAnimationFX(trans1, trans2, trans3, trans4, trans5, trans6, trans7, trans8, trans9)
                .play();

    }
}
