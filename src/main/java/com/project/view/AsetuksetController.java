package com.project.view;

import java.io.IOException;

import com.project.MainApp;
import com.project.simu.model.UserParametrit;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AsetuksetController {

    @FXML
    private TextField dbName;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button saveButton;

    private UserParametrit uP;

    /**
     * Ladattaessa täyttää mahdollisesti aiemmin tallennetut valinnat tekstikenttiin
     * 
     * @author Lassi Bågman
     */
    @FXML
    private void initialize() {
        uP = UserParametrit.getInstance();
        uP.lueTiedostostaDbParametrit();
        if(uP.getDbName() != null){
            dbName.setText(uP.getDbName());
        }
        if(uP.getUsername() != null){
            username.setText(uP.getUsername());
        }
        if(uP.getPassword() != null){
            String pw = "";
            for (int i = 0; i < uP.getPassword().length(); i++){
                pw += "*";
            }
            password.setText(pw);
        }
    }

    @FXML
    private void takaisinMainView() {
        try {
            MainApp.setRoot("mainView");
        } catch (IOException e) {
            System.err.println("Ei voitu siirtyä etusivulle");
            System.err.println(e);
        }
    }

    /**
     * Tallenna nappia painettaessa tarkistaa että käyttäjä on syöttänyt arvot kaikiin kenttiin tämän jälkeen
     * kutsuu kirjoitaTiedostoon() methodia
     * 
     * @author Lassi Bågman
     */
    @FXML
    private void tallennaAsetukset() {
        if (dbName.getText().length() > 0 && username.getText().length() > 0 && password.getText().length() > 0) {
            uP.setDbParameters(dbName.getText(), username.getText(), password.getText());
            uP.kirjoitaTiedostoonDbParametrit();
            takaisinMainView();
        } else {
            Alert varoitus = new Alert(AlertType.ERROR);
            varoitus.setTitle("Täytä kaikki kentät!");
            varoitus.setHeaderText("Täytä kaikki kentät!");
            varoitus.showAndWait();
        }
    }
}
