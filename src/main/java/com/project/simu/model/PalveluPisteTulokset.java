package com.project.simu.model;

public class PalveluPisteTulokset {

    private int id;
    private int simulaatiokerta;
    private int tyyppi;
    private int palvellutAsiakkaat;
    private int keskiPalveluAika;

    public PalveluPisteTulokset(int id, int simulaatiokerta, int tyyppi, int palvellutAsiakkaat, int keskiPalveluAika) {
        this.id = id;
        this.simulaatiokerta = simulaatiokerta;
        this.tyyppi = tyyppi;
        this.palvellutAsiakkaat = palvellutAsiakkaat;
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

    public int getKeskiPalveluAika() {
        return keskiPalveluAika;
    }

}
