package com.project.simu.model;

import java.util.ArrayList;

public class Tulokset {

    private int simulaatiokerta;
    private double kesto;
    private double palveluProsentti;
    private int asMaara;
    private int palvellutAsiakkaat;
    private int poistuneetAsiakkaat;
    private int uudelleenOhjatutAsiakkaat;
    private double keskiJonotusAika;
    private double keskiLapiMenoAika;
    private ArrayList<PalveluPisteTulokset> palveluPisteTuloksets = new ArrayList<PalveluPisteTulokset>();
    
    

    public Tulokset(int simulaatiokerta, double kesto, double palveluProsentti, int asMaara, int palvellutAsiakkaat,
            int poistuneetAsiakkaat, int uudelleenOhjatutAsiakkaat, double keskiJonotusAika, double keskiLapiMenoAika,
            ArrayList<PalveluPisteTulokset> palveluPisteTuloksets) {
        this.simulaatiokerta = simulaatiokerta;
        this.kesto = kesto;
        this.palveluProsentti = palveluProsentti;
        this.asMaara = asMaara;
        this.palvellutAsiakkaat = palvellutAsiakkaat;
        this.poistuneetAsiakkaat = poistuneetAsiakkaat;
        this.uudelleenOhjatutAsiakkaat = uudelleenOhjatutAsiakkaat;
        this.keskiJonotusAika = keskiJonotusAika;
        this.keskiLapiMenoAika = keskiLapiMenoAika;
        this.palveluPisteTuloksets = palveluPisteTuloksets;
    }

    public Tulokset(double kesto, double palveluProsentti, int asMaara, int palvellutAsiakkaat, int poistuneetAsiakkaat,
            int uudelleenOhjatutAsiakkaat, double keskiJonotusAika, double keskiLapiMenoAika,
            ArrayList<PalveluPisteTulokset> palveluPisteTuloksets) {
        this.kesto = kesto;
        this.palveluProsentti = palveluProsentti;
        this.asMaara = asMaara;
        this.palvellutAsiakkaat = palvellutAsiakkaat;
        this.poistuneetAsiakkaat = poistuneetAsiakkaat;
        this.uudelleenOhjatutAsiakkaat = uudelleenOhjatutAsiakkaat;
        this.keskiJonotusAika = keskiJonotusAika;
        this.keskiLapiMenoAika = keskiLapiMenoAika;
        this.palveluPisteTuloksets = palveluPisteTuloksets;
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

    public double getKeskiJonotusAika() {
        return keskiJonotusAika;
    }

    public ArrayList<PalveluPisteTulokset> getPalveluPisteTuloksets() {
        return palveluPisteTuloksets;
    }

    public String getSimulaatiokertaString() {
        return Integer.toString(simulaatiokerta);
    }

    public String getKestoString() {
        return Double.toString(kesto);
    }

    public String getPalveluProsenttiString() {
        return Double.toString(palveluProsentti);
    }

    public String getAsMaaraString() {
        return Integer.toString(asMaara);
    }

    public String getPalvellutAsiakkaatString() {
        return Integer.toString(palvellutAsiakkaat);
    }

    public String getPoistuneetAsiakkaatString() {
        return Integer.toString(poistuneetAsiakkaat);
    }

    public String getUudelleenOhjatutAsiakkaatString() {
        return Integer.toString(uudelleenOhjatutAsiakkaat);
    }

    public String getKeskiLapiMenoAikaString() {
        return Double.toString(keskiLapiMenoAika);
    }

    public String getKeskiJonotusAikaString() {
        return Double.toString(keskiJonotusAika);
    }

}
