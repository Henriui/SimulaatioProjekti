package com.project.simu.model;

import java.text.DecimalFormat;

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
     */
    private int ppTotalMaara;
    private int yritysPP;
    private int yksityisPP;

    private int asLisattyJonoonKpl;
    private int asPalveltuJonostaKpl;
    private int asLahtenytJonostaKpl;
    private int asReRoutattuJonostaKpl;

    private double simulointiAika;
    private double avgAsTotalAikaAvg;
    private double asTotalAikaPP;
    private double totalPAPP;
    private double totalJonoAikaPP;
    private double avgTotalPA;
    private double avgPPOleskeluAika;
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
        asLisattyJonoonKpl = 0;
        asPalveltuJonostaKpl = 0;
        asLahtenytJonostaKpl = 0;
        asReRoutattuJonostaKpl = 0;

        // Aikoja
        totalPAPP = 0;
        totalJonoAikaPP = 0;
        asTotalAikaPP = 0;
        avgAsTotalAikaAvg = 0;
        avgTotalPA = 0;
        avgPPOleskeluAika = 0;
        avgJonotusAika = 0;

        // Tämän hetkinen palveluprosentti
        palveluprosentti = 0;

        Asiakas.resetAsiakasUID();
        Asiakas.resetAsiakasSum();
        Palvelupiste.resetPPUID();
        yksityisPP = UserParametrit.getInstance().getPriPPMaara();
        yritysPP = UserParametrit.getInstance().getCoPPMaara();
        ppTotalMaara = UserParametrit.getInstance().getAllPPMaara();
    }

    /**
     * Work in progress update method
     * public void updateSuureet(int t, INewSimulationControllerMtoV kontrolleri,
     * int p, int yksityis, int yritys) {
     * }
     */

    public void tulosteet() {
        DecimalFormat dF = new DecimalFormat("#0.00");
        Trace.out(Trace.Level.INFO, "\n\n\nSimulointiaika: " + dF.format(simulointiAika));
        Trace.out(Trace.Level.INFO, "Palvelupisteiden kokonaismaara: " + ppTotalMaara);
        Trace.out(Trace.Level.INFO, "Palvelupisteitä henkilöasiakkaille: " + getYksityisPP());
        Trace.out(Trace.Level.INFO, "Palvelupisteitä yritysasiakkaille: " + getYritysPP());
        Trace.out(Trace.Level.INFO, "Asiakkaita lisatty jonoon: " + asLisattyJonoonKpl);
        Trace.out(Trace.Level.INFO, "Asiakkaita palveltu jonosta: " + asPalveltuJonostaKpl);
        Trace.out(Trace.Level.INFO, "Asiakkaita reRoutattu jonosta: " + asReRoutattuJonostaKpl);
        Trace.out(Trace.Level.INFO, "Asiakkaita poistunut jonosta: " + asLahtenytJonostaKpl);
        Trace.out(Trace.Level.INFO, "Asiakkaitten kokonaispalveluaika: " + dF.format(totalPAPP));
        Trace.out(Trace.Level.INFO, "Asiakkaitten kokonaisjonotusaika: " + dF.format(totalJonoAikaPP));
        Trace.out(Trace.Level.INFO, "Asiakkaan keskimaarainen palvelupisteen oleskeluaika: "
                + dF.format(avgPPOleskeluAika));
        Trace.out(Trace.Level.INFO,
                "Asiakkaitten kokonaisaika palvelupisteissa: " + dF.format(asTotalAikaPP));
        Trace.out(Trace.Level.INFO,
                "Asiakkaitten keskimääräinen oleskelu simulaatiossa: " + dF.format(avgAsTotalAikaAvg));
        Trace.out(Trace.Level.INFO, "Palvelupisteitten keskimaarainen palveluaika: " + dF.format(avgTotalPA));
        Trace.out(Trace.Level.INFO, "Palvelupisteitten keskimaarainen jonotusaika: "
                + dF.format(avgJonotusAika));
        Trace.out(Trace.Level.INFO,
                "Palvelupisteitten palveluprosentti: " + dF.format(getPalveluprosentti()) + " %.\n");

    }

    /**
     * Muuttuja jokaiselle jonoon lisätylle asiakkaalle
     * 
     * @return int return the asiakasLisattyJonoonKpl
     * @author Rasmus Hyyppä
     */
    public int getAsLisattyJonoonKpl() {
        return asLisattyJonoonKpl;
    }

    /**
     * Muuttuja jokaiselle jonoon lisätylle asiakkaalle
     * Method: Yksi asiakas jonossa lisää muuttujaan
     * 
     * @author Rasmus Hyyppä
     */
    public void asLisattyJonoon() {
        this.asLisattyJonoonKpl += 1;
    }

    /**
     * @param lkm setter asiakasLisattyJonoonKpl
     * @author Rasmus Hyyppä
     */
    public void setAsLisattyJonoon(int lkm) {
        this.asLisattyJonoonKpl = lkm;
    }

    /**
     * Muuttuja jokaiselle jonoon lisätylle asiakkaalle
     * 
     * @return int return the asiakasPalveluJonostaKpl
     * @author Rasmus Hyyppä
     */
    public int getAsPalveltuJonostaKpl() {
        return asPalveltuJonostaKpl;
    }

    /**
     * Muuttuja jokaiselle jonosta palvelulle asiakkaalle
     * Method: Yksi asiakas palveltu lisää muuttuujaan
     * 
     * @author Rasmus Hyyppä
     */
    public void asPalveltuJonosta() {
        this.asPalveltuJonostaKpl += 1;
    }

    /**
     * @param lkm setter asiakasPalveltuJonostaKpl
     * @author Rasmus Hyyppä
     */
    public void setAsPalveltuJonostaKpl(int lkm) {
        this.asPalveltuJonostaKpl = lkm;
    }

    /**
     * Int muuttuja jonotukseen kyllästyneille
     * Tämä lisää yhden poistuneen asiakkaan muuttujaan
     * 
     * @return int asiakasLahtenytJonostaKpl
     * @author Rasmus Hyyppä
     */
    public int getAsLahtenytJonostaKpl() {
        return asLahtenytJonostaKpl;
    }

    /**
     * Int muuttuja jonotukseen kyllästyneille
     * Tämä lisää yhden poistuneen asiakkaan muuttujaan
     * 
     * @author Rasmus Hyyppä
     */
    public void asLahtenytJonostaKpl() {
        this.asLahtenytJonostaKpl += 1;
    }

    /**
     * @return int Muuttuja ylläpitämään reroutattuja asiakkaita
     * @author Rasmus Hyyppä
     */
    public int getAsReRoutedPPKpl() {
        return asReRoutattuJonostaKpl;
    }

    /**
     * Int Muuttuja ylläpitämään reroutattuja asiakkaita
     * Tämä method lisää yhden asiakkaan muuttujaan.
     * 
     * @author Rasmus Hyyppä
     */
    public void asReRoutedPPKpl() {
        this.asReRoutattuJonostaKpl += 1;
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
     * Kokonais palveluaika palvelupisteissä
     * 
     * @return double return totalPAPP
     * @author Rasmus Hyyppä
     */
    public double getTotalPAPP() {
        return totalPAPP;
    }

    /**
     * Kokonais palveluaika palvelupisteissä
     * 
     * @param totalPAPP Parametri lisätään kokonaisPAPP muuttujan arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addTotalPAPP(double totalPAPP) {
        this.totalPAPP += totalPAPP;
    }

    /**
     * Kokonais jonotusaika palvelupisteissä
     * 
     * @return double return the totalJonoÀikaPP
     * @author Rasmus Hyyppä
     */
    public double getTotalJonoAikaPP() {
        return totalJonoAikaPP;
    }

    /**
     * Kokonais jonotusaika palvelupisteissä
     * 
     * @param totalJonoAikaPP Parametri lisätään totalJonoAikaPP muuttujan arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addTotalJonoAikaPP(double totalJonoAikaPP) {
        this.totalJonoAikaPP += totalJonoAikaPP;
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
    public double getAsTotalAikaAvg() {
        return avgAsTotalAikaAvg;
    }

    /**
     * @param asTotalAikaAvg Keskimääräinen asiakkaan viipyminen
     *                       simulaatiossa
     * @author Rasmus Hyyppä
     */
    public void setAsTotalAikaAvg(double asTotalAikaAvg) {
        this.avgAsTotalAikaAvg = asTotalAikaAvg;
    }

    /**
     * Keskimääräinen palveluaika kaikki palvelupisteet yhteen laskettuna
     * 
     * @return double return the avgPA
     * @author Rasmus Hyyppä
     */
    public double getAvgTotalPA() {
        return avgTotalPA;
    }

    /**
     * Keskimääräinen palveluaika kaikki palvelupisteet yhteen laskettuna
     * 
     * @param avgPA Parametri lisätään addAvgPA muuttujan arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addAvgTotalPA(double avgPA) {
        this.avgTotalPA += avgPA;
    }

    /**
     * Keskimääräinen asiakkaan oleskeluaika palvelupisteessä
     * 
     * @return double return the avgPPOleskeluAika
     * @author Rasmus Hyyppä
     */
    public double getAvgPPOleskeluAika() {
        return avgPPOleskeluAika / ppTotalMaara;
    }

    /**
     * Keskimääräinen asiakkaan oleskeluaika palvelupisteessä
     * 
     * @param Parametri lisätään muuttujan arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addAvgPPOleskeluAika(double avgPPOleskeluAika) {
        this.avgPPOleskeluAika += avgPPOleskeluAika;
    }

    /**
     * Kaikkien palvelupisteiden keskimääräinen jonotusaika
     * 
     * @return double return the keskiarvoJonotusAika
     * @author Rasmus Hyyppä
     */
    public double getAvgJonotusAika() {
        return avgJonotusAika;
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
        return palveluprosentti / ppTotalMaara;
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
