package com.project.view;

import java.text.DecimalFormat;
import com.project.simu.model.UserParametrit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SuureetController {

    private NewSimulationController controller = new NewSimulationController();
    private boolean canSave = false;
    UserParametrit userParametrit = UserParametrit.getInstance();

    @FXML
    private Button closeButton;
    @FXML
    private Button saveButton;

    // Simulaattorin asetukset.

    @FXML
    private TextField simuloinninAikaField;
    @FXML
    private TextField simuloinninViiveField;
    @FXML
    private TextField kärsimättömyysAikaField;
    @FXML
    private TextField väärävalintaProsenttiField;
    @FXML
    private TextField asiakasMääräField;

    // Yksityispuolen asetukset.

    @FXML
    private Slider myyntiPalvelupisteet;
    @FXML
    private Slider nettiPalvelupisteet;
    @FXML
    private Slider liittymäPalvelupisteet;
    @FXML
    private Slider laskutusPalvelupisteet;
    @FXML
    private Label myynti;
    @FXML
    private Label netti;
    @FXML
    private Label liittymä;
    @FXML
    private Label laskutus;
    @FXML
    private TextField myyntiAikaField;
    @FXML
    private TextField nettiAikaField;
    @FXML
    private TextField liittymäAikaField;
    @FXML
    private TextField laskutusAikaField;

    // Yrityspuolen asetukset.

    @FXML
    private Slider YritysmyyntiPp;
    @FXML
    private Slider YritysnettiPp;
    @FXML
    private Slider YritysliittymäPp;
    @FXML
    private Slider YrityslaskutusPp;
    @FXML
    private Label Yritysmyynti;
    @FXML
    private Label Yritysnetti;
    @FXML
    private Label Yritysliittymä;
    @FXML
    private Label Yrityslaskutus;
    @FXML
    private TextField YritysmyyntiAikaField;
    @FXML
    private TextField YritysnettiAikaField;
    @FXML
    private TextField YritysliittymäAikaField;
    @FXML
    private TextField YrityslaskutusAikaField;

    private UserParametrit uP = UserParametrit.getInstance();
    private DecimalFormat dF;

    @FXML
    private void initialize() {
        startValuet();
        onSliderChanged();
    }

    @FXML
    private void onSliderChanged() {

        // Kuuntelioitten asennukset kaikille elementeille

        // Yksityispuolen palvelupiste määrät
        setSliderListener(myyntiPalvelupisteet, myynti);
        setSliderListener(nettiPalvelupisteet, netti);
        setSliderListener(liittymäPalvelupisteet, liittymä);
        setSliderListener(laskutusPalvelupisteet, laskutus);
        // Yrityspuolen palvelupiste määrät
        setSliderListener(YritysmyyntiPp, Yritysmyynti);
        setSliderListener(YritysnettiPp, Yritysnetti);
        setSliderListener(YritysliittymäPp, Yritysliittymä);
        setSliderListener(YrityslaskutusPp, Yrityslaskutus);

        // Yksityis
        setTextFieldListener(myyntiAikaField);
        setTextFieldListener(nettiAikaField);
        setTextFieldListener(liittymäAikaField);
        setTextFieldListener(laskutusAikaField);
        // Yritys
        setTextFieldListener(YritysmyyntiAikaField);
        setTextFieldListener(YritysnettiAikaField);
        setTextFieldListener(YritysliittymäAikaField);
        setTextFieldListener(YrityslaskutusAikaField);
        // Simulaattorin asetukset
        setTextFieldListener(simuloinninAikaField);
        setTextFieldListener(simuloinninViiveField);
        setTextFieldListener(kärsimättömyysAikaField);
        setTextFieldListener(väärävalintaProsenttiField);
        setTextFieldListener(asiakasMääräField);
    }

    @FXML
    private void textFieldCheck() {
        try {
            Double.parseDouble(myyntiAikaField.getText());
            Double.parseDouble(nettiAikaField.getText());
            Double.parseDouble(liittymäAikaField.getText());
            Double.parseDouble(laskutusAikaField.getText());
            Double.parseDouble(YritysmyyntiAikaField.getText());
            Double.parseDouble(YritysnettiAikaField.getText());
            Double.parseDouble(YritysliittymäAikaField.getText());
            Double.parseDouble(YrityslaskutusAikaField.getText());
            Double.parseDouble(simuloinninAikaField.getText());
            Long.parseLong(simuloinninViiveField.getText());
            Integer.parseInt(kärsimättömyysAikaField.getText());
            Double.parseDouble(väärävalintaProsenttiField.getText());
            Double.parseDouble(asiakasMääräField.getText());
            canSave = true;
        } catch (NumberFormatException e) {
            canSave = false;
        }
    }

    @FXML
    private void tallenna() {
        // Check that all values are ok.
        if (canSave) {
            saveValues();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            controller.popupOpen(false);
            stage.close();
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Anna vain numeerista tietoa.", ButtonType.CLOSE);
            alert.setTitle("Parametrit väärin!");
            alert.setHeaderText("Virheellinen aika!");
            alert.showAndWait();
        }
    }

    @FXML
    private void peruuta() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        controller.popupOpen(false);
        stage.close();
    }

    private void startValuet() {
        canSave = true;
        dF = new DecimalFormat("#0");

        // Yksityispuolen asetukset.
        setPPArvotSingletonista(myyntiPalvelupisteet, myynti, myyntiAikaField, 1);
        setPPArvotSingletonista(nettiPalvelupisteet, netti, nettiAikaField, 2);
        setPPArvotSingletonista(liittymäPalvelupisteet, liittymä, liittymäAikaField, 3);
        setPPArvotSingletonista(laskutusPalvelupisteet, laskutus, laskutusAikaField, 4);
        // Yrityspuolen asetukset.
        setPPArvotSingletonista(YritysmyyntiPp, Yritysmyynti, YritysmyyntiAikaField, 5);
        setPPArvotSingletonista(YritysnettiPp, Yritysnetti, YritysnettiAikaField, 6);
        setPPArvotSingletonista(YritysliittymäPp, Yritysliittymä, YritysliittymäAikaField, 7);
        setPPArvotSingletonista(YrityslaskutusPp, Yrityslaskutus, YrityslaskutusAikaField, 8);

        // Simulaation asetukset.
        simuloinninAikaField.setText(dF.format(uP.getSimulaationAika()));

        kärsimättömyysAikaField.setText(dF.format(uP.getMaxJononPituus() / 60));
        väärävalintaProsenttiField.setText(dF.format(uP.getVaaraValintaProsentti()));
        asiakasMääräField.setText(dF.format(uP.getAsMaara()));
    }

    /**
     * Methodi arvojen hakuun UserPreferences Singletonista
     * 
     * @author Rasmus Hyyppä
     */
    private void setPPArvotSingletonista(Slider s, Label l, TextField tF, int ppType) {
        s.setValue(uP.getPPMaara(ppType));
        l.setText(uP.getPPMaara(ppType) + " kpl");
        tF.setText(dF.format(uP.getPPAvgAika(ppType) / 60));
    }

    private void saveValues() {
        uP.setPPMaara((int) myyntiPalvelupisteet.getValue(), 1);
        uP.setPPMaara((int) nettiPalvelupisteet.getValue(), 2);
        uP.setPPMaara((int) liittymäPalvelupisteet.getValue(), 3);
        uP.setPPMaara((int) laskutusPalvelupisteet.getValue(), 4);
        uP.setPPMaara((int) YritysmyyntiPp.getValue(), 5);
        uP.setPPMaara((int) YritysnettiPp.getValue(), 6);
        uP.setPPMaara((int) YritysliittymäPp.getValue(), 7);
        uP.setPPMaara((int) YrityslaskutusPp.getValue(), 8);
        uP.setPPAvgAika(Double.parseDouble(myyntiAikaField.getText()), 1);
        uP.setPPAvgAika(Double.parseDouble(nettiAikaField.getText()), 2);
        uP.setPPAvgAika(Double.parseDouble(liittymäAikaField.getText()), 3);
        uP.setPPAvgAika(Double.parseDouble(laskutusAikaField.getText()), 4);
        uP.setPPAvgAika(Double.parseDouble(YritysmyyntiAikaField.getText()), 5);
        uP.setPPAvgAika(Double.parseDouble(YritysnettiAikaField.getText()), 6);
        uP.setPPAvgAika(Double.parseDouble(YritysliittymäAikaField.getText()), 7);
        uP.setPPAvgAika(Double.parseDouble(YrityslaskutusAikaField.getText()), 8);
        uP.setSimulaationAika(Double.parseDouble(simuloinninAikaField.getText()));

        uP.setMaxJononPituus(Double.parseDouble(kärsimättömyysAikaField.getText()) * 60);
        uP.setVaaraValintaProsentti(Double.parseDouble(väärävalintaProsenttiField.getText()));
        uP.setAsMaara(Double.parseDouble(asiakasMääräField.getText()));
    }

    /**
     * Methodi Slider & Label kuuntelija toiminnolle
     * 
     * @author Rasmus Hyyppä
     */
    private void setSliderListener(Slider s, Label l) {
        s.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                l.setText(newValue.intValue() + " kpl");
            }
        });
    }

    /**
     * Methodi textfield kuuntelijalle, jokainen input aiheuttaa textFieldCheckin()
     * 
     * @author Rasmus Hyyppä
     */
    private void setTextFieldListener(TextField tF) {
        tF.textProperty().addListener((observable, oldValue, newValue) -> textFieldCheck());
    }

}