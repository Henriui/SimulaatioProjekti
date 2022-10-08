package com.project.simu.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import com.project.eduni.distributions.Normal;
import com.project.simu.constants.Tyyppi;

public class Parametrit {
    // Singleton
    private static Parametrit instance = null;

    // Puhelinvalikot vievät vähintään 3 spottia
    private static int MIN_PALVELUPISTE_MAARA = 3;

    // Asiakasmäärä tuntia kohden
    private double asMaara;

    // Henkilöasiakkaat / Yritysasiakkaat jakaumaluku
    private double asTyyppiJakauma;

    // Kuinka kauan asiakas jaksaa jonottaa
    private double maxJononPituus;

    // Mikä mahdollisuus % on asiakkaalla valita väärä linja puhelinvalikosta
    private double reRouteChance;

    // Kokonaisaika simulaatiolla (T)
    private double simulaationAika;

    // Array asiakaspalvelioiden määrälle
    private int[] ppMaaraArray;

    // Array asiakaspalvelioitten ajoille
    private double[] ppAikaArray;

    // Array asiakastyyppi prosenteille
    private double[] asTyyppiArr;

    // Puhelinvalikkojen keskimääräinen palveluaika
    private double pValikkoAika;

    // Tietokannan käyttäjäparametrit
    private String dbName;
    private String tableName;
    private String username;
    private String password;

    public static synchronized Parametrit getInstance() {
        if (instance == null) {
            instance = new Parametrit();
        }
        return instance;
    }

    private Parametrit() {
        setDefaultArvot();
    }

    public static int getMinPPMaara() {
        return Parametrit.MIN_PALVELUPISTE_MAARA;
    }

    public void setDefaultArvot() {
        this.pValikkoAika = 10; // 10 sekunttia puhelinvalikko 0.167 * 60
        this.asMaara = 45; // Asiakasmäärä, 45 asiakasta tuntiin
        this.maxJononPituus = 8 * 60; // 8 minuuttia jaksaa jonottaa
        this.reRouteChance = 0; // 5 % asiakkaista valitsee väärin
        this.simulaationAika = 8; // Sekunttia 3600 * 8 = 8h työpäivä

        // 50% pri/co asiakkaita
        this.asTyyppiJakauma = 50;

        // Asiakaspisteitten jakauma käyttäjän asettamana
        this.asTyyppiArr = new double[] { 25, 50, 75, 100, 25, 50, 75, 100 };

        this.ppMaaraArray = new int[Tyyppi.maxSize]; // Palvelupisteiden kokonaismäärä arraylistissä
        for (int i = 0; i < ppMaaraArray.length; i++) {
            if (i > 7) {
                ppMaaraArray[i] = 1;
            } else {
                ppMaaraArray[i] = 7;
            }
        }

        this.ppAikaArray = new double[Tyyppi.maxSize]; // Keskiverto palvelupisteen palveluaika
        for (int i = 0; i < ppAikaArray.length; i++) {
            if (i > 7) {
                ppAikaArray[i] = pValikkoAika;
            } else {
                ppAikaArray[i] = 10 * 60; // 10 minuuttia asiakaspalvelijat
            }
        }
    }

    /**
     * @param määrä  kuinka monta palvelupistettä on
     * @param ppType tätä tyyppi valueta vastaan (1-8)
     * @author Rasmus Hyyppä
     */
    public void setPPMaara(int[] ppMaaraArray) {
        this.ppMaaraArray = ppMaaraArray;
    }

    /**
     * @param ppType palvelupisteen tyyppi
     * @return Palauttaa haetun Palvelupistetyypin avg palveluajan
     * @author Rasmus Hyyppä
     */
    public double getPPAvgAika(int ppType) {
        return ppAikaArray[ppType - 1];
    }

    /**
     * @param aika   käyttäjän parametri ajalle (minuutteja)
     * @param ppType palvelupisteen tyyppi
     * @author Rasmus Hyyppä
     */
    // public void setPPAvgAika(double aika, int ppType) {
    // ppAikaArray[ppType - 1] = aika * 60;
    // }

    public void setPPAvgAika(double[] ppAikaArray) {
        this.ppAikaArray = ppAikaArray;
    }

    /**
     * @return Palauttaa haetun Palvelupistetyypin kpl maaran
     * @author Rasmus Hyyppä
     */
    public int getPPMaara(int ppType) {
        return ppMaaraArray[ppType - 1];
    }

    /**
     * @param Ottaa vastaan Tyypin jolla tunnistetaan minkä aika annetaan
     * @return Palauttaa Normal jaukaman keskimääräiselle palveluajalle
     * @author Rasmus Hyyppä
     */
    public Normal getPAJakauma(int ppType) {
        double aika = getPPAvgAika(ppType);
        if (aika > 0) {
            return new Normal(aika - (aika / 2), aika + (aika / 2));
        } else {
            aika = 10 * 60;
            return new Normal(aika - (aika / 2), aika + (aika / 2));
        }

    }

    public int getAllPPMaara() {
        int kokonaisMaara = 0;
        for (int i = 0; i < ppMaaraArray.length; i++) {
            kokonaisMaara += ppMaaraArray[i];
        }
        return kokonaisMaara;
    }

    public double getSimulaationAika() {
        return simulaationAika;
    }

    public void setSimulaationAika(double simulaationAika) {
        this.simulaationAika = simulaationAika;
    }

    public double getPValikkoAika() {
        return pValikkoAika;
    }

    public void setPValikkoAika(double pValikkoAika) {
        this.pValikkoAika = pValikkoAika;
    }

    public double getAsMaara() {
        return this.asMaara;
    }

    public void setAsMaara(double asiakasMaara) {
        this.asMaara = asiakasMaara;
    }

    public double getAsTyyppiJakauma() {
        return asTyyppiJakauma;
    }

    public void setAsTyyppiJakauma(double luku) {
        this.asTyyppiJakauma = luku;
    }

    public double getMaxJononPituus() {
        return maxJononPituus;
    }

    public void setMaxJononPituus(double maxJononPituus) {
        this.maxJononPituus = maxJononPituus;
    }

    public double getReRouteChance() {
        return reRouteChance;
    }

    public void setReRouteChance(double reRouteChance) {
        this.reRouteChance = reRouteChance;
    }

    public double[] getAsTyyppiArr() {
        return this.asTyyppiArr;
    }

    public void setAsTyyppiArr(double[] asTyyppiArr) {
        for (int i = 0; i < asTyyppiArr.length; i++) {
            if (i > 0 && i < 4) {
                asTyyppiArr[i] += asTyyppiArr[i - 1];
            }

            if (i > 4) {
                asTyyppiArr[i] += asTyyppiArr[i - 1];
            }
        }

        System.out.println("asTyyppiArr: " + Arrays.toString(asTyyppiArr));
        this.asTyyppiArr = asTyyppiArr;
    }

    // dbName, tableName ,username, password get/set

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDbParameters(String dbName, String tableName, String username, String password) {
        this.dbName = dbName;
        this.tableName = tableName;
        this.username = username;
        this.password = password;
    }

    public void setDbParameters(String dbName, String username, String password) {
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    /**
     * Tallentaa database parametrit singletonista UserAsetukset.java olion avulla
     * tiedostoon
     * 
     * @return true jos onnistui, false jos ei
     * @author Lassi Bågman
     */
    public boolean kirjoitaTiedostoonDbParametrit() {
        try (FileOutputStream virta = new FileOutputStream("data\\dbAsetukset.data");
                ObjectOutputStream tuloste = new ObjectOutputStream(virta);) {
            tuloste.writeObject(new UserAsetukset(dbName, username, password));
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
     * UserAsetukset.java olion
     * jonka avulla päivittää singletonin parametrit
     * 
     * @return true jos onnistui, false jos ei
     * @author Lassi Bågman
     */
    public boolean lueTiedostostaDbParametrit() {
        try (FileInputStream virta = new FileInputStream("data\\dbAsetukset.data");
                ObjectInputStream syote = new ObjectInputStream(virta);) {
            UserAsetukset ua = (UserAsetukset) syote.readObject();
            dbName = ua.getDbName();
            username = ua.getUsername();
            password = ua.getPassword();
            return true;
        } catch (Exception e) {
            System.out.println("Tiedoston lukeminen ei onnistunut");
            System.err.println(e);
            return false;
        }
    }
}