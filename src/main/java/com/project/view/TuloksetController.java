package com.project.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.project.MainApp;
import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.Tulokset;
import com.project.simu.model.UserAsetukset;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private ITuloksetDAO db;
    private double xOffset = 0;
    private double yOffset = 0;
    private static boolean open = false;

    @FXML
    private void initialize() {
        setTableView();
    }

    public void setTableView() {
        UserAsetukset ua = new UserAsetukset("simulaattori", "jonne", "jonnensalasana");
        db = new TuloksetDAO(ua, true);
        ArrayList<Tulokset> tuloksetArrayList = new ArrayList<Tulokset>();
        for (int i = 1; i <= db.getRowCount(); i++) {
            try {
                Tulokset t = db.queryTulos(i);
                if (t != null) {
                    tuloksetArrayList.add(t);
                }
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
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getPalvellutAsiakkaatString()));
        uudelleenOhjatutColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getUudelleenOhjatutAsiakkaatString()));
        poistuneetColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getPoistuneetAsiakkaatString()));
        keskiJonotusAikColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiJonotusAikaString()));
        keskiLapiMenoAikColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiLapiMenoAikaString()));
        keskiPalveluAikColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiLapiMenoAikaString()));

        tw.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        runTulokset(newValue);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });

        System.out.println("Ajettu");
    }

    public void runTulokset(Tulokset tulokset) throws IOException {
        FXMLLoader loader = loadFXML("tuloksetDetailedPopUp");
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Simulaatiokerran tulos");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);

        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // Can move window when mouse down and drag.
        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        TuloksedDetailedController controller = loader.getController();
        controller.setTulokset(tulokset);
        controller.setTuloksetController(this);
        stage.show();
        controller.updateValues();
    }

    // Finds fxml file from the resources folder.
    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader;
    }

    @FXML
    private void takaisinMainView() throws IOException {
        MainApp.setRoot("mainView");
    }
}
