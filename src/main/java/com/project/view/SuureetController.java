package com.project.view;

import com.project.simu.model.UserParametrit;
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

    // Yksityispuolen parametrit.

    private static int myyntivalue = 3;
    private static int nettivalue = 3;
    private static int liittymävalue = 3;
    private static int laskutusvalue = 3;

    private static int myyntiAika = 10;
    private static int nettiAika = 10;
    private static int liittymäAika = 10;
    private static int laskutusAika = 10;

    // Yrityspuolen parametrit.

    private static int Yritysmyyntivalue = 3;
    private static int Yritysnettivalue = 3;
    private static int Yritysliittymävalue = 3;
    private static int Yrityslaskutusvalue = 3;

    private static int YritysmyyntiAika = 10;
    private static int YritysnettiAika = 10;
    private static int YritysliittymäAika = 10;
    private static int YrityslaskutusAika = 10;

    // Simulaation parametrit.

    private static int simuloinninAika = 8;
    private static int viiveAika = 5000;
    private static int kärsimättömyysAika = 6;
    private static double väärävalintaProsentti = 0.5;
    private static int asiakasmääräAika = 100;

    // Temporary valuet peruutusta varten.
    
    private int Tempmyyntivalue; 
    private int Tempnettivalue; 
    private int Templiittymävalue;
    private int Templaskutusvalue;
    private int TempYritysmyyntivalue;
    private int TempYritysnettivalue;
    private int TempYritysliittymävalue;
    private int TempYrityslaskutusvalue;
    private int TempmyyntiAika;
    private int TempnettiAika;
    private int TempliittymäAika;
    private int TemplaskutusAika;
    private int TempYritysmyyntiAika;
    private int TempYritysnettiAika;
    private int TempYritysliittymäAika;
    private int TempYrityslaskutusAika;
    private int TempsimuloinninAika;
    private int TempviiveAika;
    private int TempkärsimättömyysAika;
    private Double TempväärävalintaProsentti;
    private int TempasiakasmääräAika;
        
    
    @FXML
    private void onSliderChanged() 
    {
        // Yksityispuolen valuet.

        Tempmyyntivalue = (int) myyntiPalvelupisteet.getValue();
        Tempnettivalue = (int) nettiPalvelupisteet.getValue();
        Templiittymävalue = (int) liittymäPalvelupisteet.getValue();
        Templaskutusvalue = (int) laskutusPalvelupisteet.getValue();

        myynti.setText(Tempmyyntivalue + " kpl");
        netti.setText(Tempnettivalue + " kpl");
        liittymä.setText(Templiittymävalue + " kpl");
        laskutus.setText(Templaskutusvalue + " kpl");

        // Yrityspuolen valuet.
        
        TempYritysmyyntivalue = (int) YritysmyyntiPp.getValue();
        TempYritysnettivalue = (int) YritysnettiPp.getValue();
        TempYritysliittymävalue = (int) YritysliittymäPp.getValue();
        TempYrityslaskutusvalue = (int) YrityslaskutusPp.getValue();
        
        Yritysmyynti.setText(TempYritysmyyntivalue + " kpl");
        Yritysnetti.setText(TempYritysnettivalue + " kpl");
        Yritysliittymä.setText(TempYritysliittymävalue + " kpl");
        Yrityslaskutus.setText(TempYrityslaskutusvalue + " kpl");   
    }

    
    @FXML
    private void textFieldCheck(){
        try { 
            // Yksityispuolen ajat.
            TempmyyntiAika = Integer.valueOf(myyntiAikaField.getText());
            TempnettiAika = Integer.valueOf(nettiAikaField.getText());
            TempliittymäAika = Integer.valueOf(liittymäAikaField.getText());
            TemplaskutusAika = Integer.valueOf(laskutusAikaField.getText());

            // Yrityspuolen ajat.
            TempYritysmyyntiAika = Integer.valueOf(YritysmyyntiAikaField.getText());
            TempYritysnettiAika = Integer.valueOf(YritysnettiAikaField.getText());
            TempYritysliittymäAika = Integer.valueOf(YritysliittymäAikaField.getText());
            TempYrityslaskutusAika = Integer.valueOf(YrityslaskutusAikaField.getText());

            // Simulaation asetukset.
            TempsimuloinninAika = Integer.valueOf(simuloinninAikaField.getText());
            TempviiveAika = Integer.valueOf(simuloinninViiveField.getText());
            TempkärsimättömyysAika = Integer.valueOf(kärsimättömyysAikaField.getText());
            TempväärävalintaProsentti = Double.valueOf(väärävalintaProsenttiField.getText());
            TempasiakasmääräAika = Integer.valueOf(asiakasMääräField.getText());

            canSave = true;
        } catch (NumberFormatException ex) {
           Alert alert = new Alert(AlertType.ERROR,"Anna vain numeerista tietoa.",ButtonType.CLOSE);
           alert.setTitle("Parametrit väärin!");
           alert.setHeaderText("Virheellinen aika!");
           canSave = false;
           alert.showAndWait();
       }    
    }

    @FXML
    private void tallenna()
    {
        // Check that all values are ok.

        textFieldCheck();

        if(canSave)
        {
            saveValues();
             // TODO: send data somewhere
            Stage stage = (Stage) closeButton.getScene().getWindow();
            controller.popupOpen(false);
            stage.close();
        }
    }

    private void saveValues(){
        myyntivalue = Tempmyyntivalue; 
        nettivalue = Tempnettivalue; 
        liittymävalue = Templiittymävalue;
        laskutusvalue = Templaskutusvalue;
        Yritysmyyntivalue = TempYritysmyyntivalue;
        Yritysnettivalue = TempYritysnettivalue;
        Yritysliittymävalue = TempYritysliittymävalue;
        Yrityslaskutusvalue = TempYrityslaskutusvalue;
        myyntiAika = TempmyyntiAika;
        nettiAika = TempnettiAika;
        liittymäAika = TempliittymäAika;
        laskutusAika = TemplaskutusAika;
        YritysmyyntiAika = TempYritysmyyntiAika;
        YritysnettiAika = TempYritysnettiAika;
        YritysliittymäAika = TempYritysliittymäAika;
        YrityslaskutusAika = TempYrityslaskutusAika;
        simuloinninAika = TempsimuloinninAika;
        viiveAika = TempviiveAika;
        kärsimättömyysAika = TempkärsimättömyysAika;
        väärävalintaProsentti = TempväärävalintaProsentti;
        asiakasmääräAika = TempasiakasmääräAika;
    }

    @FXML
    private void peruuta(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        
        controller.popupOpen(false);
        stage.close();
    }
    @FXML
    private void initialize() {
        startValuet();
        onSliderChanged();
    }

    private void startValuet() 
    {
        // Yksityispuolen asetukset.

        myyntiPalvelupisteet.setValue(myyntivalue);
        nettiPalvelupisteet.setValue(nettivalue);
        liittymäPalvelupisteet.setValue(liittymävalue);
        laskutusPalvelupisteet.setValue(laskutusvalue);

        myynti.setText(myyntivalue + " kpl");
        netti.setText(nettivalue + " kpl");
        liittymä.setText(liittymävalue + " kpl");
        laskutus.setText(laskutusvalue + " kpl");

        // Yrityspuolen asetukset.

        YritysmyyntiPp.setValue(Yritysmyyntivalue);
        YritysnettiPp.setValue(Yritysnettivalue);
        YritysliittymäPp.setValue(Yritysliittymävalue);
        YrityslaskutusPp.setValue(Yrityslaskutusvalue);
        
        Yritysmyynti.setText(Yritysmyyntivalue + " kpl");
        Yritysnetti.setText(Yritysnettivalue + " kpl");
        Yritysliittymä.setText(Yritysliittymävalue + " kpl");
        Yrityslaskutus.setText(Yrityslaskutusvalue + " kpl"); 

        // Yksityispuolen ajat.
        myyntiAikaField.setText(Integer.toString(myyntiAika));
        nettiAikaField.setText(Integer.toString(nettiAika));
        liittymäAikaField.setText(Integer.toString(liittymäAika));
        laskutusAikaField.setText(Integer.toString(laskutusAika));

        // Yrityspuolen ajat.
        YritysmyyntiAikaField.setText(Integer.toString(YritysmyyntiAika));
        YritysnettiAikaField.setText(Integer.toString(YritysnettiAika));
        YritysliittymäAikaField.setText(Integer.toString(YritysliittymäAika));
        YrityslaskutusAikaField.setText(Integer.toString(YrityslaskutusAika));
        
        // Simulaation asetukset.
        simuloinninAikaField.setText(Integer.toString(simuloinninAika));
        simuloinninViiveField.setText(Integer.toString(viiveAika));
        kärsimättömyysAikaField.setText(Integer.toString(kärsimättömyysAika));
        väärävalintaProsenttiField.setText(Double.toString(väärävalintaProsentti));
        asiakasMääräField.setText(Integer.toString(asiakasmääräAika));
    }

    
}