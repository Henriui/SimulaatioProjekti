package com.project.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.project.MainApp;
import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.Tulokset;
import com.project.simu.model.UserAsetukset;
import com.project.simu.model.UserAsetuksetController;

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

/**
 * Luokka Tulokset ruudun hallintaa varten
 * 
 * @author Lassi Bågman
 */
public class TuloksetController {

    // FXML komponentit
    @FXML
    private TableView<Tulokset> tableView;
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

    private ITuloksetDAO db;
    // Koordinaatit ruudun liikuttamista varten
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void initialize() {
        setTableView();
    }

    /**
     * Methodi joka järjestää listan hakemalla tiedot databasesta
     * 
     * @author Lassi Bågman
     */
    public void setTableView() {
        UserAsetuksetController uac = new UserAsetuksetController();
        UserAsetukset ua = uac.lueTiedostostaDbParametrit(); // Hakee databasen asetukset filestä
        db = new TuloksetDAO(ua, true); // Luo yhteyden databaseen

        // Luo ArrayListin ja kerää sinne kaikki rivit databasesta
        ArrayList<Tulokset> tuloksetArrayList = new ArrayList<Tulokset>();
        for (int i = 1; i <= db.getRowCount(); i++) {
            try {
                tuloksetArrayList.add(db.queryTulos(i));
            } catch (SQLException e) {
                System.err.println("Tulosten haku ei onnistunut!");
                e.printStackTrace();
            }
        }

        // ArrayList ObservableListiksi ja se TableViewiin
        ObservableList<Tulokset> tuloksetObservableList = FXCollections
                .observableArrayList(tuloksetArrayList);
        tableView.setItems(tuloksetObservableList);

        // Asettaa datat Columneihin
        simulaatiokertaColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getSimulaatiokertaString() + "."));
        kestoColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getKestoString() + "min"));
        palveluprosenttiColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getPalveluProsenttiString() + "%"));
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
                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiJonotusAikaString() + "min"));
        keskiLapiMenoAikColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        String.format("%.1f",
                                ((cellData.getValue().getKeskiLapiMenoAika()
                                        - cellData.getValue().getKeskiJonotusAika()) / 60))
                                + "min"));
        keskiPalveluAikColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getKeskiLapiMenoAikaString() + "min"));

        // Kuuntelija jos listasta halutaan tarkastella jotain riviä tarkemmin
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        runTulokset(newValue);
                    } catch (IOException e) {
                        System.err.println("Tuloksen avaaminen ei onnistunut!");
                        e.printStackTrace();
                    }
                });

        // Putket kiinni ettei data karkaa
        db.closeConnection();
        System.out.println("Lista päivitetty");
    }

    /**
     * Methodi millä välitetään valittu tulos eteenpäin tuloksetDetailedPopUpille
     * tarkempaan tarkasteluun
     * 
     * @param tulokset
     * @throws IOException
     * @author Lassi Bågman
     */
    public void runTulokset(Tulokset tulokset) throws IOException {
        FXMLLoader loader = loadFXML("tuloksetDetailedPopUp");
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Simulaatiokerran tulos");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);

        // Can move window when mouse down and drag.
        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        TuloksedDetailedController controller = loader.getController();
        controller.setTulokset(tulokset);
        stage.show();
        controller.updateValues();
    }

    /**
     * 
     * @param fxml
     * @return
     * @throws IOException
     * @author Jonne
     */
    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader;
    }

    @FXML
    private void takaisinMainView() throws IOException {
        MainApp.setRoot("mainView");
    }
}
