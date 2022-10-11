package com.project.view;

import java.util.ArrayList;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.PalvelupisteTulokset;

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
    private TableColumn<PalvelupisteTulokset, String> yriPalveluprosenttiColumn;
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
    private TableColumn<PalvelupisteTulokset, String> yksPalveluprosenttiColumn;
    @FXML
    private Button removeButton;
    @FXML
    private Button saveButton;

    private SimulaatioData sS;
    private Tulokset tulokset;
    private UserAsetukset ua;
    private boolean useSS = false;

    private ArrayList<PalvelupisteTulokset> palveluPisteTulokset = new ArrayList<PalvelupisteTulokset>();
    private ArrayList<PalvelupisteTulokset> yksPalveluPisteTulokset = new ArrayList<PalvelupisteTulokset>();
    private ArrayList<PalvelupisteTulokset> yriPalveluPisteTulokset = new ArrayList<PalvelupisteTulokset>();
    
    private void setUp(){
        ua = new UserAsetukset("projekti", "olso", "olso");
        db = new TuloksetDAO(ua, true);
        if(!useSS){
            removeButton.setText("Delete");
            saveButton.setText("Back");
        }
    }

    public void updateValues() {
        setUp();
        if (useSS) {
            for (int i = 1; i < 9; i++) {
                palveluPisteTulokset
                        .add(new PalvelupisteTulokset(i, (db.getRowCount() + 1), i, sS.getPalveluMaara(i),
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
            for (int i = 0; i < 4; i++) {
                yksPalveluPisteTulokset.add(tulokset.getPalveluPisteTulokset().get(i));
            }
            for (int i = 4; i < 8; i++) {
                yriPalveluPisteTulokset.add(tulokset.getPalveluPisteTulokset().get(i));
            }
        }

        ObservableList<PalvelupisteTulokset> oListYriPalvelupisteTulokset = FXCollections
                .observableArrayList(yriPalveluPisteTulokset);
        ObservableList<PalvelupisteTulokset> oListYksPalvelupisteTulokset = FXCollections
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
    }

    @FXML
    private void remove() {
        Stage stage = (Stage) removeButton.getScene().getWindow();
        if(useSS){
            controller.popupOpen(false);
        }
        else{
            System.out.println(tulokset.getSimulaatiokerta());
            db.removeTulos(tulokset.getSimulaatiokerta());
        }
        stage.close();
    }

    @FXML
    private void save() {
        Stage stage = (Stage) removeButton.getScene().getWindow();
        if(useSS){
            saveToDatabase();
            controller.popupOpen(false);
        }
        updateValues();
        stage.close();
    }

    private void saveToDatabase() {
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
}
