package com.project.view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.project.MainApp;
import com.project.simu.model.UserAsetukset;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AsetuksetViewController {

    @FXML
    private TextField dbName;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button saveButton;

    /**
     * Ladattaessa täyttää mahdollisesti aiemmin tallennetut valinnat tekstikenttiin
     * 
     * @author Lassi Bågman
     */
    @FXML
    private void initialize() {
        UserAsetukset ua = lueTiedostosta();
        if(ua != null){
            dbName.setText(ua.getDbName());
            username.setText(ua.getUsername());
            password.setText(ua.getPassword());
        }
    }

    @FXML
    private void takaisinMainView() throws IOException {
        MainApp.setRoot("mainView");
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
            UserAsetukset ua = new UserAsetukset(dbName.getText(), username.getText(), password.getText());
            kirjoitaTiedostoon(ua);
        } else {
            Alert varoitus = new Alert(AlertType.ERROR);
            varoitus.setTitle("Täytä kaikki kentät!");
            varoitus.setHeaderText("Täytä kaikki kentät!");
            varoitus.showAndWait();
        }
    }


    /**
     * Tallentaa annetun UserAseteukset.java olion tiedostoon
     * 
     * @author Lassi Bågman
     */
    private void kirjoitaTiedostoon(UserAsetukset ua) {
        try (FileOutputStream virta = new FileOutputStream("data\\userAsetukset.data");
                ObjectOutputStream tuloste = new ObjectOutputStream(virta);) {
            tuloste.writeObject(ua);
            tuloste.close();
        } catch (Exception e) {
            System.out.println("Tiedostoon tallentaminen ei onnistunut");
            System.err.println(e);
        }
    }

    /**
     * Lukee tiedoston jos se on olemassa ja palauttaa tiedostosta löytyvän UserAseteukset.java olion
     * 
     * @return UserAsetkset.java
     * @author Lassi Bågman
     */
    public UserAsetukset lueTiedostosta() {
        try (FileInputStream virta = new FileInputStream("data\\userAsetukset.data");
                ObjectInputStream syote = new ObjectInputStream(virta);) {
            UserAsetukset ua = (UserAsetukset) syote.readObject();
            return ua;
        } catch (Exception e) {
            System.out.println("Tiedoston lukeminen ei onnistunut");
            System.err.println(e);
        }
        return null;
    }
}
