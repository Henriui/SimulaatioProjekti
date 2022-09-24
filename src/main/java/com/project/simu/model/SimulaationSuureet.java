package com.project.simu.model;

public class SimulaationSuureet {

    private static SimulaationSuureet instance = null;

    private int palveluPisteidenKokonaisMaara;
    private int asiakkaitaLisattyJonoonKpl;
    private int asiakkaitaPalveltuJonostaKpl;
    private int asiakkaitaPoistunutJonostaKpl;
    private int asiakkaitaReRoutattuJonostaKpl;

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
        palveluPisteidenKokonaisMaara = 3;
        simulointiAika = 0;
        asiakkaitaLisattyJonoonKpl = 0;
        asiakkaitaPalveltuJonostaKpl = 0;
        asiakkaitaPoistunutJonostaKpl = 0;
        asiakkaitaReRoutattuJonostaKpl = 0;
        kokonaisPalveluAikaPalvelupisteessa = 0;
        kokonaisJonoAikaPalvelupisteessa = 0;
        asiakkaittenKokonaisAikaPalvelupisteessa = 0;
        asiakkaanKeskiArvoViipyminenSimulaatiossa = 0;
        keskimaaranenPalveluAika = 0;
        keskimaarainenOleskeluAika = 0;
        keskiarvoJonotusAika = 0;
        palveluprosentti = 0;
    }

    public void tulosteet() {
        System.out.println("\n\n\nPalvelupisteiden kokonaismaara: " + palveluPisteidenKokonaisMaara);
        System.out.println("Simulointiaika: " + simulointiAika);
        System.out.println("Asiakkaita lisatty jonoon: " + asiakkaitaLisattyJonoonKpl);
        System.out.println("Asiakkaita palveltu jonosta: " + asiakkaitaPalveltuJonostaKpl);
        System.out.println("Asiakkaita reRoutattu jonosta: " + asiakkaitaReRoutattuJonostaKpl);
        System.out.println("Asiakkaitten kokonaispalveluaika: " + kokonaisPalveluAikaPalvelupisteessa);
        System.out.println("Asiakkaitten kokonaisjonotusaika: " + kokonaisJonoAikaPalvelupisteessa);
        System.out.println("Asiakkaitten kokonaisaika palvelupisteissa: " + asiakkaittenKokonaisAikaPalvelupisteessa);
        System.out.println("Asiakkaitten keskiarvo aika simulaatiossa: " + asiakkaanKeskiArvoViipyminenSimulaatiossa);
        System.out.println("Palvelupisteitten keskimaarainen palveluaika: " + keskimaaranenPalveluAika);
        System.out.println("Palvelupisteitten keskimaarainen jonotusaika: " + keskiarvoJonotusAika);
        System.out.println("Palvelupisteissa keskimaarainen oleskeluaika: " + keskimaarainenOleskeluAika);
        System.out.println("Palvelupisteitten palveluprosentti: " + palveluprosentti / palveluPisteidenKokonaisMaara);
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
    public void setAsiakkaitaLisattyJonoonKpl(int asiakkaitaLisattyJonoonKpl) {
        this.asiakkaitaLisattyJonoonKpl += asiakkaitaLisattyJonoonKpl;
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
    public void setAsiakkaitaPalveltuJonostaKpl(int asiakkaitaPalveltuJonostaKpl) {
        this.asiakkaitaPalveltuJonostaKpl += asiakkaitaPalveltuJonostaKpl;
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
    public void setAsiakkaitaPoistunutJonostaKpl(int asiakkaitaPoistunutJonostaKpl) {
        this.asiakkaitaPoistunutJonostaKpl += asiakkaitaPoistunutJonostaKpl;
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
    public void setAsiakkaitaReRoutattuJonostaKpl(int asiakkaitaReRoutattuJonostaKpl) {
        this.asiakkaitaReRoutattuJonostaKpl += asiakkaitaReRoutattuJonostaKpl;
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

}
