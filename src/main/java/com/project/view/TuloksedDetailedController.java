package com.project.view;

import com.project.simu.model.PalveluPisteTulokset;
import com.project.simu.model.SimulaationSuureet;

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
    private Label keskiLapiMenoAikLabel;
    @FXML
    private TableView<PalveluPisteTulokset> yritysPisteetTable;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yriPisteColumn;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yriKplColumn;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yriPalveluProColumn;
    @FXML
    private TableView<PalveluPisteTulokset> yksityisPisteetTable;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yksPisteColumn;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yksKplColumn;
    @FXML
    private TableColumn<PalveluPisteTulokset, String> yksPalveluProColumn;
    @FXML
    private Button removeButton;
    @FXML
    private Button saveButton;

    private SimulaationSuureet sS;

    public void updateValues() {
        kestoLabel.setText(Double.toString(sS.getSimulointiAika()));
        pProsenttiLabel.setText(Double.toString(sS.getPalveluprosentti()));
    }


    @FXML
    private void remove() {
        Stage stage = (Stage) removeButton.getScene().getWindow();
        controller.popupOpen(false);
        stage.close();
    }

    public void setSimulaationSuureet(SimulaationSuureet sS){
        this.sS = sS;
        System.out.println(sS.getAsPalveltu());
    }

    @FXML
    private void save() {

    }
}
