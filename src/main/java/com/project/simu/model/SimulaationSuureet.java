package com.project.simu.model;

import java.text.DecimalFormat;

import com.project.simu.framework.Trace;

public class SimulaationSuureet {

    private static SimulaationSuureet instance = null;
    /**
     * pp = palvelupiste
     * pa = palveluaika
     * avg = keskimääräinen
     * total = kokonais
     */
    private int ppTotalMaara;
    private int yritysPP;
    private int yksityisPP;

    private int asiakasLisattyJonoonKpl;
    private int asiakasPalveltuJonostaKpl;
    private int asiakasLahtenytJonostaKpl;
    private int asiakasReRoutattuJonostaKpl;

    private double simulointiAika;
    private double avgAsiakasViipymisAika;
    private double asiakasTotalAikaPP;
    private double totalPAPP;
    private double totalJonoAikaPP;
    private double avgPA;
    private double avgOleskeluPPAika;
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
        asiakasLisattyJonoonKpl = 0;
        asiakasPalveltuJonostaKpl = 0;
        asiakasLahtenytJonostaKpl = 0;
        asiakasReRoutattuJonostaKpl = 0;

        // Aikoja
        totalPAPP = 0;
        totalJonoAikaPP = 0;
        asiakasTotalAikaPP = 0;
        avgAsiakasViipymisAika = 0;
        avgPA = 0;
        avgOleskeluPPAika = 0;
        avgJonotusAika = 0;

        // Tämän hetkinen palveluprosentti
        palveluprosentti = 0;

        Asiakas.resetAsiakasUID();
        Asiakas.resetAsiakasSum();
        Palvelupiste.resetPpID();
        yksityisPP = UserParametrit.getInstance().getYksityisPPMaara();
        yritysPP = UserParametrit.getInstance().getYritysPPMaara();
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
        Trace.out(Trace.Level.INFO, "Asiakkaita lisatty jonoon: " + asiakasLisattyJonoonKpl);
        Trace.out(Trace.Level.INFO, "Asiakkaita palveltu jonosta: " + asiakasPalveltuJonostaKpl);
        Trace.out(Trace.Level.INFO, "Asiakkaita reRoutattu jonosta: " + asiakasReRoutattuJonostaKpl);
        Trace.out(Trace.Level.INFO, "Asiakkaita poistunut jonosta: " + asiakasLahtenytJonostaKpl);
        Trace.out(Trace.Level.INFO, "Asiakkaitten kokonaispalveluaika: " + dF.format(totalPAPP));
        Trace.out(Trace.Level.INFO, "Asiakkaitten kokonaisjonotusaika: " + dF.format(totalJonoAikaPP));
        Trace.out(Trace.Level.INFO, "Asiakkaan keskimaarainen viipymisaika simulaatiossa: "
                + dF.format(avgOleskeluPPAika));
        Trace.out(Trace.Level.INFO,
                "Asiakkaitten kokonaisaika palvelupisteissa: " + dF.format(asiakasTotalAikaPP));
        Trace.out(Trace.Level.INFO,
                "Asiakkaitten keskimääräinen viipyminen simulaatiossa: " + dF.format(avgAsiakasViipymisAika));
        Trace.out(Trace.Level.INFO, "Palvelupisteitten keskimaarainen palveluaika: " + dF.format(avgPA));
        Trace.out(Trace.Level.INFO, "Palvelupisteitten keskimaarainen jonotusaika: "
                + dF.format(avgJonotusAika / ppTotalMaara));
        Trace.out(Trace.Level.INFO,
                "Palvelupisteitten palveluprosentti: " + dF.format(getPalveluprosentti()) + " %.\n");

    }

    /**
     * Muuttuja jokaiselle jonoon lisätylle asiakkaalle
     * 
     * @return int return the asiakasLisattyJonoonKpl
     * @author Rasmus Hyyppä
     */
    public int getAsiakasLisattyJonoonKpl() {
        return asiakasLisattyJonoonKpl;
    }

    /**
     * Muuttuja jokaiselle jonoon lisätylle asiakkaalle
     * Method: Yksi asiakas jonossa lisää muuttujaan
     * 
     * @author Rasmus Hyyppä
     */
    public void asiakasLisattyJonoon() {
        this.asiakasLisattyJonoonKpl += 1;
    }

    /**
     * @param lkm setter asiakasLisattyJonoonKpl
     * @author Rasmus Hyyppä
     */
    public void setAsiakasLisattyJonoon(int lkm) {
        this.asiakasLisattyJonoonKpl = lkm;
    }

    /**
     * Muuttuja jokaiselle jonoon lisätylle asiakkaalle
     * 
     * @return int return the asiakasPalveluJonostaKpl
     * @author Rasmus Hyyppä
     */
    public int getAsiakasPalveltuJonostaKpl() {
        return asiakasPalveltuJonostaKpl;
    }

    /**
     * Muuttuja jokaiselle jonosta palvelulle asiakkaalle
     * Method: Yksi asiakas palveltu lisää muuttuujaan
     * 
     * @author Rasmus Hyyppä
     */
    public void asiakasPalveltuJonosta() {
        this.asiakasPalveltuJonostaKpl += 1;
    }

    /**
     * @param lkm setter asiakasPalveltuJonostaKpl
     * @author Rasmus Hyyppä
     */
    public void setAsiakasPalveltuJonostaKpl(int lkm) {
        this.asiakasPalveltuJonostaKpl = lkm;
    }

    /**
     * Int muuttuja jonotukseen kyllästyneille
     * Tämä lisää yhden poistuneen asiakkaan muuttujaan
     * 
     * @return int asiakasLahtenytJonostaKpl
     * @author Rasmus Hyyppä
     */
    public int getAsiakasLahtenytJonostaKpl() {
        return asiakasLahtenytJonostaKpl;
    }

    /**
     * Int muuttuja jonotukseen kyllästyneille
     * Tämä lisää yhden poistuneen asiakkaan muuttujaan
     * 
     * @author Rasmus Hyyppä
     */
    public void asiakasLahtenytJonostaKpl() {
        this.asiakasLahtenytJonostaKpl += 1;
    }

    /**
     * @return int Muuttuja ylläpitämään reroutattuja asiakkaita
     * @author Rasmus Hyyppä
     */
    public int getAsiakasReRoutedPPKpl() {
        return asiakasReRoutattuJonostaKpl;
    }

    /**
     * Int Muuttuja ylläpitämään reroutattuja asiakkaita
     * Tämä method lisää yhden asiakkaan muuttujaan.
     * 
     * @author Rasmus Hyyppä
     */
    public void asiakasReRoutedPPKpl() {
        this.asiakasReRoutattuJonostaKpl += 1;
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
    public double getAsiakasTotalAikaPP() {
        return asiakasTotalAikaPP;
    }

    /**
     * Asiakkaitten kokonaisaika palvelupisteissä
     * 
     * @param asiakasTotalAikaPP Parametri settaa muuttujan arvoon
     * @author Rasmus Hyyppä
     */
    public void setAsiakasTotalAikaPP(double asiakasTotalAikaPP) {
        this.asiakasTotalAikaPP = asiakasTotalAikaPP;
    }

    /**
     * @return Keskimääräinen asiakkaan viipyminen simulaatiossa
     * @author Rasmus Hyyppä
     */
    public double getAsiakasViipymisAikaAvg() {
        return avgAsiakasViipymisAika;
    }

    /**
     * @param asiakasViipymisAikaAvg Keskimääräinen asiakkaan viipyminen
     *                               simulaatiossa
     * @author Rasmus Hyyppä
     */
    public void setAsiakasViipymisAikaAvg(double asiakasViipymisAikaAvg) {
        this.avgAsiakasViipymisAika = asiakasViipymisAikaAvg;
    }

    /**
     * Keskimääräinen palveluaika kaikki palvelupisteet yhteen laskettuna
     * 
     * @return double return the avgPA
     * @author Rasmus Hyyppä
     */
    public double getAvgPA() {
        return avgPA;
    }

    /**
     * Keskimääräinen palveluaika kaikki palvelupisteet yhteen laskettuna
     * 
     * @param avgPA Parametri lisätään avgPalveluAika muuttujan arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addAvgPalveluAika(double avgPA) {
        this.avgPA += avgPA;
    }

    /**
     * Keskimääräinen asiakkaan oleskeluaika palvelupisteessä
     * 
     * @return double return the avgViipymisAika
     * @author Rasmus Hyyppä
     */
    public double getAvgOleskeluPPAika() {
        return avgOleskeluPPAika;
    }

    /**
     * Keskimääräinen asiakkaan oleskeluaika palvelupisteessä
     * 
     * @param Parametri lisätään avgPalveluAika muuttujan arvoon +=
     * @author Rasmus Hyyppä
     */
    public void addAvgOleskeluPPAika(double avgViipymisAika) {
        this.avgOleskeluPPAika += avgViipymisAika;
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
    public void setPalveluPisteidenKokonaisMaara(int ppTotalMaara) {
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
