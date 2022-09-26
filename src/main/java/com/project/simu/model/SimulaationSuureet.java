package com.project.simu.model;

public class SimulaationSuureet {

    private static SimulaationSuureet instance = null;

    private int palveluPisteidenKokonaisMaara;

    private int asiakkaitaLisattyJonoonKpl;
    private int asiakkaitaPalveltuJonostaKpl;
    private int asiakkaitaPoistunutJonostaKpl;
    private int asiakkaitaReRoutattuJonostaKpl;

    private int yritysPalvelupisteita;
    private int yksityisPalvelupisteita;

    private double simulointiAika;
    private double kokonaisPalveluAikaPalvelupisteessa;
    private double kokonaisJonoAikaPalvelupisteessa;
    private double asiakkaittenKokonaisAikaPalvelupisteessa;
    private double asiakkaanKeskiArvoViipyminenSimulaatiossa;
    private double keskimaaranenPalveluAika;
    private double keskimaarainenOleskeluAika;
    private double keskiarvoJonotusAika;

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

        // Nice to have?
        yksityisPalvelupisteita = 0;
        yritysPalvelupisteita = 0;
        palveluPisteidenKokonaisMaara = 3;

        // Jono kpl
        asiakkaitaLisattyJonoonKpl = 0;
        asiakkaitaPalveltuJonostaKpl = 0;
        asiakkaitaPoistunutJonostaKpl = 0;
        asiakkaitaReRoutattuJonostaKpl = 0;

        // Aikoja
        kokonaisPalveluAikaPalvelupisteessa = 0;
        kokonaisJonoAikaPalvelupisteessa = 0;
        asiakkaittenKokonaisAikaPalvelupisteessa = 0;
        asiakkaanKeskiArvoViipyminenSimulaatiossa = 0;
        keskimaaranenPalveluAika = 0;
        keskimaarainenOleskeluAika = 0;
        keskiarvoJonotusAika = 0;

        palveluprosentti = 0;

        Asiakas.resetAsiakasUID();
        Asiakas.resetAsiakasSum();
        Palvelupiste.resetPpID();
        yksityisPalvelupisteita = UserParametrit.getInstance().getYksityisPPMaara();
        yritysPalvelupisteita = UserParametrit.getInstance().getYritysPPMaara();
        palveluPisteidenKokonaisMaara += UserParametrit.getInstance().getAllPPMaara();
    }

    /*
     * public void updateSuureet(int t, INewSimulationControllerMtoV kontrolleri,
     * int p, int yksityis, int yritys) {
     * 
     * }
     */

    public void tulosteet() {
        System.out.println("\n\n\nPalvelupisteiden kokonaismaara: " + palveluPisteidenKokonaisMaara);
        System.out.println("Simulointiaika: " + simulointiAika);
        System.out.println("Asiakkaita lisatty jonoon: " + asiakkaitaLisattyJonoonKpl);
        System.out.println("Asiakkaita palveltu jonosta: " + asiakkaitaPalveltuJonostaKpl);
        System.out.println("Asiakkaita reRoutattu jonosta: " + asiakkaitaReRoutattuJonostaKpl);
        System.out.println("Asiakkaita poistunut jonosta: " + asiakkaitaPoistunutJonostaKpl);
        System.out.println("Asiakkaitten kokonaispalveluaika: " + kokonaisPalveluAikaPalvelupisteessa);
        System.out.println("Asiakkaitten kokonaisjonotusaika: " + kokonaisJonoAikaPalvelupisteessa);
        System.out.println("Asiakkaitten kokonaisaika palvelupisteissa: " + asiakkaittenKokonaisAikaPalvelupisteessa);
        System.out.println("Asiakkaitten keskiarvo aika simulaatiossa: " + asiakkaanKeskiArvoViipyminenSimulaatiossa);
        System.out.println("Palvelupisteitten keskimaarainen palveluaika: " + keskimaaranenPalveluAika);
        System.out.println("Palvelupisteitten keskimaarainen jonotusaika: "
                + keskiarvoJonotusAika / palveluPisteidenKokonaisMaara);
        System.out.println("Palvelupisteissa keskimaarainen oleskeluaika: "
                + keskimaarainenOleskeluAika / palveluPisteidenKokonaisMaara);
        System.out.println(
                "Palvelupisteitten palveluprosentti: " + palveluprosentti / palveluPisteidenKokonaisMaara + "\n");
    }

    /**
     * @return int return the asiakkaitaLisattyJonoonKpl
     */
    public int getAsiakkaitaLisattyJonoonKpl() {
        return asiakkaitaLisattyJonoonKpl;
    }

    /**
     * @param asiakkaitaLisattyJonoonKpl the asiakkaitaLisattyJonoonKpl to set
     */
    public void asiakkaitaLisattyJonoon() {
        this.asiakkaitaLisattyJonoonKpl += 1;
    }

    public void setAsiakkaitaLisattyJonoon(int lkm) {
        this.asiakkaitaLisattyJonoonKpl = lkm;
    }

    /**
     * @return int return the asiakkaitaPalveltuJonostaKpl
     */
    public int getAsiakkaitaPalveltuJonostaKpl() {
        return asiakkaitaPalveltuJonostaKpl;
    }

    /**
     * @param asiakkaitaPalveltuJonostaKpl the asiakkaitaPalveltuJonostaKpl to set
     */
    public void setAsiakkaitaPalveltuJonostaKpl() {
        this.asiakkaitaPalveltuJonostaKpl += 1;
    }

    /**
     * @return int return the asiakkaitaPoistunutJonostaKpl
     */
    public int getAsiakkaitaPoistunutJonostaKpl() {
        return asiakkaitaPoistunutJonostaKpl;
    }

    /**
     * @param asiakkaitaPoistunutJonostaKpl the asiakkaitaPoistunutJonostaKpl to set
     */
    public void setAsiakkaitaPoistunutJonostaKpl() {
        this.asiakkaitaPoistunutJonostaKpl += 1;
    }

    /**
     * @return int return the asiakkaitaReRoutattuJonostaKpl
     */
    public int getAsiakkaitaReRoutattuJonostaKpl() {
        return asiakkaitaReRoutattuJonostaKpl;
    }

    /**
     * @param asiakkaitaReRoutattuJonostaKpl the asiakkaitaReRoutattuJonostaKpl to
     *                                       set
     */
    public void setAsiakkaitaReRoutattuJonostaKpl() {
        this.asiakkaitaReRoutattuJonostaKpl += 1;
    }

    /**
     * @return double return the simulointiAika
     */
    public double getSimulointiAika() {
        return simulointiAika;
    }

    /**
     * @param simulointiAika the simulointiAika to set
     */
    public void setSimulointiAika(double simulointiAika) {
        this.simulointiAika = simulointiAika;
    }

    /**
     * @return double return the kokonaisPalveluAikaPalvelupisteessa
     */
    public double getKokonaisPalveluAikaPalvelupisteessa() {
        return kokonaisPalveluAikaPalvelupisteessa;
    }

    /**
     * @param kokonaisPalveluAikaPalvelupisteessa the
     *                                            kokonaisPalveluAikaPalvelupisteessa
     *                                            to set
     */
    public void setKokonaisPalveluAikaPalvelupisteessa(double kokonaisPalveluAikaPalvelupisteessa) {
        this.kokonaisPalveluAikaPalvelupisteessa += kokonaisPalveluAikaPalvelupisteessa;
    }

    /**
     * @return double return the kokonaisJonoAikaPalvelupisteessa
     */
    public double getKokonaisJonoAikaPalvelupisteessa() {
        return kokonaisJonoAikaPalvelupisteessa;
    }

    /**
     * @param kokonaisJonoAikaPalvelupisteessa the kokonaisJonoAikaPalvelupisteessa
     *                                         to set
     */
    public void setKokonaisJonoAikaPalvelupisteessa(double kokonaisJonoAikaPalvelupisteessa) {
        this.kokonaisJonoAikaPalvelupisteessa += kokonaisJonoAikaPalvelupisteessa;
    }

    /**
     * @return double return the asiakkaittenKokonaisAikaPalvelupisteessa
     */
    public double getAsiakkaittenKokonaisAikaPalvelupisteessa() {
        return asiakkaittenKokonaisAikaPalvelupisteessa;
    }

    /**
     * @param asiakkaittenKokonaisAikaPalvelupisteessa the
     *                                                 asiakkaittenKokonaisAikaPalvelupisteessa
     *                                                 to set
     */
    public void setAsiakkaittenKokonaisAikaPalvelupisteessa(double asiakkaittenKokonaisAikaPalvelupisteessa) {
        this.asiakkaittenKokonaisAikaPalvelupisteessa = asiakkaittenKokonaisAikaPalvelupisteessa;
    }

    /**
     * @return double return the asiakkaanKeskiArvoViipyminenSimulaatiossa
     */
    public double getAsiakkaanKeskiArvoViipyminenSimulaatiossa() {
        return asiakkaanKeskiArvoViipyminenSimulaatiossa;
    }

    /**
     * @param asiakkaanKeskiArvoViipyminenSimulaatiossa the
     *                                                  asiakkaanKeskiArvoViipyminenSimulaatiossa
     *                                                  to set
     */
    public void setAsiakkaanKeskiArvoViipyminenSimulaatiossa(double asiakkaanKeskiArvoViipyminenSimulaatiossa) {
        this.asiakkaanKeskiArvoViipyminenSimulaatiossa = asiakkaanKeskiArvoViipyminenSimulaatiossa;
    }

    /**
     * @return double return the keskimaaranenPalveluAika
     */
    public double getKeskimaaranenPalveluAika() {
        return keskimaaranenPalveluAika;
    }

    /**
     * @param keskimaaranenPalveluAika the keskimaaranenPalveluAika to set
     */
    public void setKeskimaaranenPalveluAika(double keskimaaranenPalveluAika) {
        this.keskimaaranenPalveluAika += keskimaaranenPalveluAika;
    }

    /**
     * @return double return the keskimaarainenOleskeluAika
     */
    public double getKeskimaarainenOleskeluAika() {
        return keskimaarainenOleskeluAika;
    }

    /**
     * @param keskimaarainenOleskeluAika the keskimaarainenOleskeluAika to set
     */
    public void setKeskimaarainenOleskeluAika(double keskimaarainenOleskeluAika) {
        this.keskimaarainenOleskeluAika += keskimaarainenOleskeluAika;
    }

    /**
     * @return double return the keskiarvoJonotusAika
     */
    public double getKeskiarvoJonotusAika() {
        return keskiarvoJonotusAika;
    }

    /**
     * @param keskiarvoJonotusAika the keskiarvoJonotusAika to set
     */
    public void setKeskiarvoJonotusAika(double keskiarvoJonotusAika) {
        this.keskiarvoJonotusAika += keskiarvoJonotusAika;
    }

    /**
     * @return double return the palveluprosentti
     */
    public double getPalveluprosentti() {
        return palveluprosentti;
    }

    /**
     * @param palveluprosentti the palveluprosentti to set
     */
    public void setPalveluprosentti(double palveluprosentti) {
        this.palveluprosentti += palveluprosentti;
    }

    /**
     * @return int return the palveluPisteidenKokonaisMaara
     */
    public int getPalveluPisteidenKokonaisMaara() {
        return palveluPisteidenKokonaisMaara;
    }

    /**
     * @param palveluPisteidenKokonaisMaara the palveluPisteidenKokonaisMaara to set
     */
    public void setPalveluPisteidenKokonaisMaara(int palveluPisteidenKokonaisMaara) {
        this.palveluPisteidenKokonaisMaara = palveluPisteidenKokonaisMaara;
    }

    /**
     * @return int return the yritysPalvelupisteita
     */
    public int getYritysPalvelupisteita() {
        return yritysPalvelupisteita;
    }

    /**
     * @param yritysPalvelupisteita the yritysPalvelupisteita to set
     */
    public void setYritysPalvelupisteita(int yritysPalvelupisteita) {
        this.yritysPalvelupisteita = yritysPalvelupisteita;
    }

    /**
     * @return int return the yksityisPalvelupisteita
     */
    public int getYksityisPalvelupisteita() {
        return yksityisPalvelupisteita;
    }

    /**
     * @param yksityisPalvelupisteita the yksityisPalvelupisteita to set
     */
    public void setYksityisPalvelupisteita(int yksityisPalvelupisteita) {
        this.yksityisPalvelupisteita = yksityisPalvelupisteita;
    }

}
