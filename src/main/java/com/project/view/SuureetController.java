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

/**
 * It's a controller class for a JavaFX GUI.
 * 
 * @author Jonne Borgman
 */

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

    private UserParametrit uP = UserParametrit.getInstance();
    private DecimalFormat dF;

    @FXML
    private void initialize() {
        startValuet();
        onSliderChanged();
        onTextChanged();
    }

    @FXML
    private void onTextChanged() {
        // Yksityis
        setTextFieldListener(myyntiAikaField);
        setTextFieldListener(nettiAikaField);
        setTextFieldListener(liittymäAikaField);
        setTextFieldListener(laskutusAikaField);

        setB2CJakaumaListener(myyntiPpJakauma);
        setB2CJakaumaListener(nettiPpJakauma);
        setB2CJakaumaListener(liittymäPpJakauma);
        setB2CJakaumaListener(laskutusPpJakauma);
        // Yritys
        setTextFieldListener(YritysmyyntiAikaField);
        setTextFieldListener(YritysnettiAikaField);
        setTextFieldListener(YritysliittymäAikaField);
        setTextFieldListener(YrityslaskutusAikaField);

        setB2BJakaumaListener(myyntiYritysPpJakauma);
        setB2BJakaumaListener(nettiYritysPpJakauma);
        setB2BJakaumaListener(liittymäYritysPpJakauma);
        setB2BJakaumaListener(laskutusYritysPpJakauma);

        // Simulaattorin asetukset
        setTextFieldListener(simuloinninAikaField);
        setTextFieldListener(kärsimättömyysAikaField);
        setTextFieldListener(väärävalintaProsenttiField);
        setTextFieldListener(asiakasMääräField);

    }

    /**
     * When the slider is changed, the value of the slider is set to the label.
     *
     * @author Jonne Borgman
     */
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

            Integer.parseInt(myyntiPpJakauma.getText());
            Integer.parseInt(nettiPpJakauma.getText());
            Integer.parseInt(liittymäPpJakauma.getText());
            Integer.parseInt(laskutusPpJakauma.getText());

            Double.parseDouble(simuloinninAikaField.getText());
            Integer.parseInt(kärsimättömyysAikaField.getText());
            Double.parseDouble(väärävalintaProsenttiField.getText());
            Double.parseDouble(asiakasMääräField.getText());
            canSave = true;
        } catch (NumberFormatException e) {
            canSave = false;
        }
    }

    /**
     * If the user has entered valid values, save them and close the window
     *
     * @author Jonne Borgman
     */

    @FXML
    private void tallenna() {
        // Check that all values are ok.
        prosentti();
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
        väärävalintaProsenttiField.setText(dF.format(uP.getReRouteChance()));
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

    /**
     * It saves the values from the GUI to the simulation.
     * 
     * @author Jonne Borgman
     */
    private void saveValues() {
        uP.setPPMaara(new int[] { (int) myyntiPalvelupisteet.getValue(),
                (int) nettiPalvelupisteet.getValue(),
                (int) liittymäPalvelupisteet.getValue(),
                (int) laskutusPalvelupisteet.getValue(),
                (int) YritysmyyntiPp.getValue(),
                (int) YritysnettiPp.getValue(),
                (int) YritysliittymäPp.getValue(),
                (int) YrityslaskutusPp.getValue(), 1, 1, 1 });
        uP.setPPAvgAika(new double[] { Double.parseDouble(myyntiAikaField.getText()) * 60,
                Double.parseDouble(nettiAikaField.getText()) * 60,
                Double.parseDouble(liittymäAikaField.getText()) * 60,
                Double.parseDouble(laskutusAikaField.getText()) * 60,
                Double.parseDouble(YritysmyyntiAikaField.getText()) * 60,
                Double.parseDouble(YritysnettiAikaField.getText()) * 60,
                Double.parseDouble(YritysliittymäAikaField.getText()) * 60,
                Double.parseDouble(YrityslaskutusAikaField.getText()) * 60, uP.getPValikkoAika(), uP.getPValikkoAika(),
                uP.getPValikkoAika() });
        uP.setAsTyyppiArr(new double[] { Double.parseDouble(myyntiPpJakauma.getText()),
                Double.parseDouble(nettiPpJakauma.getText()),
                Double.parseDouble(liittymäPpJakauma.getText()),
                Double.parseDouble(laskutusPpJakauma.getText()),
                Double.parseDouble(myyntiYritysPpJakauma.getText()),
                Double.parseDouble(nettiYritysPpJakauma.getText()),
                Double.parseDouble(liittymäYritysPpJakauma.getText()),
                Double.parseDouble(laskutusYritysPpJakauma.getText()) });
        uP.setSimulaationAika(Double.parseDouble(simuloinninAikaField.getText()));
        uP.setAsTyyppiJakauma(jakauma);
        uP.setMaxJononPituus(Double.parseDouble(kärsimättömyysAikaField.getText()) * 60);
        uP.setReRouteChance(Double.parseDouble(väärävalintaProsenttiField.getText()));
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

    public void prosentti() {
        int yksityisProsentti = Integer.parseInt(YksityisJakaumaProsentti.getText());
        int yritysProsentti = Integer.parseInt(YritysJakaumaProsentti.getText());

        if (yksityisProsentti > 100 || yritysProsentti > 100)
            canSave = false;

    }

    private void setB2CJakaumaListener(TextField tF) {
        tF.textProperty().addListener((observable, oldValue, newValue) -> textFieldCheck());

        int myynti = Integer.parseInt(myyntiPpJakauma.getText());
        int netti = Integer.parseInt(nettiPpJakauma.getText());
        int liittymä = Integer.parseInt(liittymäPpJakauma.getText());
        int laskutus = Integer.parseInt(laskutusPpJakauma.getText());
        int yhteensä = myynti + netti + liittymä + laskutus;
        YksityisJakaumaProsentti.setText(String.valueOf(yhteensä));
    }

    private void setB2BJakaumaListener(TextField tF) {
        tF.textProperty().addListener((observable, oldValue, newValue) -> textFieldCheck());

        int myynti = Integer.parseInt(myyntiYritysPpJakauma.getText());
        int netti = Integer.parseInt(nettiYritysPpJakauma.getText());
        int liittymä = Integer.parseInt(liittymäYritysPpJakauma.getText());
        int laskutus = Integer.parseInt(laskutusYritysPpJakauma.getText());
        int yhteensä = myynti + netti + liittymä + laskutus;
        YritysJakaumaProsentti.setText(String.valueOf(yhteensä));
    }

    private void setTextFieldListener(TextField tF) {
        tF.textProperty().addListener((observable, oldValue, newValue) -> textFieldCheck());
    }

}