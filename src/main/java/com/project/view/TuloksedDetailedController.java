package com.project.view;

import java.util.ArrayList;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.PalvelupisteTulos;

import com.project.simu.model.SimulaatioData;
import com.project.simu.model.Tulokset;
import com.project.simu.model.UserAsetukset;

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
        private NewSimulationController controller;
        private ITuloksetDAO db;
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
        private TableView<PalvelupisteTulos> yritysPisteetTable;
        @FXML
        private TableColumn<PalvelupisteTulos, String> yriPisteColumn;
        @FXML
        private TableColumn<PalvelupisteTulos, String> yriKplColumn;
        @FXML
        private TableColumn<PalvelupisteTulos, String> yriKeskiJonoAikaColumn;
        @FXML
        private TableColumn<PalvelupisteTulos, String> yriKeskiPalveluAikaColumn;
        @FXML
        private TableColumn<PalvelupisteTulos, String> yriPalveluprosenttiColumn;
        @FXML
        private TableView<PalvelupisteTulos> yksityisPisteetTable;
        @FXML
        private TableColumn<PalvelupisteTulos, String> yksPisteColumn;
        @FXML
        private TableColumn<PalvelupisteTulos, String> yksKplColumn;
        @FXML
        private TableColumn<PalvelupisteTulos, String> yksKeskiJonoAikaColumn;
        @FXML
        private TableColumn<PalvelupisteTulos, String> yksKeskiPalveluAikaColumn;
        @FXML
        private TableColumn<PalvelupisteTulos, String> yksPalveluprosenttiColumn;
        @FXML
        private Button removeButton;
        @FXML
        private Button saveButton;

        private SimulaatioData sS;
        private Tulokset tulokset;

        private ArrayList<PalvelupisteTulos> PalvelupisteTulos = new ArrayList<PalvelupisteTulos>();
        private ArrayList<PalvelupisteTulos> yksPalveluPisteTulokset = new ArrayList<PalvelupisteTulos>();
        private ArrayList<PalvelupisteTulos> yriPalveluPisteTulokset = new ArrayList<PalvelupisteTulos>();

        public void updateValues() {
                UserAsetukset ua = new UserAsetukset("projekti", "olso", "olso");
                db = new TuloksetDAO(ua, true);
                for (int i = 1; i < 9; i++) {
                        PalvelupisteTulos
                                        .add(new PalvelupisteTulos(i, (db.getRowCount() + 1), i, sS.getPalveluMaara(i),
                                                        sS.getJonoAika(i),
                                                        sS.getPalveluAika(i), sS.getPalveluProsentti(i)));
                }
                for (int i = 0; i < 4; i++) {
                        yksPalveluPisteTulokset.add(PalvelupisteTulos.get(i));
                }
                for (int i = 4; i < 8; i++) {
                        yriPalveluPisteTulokset.add(PalvelupisteTulos.get(i));
                }

                ObservableList<PalvelupisteTulos> oListYriPalvelupisteTulokset = FXCollections
                                .observableArrayList(yriPalveluPisteTulokset);
                ObservableList<PalvelupisteTulos> oListYksPalvelupisteTulokset = FXCollections
                                .observableArrayList(yksPalveluPisteTulokset);

                yritysPisteetTable.setItems(oListYriPalvelupisteTulokset);
                yksityisPisteetTable.setItems(oListYksPalvelupisteTulokset);

                yriPisteColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getTyyppiString()));
                yriKplColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                                cellData.getValue().getPalvellutAsiakkaatString()));
                yriKeskiJonoAikaColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiJonotusAikaString()));
                yriKeskiPalveluAikaColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiPalveluAikaString()));
                yriPalveluprosenttiColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getPalveluprosetString()));

                yksPisteColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getTyyppiString()));
                yksKplColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                                cellData.getValue().getPalvellutAsiakkaatString()));
                yksKeskiJonoAikaColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiJonotusAikaString()));
                yksKeskiPalveluAikaColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiPalveluAikaString()));
                yksPalveluprosenttiColumn.setCellValueFactory(
                                cellData -> new SimpleStringProperty(cellData.getValue().getPalveluprosetString()));

                tulokset = new Tulokset(sS.getSimulointiAika(), sS.getPalveluprosentti(),
                                (sS.getAsPalveltu() + sS.getAsPoistunut()), sS.getAsPalveltu(), sS.getAsReRouted(),
                                sS.getAsPoistunut(), sS.getJonotusATotal(), sS.getAvgAsAikaSim(), PalvelupisteTulos);

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

        @FXML
        private void save() {
                saveToDatabase();
        }

        private void saveToDatabase() {
                db.addTulos(tulokset);
                db.closeConnection();
                Stage stage = (Stage) removeButton.getScene().getWindow();
                controller.popupOpen(false);
                stage.close();
        }

        public void setSimulaationSuureet(SimulaatioData sS) {
                this.sS = sS;
                System.out.println(sS.getAsPalveltu());
        }

        public void setSimulationController(NewSimulationController nSc) {
                controller = nSc;
        }

}
