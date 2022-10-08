package com.project.simu.model;

public class PalveluPisteTulokset {

    private int id;
    private int simulaatiokerta;
    private int tyyppi;
    private int palvellutAsiakkaat;
    private double keskiPalveluAika;
    private double keskiJonotusAika;
    
    public PalveluPisteTulokset(int id, int simulaatiokerta, int tyyppi, int palvellutAsiakkaat,
            double keskiPalveluAika, double keskiJonotusAika) {
        this.id = id;
        this.simulaatiokerta = simulaatiokerta;
        this.tyyppi = tyyppi;
        this.palvellutAsiakkaat = palvellutAsiakkaat;
        this.keskiPalveluAika = keskiPalveluAika;
        this.keskiJonotusAika = keskiJonotusAika;
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
    
}
