package com.project.simu.model;

public class PalveluPisteTulokset {

    private int id;
    private int simulaatiokerta;
    private int tyyppi;
    private int palvellutAsiakkaat;
    private double keskiJonotusAika;
    private double keskiPalveluAika;
    
    public PalveluPisteTulokset(int id, int simulaatiokerta, int tyyppi, int palvellutAsiakkaat,
            double keskiJonotusAika, double keskiPalveluAika) {
        this.id = id;
        this.simulaatiokerta = simulaatiokerta;
        this.tyyppi = tyyppi;
        this.palvellutAsiakkaat = palvellutAsiakkaat;
        this.keskiJonotusAika = keskiJonotusAika;
        this.keskiPalveluAika = keskiPalveluAika;
    }

    public PalveluPisteTulokset(int tyyppi, int palvellutAsiakkaat, double keskiJonotusAika, double keskiPalveluAika) {
        this.tyyppi = tyyppi;
        this.palvellutAsiakkaat = palvellutAsiakkaat;
        this.keskiJonotusAika = keskiJonotusAika;
        this.keskiPalveluAika = keskiPalveluAika;
    }

    public int getId() {
        return id;
    }

    public int getSimulaatiokerta() {
        return simulaatiokerta;
    }

    public int getTyyppi() {
        return tyyppi;
    }

    public int getPalvellutAsiakkaat() {
        return palvellutAsiakkaat;
    }

    public double getKeskiPalveluAika() {
        return keskiPalveluAika;
    }

    public double getKeskiJonotusAika() {
        return keskiJonotusAika;
    }
    
    public String getIdString() {
        return Integer.toString(id);
    }

    public String getSimulaatiokertaString() {
        return Integer.toString(simulaatiokerta);
    }

    public String getTyyppiString() {
        return Integer.toString(id);
    }

    public String getPalvellutAsiakkaatString() {
        return Integer.toString(palvellutAsiakkaat);
    }

    public String getKeskiPalveluAikaString() {
        return Double.toString(keskiPalveluAika);
    }

    public String getKeskiJonotusAikaString() {
        return Double.toString(keskiJonotusAika);
    }
    
}
