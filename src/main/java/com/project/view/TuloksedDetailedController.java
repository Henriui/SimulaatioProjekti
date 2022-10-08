package com.project.view;

import java.util.ArrayList;

import com.project.simu.model.PalveluPisteTulokset;

import com.project.simu.model.SimulaatioData;
import com.project.simu.model.Tulokset;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TuloksedDetailedController {
    private NewSimulationController controller = new NewSimulationController();
    @FXML
    private Label kestoLabel;
    @FXML
    private Label pProsenttiLabel;
    @FXML
    private Label asMaaraLabel;
    @FXML
    private Label palvellutAsLabel;
    @FXML
    private Label poistuneetAsLabel;
    @FXML
    private Label uudelleenOhjAsLabel;
    @FXML
    private Label keskiJonotusAikLabel;
    @FXML
    private Label keskiLapiMenoAikLabel;
    @FXML
    private TableView<PalveluPisteTulokset> yritysPisteetTable;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yriPisteColumn;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yriKplColumn;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yriKeskAikColumn;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yriPalveluProColumn;
    @FXML
    private TableView<PalveluPisteTulokset> yksityisPisteetTable;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yksPisteColumn;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yksKplColumn;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yksKeskAikColumn;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yksPalveluProColumn;
    @FXML
    private Button removeButton;
    @FXML
    private Button saveButton;

    private SimulaatioData sS;
    private Tulokset tulokset;

    public void updateValues() {
        ArrayList<PalveluPisteTulokset> palveluPisteTuloksets = new ArrayList<PalveluPisteTulokset>();
        for(int i = 1; i < 9; i++){
            palveluPisteTuloksets.add(new PalveluPisteTulokset(i, sS.getPalveluMaara(i), sS.getJonoAika(i), sS.getPalveluAika(i)));
        }

        tulokset = new Tulokset(sS.getSimulointiAika(), sS.getPalveluprosentti(), (sS.getAsPalveltu() + sS.getAsPoistunut()), sS.getAsPalveltu(), sS.getAsPoistunut(), sS.getAsReRouted(), sS.getJonotusATotal(), sS.getAvgAsAikaSim(), palveluPisteTuloksets);

        kestoLabel.setText(tulokset.getKestoString());
        pProsenttiLabel.setText(tulokset.getPalveluProsenttiString());
        asMaaraLabel.setText(tulokset.getAsMaaraString());
        palvellutAsLabel.setText(tulokset.getPalvellutAsiakkaatString());
        poistuneetAsLabel.setText(tulokset.getPoistuneetAsiakkaatString());
        uudelleenOhjAsLabel.setText(tulokset.getUudelleenOhjatutAsiakkaatString());
        keskiJonotusAikLabel.setText(tulokset.getKeskiJonotusAikaString());
        keskiLapiMenoAikLabel.setText(tulokset.getKeskiLapiMenoAikaString());
    }

    @FXML
    private void remove() {
        Stage stage = (Stage) removeButton.getScene().getWindow();
        controller.popupOpen(false);
        stage.close();
    }

    public void setSimulaationSuureet(SimulaatioData sS) {
        this.sS = sS;
        System.out.println(sS.getAsPalveltu());
    }

    @FXML
    private void save() {

    }
}
