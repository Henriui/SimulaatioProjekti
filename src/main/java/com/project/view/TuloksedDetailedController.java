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
    private TuloksetController tuloksetController;
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
    private UserAsetukset ua;
    private boolean useSS = false;

    private ArrayList<PalvelupisteTulos> palveluPisteTulokset = new ArrayList<PalvelupisteTulos>();
    private ArrayList<PalvelupisteTulos> yksPalveluPisteTulokset = new ArrayList<PalvelupisteTulos>();
    private ArrayList<PalvelupisteTulos> yriPalveluPisteTulokset = new ArrayList<PalvelupisteTulos>();

    public void updateValues() {
        ua = new UserAsetukset("simulaattori", "jonne", "jonnensalasana");
        db = new TuloksetDAO(ua, true);
        if (useSS) {
            int r = db.getRowCount() +1;
            for (int i = 1; i < 9; i++) {
                palveluPisteTulokset
                        .add(new PalvelupisteTulos(i, r, i, sS.getPalveluMaara(i),
                                sS.getJonoAika(i),
                                sS.getPalveluAika(i), sS.getPalveluProsentti(i)));
            }

            tulokset = new Tulokset(sS.getSimulointiAika(), sS.getPalveluprosentti(),
                (sS.getAsPalveltu() + sS.getAsPoistunut()), sS.getAsPalveltu(), sS.getAsReRouted(),
                sS.getAsPoistunut(), sS.getJonotusATotal(), sS.getAvgAsAikaSim(), palveluPisteTulokset);

                for (int i = 0; i < 4; i++) {
                    yksPalveluPisteTulokset.add(palveluPisteTulokset.get(i));
                }
                for (int i = 4; i < 8; i++) {
                    yriPalveluPisteTulokset.add(palveluPisteTulokset.get(i));
                }
        }
        else{
            saveButton.setDisable(true);
            removeButton.setText("Takaisin");
            for (int i = 0; i < 4; i++) {
                yksPalveluPisteTulokset.add(tulokset.getPalveluPisteTulokset().get(i));
            }
            for (int i = 4; i < 8; i++) {
                yriPalveluPisteTulokset.add(tulokset.getPalveluPisteTulokset().get(i));
            }
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

        kestoLabel.setText(tulokset.getKestoString());
        pProsenttiLabel.setText(tulokset.getPalveluProsenttiString());
        asMaaraLabel.setText(tulokset.getAsMaaraString());
        palvellutAsLabel.setText(tulokset.getPalvellutAsiakkaatString());
        poistuneetAsLabel.setText(tulokset.getPoistuneetAsiakkaatString());
        uudelleenOhjAsLabel.setText(tulokset.getUudelleenOhjatutAsiakkaatString());
        keskiJonotusAikLabel.setText(tulokset.getKeskiJonotusAikaString());
        keskiLapiMenoAikLabel.setText(tulokset.getKeskiLapiMenoAikaString());
        db.closeConnection();
    }

    @FXML
    private void remove() {
        Stage stage = (Stage) removeButton.getScene().getWindow();
        if(useSS){
            controller.popupOpen(false);
        }
        stage.close();
    }

    @FXML
    private void save() {
        System.out.println("Tallenna painettu");
        Stage stage = (Stage) removeButton.getScene().getWindow();
        if(useSS){
            saveToDatabase();
            controller.popupOpen(false);
        }
        stage.close();
    }

    private void saveToDatabase() {
        db.openConnection();
        db.addTulos(tulokset);
        db.closeConnection();
    }

    public void setSimulaationSuureet(SimulaatioData sS) {
        this.sS = sS;
        useSS = true;
    }

    public void setTulokset(Tulokset tulokset){
        this.tulokset = tulokset;
    }

    public void setSimulationController(NewSimulationController nSc) {
        controller = nSc;
    }

    public void setTuloksetController(TuloksetController tuloksetController){
        tuloksetController = this.tuloksetController;
    }
}
