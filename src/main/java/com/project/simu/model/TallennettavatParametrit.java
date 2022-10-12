package com.project.simu.model;

public class TallennettavatParametrit {
    
    private int simulointiKerta;
    private int yksMyyntiPisteita, yksNettiPisteita, yksLiittymaPisteita, yksLaskutusPisteita, yriMyyntiPisteita, yriNettiPisteita, yriLiittymaPisteita, yriLaskutusPisteita;
    private double yksMyyntiAika, yksNettiAika, yksLiittymaAika, yksLaskutusAika, yriMyyntiAika, yriNettiAika, yriLiittymaAika, yriLaskutusAika;
    private double yksMyyntiJakauma, yksNettiJakauma, yksLiittymaJakauma, yksLaskutusJakauma, yriMyyntiJakauma, yriNettiJakauma, yriLiittymaJakauma, yriLaskutusJakauma;
    private double simuloinninAika, yksYriJakauma, karsimaatomyysAika, vaaraValintaProsentti, asikasmaaraTunti;
    
    /**
     * 
     * 
     * @param simulointiKerta Simulointikerta
     * @param yksMyyntiPisteita Yksityisen puolen myyntipisteitä
     * @param yksNettiPisteita Yksityisen puolen nettipisteitä
     * @param yksLiittymaPisteita Yksityisen puolen liittymäpisteitä
     * @param yksLaskutusPisteita Yksityisen puolen laskutuspisteitä
     * @param yriMyyntiPisteita Yrityspuolen myyntipisteitä
     * @param yriNettiPisteita Yrityspuolen nettipisteitä
     * @param yriLiittymaPisteita Yrityspuolen liittymäpisteitä
     * @param yriLaskutusPisteita Yrityspuolen laskutuspisteitä
     * @param yksMyyntiAika Yksityisen puolen myyntipisteen aika
     * @param yksNettiAika Yksityisen puolen nettipisteen aika
     * @param yksLiittymaAika Yksityisen puolen liittymäpisteen aika
     * @param yksLaskutusAika Yksityisen puolen laskutuspisteen aika
     * @param yriMyyntiAika Yrityspuolen myyntipisteen aika
     * @param yriNettiAika Yrityspuolen nettipisteen aika
     * @param yriLiittymaAika Yrityspuolen liittymäpisteen aika
     * @param yriLaskutusAika Yrityspuolen laskutuspisteen aika
     * @param yksMyyntiJakauma Yksityisen puolen myyntipisteen jakauma
     * @param yksNettiJakauma Yksityisen puolen nettipisteen jakauma
     * @param yksLiittymaJakauma Yksityisen puolen liittymäpisteen jakauma
     * @param yksLaskutusJakauma Yksityisen puolen laskutuspisteen jakauma
     * @param yriMyyntiJakauma Yrityspuolen myyntipisteen jakauma
     * @param yriNettiJakauma Yrityspuolen nettipisteen jakauma
     * @param yriLiittymaJakauma Yrityspuolen liittymäpisteen jakauma
     * @param yriLaskutusJakauma Yrityspuolen laskutuspisteen jakauma
     * @param simuloinninAika Simuloinnin kokonaisaika
     * @param yksYriJakauma Jakauma yksityisen ja yritys puolen välillä 0-100
     * @param karsimaatomyysAika Kärsimättömyyden aika
     * @param vaaraValintaProsentti Väärä valinta prosentti
     * @param asikasmaaraTunti Asiakkaita tunnissa
     * @author Lassi Bågman
     */
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


    public String getSimulointiKertaString() {
        return Integer.toString(simulointiKerta);
    }

    public String getYksMyyntiPisteitaString() {
        return Integer.toString(yksMyyntiPisteita);
    }

    public String getYksNettiPisteitaString() {
        return Integer.toString(yksNettiPisteita);
    }

    public String getYksLiittymaPisteitaString() {
        return Integer.toString(yksLiittymaPisteita);
    }

    public String getYksLaskutusPisteitaString() {
        return Integer.toString(yksLaskutusPisteita);
    }

    public String getYriMyyntiPisteitaString() {
        return Integer.toString(yriMyyntiPisteita);
    }

    public String getYriNettiPisteitaString() {
        return Integer.toString(yriNettiPisteita);
    }

    public String getYriLiittymaPisteitaString() {
        return Integer.toString(yriLiittymaPisteita);
    }

    public String getYriLaskutusPisteitaString() {
        return Integer.toString(yriLaskutusPisteita);
    }

    public String getYksMyyntiAikaString() {
        return Double.toString(yksMyyntiAika);
    }

    public String getYksNettiAikaString() {
        return Double.toString(yksNettiAika);
    }

    public String getYksLiittymaAikaString() {
        return Double.toString(yksLiittymaAika);
    }

    public String getYksLaskutusAikaString() {
        return Double.toString(yksLaskutusAika);
    }

    public String getYriMyyntiAikaString() {
        return Double.toString(yriMyyntiAika);
    }

    public String getYriNettiAikaString() {
        return Double.toString(yriNettiAika);
    }

    public String getYriLiittymaAikaString() {
        return Double.toString(yriLiittymaAika);
    }

    public String getYriLaskutusAikaString() {
        return Double.toString(yriLaskutusAika);
    }

    public String getYksMyyntiJakaumaString() {
        return Double.toString(yksMyyntiJakauma);
    }

    public String getYksNettiJakaumaString() {
        return Double.toString(yksNettiJakauma);
    }

    public String getYksLiittymaJakaumaString() {
        return Double.toString(yksLiittymaJakauma);
    }

    public String getYksLaskutusJakaumaString() {
        return Double.toString(yksLaskutusJakauma);
    }

    public String getYriMyyntiJakaumaString() {
        return Double.toString(yriMyyntiJakauma);
    }

    public String getYriNettiJakaumaString() {
        return Double.toString(yriNettiJakauma);
    }

    public String getYriLiittymaJakaumaString() {
        return Double.toString(yriLiittymaAika);
    }

    public String getYriLaskutusJakaumaString() {
        return Double.toString(yksMyyntiAika);
    }

    public String getSimuloinninAikaString() {
        return Double.toString(yksMyyntiAika);
    }

    public String getYksYriJakaumaString() {
        return Double.toString(yksMyyntiAika);
    }

    public String getKarsimaatomyysAikaString() {
        return Double.toString(yksMyyntiAika);
    }

    public String getVaaraValintaProsenttiString() {
        return Double.toString(yksMyyntiAika);
    }

    public String getAsikasmaaraTuntiString() {
        return Double.toString(yksMyyntiAika);
    }
    
}
