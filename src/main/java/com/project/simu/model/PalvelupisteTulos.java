package com.project.simu.model;

public class PalvelupisteTulos {

    private int id;
    private int simulaatiokerta;
    private int tyyppi;
    private int palvellutAsiakkaat;
    private double keskiJonotusAika;
    private double keskiPalveluAika;
    private double palveluProsentti;

    public PalvelupisteTulos(int id, int simulaatiokerta, int tyyppi, int palvellutAsiakkaat,
            double keskiJonotusAika, double keskiPalveluAika, double palveluProsentti) {
        this.id = id;
        this.simulaatiokerta = simulaatiokerta;
        this.tyyppi = tyyppi;
        this.palvellutAsiakkaat = palvellutAsiakkaat;
        this.keskiJonotusAika = keskiJonotusAika;
        this.keskiPalveluAika = keskiPalveluAika;
        this.palveluProsentti = palveluProsentti;
    }

    public PalvelupisteTulos(int simulaatiokerta, int tyyppi, int palvellutAsiakkaat,
            double keskiJonotusAika, double keskiPalveluAika, double palveluProsentti) {
        this.simulaatiokerta = simulaatiokerta;
        this.tyyppi = tyyppi;
        this.palvellutAsiakkaat = palvellutAsiakkaat;
        this.keskiJonotusAika = keskiJonotusAika;
        this.keskiPalveluAika = keskiPalveluAika;
        this.palveluProsentti = palveluProsentti;
    }

    public PalvelupisteTulos(int tyyppi, int palvellutAsiakkaat, double keskiJonotusAika, double keskiPalveluAika, double palveluProsentti) {
        this.tyyppi = tyyppi;
        this.palvellutAsiakkaat = palvellutAsiakkaat;
        this.keskiJonotusAika = keskiJonotusAika;
        this.keskiPalveluAika = keskiPalveluAika;
        this.palveluProsentti = palveluProsentti;
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

    public double getPalveluProsentti(){
        return palveluProsentti;
    }

    public String getIdString() {
        return Integer.toString(id);
    }

    public String getSimulaatiokertaString() {
        return Integer.toString(simulaatiokerta);
    }

    public String getTyyppiString() {
        switch (tyyppi) {
            case 1:
                return "Myynti";
            case 2:
                return "Netti";
            case 3:
                return "Liittym??";
            case 4:
                return "Laskutus";
            case 5:
                return "Myynti";
            case 6:
                return "Netti";
            case 7:
                return "Liittym??";
            case 8:
                return "Laskutus";
        }
        return Integer.toString(tyyppi);
    }

    public String getTyyppiStringPieni() {
        switch (tyyppi) {
            case 1:
                return "myynti";
            case 2:
                return "netti";
            case 3:
                return "liittym??";
            case 4:
                return "laskutus";
            case 5:
                return "myynti";
            case 6:
                return "netti";
            case 7:
                return "liittym??";
            case 8:
                return "laskutus";
        }
        return Integer.toString(tyyppi);
    }

    public String getPalvellutAsiakkaatString() {
        return Integer.toString(palvellutAsiakkaat);
    }

    public String getKeskiPalveluAikaString() {
        return String.format("%.2f", (keskiPalveluAika / 60));
    }

    public String getKeskiJonotusAikaString() {
        return String.format("%.2f", (keskiJonotusAika / 60));
    }

    public String getPalveluprosetString(){
        return String.format("%.2f", palveluProsentti);
    }

}
