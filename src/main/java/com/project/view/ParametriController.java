package com.project.view;

import java.text.DecimalFormat;

import java.util.function.UnaryOperator;

import com.project.simu.model.Parametrit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

/**
 * It's a controller class for a JavaFX GUI.
 * 
 * @author Jonne Borgman
 */

public class ParametriController {

    private NewSimulationController controller;
    private boolean canSave = false;
    private Parametrit userParametrit;
    private DecimalFormat dF;
    private UnaryOperator<Change> integerFilter;
    private IntegerStringConverter intConverter;

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
    private TextField myyntiPpJakauma;
    @FXML
    private TextField nettiPpJakauma;
    @FXML
    private TextField liittymäPpJakauma;
    @FXML
    private TextField laskutusPpJakauma;
    @FXML
    private Label YksityisJakaumaProsentti;
    @FXML
    private TextField myyntiAikaField;
    @FXML
    private TextField nettiAikaField;
    @FXML
    private TextField liittymäAikaField;
    @FXML
    private TextField laskutusAikaField;
    @FXML
    private TextField myyntiYritysPpJakauma;
    @FXML
    private TextField nettiYritysPpJakauma;
    @FXML
    private TextField liittymäYritysPpJakauma;
    @FXML
    private TextField laskutusYritysPpJakauma;
    @FXML
    private Label YritysJakaumaProsentti;

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

    @FXML
    private void initialize() {
        canSave = true;

        dF = new DecimalFormat("#0");

        integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("^([0-9])*$")) {
                return change;
            }
            return null;
        };
        intConverter = new IntegerStringConverter() {
            @Override
            public Integer fromString(String s) {
                if (s.isEmpty())
                    return 0;
                return super.fromString(s);
            }
        };
    }

    public void setSimulationController(NewSimulationController nSc) {
        controller = nSc;
        userParametrit = controller.getParametri();
        startValuet();
        onSliderChanged();
        onTextChanged();
    }

    @FXML
    private void onTextChanged() {
        textFieldCheck();
    }

    @FXML
    private void onSliderChanged() {
        // Simulaattorin asetukset.
        setJakauma(jakaumaSlider, jakaumaText);
    }

    /**
     * It checks if the all of the textfields contain numbers and if they do, it
     * sets canSave to true.
     * If textfields contains anything else than numbers, sets canSave to false.
     * 
     * @author Jonne Borgman
     */
    @FXML
    private String textFieldCheck() {
        canSave = false;
        int priyhteensä = getTextFieldInt(myyntiPpJakauma, 0) + getTextFieldInt(nettiPpJakauma, 0)
                + getTextFieldInt(liittymäPpJakauma, 0) + getTextFieldInt(laskutusPpJakauma, 0);
        YksityisJakaumaProsentti.setText(String.valueOf(priyhteensä));
        int coyhteensä = getTextFieldInt(myyntiYritysPpJakauma, 0) + getTextFieldInt(nettiYritysPpJakauma, 0)
                + getTextFieldInt(liittymäYritysPpJakauma, 0) + getTextFieldInt(laskutusYritysPpJakauma, 0);
        YritysJakaumaProsentti.setText(String.valueOf(coyhteensä));

        if (getTextFieldInt(simuloinninAikaField, 1) > 12) {
            return "Simulointiaika:" + Integer.parseInt(simuloinninAikaField.getText())
                    + ", max arvo 12h.";
        }

        if (getTextFieldInt(väärävalintaProsenttiField, 0) > 100) {
            return "Väärävalintaprosentti:" + Integer.parseInt(väärävalintaProsenttiField.getText())
                    + ", max arvo 100%.";
        }

        if (coyhteensä > 100) {
            // YritysJakaumaProsentti.setText("100");
            return "Yrityspalvelupisteiden jakauma on: " + coyhteensä + ", aseta arvo 100%";
        } else if (coyhteensä < 100) {
            return "Yrityspalvelupisteiden jakauma on: " + coyhteensä + ", aseta arvo 100%";
        }

        if (priyhteensä > 100) {
            // YksityisJakaumaProsentti.setText("100");
            return "Henkilöpalveluspisteiden jakauma on: " + priyhteensä + ", aseta arvo 100%";
        } else if (priyhteensä < 100) {
            return "Henkilöpalvelupisteiden jakauma on: " + priyhteensä + ", aseta arvo 100%";
        }

        if (getTextFieldInt(myyntiAikaField, 1) <= 0
                || getTextFieldInt(nettiAikaField, 1) <= 0
                || getTextFieldInt(liittymäAikaField, 1) <= 0
                || getTextFieldInt(laskutusAikaField, 1) <= 0
                || getTextFieldInt(YritysmyyntiAikaField, 1) <= 0
                || getTextFieldInt(YritysnettiAikaField, 1) <= 0
                || getTextFieldInt(YritysliittymäAikaField, 1) <= 0
                || getTextFieldInt(YrityslaskutusAikaField, 1) <= 0
                || getTextFieldInt(myyntiAikaField, 1) >= 10000
                || getTextFieldInt(nettiAikaField, 1) >= 10000
                || getTextFieldInt(liittymäAikaField, 1) >= 10000
                || getTextFieldInt(laskutusAikaField, 1) >= 10000
                || getTextFieldInt(YritysmyyntiAikaField, 1) >= 10000
                || getTextFieldInt(YritysnettiAikaField, 1) >= 10000
                || getTextFieldInt(YritysliittymäAikaField, 1) >= 10000
                || getTextFieldInt(YrityslaskutusAikaField, 1) >= 10000) {
            return "Palveluajoissa virhe.";
        }

        if (priyhteensä == 100 && coyhteensä == 100) {
            canSave = true;
        }

        return null;

    }

    /**
     * If the user has entered valid values, save them and close the window
     *
     * @author Jonne Borgman
     */

    @FXML
    private void tallenna() {
        // Check that all values are ok.
        // onTextChanged();
        canSave = false;
        textFieldCheck();
        if (canSave) {
            saveValues();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            controller.popupOpen(false);
            controller.setParametri(userParametrit);
            stage.close();
        } else {
            Alert alert = new Alert(AlertType.ERROR, textFieldCheck(),
                    ButtonType.CLOSE);
            alert.setTitle("Parametrit väärin!");
            alert.setHeaderText("Virhe");
            alert.showAndWait();
        }
    }

    /**
     * Closes the window.
     *
     * @author Jonne Borgman
     */
    @FXML
    private void peruuta() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        controller.popupOpen(false);
        stage.close();
    }

    /**
     * Gets all values from the singleton, and sets them for the right fields.
     * 
     * @author Jonne Borgman
     */
    private void startValuet() {
        // Yksityispuolen asetukset.
        setPalvelupisteArvot(myyntiPalvelupisteet, myyntiPpKpl, myyntiAikaField, 1, myyntiPpJakauma);
        setPalvelupisteArvot(nettiPalvelupisteet, nettiPpKpl, nettiAikaField, 2, nettiPpJakauma);
        setPalvelupisteArvot(liittymäPalvelupisteet, liittymäPpKpl, liittymäAikaField, 3, liittymäPpJakauma);
        setPalvelupisteArvot(laskutusPalvelupisteet, laskutusPpKpl, laskutusAikaField, 4, laskutusPpJakauma);
        // Yrityspuolen asetukset.
        setPalvelupisteArvot(YritysmyyntiPp, YritysmyyntiPpKpl, YritysmyyntiAikaField, 5, myyntiYritysPpJakauma);
        setPalvelupisteArvot(YritysnettiPp, YritysnettiPpKpl, YritysnettiAikaField, 6, nettiYritysPpJakauma);
        setPalvelupisteArvot(YritysliittymäPp, YritysliittymäPpKpl, YritysliittymäAikaField, 7,
                liittymäYritysPpJakauma);
        setPalvelupisteArvot(YrityslaskutusPp, YrityslaskutusPpKpl, YrityslaskutusAikaField, 8,
                laskutusYritysPpJakauma);

        setTextFields();

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

        // Simulaation asetukset.
        simuloinninAikaField.setText(dF.format(userParametrit.getSimulaationAika()));
        jakaumaSlider.setValue(3);
        kärsimättömyysAikaField.setText(dF.format(userParametrit.getMaxJononPituus() / 60));
        väärävalintaProsenttiField.setText(dF.format(userParametrit.getReRouteChance()));
        asiakasMääräField.setText(dF.format(userParametrit.getAsMaara()));

        String jakaumaSaved = Double.toString(userParametrit.getAsTyyppiJakauma());

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
     * It saves the values from the GUI to the simulation.
     * 
     * @author Jonne Borgman
     */
    private void saveValues() {
        userParametrit.setPPMaaraArr(new int[] { (int) myyntiPalvelupisteet.getValue(),
                (int) nettiPalvelupisteet.getValue(),
                (int) liittymäPalvelupisteet.getValue(),
                (int) laskutusPalvelupisteet.getValue(),
                (int) YritysmyyntiPp.getValue(),
                (int) YritysnettiPp.getValue(),
                (int) YritysliittymäPp.getValue(),
                (int) YrityslaskutusPp.getValue(), 1, 1, 1 });
        userParametrit.setPPAvgAikaArr(new double[] { Double.parseDouble(myyntiAikaField.getText()) * 60,
                Double.parseDouble(nettiAikaField.getText()) * 60,
                Double.parseDouble(liittymäAikaField.getText()) * 60,
                Double.parseDouble(laskutusAikaField.getText()) * 60,
                Double.parseDouble(YritysmyyntiAikaField.getText()) * 60,
                Double.parseDouble(YritysnettiAikaField.getText()) * 60,
                Double.parseDouble(YritysliittymäAikaField.getText()) * 60,
                Double.parseDouble(YrityslaskutusAikaField.getText()) * 60, userParametrit.getPValikkoAika(),
                userParametrit.getPValikkoAika(),
                userParametrit.getPValikkoAika() });
        userParametrit.setAsTyyppiParametri(new double[] { Double.parseDouble(myyntiPpJakauma.getText()),
                Double.parseDouble(nettiPpJakauma.getText()),
                Double.parseDouble(liittymäPpJakauma.getText()),
                Double.parseDouble(laskutusPpJakauma.getText()),
                Double.parseDouble(myyntiYritysPpJakauma.getText()),
                Double.parseDouble(nettiYritysPpJakauma.getText()),
                Double.parseDouble(liittymäYritysPpJakauma.getText()),
                Double.parseDouble(laskutusYritysPpJakauma.getText()) });
        userParametrit.setSimulaationAika(Double.parseDouble(simuloinninAikaField.getText()));
        userParametrit.setAsTyyppiJakauma(jakauma);
        userParametrit.setMaxJononPituus(Double.parseDouble(kärsimättömyysAikaField.getText()) * 60);
        userParametrit.setReRouteChance(Double.parseDouble(väärävalintaProsenttiField.getText()));
        userParametrit.setAsMaara(Double.parseDouble(asiakasMääräField.getText()));
    }

    /**
     * When the slider value changes, the label text changes to the corresponding
     * value and the jakauma variable is set to the corresponding value.
     * 
     * 
     * The jakauma variable is used to calculate the probability for what type of
     * customers enter the simulation.
     * 
     * @param s Slider
     * @param l Label that shows the current value of the slider
     * 
     * @author Jonne Borgman
     */

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

    /**
     * Methodi arvojen hakuun UserPreferences Singletonista
     * 
     * @author Rasmus Hyyppä
     */
    private void setPalvelupisteArvot(Slider s, Label l, TextField ppAikaField, int ppType, TextField ppJakaumaField) {
        s.setValue(userParametrit.getPPMaara(ppType));
        l.setText(userParametrit.getPPMaara(ppType) + " kpl");
        ppAikaField.setText(dF.format((userParametrit.getPPAvgAika(ppType) / 60)));
        ppJakaumaField.setText(dF.format(userParametrit.getAsTyyppiParametri(ppType)));
    }

    private void setTextFieldListener(TextField tF, int minVal) {
        TextFormatter<Integer> textFormat = new TextFormatter<Integer>(intConverter,
                (int) Double.parseDouble(tF.getText()),
                integerFilter);
        tF.setTextFormatter(textFormat);
        tF.textFormatterProperty().addListener((observable, oldValue, newValue) -> getTextFieldInt(tF, minVal));
    }

    private int getTextFieldInt(TextField tF, int minInt) {
        try {
            return Integer.parseInt(tF.getText());
        } catch (NumberFormatException e) {
            tF.setText("");
            return minInt;
        }
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

    private void setTextFields() {
        // Yksityis
        setTextFieldListener(myyntiAikaField, 1);
        setTextFieldListener(nettiAikaField, 1);
        setTextFieldListener(liittymäAikaField, 1);
        setTextFieldListener(laskutusAikaField, 1);
        // Yritys
        setTextFieldListener(YritysmyyntiAikaField, 1);
        setTextFieldListener(YritysnettiAikaField, 1);
        setTextFieldListener(YritysliittymäAikaField, 1);
        setTextFieldListener(YrityslaskutusAikaField, 1);
        // Simulaattorin asetukset
        setTextFieldListener(simuloinninAikaField, 1);
        setTextFieldListener(kärsimättömyysAikaField, 1);
        setTextFieldListener(väärävalintaProsenttiField, 1);
        setTextFieldListener(asiakasMääräField, 1);
        // Palvelupiste jakaumat
        setTextFieldListener(myyntiPpJakauma, 0);
        setTextFieldListener(nettiPpJakauma, 0);
        setTextFieldListener(liittymäPpJakauma, 0);
        setTextFieldListener(laskutusPpJakauma, 0);
        setTextFieldListener(myyntiYritysPpJakauma, 0);
        setTextFieldListener(nettiYritysPpJakauma, 0);
        setTextFieldListener(liittymäYritysPpJakauma, 0);
        setTextFieldListener(laskutusYritysPpJakauma, 0);
    }

}