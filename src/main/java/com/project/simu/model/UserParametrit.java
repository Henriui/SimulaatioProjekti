package com.project.simu.model;

public class UserParametrit {
    private static UserParametrit instance = null;

    // Henkilöasiakkaiden palvelupisteiden kplmaarat
    private int priSalesPpMaara;
    private int priNetworkPpMaara;
    private int priSubscriberPpMaara;
    private int priInvoicePpMaara;

    // Yritysasiakkaiden palvelupisteiden kplmaarat
    private int coSalesPpMaara;
    private int coNetworkPpMaara;
    private int coSubscriberPpMaara;
    private int coInvoicePpMaara;

    // Henkilöasiakkaat / Yritysasiakkaat jakaumaluku
    private double asiakasTyyppiJakauma;

    // Kuinka kauan asiakas jaksaa jonottaa
    private double asiakkaidenKarsivallisyys;

    // Mikä mahdollisuus % on asiakkaalla valita väärä linja puhelinvalikosta
    private double vaaraValintaProsentti;

    // Kokonaisaika simulaatiolla (T)
    private double simulaationAika;

    // Thread sleep aika
    private long viiveAika;

    // Puhelinvalikkojen keskimääräinen palveluaika
    private double pValikkoAika;

    // Henkilöasiakkaitten SALES keksimääräinen palveluaika
    private double priSalesKkPAika;

    // Henkilöasiakkaitten NETWORK keksimääräinen palveluaika
    private double priNetworkKkPAika;

    // Henkilöasiakkaitten SUBSCRIBER keksimääräinen palveluaika
    private double priSubscriberKkPAika;

    // Henkilöasiakkaitten INVOICE keksimääräinen palveluaika
    private double priInvoiceKkPAika;

    // Yritysasiakkaitten SALES keksimääräinen palveluaika
    private double coSalesKkPAika;

    // Yritysasiakkaitten NETWORK keksimääräinen palveluaika
    private double coNetworkKkPAika;

    // Yritysasiakkaitten SUBSCRIBER keksimääräinen palveluaika
    private double coSubscriberKkPAika;

    // Yritysasiakkaitten INVOICE keksimääräinen palveluaika
    private double coInvoiceKkPAika;

    public static synchronized UserParametrit getInstance() {
        if (instance == null) {
            instance = new UserParametrit();
        }
        return instance;
    }

    private UserParametrit() {
        setDefaultArvot();
    }

    public void setDefaultArvot() {

        // Palvelupisteden default määrä: 3 kpl
        this.priSalesPpMaara = 3;
        this.priNetworkPpMaara = 3;
        this.priSubscriberPpMaara = 3;
        this.priInvoicePpMaara = 3;
        this.coSalesPpMaara = 3;
        this.coNetworkPpMaara = 3;
        this.coSubscriberPpMaara = 3;
        this.coInvoicePpMaara = 3;

        /**
         * 85% Henkilöasiakkaita, 15% Yritysasiakkaita = 0.45
         * 50% Henkilöasiakkaita, 50% Yritysasiakkaita = 0.5
         * 70% Henkilöasiakkaita, 30% Yritysasiakkaita = 0.4725
         * 30% Henkilöasiakkaita, 70% Yritysasiakkaita = 0.5275
         * 15% Henkilöasiakkaita, 15% Yritysasiakkaita = 0.55
         * 
         * @author Rasmus Hyyppä
         */
        this.asiakasTyyppiJakauma = 0.5;

        // Thread sleeppi aika
        this.viiveAika = 1500;

        // 600 sek jaksaa jonottaa
        this.asiakkaidenKarsivallisyys = 10 * 60;

        // 1 % asiakkaista valitsee väärin
        this.vaaraValintaProsentti = 0.01;

        // Sekunttia 3600 * 8 = 8h työpäivä
        this.simulaationAika = 3600 * 8;

        // Sekuntteina oletuspalveluaikoja, 10 sekunttia puhelinvalikko
        this.pValikkoAika = 5;
        this.priSalesKkPAika = 10 * 60;
        this.priNetworkKkPAika = 10 * 60;
        this.priSubscriberKkPAika = 10 * 60;
        this.priInvoiceKkPAika = 10 * 60;
        this.coSalesKkPAika = 10 * 60;
        this.coNetworkKkPAika = 10 * 60;
        this.coSubscriberKkPAika = 10 * 60;
        this.coInvoiceKkPAika = 10 * 60;
    }

    // Liuta settereitä ja gettereitä tästä eteenpäin //
    // ********************************************* //

    public void setPriSalesPpMaara(int priSalesPpMaara) {
        this.priSalesPpMaara = priSalesPpMaara;
    }

    public void setPriNetworkPpMaara(int priNetworkPpMaara) {
        this.priNetworkPpMaara = priNetworkPpMaara;
    }

    public void setPriSubscriberPpMaara(int priSubscriberPpMaara) {
        this.priSubscriberPpMaara = priSubscriberPpMaara;
    }

    public void setPriInvoicePpMaara(int priInvoicePpMaara) {
        this.priInvoicePpMaara = priInvoicePpMaara;
    }

    public void setCoSalesPpMaara(int coSalesPpMaara) {
        this.coSalesPpMaara = coSalesPpMaara;
    }

    public void setCoNetworkPpMaara(int coNetworkPpMaara) {
        this.coNetworkPpMaara = coNetworkPpMaara;
    }

    public void setCoSubscriberPpMaara(int coSubscriberPpMaara) {
        this.coSubscriberPpMaara = coSubscriberPpMaara;
    }

    public void setCoInvoicePpMaara(int coInvoicePpMaara) {
        this.coInvoicePpMaara = coInvoicePpMaara;
    }

    public double getSimulaationAika() {
        return simulaationAika;
    }

    public void setSimulaationAika(double simulaationAika) {
        this.simulaationAika = simulaationAika;
    }

    public double getAsiakkaidenKarsivallisyys() {
        return asiakkaidenKarsivallisyys;
    }

    public void setAsiakkaidenKarsivallisyys(int asiakkaidenKarsivallisyys) {
        this.asiakkaidenKarsivallisyys = asiakkaidenKarsivallisyys;
    }

    public double getVaaraValintaProsentti() {
        return vaaraValintaProsentti;
    }

    public void setVaaraValintaProsentti(double vaaraValintaProsentti) {
        this.vaaraValintaProsentti = vaaraValintaProsentti;
    }

    public void setPValikkoAika(double pValikkoAika) {
        this.pValikkoAika = pValikkoAika;
    }

    public void setPriSalesKkPAika(double priSalesKkPAika) {
        this.priSalesKkPAika = priSalesKkPAika;
    }

    public void setPriNetworkKkPAika(double priNetworkKkPAika) {
        this.priNetworkKkPAika = priNetworkKkPAika;
    }

    public void setPriSubscriberKkPAika(double priSubscriberKkPAika) {
        this.priSubscriberKkPAika = priSubscriberKkPAika;
    }

    public void setPriInvoiceKkPAika(double priInvoiceKkPAika) {
        this.priInvoiceKkPAika = priInvoiceKkPAika;
    }

    public void setCoSalesKkPAika(double coSalesKkPAika) {
        this.coSalesKkPAika = coSalesKkPAika;
    }

    public void setCoNetworkKkPAika(double coNetworkKkPAika) {
        this.coNetworkKkPAika = coNetworkKkPAika;
    }

    public void setCoSubscriberKkPAika(double coSubscriberKkPAika) {
        this.coSubscriberKkPAika = coSubscriberKkPAika;
    }

    public void setCoInvoiceKkPAika(double coInvoiceKkPAika) {
        this.coInvoiceKkPAika = coInvoiceKkPAika;
    }

    public int getPriSalesPpMaara() {
        return priSalesPpMaara;
    }

    public int getPriNetworkPpMaara() {
        return priNetworkPpMaara;
    }

    public int getPriSubscriberPpMaara() {
        return priSubscriberPpMaara;
    }

    public int getPriInvoicePpMaara() {
        return priInvoicePpMaara;
    }

    public int getCoSalesPpMaara() {
        return coSalesPpMaara;
    }

    public int getCoNetworkPpMaara() {
        return coNetworkPpMaara;
    }

    public int getCoSubscriberPpMaara() {
        return coSubscriberPpMaara;
    }

    public int getCoInvoicePpMaara() {
        return coInvoicePpMaara;
    }

    public double getPValikkoAika() {
        return pValikkoAika;
    }

    public double getPriSalesKkPAika() {
        return priSalesKkPAika;
    }

    public double getPriNetworkKkPAika() {
        return priNetworkKkPAika;
    }

    public double getPriSubscriberKkPAika() {
        return priSubscriberKkPAika;
    }

    public double getPriInvoiceKkPAika() {
        return priInvoiceKkPAika;
    }

    public double getCoSalesKkPAika() {
        return coSalesKkPAika;
    }

    public double getCoNetworkKkPAika() {
        return coNetworkKkPAika;
    }

    public double getCoSubscriberKkPAika() {
        return coSubscriberKkPAika;
    }

    public double getCoInvoiceKkPAika() {
        return coInvoiceKkPAika;
    }

    public double getAsiakasTyyppiJakauma() {
        return asiakasTyyppiJakauma;
    }

    public void setAsiakasTyyppiJakauma(int asiakasTyyppiJakauma) {
        this.asiakasTyyppiJakauma = asiakasTyyppiJakauma;
    }

    /**
     * @return long return the viiveAika
     */
    public long getViiveAika() {
        return viiveAika;
    }

    /**
     * @param viiveAika the viiveAika to set
     */
    public void setViiveAika(long viiveAika) {
        this.viiveAika = viiveAika;
    }

}
