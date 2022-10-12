package com.project.view;

import java.util.ArrayList;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.PalvelupisteTulos;

import com.project.simu.model.SimulaatioData;
import com.project.simu.model.Tulokset;
import com.project.simu.model.UserAsetukset;
import com.project.simu.model.UserAsetuksetController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TuloksedDetailedController {

    // FXML komponentit
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
    private ListView<String> pisteetListView;
    @FXML
    private ListView<String> ajatListView;
    @FXML
    private ListView<String> jakaumaListView;
    @FXML
    private ListView<String> miscListView;
    @FXML
    private Button removeButton;
    @FXML
    private Button saveButton;

    private NewSimulationController controller;
    private ITuloksetDAO db;
    private SimulaatioData sd;
    private Tulokset tulokset;
    private boolean useSS = false; // Boolean jotta tiedetään kummalta sivulta ollaan tulossa

    /**
     * Methodi millä haetaan data riippuen siitä mistä ollaan tulossa ja laitetaan
     * se sivulle
     * 
     * @author Lassi Bågman
     */
    public void updateValues() {

        // ArrayListit Palvelupiste tuloksein hallintaan
        ArrayList<PalvelupisteTulos> palveluPisteTulokset = new ArrayList<PalvelupisteTulos>();
        ArrayList<PalvelupisteTulos> yksPalveluPisteTulokset = new ArrayList<PalvelupisteTulos>();
        ArrayList<PalvelupisteTulos> yriPalveluPisteTulokset = new ArrayList<PalvelupisteTulos>();

        UserAsetuksetController uac = new UserAsetuksetController();
        UserAsetukset ua = uac.lueTiedostostaDbParametrit(); // Hakee databasen asetukset filestä
        db = new TuloksetDAO(ua, true); // Luo yhteyden databaseen

        // Jos tullaan NewSimulationGUI.fxml kautta
        if (useSS) {
            int r = db.getRowCount() + 1; // Haetaan simulaatiokertojen määrä ja lisätään 1 mahdollista tulevaa
                                          // tallennusta varten

            // Palvelupiste data simulaatio datasta
            for (int i = 1; i < 9; i++) {
                palveluPisteTulokset
                        .add(new PalvelupisteTulos(i, r, i, sd.getPalveluMaara(i),
                                sd.getJonoAika(i),
                                sd.getPalveluAika(i), sd.getPalveluProsentti(i)));
            }

            // Tulokset simulaatio datasta
            tulokset = new Tulokset(sd.getSimulointiAika(), sd.getPalveluprosentti(),
                    ((int)sd.getAsTotalMaara()), sd.getAsPalveltu(), sd.getAsReRouted(),
                    sd.getAsPoistunut(), sd.getJonotusATotal(), sd.getAvgAsAikaSim(), palveluPisteTulokset);

            // Yksityis palvelupisteet erotettuna yritys palvelupisteistä
            for (int i = 0; i < 4; i++) {
                yksPalveluPisteTulokset.add(palveluPisteTulokset.get(i));
            }
            for (int i = 4; i < 8; i++) {
                yriPalveluPisteTulokset.add(palveluPisteTulokset.get(i));
            }
        }

        // Jos tullaan tulokset.fxml kautta
        else {
            // Napit vastaamaan paremmin tilannetta
            saveButton.setText("Takaisin");
            removeButton.setText("Takaisin");

            // Yksityis palvelupisteet erotettuna yritys palvelupisteistä
            for (int i = 0; i < 4; i++) {
                yksPalveluPisteTulokset.add(tulokset.getPalveluPisteTulokset().get(i));
            }
            for (int i = 4; i < 8; i++) {
                yriPalveluPisteTulokset.add(tulokset.getPalveluPisteTulokset().get(i));
            }
        }

        // Palvelupiste ArrayListit ObservableListeiksi
        ObservableList<PalvelupisteTulos> oListYriPalvelupisteTulokset = FXCollections
                .observableArrayList(yriPalveluPisteTulokset);
        ObservableList<PalvelupisteTulos> oListYksPalvelupisteTulokset = FXCollections
                .observableArrayList(yksPalveluPisteTulokset);

        // ObservableListit TableVieweihin
        yritysPisteetTable.setItems(oListYriPalvelupisteTulokset);
        yksityisPisteetTable.setItems(oListYksPalvelupisteTulokset);

        // Yritys TableViewin columnit kuntoon
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

        // Yksityis TableViewin columnit kuntoon
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

        // Yleinen informaatio kuntoon
        kestoLabel.setText(tulokset.getKestoString() + "min");
        pProsenttiLabel.setText(tulokset.getPalveluProsenttiString() + "%");
        asMaaraLabel.setText(tulokset.getAsMaaraString());
        palvellutAsLabel.setText(tulokset.getPalvellutAsiakkaatString());
        poistuneetAsLabel.setText(tulokset.getPoistuneetAsiakkaatString());
        uudelleenOhjAsLabel.setText(tulokset.getUudelleenOhjatutAsiakkaatString());
        keskiJonotusAikLabel.setText(tulokset.getKeskiJonotusAikaString() + "min");
        keskiLapiMenoAikLabel.setText(tulokset.getKeskiLapiMenoAikaString() + "min");

        ObservableList<String> pisteetObservableList = FXCollections.observableArrayList("Yksityismyynti: ", "Yksityisnetti: ",
                "Yksityisliittymä: ", "Yksityislaskutus: ", "Yritysmyynti: ", "Yritysnetti: ", "Yritysliittymä: ",
                "Yrityslaskutus: ");
        ObservableList<String> ajatObservableList = FXCollections.observableArrayList("Yksityismyynti: ", "Yksityisnetti: ",
                "Yksityisliittymä: ", "Yksityislaskutus: ", "Yritysmyynti: ", "Yritysnetti: ", "Yritysliittymä: ",
                "Yrityslaskutus: ");
        ObservableList<String> jakaumaObservableList = FXCollections.observableArrayList("Yksityismyynti: ", "Yksityisnetti: ",
                "Yksityisliittymä: ", "Yksityislaskutus: ", "Yritysmyynti: ", "Yritysnetti: ", "Yritysliittymä: ",
                "Yrityslaskutus: ");
        ObservableList<String> miscObservableList = FXCollections.observableArrayList("Simuloinninaika: ", "Yksityis/yritys jakauma: ",
                "Kärsimättömyys aika: ", "Väärävalinta prosentti: ", "Asiakasmäärä tunnissa: ");

        pisteetListView.setItems(pisteetObservableList);
        ajatListView.setItems(ajatObservableList);
        jakaumaListView.setItems(jakaumaObservableList);
        miscListView.setItems(miscObservableList);

        db.closeConnection(); // Putket kiinni ettei data karkaa
    }

    /**
     * Methodi poista nappia varten. Toiminto vaihtelee sen mukaan mistä ollaan
     * tulossa
     * 
     * @author Lassi Bågman
     */
    @FXML
    private void remove() {
        System.out.println(removeButton.getText() + " painettu");
        Stage stage = (Stage) removeButton.getScene().getWindow();

        // NewSimulationGUI.fxml kautta tullessa ilmoitetaan sen controllerille
        // tarvittava tieto
        if (useSS) {
            controller.popupOpen(false);
        }
        stage.close();
    }

    /**
     * Methodi tallenna nappia varten. Toiminto vaihtelee sen mukaan mistä ollaan
     * tulossa
     * 
     * @author Lassi Bågman
     */
    @FXML
    private void save() {
        System.out.println(saveButton.getText() + " painettu");
        Stage stage = (Stage) removeButton.getScene().getWindow();

        // NewSimulationGUI.fxml kautta tullessa tallennetaan tiedot databaseen
        if (useSS) {
            saveToDatabase();
            controller.popupOpen(false);
        }
        stage.close();
    }

    /**
     * Methodi varmistaa että yhteys databaseen on auki ja tallentaa tulokset
     * databaseen
     * 
     * @author Lassi Bågman
     */
    private void saveToDatabase() {
        db.openConnection();
        db.addTulos(tulokset);
        db.closeConnection();
    }

    /**
     * Välittää simulaation datan
     * 
     * @param sd SimulaatioData
     * @author Lassi Bågman
     */
    public void setSimulaationData(SimulaatioData sd) {
        this.sd = sd;
        useSS = true;
    }

    /**
     * Välittää tulokset
     * 
     * @param tulokset Tulokset
     * @author Lassi Bågman
     */
    public void setTulokset(Tulokset tulokset) {
        this.tulokset = tulokset;
        useSS = false;
    }

    /**
     * Välittää kontrollerin jotta sille voidaan antaa komentoja
     * 
     * @param nSc NewSimulationController
     */
    public void setSimulationController(NewSimulationController nSc) {
        controller = nSc;
    }
}
