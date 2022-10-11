package com.project.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.project.MainApp;
import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.SimulaatioData;
import com.project.simu.model.Tulokset;
import com.project.simu.model.UserAsetukset;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TuloksetController {

    @FXML
    private TableView<Tulokset> tw;
    @FXML
    private TableColumn<Tulokset, String> simulaatiokertaColumn;
    @FXML
    private TableColumn<Tulokset, String> kestoColumn;
    @FXML
    private TableColumn<Tulokset, String> palveluprosenttiColumn;
    @FXML
    private TableColumn<Tulokset, String> asiakkaitaColumn;
    @FXML
    private TableColumn<Tulokset, String> palveltuColumn;
    @FXML
    private TableColumn<Tulokset, String> uudelleenOhjatutColumn;
    @FXML
    private TableColumn<Tulokset, String> poistuneetColumn;
    @FXML
    private TableColumn<Tulokset, String> keskiJonotusAikColumn;
    @FXML
    private TableColumn<Tulokset, String> keskiPalveluAikColumn;
    @FXML
    private TableColumn<Tulokset, String> keskiLapiMenoAikColumn;


    // Hakee asetukset ja kutsuu tietokannan
    private UserAsetukset asetukset; // TODO: VAATII ASETUKSET
    private Tulokset tulokset;
    private ITuloksetDAO db;

    @FXML
    private void initialize() {
        setTableView();
    }

    private void setTableView() {
        UserAsetukset ua = new UserAsetukset("projekti", "olso", "olso");
        db = new TuloksetDAO(ua, true);
        ArrayList<Tulokset>tuloksetArrayList = new ArrayList<Tulokset>();
        for(int i = 1; i <= db.getRowCount(); i++){
            try {
                tuloksetArrayList.add(db.queryTulos(i));
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        ObservableList<Tulokset> tuloksetObservableList = FXCollections
                .observableArrayList(tuloksetArrayList);
        tw.setItems(tuloksetObservableList);

        simulaatiokertaColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getSimulaatiokertaString()));
        kestoColumn.setCellValueFactory(
                    cellData -> new SimpleStringProperty(cellData.getValue().getKestoString()));
        palveluprosenttiColumn.setCellValueFactory(
                    cellData -> new SimpleStringProperty(cellData.getValue().getPalveluProsenttiString()));
        asiakkaitaColumn.setCellValueFactory(
                    cellData -> new SimpleStringProperty(cellData.getValue().getAsMaaraString()));
        palveltuColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getPalvellutAsiakkaatString()));
        uudelleenOhjatutColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getUudelleenOhjatutAsiakkaatString()));
        poistuneetColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getPoistuneetAsiakkaatString()));
        keskiJonotusAikColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiJonotusAikaString()));
        keskiLapiMenoAikColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiLapiMenoAikaString()));
        keskiPalveluAikColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiLapiMenoAikaString()));

        System.out.println("Ajettu");
    }

    @FXML
    private void takaisinMainView() throws IOException {
        MainApp.setRoot("mainView");
    }
}
