package com.project.view;

import java.util.ArrayList;

import com.project.simu.model.PalvelupisteTulokset;

import com.project.simu.model.SimulaatioData;
import com.project.simu.model.Tulokset;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        private TableView<PalvelupisteTulokset> yritysPisteetTable;
        @FXML
        private TableColumn<PalvelupisteTulokset, String> yriPisteColumn;
        @FXML
        private TableColumn<PalvelupisteTulokset, String> yriKplColumn;
        @FXML
        private TableColumn<PalvelupisteTulokset, String> yriKeskiJonoAikaColumn;
        @FXML
        private TableColumn<PalvelupisteTulokset, String> yriKeskiPalveluAikaColumn;
        @FXML
        private TableView<PalvelupisteTulokset> yksityisPisteetTable;
        @FXML
        private TableColumn<PalvelupisteTulokset, String> yksPisteColumn;
        @FXML
        private TableColumn<PalvelupisteTulokset, String> yksKplColumn;
        @FXML
        private TableColumn<PalvelupisteTulokset, String> yksKeskiJonoAikaColumn;
        @FXML
        private TableColumn<PalvelupisteTulokset, String> yksKeskiPalveluAikaColumn;
        @FXML
        private Button removeButton;
        @FXML
        private Button saveButton;

        private SimulaatioData sS;
        private Tulokset tulokset;

        public void updateValues() {
                ArrayList<PalvelupisteTulokset> palveluPisteTulokset = new ArrayList<PalvelupisteTulokset>();
                ArrayList<PalvelupisteTulokset> yksPalveluPisteTulokset = new ArrayList<PalvelupisteTulokset>();
                ArrayList<PalvelupisteTulokset> yriPalveluPisteTulokset = new ArrayList<PalvelupisteTulokset>();

                for (int i = 1; i < 9; i++) {
                        palveluPisteTulokset
                                        .add(new PalvelupisteTulokset(i, sS.getPalveluMaara(i), sS.getJonoAika(i),
                                                        sS.getPalveluAika(i)));
                }
                for (int i = 0; i < 4; i++) {
                        yksPalveluPisteTulokset.add(palveluPisteTulokset.get(i));
                }
                for (int i = 4; i < 8; i++) {
                        yriPalveluPisteTulokset.add(palveluPisteTulokset.get(i));
                }

                ObservableList<PalvelupisteTulokset> oListYriPalvelupisteTulokset = FXCollections
                                .observableArrayList(yriPalveluPisteTulokset);
                ObservableList<PalvelupisteTulokset> oListYksPalvelupisteTulokset = FXCollections
                                .observableArrayList(yksPalveluPisteTulokset);

                yritysPisteetTable.setItems(oListYriPalvelupisteTulokset);
                yksityisPisteetTable.setItems(oListYksPalvelupisteTulokset);

                yriPisteColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getTyyppiString()));
                yriKplColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(
                                                cellData.getValue().getPalvellutAsiakkaatString()));
                yriKeskiJonoAikaColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiJonotusAikaString()));
                yriKeskiPalveluAikaColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiPalveluAikaString()));

                yksPisteColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getTyyppiString()));
                yksKplColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(
                                                cellData.getValue().getPalvellutAsiakkaatString()));
                yksKeskiJonoAikaColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiJonotusAikaString()));
                yksKeskiPalveluAikaColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiPalveluAikaString()));

                tulokset = new Tulokset(sS.getSimulointiAika(), sS.getPalveluprosentti(),
                                (sS.getAsPalveltu() + sS.getAsPoistunut()), sS.getAsPalveltu(), sS.getAsPoistunut(),
                                sS.getAsReRouted(), sS.getJonotusATotal(), sS.getAvgAsAikaSim(), palveluPisteTulokset);

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
