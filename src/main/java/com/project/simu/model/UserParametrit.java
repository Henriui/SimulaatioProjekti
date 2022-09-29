package com.project.simu.model;

import com.project.eduni.distributions.Binomial;
import com.project.eduni.distributions.ContinuousGenerator;
import com.project.eduni.distributions.DiscreteGenerator;
import com.project.eduni.distributions.Normal;
import com.project.eduni.distributions.Uniform;
import com.project.view.INewSimulationControllerVtoM;

public class UserParametrit {
    private static UserParametrit instance = null;

    // Puhelinvalikot vievät vähintään 3 spottia
    private final static int MIN_PALVELUPISTE_MAARA = 3;

    // Asiakasmäärä tuntia kohden
    private double asiakasMaara;

    // Henkilöasiakkaat / Yritysasiakkaat jakaumaluku
    private double asiakasTyyppiJakauma;

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
    private double[] priAsiakasTyyppiArr;
    // Array yrityspiste jakaumalle
    private double[] coAsiakasTyyppiArr;

    // Puhelinvalikkojen keskimääräinen palveluaika
    private double pValikkoAika;

    public static int getMinimiPPMaara() {
        return UserParametrit.MIN_PALVELUPISTE_MAARA;
    }

    public static synchronized UserParametrit getInstance() {
        if (instance == null) {
            instance = new UserParametrit();
        }
        return instance;
    }

    private UserParametrit() {
        setDefaultArvot();
    }

    public void setDefaultArvot() {
        ppMaaraArray = new int[11];
        ppAikaArray = new double[11];

        for (int i = 0; i < ppMaaraArray.length; i++) {
            if (i > 7) {
                ppMaaraArray[i] = 1;
            } else {
                ppMaaraArray[i] = 7;
            }
        }

        // Keskiverto palvelupisteen palveluaika
        // 10 sekunttia puhelinvalikko 0.167 * 60
        // 10 minuuttia asiakaspalvelijat
        pValikkoAika = 10;
        for (int i = 0; i < ppAikaArray.length; i++) {
            if (i > 7) {
                ppAikaArray[i] = pValikkoAika;
            } else {
                ppAikaArray[i] = 10 * 60;
            }
        }

        // Normaalijakaumalle boolean
        normaaliJakauma = false;

        // Asiakaspisteitten jakauma käyttäjän asettamana
        priAsiakasTyyppiArr = new double[] { 25, 50, 65, 100 };
        coAsiakasTyyppiArr = new double[] { 25, 50, 65, 100 };

        /**
         * 85% Henkilöasiakkaita, 15% Yritysasiakkaita = 0.45
         * 50% Henkilöasiakkaita, 50% Yritysasiakkaita = 0.5
         * 70% Henkilöasiakkaita, 30% Yritysasiakkaita = 0.4725
         * 30% Henkilöasiakkaita, 70% Yritysasiakkaita = 0.5275
         * 15% Henkilöasiakkaita, 85% Yritysasiakkaita = 0.55
         *
         * @author Rasmus Hyyppä
         */
        this.asiakasTyyppiJakauma = 0.5;

        // Asiakasmäärä, 45 asiakasta tuntiin
        this.asiakasMaara = 45;

        // Thread sleeppi aika
        this.viiveAika = 0;

        // 8 minuuttia jaksaa jonottaa
        this.maxJononPituus = 8 * 60;

        // 5 % asiakkaista valitsee väärin
        this.vaaraValintaProsentti = 5;

        // Sekunttia 3600 * 8 = 8h työpäivä
        this.simulaationAika = 8;
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
        int asTypeNum = 3;
        int j = 0;
        if (t == AsiakasTyyppi.CO) {
            while (gSample >= coAsiakasTyyppiArr[j]) {
                j++;
            }
            asTypeNum += j;
        } else {
            while (gSample >= priAsiakasTyyppiArr[j]) {
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
    public DiscreteGenerator getAsiakasJakauma() {
        double jakaumaNumero = UserParametrit.getInstance().getAsiakasTyyppiJakauma();
        System.out.println("Jakauma numero: " + jakaumaNumero); // 0.5 < Henkilöasiakas, 0.5 > Yritysasiakas
        // Jakauma i, 100 yritystä (100%)
        return new Binomial(jakaumaNumero, 100);
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
     * Käyttäjä voi valita kuinka monta kappaletta asiakaspalvelioita on missäkin
     * linjassa
     * 
     * @param ppType palvelupisteen tyyppi
     * @return Palauttaa haetun Palvelupistetyypin kpl maaran
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
        return (getYksityisPPMaara() + getYritysPPMaara() + UserParametrit.MIN_PALVELUPISTE_MAARA);
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

    /**
     * Puhelinvalikkojen odotusaika Normalina jakaumana
     * 
     * @return Normal return the pValikkoAika
     * @author Rasmus Hyyppä
     */
    public Normal getPAJakaumaPuhelinValikolle() {
        return new Normal(pValikkoAika - (pValikkoAika / 2), pValikkoAika + (pValikkoAika / 2));
    }

    public double getPValikkoAika() {
        return pValikkoAika;
    }

    public void setPValikkoAika(double pValikkoAika) {
        this.pValikkoAika = pValikkoAika;
    }

    public double getAsiakasMaara() {
        return this.asiakasMaara;
    }

    public void setAsiakasMaara(double asiakasMaara) {
        this.asiakasMaara = asiakasMaara;
    }

    public double getAsiakasTyyppiJakauma() {
        return asiakasTyyppiJakauma;
    }

    public void setAsiakasTyyppiJakauma(int asiakasTyyppiJakauma) {
        this.asiakasTyyppiJakauma = asiakasTyyppiJakauma;
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

    public double[] getPriAsiakasTyyppiArr() {
        return this.priAsiakasTyyppiArr;
    }

    public void setPriAsiakasTyyppiArr(double[] priAsiakasTyyppiArr) {
        this.priAsiakasTyyppiArr = priAsiakasTyyppiArr;
    }

    public double[] getCoAsiakasTyyppiArr() {
        return this.coAsiakasTyyppiArr;
    }

    public void setCoAsiakasTyyppiArr(double[] coAsiakasTyyppiArr) {
        this.coAsiakasTyyppiArr = coAsiakasTyyppiArr;
    }

    /**
     * 
     * @return
     */
    public int getYksityisPPMaara() {
        int kokonaisMaara = 0;
        for (int i = 0; i < 4; i++) {
            kokonaisMaara += ppMaaraArray[i];
        }
        return kokonaisMaara;
    }

    /**
     * 
     * @return
     */
    public int getYritysPPMaara() {
        int kokonaisMaara = 0;
        for (int i = 4; i < 8; i++) {
            kokonaisMaara += ppMaaraArray[i];
        }
        return kokonaisMaara;
    }

}
