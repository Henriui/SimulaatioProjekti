package com.project.simu.model;

public class Tulokset {

    private int simulaatiokerta;
    private double kesto;
    private double palveluProsentti;
    private int asMaara;
    private int palvellutAsiakkaat;
    private int poistuneetAsiakkaat;
    private int uudelleenOhjatutAsiakkaat;
    private double keskiLapiMenoAika;
    private PalveluPisteTulokset palveluPisteTulokset;

    public Tulokset(int simulaatiokerta, double kesto, double palveluProsentti, int asMaara, int palvellutAsiakkaat,
            int poistuneetAsiakkaat, int uudelleenOhjatutAsiakkaat, double keskiLapiMenoAika,
            PalveluPisteTulokset palveluPisteTulokset) {
        this.simulaatiokerta = simulaatiokerta;
        this.kesto = kesto;
        this.palveluProsentti = palveluProsentti;
        this.asMaara = asMaara;
        this.palvellutAsiakkaat = palvellutAsiakkaat;
        this.poistuneetAsiakkaat = poistuneetAsiakkaat;
        this.uudelleenOhjatutAsiakkaat = uudelleenOhjatutAsiakkaat;
        this.keskiLapiMenoAika = keskiLapiMenoAika;
        this.palveluPisteTulokset = palveluPisteTulokset;
    }

    public int getSimulaatiokerta() {
        return simulaatiokerta;
    }

    public double getKesto() {
        return kesto;
    }

    public double getPalveluProsentti() {
        return palveluProsentti;
    }

    public int getAsMaara() {
        return asMaara;
    }

    public int getPalvellutAsiakkaat() {
        return palvellutAsiakkaat;
    }

    public int getPoistuneetAsiakkaat() {
        return poistuneetAsiakkaat;
    }

    public int getUudelleenOhjatutAsiakkaat() {
        return uudelleenOhjatutAsiakkaat;
    }

    public double getKeskiLapiMenoAika() {
        return keskiLapiMenoAika;
    }

    public PalveluPisteTulokset getPalvelupisteTulokset() {
        return palveluPisteTulokset;
    }
}
