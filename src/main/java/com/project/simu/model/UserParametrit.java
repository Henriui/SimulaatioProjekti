package com.project.simu.model;

import com.project.eduni.distributions.Binomial;
import com.project.eduni.distributions.DiscreteGenerator;
import com.project.eduni.distributions.Normal;
import com.project.eduni.distributions.Uniform;

public class UserParametrit {
    // Singleton
    private static UserParametrit instance = null;

    // Puhelinvalikot vievät vähintään 3 spottia
    private final static int MIN_PALVELUPISTE_MAARA = 3;

    // Asiakasmäärä tuntia kohden
    private double asMaara;

    // Henkilöasiakkaat / Yritysasiakkaat jakaumaluku
    private double asTyyppiJakauma;

    // Kuinka kauan asiakas jaksaa jonottaa
    private double maxJononPituus;

    // Mikä mahdollisuus % on asiakkaalla valita väärä linja puhelinvalikosta
    private double vaaraValintaProsentti;

    // Kokonaisaika simulaatiolla (T)
    private double simulaationAika;

    // Thread sleep aika
    private long viiveAika;

    // Boolean normaaliJakaumalle
    private boolean normaaliJakauma;

    // Array asiakaspalvelioiden määrälle
    private int[] ppMaaraArray;

    // Array asiakaspalvelioitten ajoille
    private double[] ppAikaArray;

    // Array yksityispiste jakaumalle
    private double[] priAsTyyppiArr;

    // Array yrityspiste jakaumalle
    private double[] coAsTyyppiArr;

    // Puhelinvalikkojen keskimääräinen palveluaika
    private double pValikkoAika;

    // Tietokannan käyttäjäparametrit
    private String dbName;
    
    private String username;
    
    private String password;
    
    public static synchronized UserParametrit getInstance() {
        if (instance == null) {
            instance = new UserParametrit();
        }
        return instance;
    }
    
    private UserParametrit() {
        setDefaultArvot();
    }

    public static int getMinimiPPMaara() {
        return UserParametrit.MIN_PALVELUPISTE_MAARA;
    }

    public void setDefaultArvot() {
        ppMaaraArray = new int[11]; // Palvelupisteiden kokonaismäärä arraylistissä
        for (int i = 0; i < ppMaaraArray.length; i++) {
            if (i > 7) {
                ppMaaraArray[i] = 1;
            } else {
                ppMaaraArray[i] = 7;
            }
        }

        ppAikaArray = new double[11]; // Keskiverto palvelupisteen palveluaika
        pValikkoAika = 10; // 10 sekunttia puhelinvalikko 0.167 * 60
        for (int i = 0; i < ppAikaArray.length; i++) {
            if (i > 7) {
                ppAikaArray[i] = pValikkoAika;
            } else {
                ppAikaArray[i] = 10 * 60; // 10 minuuttia asiakaspalvelijat
            }
        }

        this.asMaara = 45; // Asiakasmäärä, 45 asiakasta tuntiin
        this.viiveAika = 0; // Thread sleeppi aika
        this.maxJononPituus = 8 * 60; // 8 minuuttia jaksaa jonottaa
        this.vaaraValintaProsentti = 5; // 5 % asiakkaista valitsee väärin
        this.simulaationAika = 8; // Sekunttia 3600 * 8 = 8h työpäivä

        /**
         * asTyyppiJakaumalla voi valita tuleeko pri vai co as. enemmän
         * 85% Henkilöasiakkaita, 15% Yritysasiakkaita = 0.45
         * 50% Henkilöasiakkaita, 50% Yritysasiakkaita = 0.5
         * 70% Henkilöasiakkaita, 30% Yritysasiakkaita = 0.4725
         * 30% Henkilöasiakkaita, 70% Yritysasiakkaita = 0.5275
         * 15% Henkilöasiakkaita, 85% Yritysasiakkaita = 0.55
         */
        this.asTyyppiJakauma = 0.5;
        // Normaalijakaumalle boolean
        this.normaaliJakauma = false;
        // Asiakaspisteitten jakauma käyttäjän asettamana
        this.priAsTyyppiArr = new double[] { 25, 50, 65, 100 };
        this.coAsTyyppiArr = new double[] { 25, 50, 65, 100 };
    }
    
    /**
     * Kun käyttäjä haluaa itse valita asiakastyyppien jakautumisen,
     * niin haetaan tällä methodilla jakauman samplea käyttäen oikea
     * palvelupiste
     * 
     * @param t       Asiakkaan tyyppi, Yksityis/Yritys
     * @param gSample sample generaattorin jakaumasta
     * @return Asiakkaan tyyppinumeron, joka määrittää mitä palvelua hän haluaa
     * @author Rasmus Hyyppä
     */
    public int getAsiakkaanPP(AsiakasTyyppi t, int gSample) {
        int asTypeNum = 3; // astypeNum on 3 mikäli yrityspuoli kyseessä
        int j = 0;
        if (t == AsiakasTyyppi.CO) {
            while (gSample >= coAsTyyppiArr[j]) {
                j++;
            }
            asTypeNum += j;
        } else {
            while (gSample >= priAsTyyppiArr[j]) {
                j++;
            }
            asTypeNum = j;
        }
        return asTypeNum;
    }

    /**
     * Käyttäjän syöttämä arvo yhdistetään Binomial jakaumaan
     * Tätä käytetään Asiakas luokassa
     * 
     * @return palauttaa Binomial discretegeneraattorin
     * @author Rasmus Hyyppä
     */
    public DiscreteGenerator getAsJakauma() {
        // 0.5 < Henkilöasiakas, 0.5 > Yritysasiakas
        // Jakauma i, 100 yritystä (100%)
        return new Binomial(asTyyppiJakauma, 100);
    }
    
    /**
     * Käyttäjä voi valita kuinka monta kappaletta asiakaspalvelioita on missäkin
     * linjassa
     * 
     * @return Palauttaa haetun Palvelupistetyypin kpl maaran
     * @author Rasmus Hyyppä
     */
    public int getPPMaara(int ppType) {
        return ppMaaraArray[ppType - 1];
    }
    
    /**
     * Tallennetaan käyttäjän parametrejä taulukkoon
     * josta ne luetaan simulaation alkaessa.
     * 
     * @param määrä  kuinka monta palvelupistettä on
     * @param ppType tätä tyyppi valueta vastaan (1-8)
     * @author Rasmus Hyyppä
     */
    public void setPPMaara(int määrä, int ppType) {
        ppMaaraArray[ppType - 1] = määrä;
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
     * Tallennetaan käyttäjän parametrejä taulukkoon
     * josta ne luetaan simulaation alkaessa.
     * 
     * @param aika   käyttäjän parametri ajalle (minuutteja)
     * @param ppType palvelupisteen tyyppi
     * @author Rasmus Hyyppä
     */
    public void setPPAvgAika(double aika, int ppType) {
        ppAikaArray[ppType - 1] = aika * 60;
    }
    
    /**
     * Arpoo uniform jakaumalla tuleeko asiakas menemään väärään jonoon
     * 
     * @return True mikäli on, false mikäli ei
     * @author Rasmus Hyyppä
     */
    public boolean onkoVaaraValinta() {
        if (new Uniform(1, 100).sample() < vaaraValintaProsentti) {
            return true;
        }
        return false;
    }
    
    /**
     * Käytetään alustuksessa jotta saadaan oikea määrä palvelupisteitä luotua
     * 
     * @return Palauttaa asiakaspalvelupisteiden kokonaismäärän
     * @author Rasmus Hyyppä
     */
    public int getAllPPMaara() {
        return (getPriPPMaara() + getCoPPMaara() + UserParametrit.MIN_PALVELUPISTE_MAARA);
    }
    
    /**
     * 
     * @param Ottaa vastaan Tyypin jolla tunnistetaan minkä aika annetaan
     * @return Palauttaa Normal jaukaman keskimääräiselle palveluajalle
     * @author Rasmus Hyyppä
     */
    public Normal getPAJakauma(int ppType) {
        double aika = getPPAvgAika(ppType);
        return new Normal(aika - (aika / 2), aika + (aika / 2));
    }

    public long getViiveAika() {
        return viiveAika;
    }

    public void setViiveAika(long viiveAika) {
        this.viiveAika = viiveAika;
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

    public void setAsTyyppiJakauma(int asiakasTyyppiJakauma) {
        this.asTyyppiJakauma = asiakasTyyppiJakauma;
    }

    public double getMaxJononPituus() {
        return maxJononPituus;
    }

    public void setMaxJononPituus(double maxJononPituus) {
        this.maxJononPituus = maxJononPituus;
    }

    public double getVaaraValintaProsentti() {
        return vaaraValintaProsentti;
    }

    public void setVaaraValintaProsentti(double vaaraValintaProsentti) {
        this.vaaraValintaProsentti = vaaraValintaProsentti;
    }

    public boolean isNormaaliJakauma() {
        return this.normaaliJakauma;
    }

    public void setNormaaliJakauma(boolean normaaliJakauma) {
        this.normaaliJakauma = normaaliJakauma;
    }

    public double[] getPriAsTyyppiArr() {
        return this.priAsTyyppiArr;
    }

    public void setPriAsTyyppiArr(double[] priAsTyyppiArr) {
        this.priAsTyyppiArr = priAsTyyppiArr;
    }

    public double[] getCoAsTyyppiArr() {
        return this.coAsTyyppiArr;
    }

    public void setCoAsTyyppiArr(double[] coAsTyyppiArr) {
        this.coAsTyyppiArr = coAsTyyppiArr;
    }

    public int getPriPPMaara() {
        int kokonaisMaara = 0;
        for (int i = 0; i < 4; i++) {
            kokonaisMaara += ppMaaraArray[i];
        }
        return kokonaisMaara;
    }

    public int getCoPPMaara() {
        int kokonaisMaara = 0;
        for (int i = 4; i < 8; i++) {
            kokonaisMaara += ppMaaraArray[i];
        }
        return kokonaisMaara;
    }
    
    // dbName, username, password get/set
     
    public String getDbName() {return dbName;}
    public void setDbName(String dbName) { this.dbName = dbName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public void setDbParameters(String dbName, String username, String password){
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }
}
