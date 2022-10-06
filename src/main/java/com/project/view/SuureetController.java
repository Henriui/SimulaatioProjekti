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
    private TextField kärsimättömyysAikaField;
    @FXML
    private Slider jakaumaSlider;
    private Double jakauma;
    @FXML
    private Label jakaumaText;
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
    private Label myyntiPpKpl;
    @FXML
    private Label nettiPpKpl;
    @FXML
    private Label liittymäPpKpl;
    @FXML
    private Label laskutusPpKpl;
    @FXML
    private Slider myyntiPpJakauma;
    @FXML
    private Slider nettiPpJakauma;
    @FXML
    private Slider liittymäPpJakauma;
    @FXML
    private Slider laskutusPpJakauma;
    @FXML
    private Label myyntiJakaumaText;
    @FXML
    private Label nettiJakaumaText;
    @FXML
    private Label liittymäJakaumaText;
    @FXML
    private Label laskutusJakaumaText;
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
    private Label YritysmyyntiPpKpl;
    @FXML
    private Label YritysnettiPpKpl;
    @FXML
    private Label YritysliittymäPpKpl;
    @FXML
    private Label YrityslaskutusPpKpl;
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
        setSliderListener(myyntiPalvelupisteet, myyntiPpKpl);
        setSliderListener(nettiPalvelupisteet, nettiPpKpl);
        setSliderListener(liittymäPalvelupisteet, liittymäPpKpl);
        setSliderListener(laskutusPalvelupisteet, laskutusPpKpl);
        // Yrityspuolen palvelupiste määrät
        setSliderListener(YritysmyyntiPp, YritysmyyntiPpKpl);
        setSliderListener(YritysnettiPp, YritysnettiPpKpl);
        setSliderListener(YritysliittymäPp, YritysliittymäPpKpl);
        setSliderListener(YrityslaskutusPp, YrityslaskutusPpKpl);

        // Yksityis
        setTextFieldListener(myyntiAikaField);
        setTextFieldListener(nettiAikaField);
        setTextFieldListener(liittymäAikaField);
        setTextFieldListener(laskutusAikaField);
        setJakauma(myyntiPpJakauma, myyntiJakaumaText);
        setJakauma(nettiPpJakauma, nettiJakaumaText);
        setJakauma(liittymäPpJakauma, liittymäJakaumaText);
        setJakauma(laskutusPpJakauma, laskutusJakaumaText);
        // Yritys
        setTextFieldListener(YritysmyyntiAikaField);
        setTextFieldListener(YritysnettiAikaField);
        setTextFieldListener(YritysliittymäAikaField);
        setTextFieldListener(YrityslaskutusAikaField);
        // Simulaattorin asetukset
        setTextFieldListener(simuloinninAikaField);
        setTextFieldListener(kärsimättömyysAikaField);
        setTextFieldListener(väärävalintaProsenttiField);
        setTextFieldListener(asiakasMääräField);
        setJakauma(jakaumaSlider, jakaumaText);
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
        setPPArvotSingletonista(myyntiPalvelupisteet, myyntiPpKpl, myyntiAikaField, 1);
        setPPArvotSingletonista(nettiPalvelupisteet, nettiPpKpl, nettiAikaField, 2);
        setPPArvotSingletonista(liittymäPalvelupisteet, liittymäPpKpl, liittymäAikaField, 3);
        setPPArvotSingletonista(laskutusPalvelupisteet, laskutusPpKpl, laskutusAikaField, 4);
        // Yrityspuolen asetukset.
        setPPArvotSingletonista(YritysmyyntiPp, YritysmyyntiPpKpl, YritysmyyntiAikaField, 5);
        setPPArvotSingletonista(YritysnettiPp, YritysnettiPpKpl, YritysnettiAikaField, 6);
        setPPArvotSingletonista(YritysliittymäPp, YritysliittymäPpKpl, YritysliittymäAikaField, 7);
        setPPArvotSingletonista(YrityslaskutusPp, YrityslaskutusPpKpl, YrityslaskutusAikaField, 8);

        // Simulaation asetukset.
        simuloinninAikaField.setText(dF.format(uP.getSimulaationAika()));
        jakaumaSlider.setValue(3);
        kärsimättömyysAikaField.setText(dF.format(uP.getMaxJononPituus() / 60));
        väärävalintaProsenttiField.setText(dF.format(uP.getVaaraValintaProsentti()));
        asiakasMääräField.setText(dF.format(uP.getAsMaara()));

        String jakaumaSaved = Double.toString(uP.getAsTyyppiJakauma());

        switch (jakaumaSaved) {
            case "15.0":
                jakaumaSlider.setValue(1);
                jakaumaText.setText(85 + " / " + 15);
                jakauma = 15.0;
                break;
            case "30.0":
                jakaumaSlider.setValue(2);
                jakaumaText.setText(70 + " / " + 30);
                jakauma = 30.0;
                break;
            case "50.0":
                jakaumaSlider.setValue(3);
                jakaumaText.setText(50 + " / " + 50);
                jakauma = 50.0;
                break;
            case "70.0":
                jakaumaSlider.setValue(4);
                jakaumaText.setText(30 + " / " + 70);
                jakauma = 70.0;
                break;
            case "85.0":
                jakaumaSlider.setValue(5);
                jakaumaText.setText(15 + " / " + 85);
                jakauma = 85.0;
                break;
        }

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
        uP.setAsTyyppiJakauma(jakauma);
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

    private void setJakauma(Slider s, Label l) {
        s.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                switch (newValue.intValue()) {
                    case 1:
                        l.setText(85 + " / " + 15);
                        jakauma = 15.0;
                        break;
                    case 2:
                        l.setText(70 + " / " + 30);
                        jakauma = 30.0;
                        break;
                    case 3:
                        l.setText(50 + " / " + 50);
                        jakauma = 50.0;
                        break;
                    case 4:
                        l.setText(30 + " / " + 70);
                        jakauma = 70.0;
                        break;
                    case 5:
                        l.setText(15 + " / " + 85);
                        jakauma = 85.0;
                        break;
                }
            }
        });
    }

    private void setPpJakauma(Slider s, Label l) {
        s.valueProperty().addListener(new ChangeListener<Number>() 
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) 
                    {
                        int myynti = (int) myyntiPpJakauma.getValue();
                        int netti = (int) nettiPpJakauma.getValue();
                        int liittymä = (int) liittymäPpJakauma.getValue();
                        int laskutus = (int) laskutusPpJakauma.getValue();

                        switch(newValue.intValue()){
                            case 1:
                                l.setText(85 + " / " + 15);
                                jakauma = 0.45;
                                break;
                            case 2:
                                l.setText(70 + " / " + 30);
                                jakauma = 0.4725;
                                break;
                            case 3:
                                l.setText(50 + " / " + 50);
                                jakauma = 0.5;
                                break;
                            case 4:
                                l.setText(30 + " / " + 70);
                                jakauma = 0.5275;
                                break;
                            case 5:
                                l.setText(15 + " / " + 85);
                                jakauma = 0.55;
                                break;
                            default:
                                Alert alert = new Alert(AlertType.ERROR, "Anna vain numeerista tietoa.", ButtonType.CLOSE);
                                alert.setTitle("Parametrit väärin!");
                                alert.setHeaderText("Virheellinen aika!");
                                alert.showAndWait();
                        }
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