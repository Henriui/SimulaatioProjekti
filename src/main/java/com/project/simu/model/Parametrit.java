package com.project.simu.model;

import com.project.eduni.distributions.Normal;
import com.project.simu.constants.Tyyppi;

/**
 * Object for all the parameters what is asked from user to run the simulation.
 * This object gathers all the data in one place.
 * 
 * @author Rasmus Hyyppä
 */
public class Parametrit {
    private static int MIN_PALVELUPISTE_MAARA = 3;// Puhelinvalikot vievät vähintään 3 spottia
    private double simulaationAika;// Kokonaisaika simulaatiolla (T)
    private double asMaara;// Asiakasmäärä tuntia kohden
    private double pValikkoAika;// Puhelinvalikkojen keskimääräinen palveluaika
    private double maxJononPituus;// Kuinka kauan asiakas jaksaa jonottaa
    private double asTyyppiJakauma; // Henkilöasiakkaat / Yritysasiakkaat jakaumaluku
    private double reRouteChance; // Mikä mahdollisuus % on asiakkaalla valita väärä linja puhelinvalikosta
    private int[] ppMaaraArray; // Array asiakaspalvelioiden määrälle
    private double[] ppAikaArray; // Array asiakaspalvelioitten ajoille
    private double[] asTyyppiArr; // Array asiakastyyppi prosenteille
    private double[] asTyyppiParametri; // Array parametri controllerille

    public Parametrit() {
        setDefaultArvot();
    }

    public static int getMinPPMaara() {
        return Parametrit.MIN_PALVELUPISTE_MAARA;
    }

    /**
     * Asetetaan oletus arvot muuttujille
     * 
     * @author Rasmus Hyyppä
     */
    public void setDefaultArvot() {
        this.pValikkoAika = 10; // 10 sekunttia puhelinvalikko 0.167 * 60
        this.asMaara = 23; // Asiakasmäärä, 45 asiakasta tuntiin
        this.maxJononPituus = 10 * 60; // 8 minuuttia jaksaa jonottaa
        this.reRouteChance = 5; // 5 % asiakkaista valitsee väärin
        this.simulaationAika = 12; // Sekunttia 3600 * 8 = 8h työpäivä

        // 50% pri/co asiakkaita
        this.asTyyppiJakauma = 50;

        // Asiakaspisteitten jakauma käyttäjän asettamana
        this.asTyyppiArr = new double[] { 25, 55, 85, 100, 35, 65, 85, 100 };
        this.asTyyppiParametri = new double[] { 25, 30, 30, 15, 35, 30, 20, 15 };
        this.ppMaaraArray = new int[Tyyppi.maxSize]; // Palvelupisteiden kokonaismäärä arraylistissä
        for (int i = 0; i < ppMaaraArray.length; i++) {
            if (i > 7) {
                ppMaaraArray[i] = 1;
            } else {
                ppMaaraArray[i] = 9;
            }
        }

        this.ppAikaArray = new double[Tyyppi.maxSize]; // Keskiverto palvelupisteen palveluaika
        for (int i = 0; i < ppAikaArray.length; i++) {
            if (i > 7) {
                ppAikaArray[i] = pValikkoAika;
            } else {
                ppAikaArray[i] = 22 * 60; // 10 minuuttia asiakaspalvelijat
            }
        }
    }

    /**
     * @param määrä  kuinka monta palvelupistettä on
     * @param ppType tätä tyyppi valueta vastaan (1-8)
     * @author Rasmus Hyyppä
     */
    public void setPPMaaraArr(int[] ppMaaraArray) {
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
    public void setPPAvgAika(double aika, int ppType) {
        ppAikaArray[ppType - 1] = aika * 60;
    }

    public void setPPAvgAikaArr(double[] ppAikaArray) {
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
     * @param ppType ottaa vastaan Tyypin jolla tunnistetaan minkä aika annetaan
     * @return Palauttaa Normal jaukaman keskimääräiselle palveluajalle
     * @author Rasmus Hyyppä
     */
    public Normal getPAJakauma(int ppType) {
        double aika = getPPAvgAika(ppType);
        return new Normal(aika, aika);
    }

    /**
     * Palauttaa kaikkien palvelupisteiden kokonaismaaran
     * 
     * @return int kokonaismaara
     * @author Rasmus Hyyppä
     */
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
        return asTyyppiArr;
    }

    /**
     * Hakee Tyyppi luokan int arvoa vastaavan asiakas jakauman
     * 
     * @param ppType
     * @return double
     * @author Rasmus Hyyppä
     */
    public double getAsTyyppiParametri(int ppType) {
        return this.asTyyppiParametri[ppType - 1];
    }

    /**
     * Asettaa asTyyppiArr arvot ParametriControllerin textfieldien mukaiseksi.
     * 
     * @param asTyyppiParametri
     * @author Rasmus Hyyppä
     */
    public void setAsTyyppiParametri(double[] asTyyppiParametri) {
        this.asTyyppiParametri = new double[asTyyppiArr.length];
        for (int i = 0; i < asTyyppiArr.length; i++) {
            this.asTyyppiParametri[i] += asTyyppiParametri[i];
        }
        setAsTyyppiArr(asTyyppiParametri);
    }

    /**
     * Asettaa kayttajan syottamat arvot prosentti jakaumaksi
     * 
     * @param asTyyppiArr
     * @author Rasmus Hyyppä
     */
    private void setAsTyyppiArr(double[] asTyyppiArr) {
        for (int i = 0; i < asTyyppiArr.length; i++) {
            if (i > 0 && i < 4) {
                asTyyppiArr[i] += asTyyppiArr[i - 1];
            }

            if (i > 4) {
                asTyyppiArr[i] += asTyyppiArr[i - 1];
            }
        }
        this.asTyyppiArr = asTyyppiArr;
    }
}