package com.project.view;

import java.io.IOException;

import com.project.MainApp;
import com.project.simu.model.Parametrit;
import com.project.simu.model.UserAsetukset;
import com.project.simu.model.UserAsetuksetController;

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

    private UserAsetuksetController uac;

    /**
     * Ladattaessa täyttää mahdollisesti aiemmin tallennetut valinnat tekstikenttiin
     * 
     * @author Lassi Bågman
     */
    @FXML
    private void initialize() {
        uac = new UserAsetuksetController();
        UserAsetukset ua = uac.lueTiedostostaDbParametrit();
        if (ua.getDbName() != null) {
            dbName.setText(ua.getDbName());
        }
        if (ua.getUsername() != null) {
            username.setText(ua.getUsername());
        }
        if (ua.getPassword() != null) {
            String pw = "";
            for (int i = 0; i < ua.getPassword().length(); i++) {
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
     * Tallenna nappia painettaessa tarkistaa että käyttäjä on syöttänyt arvot
     * kaikiin kenttiin tämän jälkeen
     * kutsuu kirjoitaTiedostoon() methodia
     * 
     * @author Lassi Bågman
     */
    @FXML
    private void tallennaAsetukset() {
        if (dbName.getText().length() > 0 && username.getText().length() > 0 && password.getText().length() > 0) {
            uac.kirjoitaTiedostoonDbParametrit(new UserAsetukset(dbName.getText(), username.getText(), password.getText()));
            takaisinMainView();
        } else {
            Alert varoitus = new Alert(AlertType.ERROR);
            varoitus.setTitle("Täytä kaikki kentät!");
            varoitus.setHeaderText("Täytä kaikki kentät!");
            varoitus.showAndWait();
        }
    }
}
