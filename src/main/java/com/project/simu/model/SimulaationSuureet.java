package com.project.simu.model;

import java.text.DecimalFormat;
import java.util.Arrays;

import com.project.simu.framework.Trace;

public class SimulaationSuureet {

    private static SimulaationSuureet instance = null;
    /**
     * num = numero
     * as = asiakas
     * pp = palvelupiste
     * pa = palveluaika
     * avg = keskimääräinen
     * total = kokonais
     * pv = puhelinvalikko
     * ap = asiakaspalvelija
     */
    private int ppTotalMaara;
    private int yritysPP;
    private int yksityisPP;

    private int asLisattyJonoon;
    private int asPalveltuJonosta;
    private int asPoistunutJonosta;
    private int asReRoutattuJonosta;

    private double simulointiAika;

    private double avgAsTotalAikaAvg;
    private double asTotalAikaPP;
    private double totalPA;
    private double totalJonoAika;
    private double avgPA;
    private double avgOleskeluAika;
    private double avgJonotusAika;

    private double palveluprosentti;

    public static synchronized SimulaationSuureet getInstance() {
        if (instance == null) {
            instance = new SimulaationSuureet();
        }
        return instance;
    }

    private SimulaationSuureet() {
        resetSuureet();
    }

    public void resetSuureet() {

        // Jono kpl
        asLisattyJonoon = 0;
        asPalveltuJonosta = 0;
        asPoistunutJonosta = 0;
        asReRoutattuJonosta = 0;

        // Aikoja
        totalPA = 0;
        totalJonoAika = 0;
        asTotalAikaPP = 0;

        avgAsTotalAikaAvg = 0;
        avgPA = 0;
        avgOleskeluAika = 0;
        avgJonotusAika = 0;

        simulointiAika = UserParametrit.getInstance().getSimulaationAika();

        // Tämän hetkinen palveluprosentti
        palveluprosentti = 0;

        Asiakas.resetAsiakasUID();
        Asiakas.resetAsiakasSum();
        Palvelupiste.resetPPUID();
        yksityisPP = UserParametrit.getInstance().getPriPPMaara();
        yritysPP = UserParametrit.getInstance().getCoPPMaara();
        ppTotalMaara = UserParametrit.getInstance().getAllPPMaara();
    }

    public void tulosteet() {
        DecimalFormat dF = new DecimalFormat("#0.00");
        Trace.out(Trace.Level.INFO, "\n\n\nSimulointiaika: " + dF.format(getSimulointiAika()));
        Trace.out(Trace.Level.INFO, "Palvelupisteiden kokonaismaara: " + getPPTotalMaara());
        Trace.out(Trace.Level.INFO, "Palvelupisteitä henkilöasiakkaille: " + getYksityisPP());
        Trace.out(Trace.Level.INFO, "Palvelupisteitä yritysasiakkaille: " + getYritysPP());
        Trace.out(Trace.Level.INFO, "Asiakkaita lisatty jonoon: " + getAsJonoon());
        Trace.out(Trace.Level.INFO, "Asiakkaita palveltu jonosta: " + getAsPalveltu());
        Trace.out(Trace.Level.INFO, "Asiakkaita reRoutattu jonosta: " + getAsReRouted());
        Trace.out(Trace.Level.INFO, "Asiakkaita poistunut jonosta: " + getAsPoistunut());

        // Total ajat
        Trace.out(Trace.Level.INFO, "Asiakkaitten kokonaispalveluaika: " + dF.format(getTotalPAPP()));
        Trace.out(Trace.Level.INFO, "Asiakkaitten kokonaisjonotusaika: " + dF.format(getJonoAika()));
        Trace.out(Trace.Level.INFO, "Asiakkaitten kokonaisaika palvelupisteissa: " + dF.format(getAsTotalAikaPP()));

        // Avg suureet
        Trace.out(Trace.Level.INFO,
                "Asiakkaitten keskimääräinen oleskelu simulaatiossa: " + dF.format(getAvgAsAika()));
        Trace.out(Trace.Level.INFO,
                "Asiakkaan keskimaarainen palvelupisteen oleskeluaika: " + dF.format(getAvgPPOleskeluAika()));
        Trace.out(Trace.Level.INFO, "Palvelupisteitten keskimaarainen palveluaika: " + dF.format(getAvgTotalPA()));
        Trace.out(Trace.Level.INFO, "Palvelupisteitten keskimaarainen jonotusaika: " + dF.format(getAvgJonotusAika()));

        // Vastausprosentti / Palveluprosentti
        Trace.out(Trace.Level.INFO,
                "Palvelupisteitten palveluprosentti: " + dF.format(getPalveluprosentti()) + " %.\n");

        // Palvelupisteitä työvuoroissa
        Trace.out(Trace.Level.INFO, "Asiakaspalvelijoita työvuoroissa array: "
                + Arrays.toString(UserParametrit.getInstance().getTyoVuoroArr()));
    }

    /**
     * Muuttuja jokaiselle jonoon lisätylle asiakkaalle
     * 
     * @return int return the asiakasLisattyJonoonKpl
     * @author Rasmus Hyyppä
     */
    public int getAsJonoon() {
        return asLisattyJonoon;
    }

    /**
     * Muuttuja jokaiselle jonoon lisätylle asiakkaalle
     * Method: Yksi asiakas jonossa lisää muuttujaan
     * 
     * @author Rasmus Hyyppä
     */
    public void addAsJonoon() {
        this.asLisattyJonoon += 1;
    }

    /**
     * @param lkm setter asiakasLisattyJonoonKpl
     * @author Rasmus Hyyppä
     */
    public void setAsJonoon(int lkm) {
        this.asLisattyJonoon = lkm;
    }

    /**
     * Muuttuja jokaiselle jonoon lisätylle asiakkaalle
     * 
     * @return int return the asiakasPalveluJonostaKpl
     * @author Rasmus Hyyppä
     */
    public int getAsPalveltu() {
        return asPalveltuJonosta;
    }

    /**
     * Muuttuja jokaiselle jonosta palvelulle asiakkaalle
     * Method: Yksi asiakas palveltu lisää muuttuujaan
     * 
     * @author Rasmus Hyyppä
     */
    public void addAsPalveltu() {
        this.asPalveltuJonosta += 1;
    }

    /**
     * @param lkm setter asiakasPalveltuJonostaKpl
     * @author Rasmus Hyyppä
     */
    public void setAsPalveltu(int lkm) {
        this.asPalveltuJonosta = lkm;
    }

    /**
     * Int muuttuja jonotukseen kyllästyneille
     * Tämä lisää yhden poistuneen asiakkaan muuttujaan
     * 
     * @return int asiakasLahtenytJonostaKpl
     * @author Rasmus Hyyppä
     */
    public int getAsPoistunut() {
        return asPoistunutJonosta;
    }

    /**
     * Int muuttuja jonotukseen kyllästyneille
     * Tämä lisää yhden poistuneen asiakkaan muuttujaan
     * 
     * @author Rasmus Hyyppä
     */
    public void addAsPoistunut() {
        this.asPoistunutJonosta += 1;
    }

    /**
     * @return int Muuttuja ylläpitämään reroutattuja asiakkaita
     * @author Rasmus Hyyppä
     */
    public int getAsReRouted() {
        return asReRoutattuJonosta;
    }

    /**
     * Int Muuttuja ylläpitämään reroutattuja asiakkaita
     * Tämä method lisää yhden asiakkaan muuttujaan.
     * 
     * @author Rasmus Hyyppä
     */
    public void addAsReRouted() {
        this.asReRoutattuJonosta += 1;
    }

    /**
     * @return double return the simulointiAika
     * @author Rasmus Hyyppä
     */
    public double getSimulointiAika() {
        return simulointiAika;
    }

    /**
     * @param simulointiAika set the simuloitavaAika
     * @author Rasmus Hyyppä
     */
    public void setSimulointiAika(double simulointiAika) {
        this.simulointiAika = simulointiAika;
    }

    /**
     * Kokonais palveluaika asiakaspalvelijoilla
     * 
     * @return double return totalPAPP
     * @author Rasmus Hyyppä
     */
    public double getTotalPAPP() {
        return totalPA;
    }

    /**
     * Kokonais palveluaika asiakaspalveljoilla
     * 
     * @param totalPAPP Parametri lisätään kokonaisPAPP muuttujan arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addTotalPAPP(double totalPAPP) {
        this.totalPA += totalPAPP;
    }

    /**
     * Kokonais jonotusaika palvelupisteissä
     * 
     * @return double return the totalJonoÀikaPP
     * @author Rasmus Hyyppä
     */
    public double getJonoAika() {
        return totalJonoAika;
    }

    /**
     * Kokonais jonotusaika palvelupisteissä
     * 
     * @param totalJonoAikaPP Parametri lisätään totalJonoAikaPP muuttujan arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addJonoAika(double totalJonoAikaPP) {
        this.totalJonoAika += totalJonoAikaPP;
    }

    /**
     * Asiakkaitten kokonaisaika palvelupisteissä
     * 
     * @return double return the asiakasTotalAikaPP
     * @author Rasmus Hyyppä
     */
    public double getAsTotalAikaPP() {
        return asTotalAikaPP;
    }

    /**
     * Asiakkaitten kokonaisaika palvelupisteissä
     * 
     * @param asTotalAikaPP Parametri lisätään asiakasTotalAikaPP muuttujan
     *                      arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addAsTotalAikaPP(double asTotalAikaPP) {
        this.asTotalAikaPP += asTotalAikaPP;
    }

    /**
     * @return Keskimääräinen asiakkaan viipyminen simulaatiossa
     * @author Rasmus Hyyppä
     */
    public double getAvgAsAika() {
        return avgAsTotalAikaAvg;
    }

    /**
     * @param asTotalAikaAvg Keskimääräinen asiakkaan viipyminen
     *                       simulaatiossa
     * @author Rasmus Hyyppä
     */
    public void setAvgAsAika(double asTotalAikaAvg) {
        this.avgAsTotalAikaAvg = asTotalAikaAvg;
    }

    /**
     * Keskimääräinen palveluaika kaikki palvelupisteet yhteen laskettuna
     * 
     * @return double return the avgPA
     * @author Rasmus Hyyppä
     */
    public double getAvgTotalPA() {
        return avgPA / (ppTotalMaara - UserParametrit.getMinimiPPMaara());
    }

    /**
     * Keskimääräinen palveluaika kaikki palvelupisteet yhteen laskettuna
     * 
     * @param avgPA Parametri lisätään addAvgPA muuttujan arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addAvgTotalPA(double avgPA) {
        this.avgPA += avgPA;
    }

    /**
     * Keskimääräinen asiakkaan oleskeluaika palvelupisteessä
     * 
     * @return double return the avgPPOleskeluAika
     * @author Rasmus Hyyppä
     */
    public double getAvgPPOleskeluAika() {
        return avgOleskeluAika / (ppTotalMaara - UserParametrit.getMinimiPPMaara());
    }

    /**
     * Keskimääräinen asiakkaan oleskeluaika palvelupisteessä
     * 
     * @param Parametri lisätään muuttujan arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addAvgPPOleskeluAika(double avgPPOleskeluAika) {
        this.avgOleskeluAika += avgPPOleskeluAika;
    }

    /**
     * Kaikkien palvelupisteiden keskimääräinen jonotusaika
     * / (ppTotalMaara - UserParametrit.getMinimiPPMaara()))
     * 
     * @return double return the keskiarvoJonotusAika
     * @author Rasmus Hyyppä
     */
    public double getAvgJonotusAika() {
        return avgJonotusAika / (ppTotalMaara - UserParametrit.getMinimiPPMaara());
    }

    /**
     * Kaikkien palvelupisteiden keskimääräinen jonotusaika
     * 
     * @param avgJonotusAika Parametri lisätään avgPalveluAika muuttujan arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addAvgJonotusAika(double avgJonotusAika) {
        this.avgJonotusAika += avgJonotusAika;
    }

    /**
     * Palveluprosentti (Palveltuja asiakkaita / Jonossa olleita)
     * Myös reroutattu asiakas on palveltu
     * 
     * @return double return the palveluprosentti
     * @author Rasmus Hyyppä
     */
    public double getPalveluprosentti() {
        return palveluprosentti / (ppTotalMaara - UserParametrit.getMinimiPPMaara());
    }

    /**
     * Palveluprosentti (Palveltuja asiakkaita / Jonossa olleita)
     * Myös reroutattu asiakas on palveltu
     * 
     * @param palveluprosentti Parametri lisätään avgPalveluAika muuttujan arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addPalveluprosentti(double palveluprosentti) {
        this.palveluprosentti += palveluprosentti;
    }

    /**
     * Palvelupisteiden kokonaismäärä
     * 
     * @return int return ppTotalMaara
     * @author Rasmus Hyyppä
     */
    public int getPPTotalMaara() {
        return ppTotalMaara;
    }

    /**
     * Palvelupisteden kokonaismäärä
     * 
     * @param ppTotalMaara Parametri asettaa ppTotalMaaran
     * @author Rasmus Hyyppä
     */
    public void setPPTotalMaara(int ppTotalMaara) {
        this.ppTotalMaara = ppTotalMaara;
    }

    /**
     * Yrityspalvelupisteiden määrä
     * 
     * @return int return the yritysPP
     * @author Rasmus Hyyppä
     */
    public int getYritysPP() {
        return yritysPP;
    }

    /**
     * Yrityspalvelupisteden määrä
     * 
     * @param yritysPP the yritysPP to set
     * @author Rasmus Hyyppä
     */
    public void setYritysPP(int yritysPP) {
        this.yritysPP = yritysPP;
    }

    /**
     * Yksityispalvelupisteiden määrä
     * 
     * @return int return the yksityisPP
     * @author Rasmus Hyyppä
     */
    public int getYksityisPP() {
        return yksityisPP;
    }

    /**
     * Yksityispalvelupisteiden määrä
     * 
     * @param yksityisPP the yksityisPP to set
     * @author Rasmus Hyyppä
     */
    public void setYksityisPP(int yksityisPP) {
        this.yksityisPP = yksityisPP;
    }

}
