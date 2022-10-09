package com.project.simu.model;

import java.text.DecimalFormat;
import java.util.HashMap;

import com.project.simu.constants.Tyyppi;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Trace;

public class SimulaatioData {
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

    Parametrit uP;

    // Hashmap for view
    HashMap<String, int[]> suureStatusMap;
    // Arrayt
    private int[] palveluMaaraArr;
    private int[] palveluJonoArr;
    private int[] palveluQuitterArr;
    private int[] palveluReRoutedArr;
    private int[] palveluVuoroArr;
    private int[] palveluVarattuArr;
    private int[] palveluTotalArr;

    private double[] palveluAikaArr;
    private double[] jonoAikaArr;

    // *************************RANDOM SHIT DELETE ALL*****************************
    private double jonoATotal;
    private double avgPalveluATotal;
    private double avgPPViipymisATotal;
    private double avgJonotusATotal;

    public SimulaatioData(Parametrit parametrit) {
        uP = parametrit;
        alustaSuureet();
    }

    public void alustaSuureet() {
        // Oleeliset
        simulointiAika = Kello.getInstance().getAika();
        palveluprosentti = 0;
        asTotalMaara = 0;
        avgAvgAsAikaSim = 0;
        asPalveltu = 0;
        asPoistunut = 0;
        asReRoutattu = 0;

        suureStatusMap = new HashMap<String, int[]>();
        reloadHashMap();

        jonoAikaArr = new double[Tyyppi.maxSize];
        palveluAikaArr = new double[Tyyppi.maxSize];
        // Reset asiakas & palvelupiste luokat
        Asiakas.resetAsiakasUID();
        Asiakas.resetAsiakasSum();
        Palvelupiste.resetPPUID();
        // *************************RANDOM SHIT DELETE
        // ALL*******************************
        jonoATotal = 0;
        avgPalveluATotal = 0;
        avgPPViipymisATotal = 0;
        avgJonotusATotal = 0;
    }

    private void reloadHashMap() {
        palveluMaaraArr = new int[Tyyppi.maxSize];
        palveluJonoArr = new int[Tyyppi.maxSize];
        palveluQuitterArr = new int[Tyyppi.maxSize];
        palveluReRoutedArr = new int[Tyyppi.maxSize];
        palveluVuoroArr = new int[Tyyppi.maxSize];
        palveluVarattuArr = new int[Tyyppi.maxSize];
        palveluTotalArr = new int[] { (int) asTotalMaara, asPalveltu, asPoistunut, asReRoutattu, (int) simulointiAika,
                (int) palveluprosentti };
        suureStatusMap.put("Palveltu", palveluMaaraArr);
        suureStatusMap.put("Jonossa", palveluJonoArr);
        suureStatusMap.put("Quitter", palveluQuitterArr);
        suureStatusMap.put("ReRouted", palveluReRoutedArr);
        suureStatusMap.put("Tyovuorossa", palveluVuoroArr);
        suureStatusMap.put("Totalit", palveluTotalArr);
        suureStatusMap.put("Varattu", palveluVarattuArr);
    }

    // Oleeliset
    public double getSimulointiAika() {
        return simulointiAika;
    }

    public void setSimulointiAika(double simulointiAika) {
        this.simulointiAika = simulointiAika;
    }

    public double getPalveluprosentti() {
        return palveluprosentti / (uP.getAllPPMaara() - Parametrit.getMinPPMaara());
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

    // DATABASE TALLENNUS anna tyyppi numero -> saa avg palveluaika
    public double getPalveluAika(int ppType) {
        return palveluAikaArr[ppType - 1] / uP.getPPMaara(ppType);
    }

    // DATABASE TALLENNUS anna tyyppi numero -> saa avg jonotusaika
    public double getJonoAika(int ppType) {
        return this.jonoAikaArr[ppType - 1] / uP.getPPMaara(ppType);
    }

    // DATABASE TALLENNUS anna tyyppi numero -> saa palvellut asiakkaat
    public int getPalveluMaara(int ppType) {
        return palveluMaaraArr[ppType - 1];
    }

    // Viewi juttuja
    public int getJonoMaara(int ppType) {
        return palveluJonoArr[ppType - 1];
    }

    // Viewi juttuja
    public int getQuitterMaara(int ppType) {
        return palveluQuitterArr[ppType - 1];
    }

    // Viewi juttuja
    public int getReRoutedMaara(int ppType) {
        return palveluReRoutedArr[ppType - 1];
    }

    public void addVuoroMaara(int ppType, boolean tulos) {
        if (tulos) {
            palveluVuoroArr[ppType]++;
        }
    }

    // Viewi juttuja
    public int getVuoroMaara(int ppType) {
        return palveluVuoroArr[ppType - 1];
    }

    public void addVarattuMaara(int ppType, boolean tulos) {
        if (tulos) {
            palveluVarattuArr[ppType]++;
        }
    }

    public HashMap<String, int[]> getPPStatus(Palvelupiste[] palvelupisteet) {
        reloadHashMap();
        for (Palvelupiste pp : palvelupisteet) {
            int ppTyyppi = pp.getPPTyyppi().getTypeValue() - 1; // -1 for array slot
            palveluMaaraArr[ppTyyppi] += pp.getAsPalveltuJonosta();
            palveluJonoArr[ppTyyppi] += pp.getJonoKoko();
            palveluQuitterArr[ppTyyppi] += pp.getAsPoistunutJonosta();
            if (ppTyyppi < 8) {
                palveluReRoutedArr[ppTyyppi] += ((Asiakaspalvelija) pp).getAsReRoutedJonosta();
                addVuoroMaara(ppTyyppi, pp.getOnPaikalla());
                addVarattuMaara(ppTyyppi, pp.onVarattu());
            }
        }
        return suureStatusMap;
    }

    public void getSuureetPP(Palvelupiste pp) {
        int ppTyyppi = pp.getPPTyyppi().getTypeValue() - 1;
        palveluAikaArr[ppTyyppi] += pp.getAvgPalveluAika();
        jonoAikaArr[ppTyyppi] += pp.getAvgJonotusAika();
        if (ppTyyppi < 8) {
            palveluprosentti += pp.getPProsentti();
            jonoATotal += pp.getJonoAika();
            avgJonotusATotal += pp.getAvgJonotusAika();
            avgPPViipymisATotal += pp.getAvgViipyminenPP();
            avgPalveluATotal += pp.getAvgPalveluAika();
        }
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

    // *************************RANDOM SHIT DELETE ALL****************************
    public double getJonoATotal() {
        return jonoATotal;
    }

    public double getPPViipymisATotal() {
        return avgPPViipymisATotal / (uP.getAllPPMaara() - Parametrit.getMinPPMaara());
    }

    public double getJonotusATotal() {
        return avgJonotusATotal / (uP.getAllPPMaara() - Parametrit.getMinPPMaara());
    }

    public double getPalveluATotal() {
        return avgPalveluATotal / (uP.getAllPPMaara() - Parametrit.getMinPPMaara());
    }
}