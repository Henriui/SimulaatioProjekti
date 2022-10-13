package com.project.view;

import java.util.ArrayList;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.PalvelupisteTulos;
import com.project.simu.model.Parametrit;
import com.project.simu.model.SimulaatioData;
import com.project.simu.model.TallennettavatParametrit;
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
import javafx.scene.control.TextArea;
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
    private TextArea ohjTextArea;
    @FXML
    private Button removeButton;
    @FXML
    private Button saveButton;

    private NewSimulationController controller;
    private ITuloksetDAO db;
    private SimulaatioData sd;
    private Tulokset tulokset;
    private Parametrit parametrit;
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
            int r = db.getRowCount() + 1; // Haetaan simulaatiokertojen määrä ja lisätään 1 mahdollista
                                          // tulevaa
                                          // tallennusta varten

            // Palvelupiste data simulaatio datasta
            for (int i = 1; i < 9; i++) {
                palveluPisteTulokset
                        .add(new PalvelupisteTulos(i, r, i, sd.getPalveluMaara(i),
                                sd.getJonoAika(i),
                                sd.getPalveluAika(i), sd.getPalveluProsentti(i)));
            }

            TallennettavatParametrit tallennettavatParametrit = new TallennettavatParametrit(r,
                    parametrit.getPPMaara(1), parametrit.getPPMaara(2), parametrit.getPPMaara(3),
                    parametrit.getPPMaara(4), parametrit.getPPMaara(5), parametrit.getPPMaara(6),
                    parametrit.getPPMaara(7), parametrit.getPPMaara(8), parametrit.getPPAvgAika(1),
                    parametrit.getPPAvgAika(2), parametrit.getPPAvgAika(3),
                    parametrit.getPPAvgAika(4), parametrit.getPPAvgAika(5),
                    parametrit.getPPAvgAika(6), parametrit.getPPAvgAika(7),
                    parametrit.getPPAvgAika(8), parametrit.getAsTyyppiParametri(1),
                    parametrit.getAsTyyppiParametri(2), parametrit.getAsTyyppiParametri(3),
                    parametrit.getAsTyyppiParametri(4), parametrit.getAsTyyppiParametri(5),
                    parametrit.getAsTyyppiParametri(6), parametrit.getAsTyyppiParametri(7),
                    parametrit.getAsTyyppiParametri(8),
                    parametrit.getSimulaationAika(), parametrit.getAsTyyppiJakauma(),
                    parametrit.getMaxJononPituus(), parametrit.getReRouteChance(),
                    parametrit.getAsMaara());

            // Tulokset simulaatio datasta
            tulokset = new Tulokset(sd.getSimulointiAika(), sd.getPalveluprosentti(),
                    ((int) sd.getAsTotalMaara()), sd.getAsPalveltu(), sd.getAsReRouted(),
                    sd.getAsPoistunut(), sd.getJonotusATotal(), sd.getAvgAsAikaSim(),
                    palveluPisteTulokset, tallennettavatParametrit);

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
            removeButton.setText("Takaisin");
            saveButton.setDisable(true);
            saveButton.setVisible(false);

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

        // ObservableListit simulaatiossa käytetyistä asetuksista
        ObservableList<String> pisteetObservableList = FXCollections.observableArrayList(
                "Yksityismyynti: " + tulokset.getTallennettavatParametrit().getYksMyyntiPisteitaString() + "kpl",
                "Yksityisnetti: " + tulokset.getTallennettavatParametrit().getYksNettiPisteitaString() + "kpl",
                "Yksityisliittymä: " + tulokset.getTallennettavatParametrit().getYksLiittymaPisteitaString() + "kpl",
                "Yksityislaskutus: " + tulokset.getTallennettavatParametrit().getYksLaskutusPisteitaString() + "kpl",
                "Yritysmyynti: " + tulokset.getTallennettavatParametrit().getYriMyyntiPisteitaString() + "kpl",
                "Yritysnetti: " + tulokset.getTallennettavatParametrit().getYriNettiPisteitaString() + "kpl",
                "Yritysliittymä: " + tulokset.getTallennettavatParametrit().getYriLiittymaPisteitaString() + "kpl",
                "Yrityslaskutus: " + tulokset.getTallennettavatParametrit().getYriLaskutusPisteitaString() + "kpl");
        ObservableList<String> ajatObservableList = FXCollections.observableArrayList(
                "Yksityismyynti: " + tulokset.getTallennettavatParametrit().getYksMyyntiAikaString() + "min",
                "Yksityisnetti: " + tulokset.getTallennettavatParametrit().getYksNettiAikaString() + "min",
                "Yksityisliittymä: " + tulokset.getTallennettavatParametrit().getYksLiittymaAikaString() + "min",
                "Yksityislaskutus: " + tulokset.getTallennettavatParametrit().getYksLaskutusAikaString() + "min",
                "Yritysmyynti: " + tulokset.getTallennettavatParametrit().getYriMyyntiAikaString() + "min",
                "Yritysnetti: " + tulokset.getTallennettavatParametrit().getYriNettiAikaString() + "min",
                "Yritysliittymä: " + tulokset.getTallennettavatParametrit().getYriLiittymaAikaString() + "min",
                "Yrityslaskutus: " + tulokset.getTallennettavatParametrit().getYriLaskutusAikaString() + "min");
        ObservableList<String> jakaumaObservableList = FXCollections.observableArrayList(
                "Yksityismyynti: " + tulokset.getTallennettavatParametrit().getYksMyyntiJakaumaString() + "%",
                "Yksityisnetti: " + tulokset.getTallennettavatParametrit().getYksNettiJakaumaString() + "%",
                "Yksityisliittymä: " + tulokset.getTallennettavatParametrit().getYksLiittymaJakaumaString() + "%",
                "Yksityislaskutus: " + tulokset.getTallennettavatParametrit().getYksLaskutusJakaumaString() + "%",
                "Yritysmyynti: " + tulokset.getTallennettavatParametrit().getYriMyyntiJakaumaString() + "%",
                "Yritysnetti: " + tulokset.getTallennettavatParametrit().getYriNettiJakaumaString() + "%",
                "Yritysliittymä: " + tulokset.getTallennettavatParametrit().getYriLiittymaJakaumaString() + "%",
                "Yrityslaskutus: " + tulokset.getTallennettavatParametrit().getYriLaskutusJakaumaString() + "%");
        ObservableList<String> miscObservableList = FXCollections.observableArrayList(
                "Simuloinninaika: " + tulokset.getTallennettavatParametrit().getSimuloinninAikaString() + "h",
                "Yksityis/yritys jakauma: " + tulokset.getTallennettavatParametrit().getYksYriJakaumaString() + "/"
                        + (int) (100 - tulokset.getTallennettavatParametrit().getYksYriJakauma()) + "%",
                "Kärsimättömyys aika: " + tulokset.getTallennettavatParametrit().getKarsimaatomyysAikaString() + "min",
                "Väärävalinta prosentti: " + tulokset.getTallennettavatParametrit().getVaaraValintaProsenttiString()
                        + "%",
                "Asiakasmäärä tunnissa: " + tulokset.getTallennettavatParametrit().getAsikasmaaraTuntiString()
                        + "kpl/h");

        pisteetListView.setItems(pisteetObservableList);
        ajatListView.setItems(ajatObservableList);
        jakaumaListView.setItems(jakaumaObservableList);
        miscListView.setItems(miscObservableList);

        String ohje = "";
        boolean lisaa = false;
        boolean vahemman = false;
        int tavoiteAlin = 85;
        int tavoiteYlin = 90;
        for (int i = 0; i < 8; i++) {
            if (tulokset.getPalveluPisteTulokset().get(i).getPalveluProsentti() < tavoiteAlin) {
                lisaa = true;
            }
        }
        for (int i = 0; i < 8; i++) {
            if (tulokset.getPalveluPisteTulokset().get(i).getPalveluProsentti() > tavoiteYlin) {
                vahemman = true;
            }
        }
        if (lisaa) {
            ohje += "Tarvitset lisää ";
            ArrayList<String> tarvYksPisteet = new ArrayList<String>();
            ArrayList<String> tarvYriPisteet = new ArrayList<String>();
            for (int i = 0; i < 4; i++) {
                if (tulokset.getPalveluPisteTulokset().get(i).getPalveluProsentti() < tavoiteAlin) {
                    tarvYksPisteet.add(tulokset.getPalveluPisteTulokset().get(i).getTyyppiStringPieni());
                }
            }
            for (int i = 4; i < 8; i++) {
                if (tulokset.getPalveluPisteTulokset().get(i).getPalveluProsentti() < tavoiteAlin) {
                    tarvYriPisteet.add(tulokset.getPalveluPisteTulokset().get(i).getTyyppiStringPieni());
                }
            }
            if (tarvYksPisteet.size() > 0) {
                ohje += "yksityis: ";
                if (tarvYksPisteet.size() == 1) {
                    ohje += tarvYksPisteet.get(0) + " pisteitä";
                } else if (tarvYksPisteet.size() == 2) {
                    int u;
                    for (u = 0; u < tarvYksPisteet.size() - 1; u++) {
                        ohje += tarvYksPisteet.get(u) + " ja ";
                    }
                    ohje += tarvYksPisteet.get(u) + " pisteitä";
                } else if (tarvYksPisteet.size() > 2) {
                    int u;
                    for (u = 0; u < tarvYksPisteet.size() - 2; u++) {
                        ohje += tarvYksPisteet.get(u) + ", ";
                    }
                    ohje += tarvYksPisteet.get(u) + " ja " + tarvYksPisteet.get(u + 1) + " pisteitä";
                }
            }
            if (tarvYksPisteet.size() > 0 && tarvYriPisteet.size() > 0) {
                ohje += " sekä ";
            }
            if (tarvYriPisteet.size() > 0) {
                ohje += "yritys: ";
                if (tarvYriPisteet.size() == 1) {
                    ohje += tarvYriPisteet.get(0) + " pisteitä";
                } else if (tarvYriPisteet.size() > 1) {
                    int u;
                    for (u = 0; u < tarvYriPisteet.size() - 1; u++) {
                        ohje += tarvYriPisteet.get(u) + " ja ";
                    }
                    ohje += tarvYriPisteet.get(u) + " pisteitä";
                } else if (tarvYriPisteet.size() > 2) {
                    int u;
                    for (u = 0; u < tarvYriPisteet.size() - 2; u++) {
                        ohje += tarvYriPisteet.get(u) + ", ";
                    }
                    ohje += tarvYriPisteet.get(u) + " ja " + tarvYriPisteet.get(u + 1) + " pisteitä";
                }
            }
            ohje += ".";
        }
        if (lisaa && vahemman) {
            ohje += "\n\n";
        }
        if (vahemman) {
            ohje += "Voit vähentää ";
            ArrayList<String> vahYksPisteet = new ArrayList<String>();
            ArrayList<String> vahYriPisteet = new ArrayList<String>();
            for (int i = 0; i < 4; i++) {
                if (tulokset.getPalveluPisteTulokset().get(i).getPalveluProsentti() > tavoiteYlin) {
                    vahYksPisteet.add(tulokset.getPalveluPisteTulokset().get(i).getTyyppiStringPieni());
                }
            }
            for (int i = 4; i < 8; i++) {
                if (tulokset.getPalveluPisteTulokset().get(i).getPalveluProsentti() > tavoiteYlin) {
                    vahYriPisteet.add(tulokset.getPalveluPisteTulokset().get(i).getTyyppiStringPieni());
                }
            }
            if (vahYksPisteet.size() > 0) {
                ohje += "yksityis: ";
                if (vahYksPisteet.size() == 1) {
                    ohje += vahYksPisteet.get(0) + " pisteitä";
                } else if (vahYksPisteet.size() == 2) {
                    int u;
                    for (u = 0; u < vahYksPisteet.size() - 1; u++) {
                        ohje += vahYksPisteet.get(u) + " ja ";
                    }
                    ohje += vahYksPisteet.get(u) + " pisteitä";
                } else if (vahYksPisteet.size() > 2) {
                    int u;
                    for (u = 0; u < vahYksPisteet.size() - 2; u++) {
                        ohje += vahYksPisteet.get(u) + ", ";
                    }
                    ohje += vahYksPisteet.get(u) + " ja " + vahYksPisteet.get(u + 1) + " pisteitä";
                }
            }
            if (vahYksPisteet.size() > 0 && vahYriPisteet.size() > 0) {
                ohje += " sekä ";
            }
            if (vahYriPisteet.size() > 0) {
                ohje += "yritys: ";
                if (vahYriPisteet.size() == 1) {
                    ohje += vahYriPisteet.get(0) + " pisteitä";
                } else if (vahYriPisteet.size() > 1) {
                    int u;
                    for (u = 0; u < vahYriPisteet.size() - 1; u++) {
                        ohje += vahYriPisteet.get(u) + " ja ";
                    }
                    ohje += vahYriPisteet.get(u) + " pisteitä";
                } else if (vahYriPisteet.size() > 2) {
                    int u;
                    for (u = 0; u < vahYriPisteet.size() - 2; u++) {
                        ohje += vahYriPisteet.get(u) + ", ";
                    }
                    ohje += vahYriPisteet.get(u) + " ja " + vahYriPisteet.get(u + 1) + " pisteitä";
                }
            }
            ohje += ".";

            int asiakkaitaJonossa = ((int) sd.getAsTotalMaara() - (sd.getAsPalveltu() + sd.getAsPoistunut()));
            if(asiakkaitaJonossa > 0){
                if(asiakkaitaJonossa == 1){
                    ohje += "\n\nJonoihin jäi yksi asiakas.";
                }
                else{
                    ohje += "\n\nJonoihin jäi " + asiakkaitaJonossa + " asiakasta.";
                }
            }
        }

        ohjTextArea.setText(ohje);
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

    public void setParametrit(Parametrit parametrit) {
        this.parametrit = parametrit;
    }
}
