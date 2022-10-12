package com.project.simu.model;

public class TallennettavatParametrit {
    
    private int simulointiKerta;
    private int yksMyyntiPisteita, yksNettiPisteita, yksLiittymaPisteita, yksLaskutusPisteita, yriMyyntiPisteita, yriNettiPisteita, yriLiittymaPisteita, yriLaskutusPisteita;
    private double yksMyyntiAika, yksNettiAika, yksLiittymaAika, yksLaskutusAika, yriMyyntiAika, yriNettiAika, yriLiittymaAika, yriLaskutusAika;
    private double yksMyyntiJakauma, yksNettiJakauma, yksLiittymaJakauma, yksLaskutusJakauma, yriMyyntiJakauma, yriNettiJakauma, yriLiittymaJakauma, yriLaskutusJakauma;
    private double simuloinninAika, yksYriJakauma, karsimaatomyysAika, vaaraValintaProsentti, asikasmaaraTunti;
    
    public TallennettavatParametrit(int simulointiKerta, int yksMyyntiPisteita, int yksNettiPisteita,
            int yksLiittymaPisteita, int yksLaskutusPisteita, int yriMyyntiPisteita, int yriNettiPisteita,
            int yriLiittymaPisteita, int yriLaskutusPisteita, double yksMyyntiAika, double yksNettiAika,
            double yksLiittymaAika, double yksLaskutusAika, double yriMyyntiAika, double yriNettiAika,
            double yriLiittymaAika, double yriLaskutusAika, double yksMyyntiJakauma, double yksNettiJakauma,
            double yksLiittymaJakauma, double yksLaskutusJakauma, double yriMyyntiJakauma, double yriNettiJakauma,
            double yriLiittymaJakauma, double yriLaskutusJakauma, double simuloinninAika, double yksYriJakauma,
            double karsimaatomyysAika, double vaaraValintaProsentti, double asikasmaaraTunti) {
        this.simulointiKerta = simulointiKerta;
        this.yksMyyntiPisteita = yksMyyntiPisteita;
        this.yksNettiPisteita = yksNettiPisteita;
        this.yksLiittymaPisteita = yksLiittymaPisteita;
        this.yksLaskutusPisteita = yksLaskutusPisteita;
        this.yriMyyntiPisteita = yriMyyntiPisteita;
        this.yriNettiPisteita = yriNettiPisteita;
        this.yriLiittymaPisteita = yriLiittymaPisteita;
        this.yriLaskutusPisteita = yriLaskutusPisteita;
        this.yksMyyntiAika = yksMyyntiAika;
        this.yksNettiAika = yksNettiAika;
        this.yksLiittymaAika = yksLiittymaAika;
        this.yksLaskutusAika = yksLaskutusAika;
        this.yriMyyntiAika = yriMyyntiAika;
        this.yriNettiAika = yriNettiAika;
        this.yriLiittymaAika = yriLiittymaAika;
        this.yriLaskutusAika = yriLaskutusAika;
        this.yksMyyntiJakauma = yksMyyntiJakauma;
        this.yksNettiJakauma = yksNettiJakauma;
        this.yksLiittymaJakauma = yksLiittymaJakauma;
        this.yksLaskutusJakauma = yksLaskutusJakauma;
        this.yriMyyntiJakauma = yriMyyntiJakauma;
        this.yriNettiJakauma = yriNettiJakauma;
        this.yriLiittymaJakauma = yriLiittymaJakauma;
        this.yriLaskutusJakauma = yriLaskutusJakauma;
        this.simuloinninAika = simuloinninAika;
        this.yksYriJakauma = yksYriJakauma;
        this.karsimaatomyysAika = karsimaatomyysAika;
        this.vaaraValintaProsentti = vaaraValintaProsentti;
        this.asikasmaaraTunti = asikasmaaraTunti;
    }

    public int getSimulointiKerta() {
        return simulointiKerta;
    }

    public int getYksMyyntiPisteita() {
        return yksMyyntiPisteita;
    }

    public int getYksNettiPisteita() {
        return yksNettiPisteita;
    }

    public int getYksLiittymaPisteita() {
        return yksLiittymaPisteita;
    }

    public int getYksLaskutusPisteita() {
        return yksLaskutusPisteita;
    }

    public int getYriMyyntiPisteita() {
        return yriMyyntiPisteita;
    }

    public int getYriNettiPisteita() {
        return yriNettiPisteita;
    }

    public int getYriLiittymaPisteita() {
        return yriLiittymaPisteita;
    }

    public int getYriLaskutusPisteita() {
        return yriLaskutusPisteita;
    }

    public double getYksMyyntiAika() {
        return yksMyyntiAika;
    }

    public double getYksNettiAika() {
        return yksNettiAika;
    }

    public double getYksLiittymaAika() {
        return yksLiittymaAika;
    }

    public double getYksLaskutusAika() {
        return yksLaskutusAika;
    }

    public double getYriMyyntiAika() {
        return yriMyyntiAika;
    }

    public double getYriNettiAika() {
        return yriNettiAika;
    }

    public double getYriLiittymaAika() {
        return yriLiittymaAika;
    }

    public double getYriLaskutusAika() {
        return yriLaskutusAika;
    }

    public double getYksMyyntiJakauma() {
        return yksMyyntiJakauma;
    }

    public double getYksNettiJakauma() {
        return yksNettiJakauma;
    }

    public double getYksLiittymaJakauma() {
        return yksLiittymaJakauma;
    }

    public double getYksLaskutusJakauma() {
        return yksLaskutusJakauma;
    }

    public double getYriMyyntiJakauma() {
        return yriMyyntiJakauma;
    }

    public double getYriNettiJakauma() {
        return yriNettiJakauma;
    }

    public double getYriLiittymaJakauma() {
        return yriLiittymaJakauma;
    }

    public double getYriLaskutusJakauma() {
        return yriLaskutusJakauma;
    }

    public double getSimuloinninAika() {
        return simuloinninAika;
    }

    public double getYksYriJakauma() {
        return yksYriJakauma;
    }

    public double getKarsimaatomyysAika() {
        return karsimaatomyysAika;
    }

    public double getVaaraValintaProsentti() {
        return vaaraValintaProsentti;
    }

    public double getAsikasmaaraTunti() {
        return asikasmaaraTunti;
    }

    
}
