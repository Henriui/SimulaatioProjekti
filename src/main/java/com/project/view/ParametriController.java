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
        integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([0-9][0-9]*)")) {
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
        try {
            int primyynti = Integer.parseInt(myyntiPpJakauma.getText());
            int prinetti = Integer.parseInt(nettiPpJakauma.getText());
            int priliittymä = Integer.parseInt(liittymäPpJakauma.getText());
            int prilaskutus = Integer.parseInt(laskutusPpJakauma.getText());
            int priyhteensä = primyynti + prinetti + priliittymä + prilaskutus;

            int comyynti = Integer.parseInt(myyntiYritysPpJakauma.getText());
            int conetti = Integer.parseInt(nettiYritysPpJakauma.getText());
            int coliittymä = Integer.parseInt(liittymäYritysPpJakauma.getText());
            int colaskutus = Integer.parseInt(laskutusYritysPpJakauma.getText());
            int coyhteensä = comyynti + conetti + coliittymä + colaskutus;

            YksityisJakaumaProsentti.setText(String.valueOf(priyhteensä));
            YritysJakaumaProsentti.setText(String.valueOf(coyhteensä));

            canSave = false;

            if (Double.parseDouble(simuloinninAikaField.getText()) > 12) {
                return "Simulointiaika: " + Double.parseDouble(simuloinninAikaField.getText())
                        + ", on yli maksimi limitin 12h.";
            }

            if (Double.parseDouble(väärävalintaProsenttiField.getText()) > 100) {
                return Double.parseDouble(väärävalintaProsenttiField.getText())
                        + " väärävalintaprosentin arvona, aseta max 100%.";
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

            if (priyhteensä == 100 && coyhteensä == 100) {
                canSave = true;
            }

        } catch (NumberFormatException e) {
            myyntiPpJakauma.setText("25");
            nettiPpJakauma.setText("25");
            liittymäPpJakauma.setText("25");
            laskutusPpJakauma.setText("25");
            myyntiYritysPpJakauma.setText("25");
            nettiYritysPpJakauma.setText("25");
            liittymäYritysPpJakauma.setText("25");
            laskutusYritysPpJakauma.setText("25");
            YritysJakaumaProsentti.setText("100");
            YksityisJakaumaProsentti.setText("100");
            Alert alert = new Alert(AlertType.ERROR, "Too many numbers typed reseting input area",
                    ButtonType.CLOSE);
            alert.setTitle("Parametrit väärin!");
            alert.setHeaderText("Virhe");
            alert.showAndWait();
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
        canSave = true;
        dF = new DecimalFormat("#0");

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
        userParametrit.setPPMaara(new int[] { (int) myyntiPalvelupisteet.getValue(),
                (int) nettiPalvelupisteet.getValue(),
                (int) liittymäPalvelupisteet.getValue(),
                (int) laskutusPalvelupisteet.getValue(),
                (int) YritysmyyntiPp.getValue(),
                (int) YritysnettiPp.getValue(),
                (int) YritysliittymäPp.getValue(),
                (int) YrityslaskutusPp.getValue(), 1, 1, 1 });
        userParametrit.setPPAvgAika(new double[] { Double.parseDouble(myyntiAikaField.getText()) * 60,
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
     * value and the
     * jakauma variable is set to the corresponding value.
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
    private void setPalvelupisteArvot(Slider s, Label l, TextField tF, int ppType, TextField ppJakauma) {
        s.setValue(userParametrit.getPPMaara(ppType));
        l.setText(userParametrit.getPPMaara(ppType) + " kpl");
        tF.setText(dF.format((userParametrit.getPPAvgAika(ppType) / 60)));
        ppJakauma.setText(dF.format(userParametrit.getAsTyyppiParametri(ppType)));
    }

    private void setTextFieldListener(TextField tF) {
        TextFormatter<Integer> textFormat = new TextFormatter<Integer>(intConverter,
                (int) Double.parseDouble(tF.getText()),
                integerFilter);
        tF.setTextFormatter(textFormat);
        tF.textFormatterProperty().addListener((observable, oldValue, newValue) -> textFieldCheck());
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
        setTextFieldListener(kärsimättömyysAikaField);
        setTextFieldListener(väärävalintaProsenttiField);
        setTextFieldListener(asiakasMääräField);
        // Palvelupiste jakaumat
        setTextFieldListener(myyntiPpJakauma);
        setTextFieldListener(nettiPpJakauma);
        setTextFieldListener(liittymäPpJakauma);
        setTextFieldListener(laskutusPpJakauma);
        setTextFieldListener(myyntiYritysPpJakauma);
        setTextFieldListener(nettiYritysPpJakauma);
        setTextFieldListener(liittymäYritysPpJakauma);
        setTextFieldListener(laskutusYritysPpJakauma);
    }

}