package com.project.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.project.MainApp;
import com.project.simu.framework.Moottori;
import com.project.simu.framework.Trace;
import com.project.simu.framework.Trace.Level;
import com.project.simu.model.OmaMoottori;
import com.project.simu.model.UserParametrit;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;


public class SuureetController {

    private NewSimulationController controller = new NewSimulationController();
    private boolean canSave = false;

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
    private static int myyntivalue;
    private static int nettivalue;
    private static int liittymävalue;
    private static int laskutusvalue;

    private static int myyntiAika;
    private static int nettiAika;
    private static int liittymäAika;
    private static int laskutusAika;

    // Yrityspuolen parametrit.
    private static int Yritysmyyntivalue;
    private static int Yritysnettivalue;
    private static int Yritysliittymävalue;
    private static int Yrityslaskutusvalue;

    private static int YritysmyyntiAika;
    private static int YritysnettiAika;
    private static int YritysliittymäAika;
    private static int YrityslaskutusAika;

    // Simulaation parametrit.
    private static int simuloinninAika;
    private static int viiveAika;
    private static int kärsimättömyysAika;
    private static double väärävalintaProsentti;
    private static int asiakasmääräAika;
    
    @FXML
    private void onSliderChanged() 
    {
        // Yksityispuolen valuet.

        myyntivalue = (int) myyntiPalvelupisteet.getValue();
        nettivalue = (int) nettiPalvelupisteet.getValue();
        liittymävalue = (int) liittymäPalvelupisteet.getValue();
        laskutusvalue = (int) laskutusPalvelupisteet.getValue();

        myynti.setText(myyntivalue + " kpl");
        netti.setText(nettivalue + " kpl");
        liittymä.setText(liittymävalue + " kpl");
        laskutus.setText(laskutusvalue + " kpl");

        // Yrityspuolen valuet.
        
        Yritysmyyntivalue = (int) YritysmyyntiPp.getValue();
        Yritysnettivalue = (int) YritysnettiPp.getValue();
        Yritysliittymävalue = (int) YritysliittymäPp.getValue();
        Yrityslaskutusvalue = (int) YrityslaskutusPp.getValue();
        
        Yritysmyynti.setText(Yritysmyyntivalue + " kpl");
        Yritysnetti.setText(Yritysnettivalue + " kpl");
        Yritysliittymä.setText(Yritysliittymävalue + " kpl");
        Yrityslaskutus.setText(Yrityslaskutusvalue + " kpl");   
    }

    
    @FXML
    private void textFieldCheck(){
        try { 
            // Yksityispuolen ajat.
            myyntiAika = Integer.valueOf(myyntiAikaField.getText());
            nettiAika = Integer.valueOf(nettiAikaField.getText());
            liittymäAika = Integer.valueOf(liittymäAikaField.getText());
            laskutusAika = Integer.valueOf(laskutusAikaField.getText());

            // Yrityspuolen ajat.
            YritysmyyntiAika = Integer.valueOf(YritysmyyntiAikaField.getText());
            YritysnettiAika = Integer.valueOf(YritysnettiAikaField.getText());
            YritysliittymäAika = Integer.valueOf(YritysliittymäAikaField.getText());
            YrityslaskutusAika = Integer.valueOf(YrityslaskutusAikaField.getText());

            // Simulaation asetukset.
            simuloinninAika = Integer.valueOf(simuloinninAikaField.getText());
            viiveAika = Integer.valueOf(simuloinninViiveField.getText());
            kärsimättömyysAika = Integer.valueOf(kärsimättömyysAikaField.getText());
            väärävalintaProsentti = Double.valueOf(väärävalintaProsenttiField.getText());
            asiakasmääräAika = Integer.valueOf(asiakasMääräField.getText());

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
             // TODO: send data somewhere
            Stage stage = (Stage) closeButton.getScene().getWindow();
            controller.popupOpen(false);
            stage.close();
        }
    }

    @FXML
    private void peruuta(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        
        controller.popupOpen(false);
        stage.close();
    }
    @FXML
    private void initialize() 
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