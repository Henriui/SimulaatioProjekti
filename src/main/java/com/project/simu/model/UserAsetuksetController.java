package com.project.simu.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UserAsetuksetController {
    /**
     * Tallentaa database parametrit annetun UserAsetukset.java olion avulla
     * tiedostoon
     * 
     * @return true jos onnistui, false jos ei
     * @author Lassi Bågman
     */
    public boolean kirjoitaTiedostoonDbParametrit(UserAsetukset ua) {
        try (FileOutputStream virta = new FileOutputStream("data\\dbAsetukset.data");
                ObjectOutputStream tuloste = new ObjectOutputStream(virta);) {
            tuloste.writeObject(ua);
            tuloste.close();
            return true;
        } catch (Exception e) {
            System.out.println("Tiedostoon tallentaminen ei onnistunut");
            System.err.println(e);
            return false;
        }
    }

    /**
     * Lukee tiedoston jos se on olemassa ja palauttaa tiedostosta löytyvän
     * UserAsetukset.java olion ja palauttaa sen
     * 
     * @return Asetukset jos onnistuu. Null jos ei.
     * @author Lassi Bågman
     */
    public UserAsetukset lueTiedostostaDbParametrit() {
        try (FileInputStream virta = new FileInputStream("data\\dbAsetukset.data");
                ObjectInputStream syote = new ObjectInputStream(virta);) {
            UserAsetukset ua = (UserAsetukset) syote.readObject();
            return ua;
        } catch (Exception e) {
            System.out.println("Tiedoston lukeminen ei onnistunut");
            System.err.println(e);
            return null;
        }
    }
}
