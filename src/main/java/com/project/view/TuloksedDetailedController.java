package com.project.view;

import com.project.simu.model.PalveluPisteTulokset;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private TableView<PalveluPisteTulokset> yksityisPisteetTable;

    @FXML
    private Button removeButton;
    @FXML
    private Button saveButton;

    @FXML
    private void remove() {
        Stage stage = (Stage) removeButton.getScene().getWindow();
        controller.popupOpen(false);
        stage.close();
    }

    @FXML
    private void save() {

    }
}
