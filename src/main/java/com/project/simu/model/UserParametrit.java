package com.project.simu.model;

import com.project.eduni.distributions.Binomial;
import com.project.eduni.distributions.ContinuousGenerator;
import com.project.eduni.distributions.DiscreteGenerator;
import com.project.eduni.distributions.Normal;
import com.project.eduni.distributions.Uniform;
import com.project.view.INewSimulationControllerVtoM;

public class UserParametrit {
    private static UserParametrit instance = null;

    // Asiakasmäärä tuntia kohden
    private double asiakasMaara;

    // Henkilöasiakkaat / Yritysasiakkaat jakaumaluku
    private double asiakasTyyppiJakauma;

    // Kuinka kauan asiakas jaksaa jonottaa
    private double asiakkaidenKarsivallisyys;

    // Mikä mahdollisuus % on asiakkaalla valita väärä linja puhelinvalikosta
    private double vaaraValintaProsentti;

    // Kokonaisaika simulaatiolla (T)
    private double simulaationAika;

    // Thread sleep aika
    private long viiveAika;

    // Boolean normaaliJakaumalle
    private boolean normaaliJakauma;

    // Array asiakaspalvelioiden määrälle
    private int asiakasPisteMaaraArray[];

    // Array asiakaspalvelioiden ajoille
    private double asiakasPalveluAikaArray[];

    // Array yksityispiste jakaumalle
    private double[] priAsiakasTyyppiArr;
    // Array yrityspiste jakaumalle
    private double[] coAsiakasTyyppiArr;

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
    
    public void setDefaultArvot() {

        asiakasPisteMaaraArray = new int[11];
        asiakasPalveluAikaArray = new double[8];

        for (int i = 0; i < asiakasPisteMaaraArray.length; i++) {
            if (i > 7) {
                asiakasPisteMaaraArray[i] = 1;
            } else {
                asiakasPisteMaaraArray[i] = 7;
            }
        }

        // Keskiverto palvelupisteen palveluaika
        // 10 sekunttia puhelinvalikko
        // 600 sekunttia asiakaspalvelijat
        this.pValikkoAika = 10;
        for (int i = 0; i < asiakasPalveluAikaArray.length; i++) {
            asiakasPalveluAikaArray[i] = 10 * 60;
        }

        // Normaalijakaumalle boolean
        normaaliJakauma = false;
        
        // Asiakaspisteiden jakauma käyttäjän asettamana
        priAsiakasTyyppiArr = new double[] { 50, 51, 65, 100 };
        coAsiakasTyyppiArr = new double[] { 50, 51, 65, 100 };

        /**
         * 85% Henkilöasiakkaita, 15% Yritysasiakkaita = 0.45
         * 50% Henkilöasiakkaita, 50% Yritysasiakkaita = 0.5
         * 70% Henkilöasiakkaita, 30% Yritysasiakkaita = 0.4725
         * 30% Henkilöasiakkaita, 70% Yritysasiakkaita = 0.5275
         * 15% Henkilöasiakkaita, 15% Yritysasiakkaita = 0.55
         *
         * @author Rasmus Hyyppä
         */
        this.asiakasTyyppiJakauma = 0.5;
        
        // Asiakasmäärä, 45 asiakasta tuntiin
        this.asiakasMaara = 45;

        // Thread sleeppi aika
        this.viiveAika = 1500;
        
        // 480 sek jaksaa jonottaa
        this.asiakkaidenKarsivallisyys = 8 * 60;
        
        // 5 % asiakkaista valitsee väärin
        this.vaaraValintaProsentti = 0.05;
        
        // Sekunttia 3600 * 8 = 8h työpäivä
        this.simulaationAika = 3600 * 8;
        
    }
    
    /**
     * Käytä tätä parametrien hakemiseen kontrollerilta ennen simulaation
     * alottamista
     * 
     * @param kontrolleri
     * @author Rasmus Hyyppä
     */
    public void getParametrit(INewSimulationControllerVtoM kontrolleri) {
        /*
        * double asiakasPalvelijoidenAjat[] =
        * kontrolleri.getAsiakaspalvelijoidenAjat();
        */
    }
    
    public synchronized int getProbability(AsiakasTyyppi t, int sample) {
        int asTypeNum = 3;
        int j = 0;
        if (t == AsiakasTyyppi.CO) {
            while (sample >= coAsiakasTyyppiArr[j]) {
                j++;
            }
            asTypeNum += j;
        } else {
            while (sample >= priAsiakasTyyppiArr[j]) {
                j++;
            }
            asTypeNum = j;
        }
        return asTypeNum; // value = ThreadLocalRandom.current().nextInt(yritysPisteArray.length);
    }

    // Liuta settereitä ja gettereitä tästä eteenpäin //
    // ********************************************* //
    
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
     * 
     * @param Ottaa vastaan Tyypin jolla tunnistetaan minkä aika annetaan
     * @return Palauttaa kayttajan antaman keskimääräisen palveluajan
     * @author Rasmus Hyyppä
     */
    public Normal getPAJakauma(Tyyppi t) {
        double aika = getPalveluPisteAvgAika(t.getTyyppiValue());
        return new Normal(aika - (aika / 2), aika + (aika / 2));
    }
    
    /**
     * 
     * @param ppType on Tyyppi
     * @return
     */
    public double getPalveluPisteAvgAika(int ppType) {
        return asiakasPalveluAikaArray[ppType - 1];
    }
    
    /**
     * Puhelinvalikkojen odotusaika Normalina jakaumana
     * 
     * @return Normal return the pValikkoAika
     * @author Rasmus Hyyppä
     */
    public Normal getPAPuhelinValikolle() {
        return new Normal(pValikkoAika - (pValikkoAika / 2), pValikkoAika + (pValikkoAika / 2));
    }

    /**
     * Käyttäjä voi valita kuinka monta kappaletta asiakaspalvelioita on missäkin
     * linjassa
     * 
     * @return Palauttaa haetun Palvelupistetyypin kpl maaran
     * @author Rasmus Hyyppä
     */
    public int getPalveluPisteMaara(Tyyppi t) {
        return asiakasPisteMaaraArray[t.getTyyppiValue() - 1];
    }
    
    /**
     * Arpoo uniform jakaumalla tuleeko asiakas menemään väärään jonoon
     * 
     * @return True mikäli on, false mikäli ei
     * @author Rasmus Hyyppä
     */
    public boolean onkoVaaraValinta() {
        if (new Uniform(1, 100).sample() < (vaaraValintaProsentti * 100)) {
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
        return getYksityisPPMaara() + getYritysPPMaara();
    }
    
    /**
     * 
     * @return
     */
    public int getYksityisPPMaara() {
        int kokonaisMaara = 0;
        for (int i = 0; i < 4; i++) {
            kokonaisMaara += asiakasPisteMaaraArray[i];
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
            kokonaisMaara += asiakasPisteMaaraArray[i];
        }
        return kokonaisMaara;
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
    
    public boolean isNormaaliJakauma() {
        return this.normaaliJakauma;
    }
    
    public void setNormaaliJakauma(boolean normaaliJakauma) {
        this.normaaliJakauma = normaaliJakauma;
    }
    
    public double getPValikkoAika() {
        return pValikkoAika;
    }
    
    public double getAsiakasTyyppiJakauma() {
        return asiakasTyyppiJakauma;
    }
    
    public void setAsiakasTyyppiJakauma(int asiakasTyyppiJakauma) {
        this.asiakasTyyppiJakauma = asiakasTyyppiJakauma;
    }
    
    public void setAsiakasMaara(double asiakasMaara) {
        this.asiakasMaara = asiakasMaara;
    }
    
    public double getAsiakasMaara() {
        return this.asiakasMaara;
    }
    
    public double getSimulaationAika() {
        return simulaationAika;
    }
    
    public void setSimulaationAika(double simulaationAika) {
        this.simulaationAika = simulaationAika;
    }
    
    public double getAsiakkaidenKarsivallisyys() {
        return asiakkaidenKarsivallisyys;
    }

    public void setAsiakkaidenKarsivallisyys(int asiakkaidenKarsivallisyys) {
        this.asiakkaidenKarsivallisyys = asiakkaidenKarsivallisyys;
    }
    
    public double getVaaraValintaProsentti() {
        return vaaraValintaProsentti;
    }
    
    public void setVaaraValintaProsentti(double vaaraValintaProsentti) {
        this.vaaraValintaProsentti = vaaraValintaProsentti;
    }

    public void setPValikkoAika(double pValikkoAika) {
        this.pValikkoAika = pValikkoAika;
    }
    
    /**
     * @return long return the viiveAika
     */
    public long getViiveAika() {
        return viiveAika;
    }

    /**
     * @param viiveAika the viiveAika to set
     */
    public void setViiveAika(long viiveAika) {
        this.viiveAika = viiveAika;
    }
    
    // dbName, username, password get/set
     
    public String getDbName() {return dbName;}
    public void setDbName(String dbName) { this.dbName = dbName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
