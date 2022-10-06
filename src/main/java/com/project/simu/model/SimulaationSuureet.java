package com.project.simu.model;

import java.text.DecimalFormat;

import com.project.simu.constants.Tyovuoro;
import com.project.simu.constants.Tyyppi;
import com.project.simu.framework.Trace;

public class SimulaationSuureet {
    // *************************RANDOM SHIT DELETE ALL*****************************
    // private static SimulaationSuureet instance = null;
    // *************************RANDOM SHIT DELETE ALL*****************************

    // Kesto
    private double simulointiAika;
    // Vastausprosentti / Palveluprosentti
    private double palveluprosentti;
    // Asiakas määrä simulaatiossa
    private long asTotalMaara;
    // Asiakkaita palveltu simulaatiossa
    private int asPalveltu;
    // Asiakkaita kyllästynyt jonottamaan simulaatiossa
    private int asPoistunut;
    // Asiakkaita reroutattu väärän valinnan vuoksi
    private int asReRoutattu;
    // Asiakkaiden kokonais viipyminen keskimääräisesti simulaatiossa
    private double avgAvgAsAikaSim;

    // Arrayt
    private int[] palveluMaaraArr;
    private double[] palveluAikaArr;
    private double[] jonoAikaArr;
    private int[] tyoVuoroArr;

    // *************************RANDOM SHIT DELETE ALL*****************************
    private double jonoATotal;
    private double avgPalveluATotal;
    private double avgPPViipymisATotal;
    private double avgJonotusATotal;

    // public static synchronized SimulaationSuureet getInstance() {
    // if (instance == null) {
    // instance = new SimulaationSuureet();
    // }
    // return instance;
    // }
    // *************************RANDOM SHIT DELETE ALL*****************************

    public SimulaationSuureet() {
        resetSuureet();
    }

    public void resetSuureet() {
        // Oleeliset
        simulointiAika = UserParametrit.getInstance().getSimulaationAika();
        palveluprosentti = 0;
        asTotalMaara = 0;
        avgAvgAsAikaSim = 0;
        asPalveltu = 0;
        asPoistunut = 0;
        asReRoutattu = 0;
        palveluMaaraArr = new int[Tyyppi.size];
        palveluAikaArr = new double[Tyyppi.size];
        jonoAikaArr = new double[Tyyppi.size];

        // Reset asiakas & palvelupiste luokat
        Asiakas.resetAsiakasUID();
        Asiakas.resetAsiakasSum();
        Palvelupiste.resetPPUID();
        tyoVuoroArr = new int[] { 0, 0, 0, 0, 0 };

        // *************************RANDOM SHIT DELETE
        // ALL*******************************
        jonoATotal = 0;
        avgPalveluATotal = 0;
        avgPPViipymisATotal = 0;
        avgJonotusATotal = 0;
        // *************************RANDOM SHIT DELETE ALL*****************************
    }

    public void tulosteet() {
        DecimalFormat dF = new DecimalFormat("#0.00");
        // Kesto
        Trace.out(Trace.Level.INFO, "\n\n\nSimulointiaika: " + dF.format(getSimulointiAika()));
        // Vastausprosentti / Palveluprosentti
        Trace.out(Trace.Level.INFO, "Palvelupisteiden palveluprosentti: " + dF.format(getPalveluprosentti()) + " %.");
        // As Määrä
        Trace.out(Trace.Level.INFO, "Asiakkaita lisatty jonoon: " + getAsTotalMaara());
        // Palveltuja asiakkaita
        Trace.out(Trace.Level.INFO, "Asiakkaita palveltu jonosta: " + getAsPalveltu());
        // Quitterit
        Trace.out(Trace.Level.INFO, "Asiakkaita poistunut jonosta: " + getAsPoistunut());
        // Reroutatut
        Trace.out(Trace.Level.INFO, "Asiakkaita reRoutattu jonosta: " + getAsReRouted());
        // Keskiläpimeno
        Trace.out(Trace.Level.INFO,
                "Asiakkaitten avg viipyminen simulaatiossa: " + dF.format(getAvgAsAikaSim()) + "\n");

        // Uudet printit, palvelumaara & palveluaika
        for (int i = 0; i < palveluMaaraArr.length; i++) {
            Tyyppi t = Tyyppi.values()[i];
            Trace.out(Trace.Level.INFO, "Tyyppi: " + t + ", Palveltuja as: " + getPalveluMaara(t.getTypeValue()));
            Trace.out(Trace.Level.INFO,
                    "Tyyppi: " + t + ", Palveluaika avg: " + dF.format(getPalveluAika(t.getTypeValue())) + "\n");
        }

    }

    // Oleeliset
    public double getSimulointiAika() {
        return simulointiAika;
    }

    public void setSimulointiAika(double simulointiAika) {
        this.simulointiAika = simulointiAika;
    }

    public double getPalveluprosentti() {
        return palveluprosentti / (UserParametrit.getInstance().getAllPPMaara());
    }

    public void addPalveluprosentti(double palveluprosentti) {
        this.palveluprosentti += palveluprosentti;
    }

    public long getAsTotalMaara() {
        return asTotalMaara;
    }

    public void setAsTotalMaara(long sum) {
        asTotalMaara = sum;
    }

    public int getAsPalveltu() {
        return asPalveltu;
    }

    public void addAsPalveltu() {
        this.asPalveltu += 1;
    }

    public int getAsPoistunut() {
        return asPoistunut;
    }

    public void addAsPoistunut() {
        this.asPoistunut += 1;
    }

    public int getAsReRouted() {
        return asReRoutattu;
    }

    public void addAsReRouted() {
        this.asReRoutattu += 1;
    }

    public double getAvgAsAikaSim() {
        return avgAvgAsAikaSim;
    }

    public void setAvgAsAikaSim(double avgAvgAsAikaSim) {
        this.avgAvgAsAikaSim = avgAvgAsAikaSim;
    }

    public void addPalveluMaara(int ppType, int määrä) {
        palveluMaaraArr[ppType - 1] += määrä;
    }

    // DATABASE TALLENNUS anna tyyppi numero -> saa palvellut asiakkaat
    public int getPalveluMaara(int ppType) {
        return palveluMaaraArr[ppType - 1];
    }

    public void addPalveluAika(int ppType, double aika) {
        palveluAikaArr[ppType - 1] += aika;
    }

    // DATABASE TALLENNUS anna tyyppi numero -> saa avg palveluaika
    public double getPalveluAika(int ppType) {
        return palveluAikaArr[ppType - 1] / UserParametrit.getInstance().getPPMaara(ppType);
    }

    public void addJonoAika(int ppType, double aika) {
        this.jonoAikaArr[ppType - 1] += aika;
    }

    // DATABASE TALLENNUS anna tyyppi numero -> saa avg jonotusaika
    public double getJonoAika(int ppType) {
        return this.jonoAikaArr[ppType - 1] / UserParametrit.getInstance().getPPMaara(ppType);
    }

    public void getSuureetPP(Palvelupiste pp) {
        int ppTyyppi = pp.getPPTyyppi().getTypeValue();
        addPalveluMaara(ppTyyppi, pp.getAsPalveltuJonosta());
        addPalveluAika(ppTyyppi, pp.getAvgPalveluAika());
        addJonoAika(ppTyyppi, pp.getAvgJonotusAika());
        addPalveluprosentti(pp.getPProsentti());
        if (ppTyyppi < 9) {
            addJonoATotal(pp.getJonoAika());
            addJonotusATotal(pp.getAvgJonotusAika());
            addPPViipymisATotal(pp.getAvgViipyminenPP());
            addPalveluATotal(pp.getAvgPalveluAika());
        }
    }

    public void addTyoVuoroArr(int tyoVuoroType) {
        this.tyoVuoroArr[tyoVuoroType]++;
    }

    public int[] getTyoVuoroArr() {
        return tyoVuoroArr;
    }

    public int getMinTyoVuoroArr() {
        int minValue = tyoVuoroArr[0];
        int minIndex = 0;
        for (int i = 0; i < tyoVuoroArr.length; i++) {
            if (tyoVuoroArr[i] < minValue) {
                minValue = tyoVuoroArr[i];
                minIndex = i;
            }
        }
        System.out.println("Pienintä työvuoroa on: " + minValue + ", index: " + Tyovuoro.values()[minIndex]);
        return minIndex;
    }

    // *************************RANDOM SHIT DELETE ALL****************************
    public double getJonoATotal() {
        return jonoATotal;
    }

    public void addJonoATotal(double jonoATotal) {
        this.jonoATotal += jonoATotal;
    }

    public double getPPViipymisATotal() {
        return avgPPViipymisATotal / (UserParametrit.getInstance().getAllPPMaara() - UserParametrit.getMinPPMaara());
    }

    public void addPPViipymisATotal(double avgAsSimATotal) {
        this.avgPPViipymisATotal += avgAsSimATotal;
    }

    public double getJonotusATotal() {
        return avgJonotusATotal / (UserParametrit.getInstance().getAllPPMaara() - UserParametrit.getMinPPMaara());
    }

    public void addJonotusATotal(double avgJonotusATotal) {
        this.avgJonotusATotal += avgJonotusATotal;
    }

    public double getPalveluATotal() {
        return avgPalveluATotal / (UserParametrit.getInstance().getAllPPMaara() - UserParametrit.getMinPPMaara());
    }

    public void addPalveluATotal(double avgPalveluATotal) {
        this.avgPalveluATotal += avgPalveluATotal;
    }
    // *************************RANDOM SHIT DELETE ALL*****************************
}