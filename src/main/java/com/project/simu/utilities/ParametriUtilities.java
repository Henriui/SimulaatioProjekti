package com.project.simu.utilities;

import com.project.simu.model.UserParametrit;
import com.project.eduni.distributions.Binomial;
import com.project.eduni.distributions.DiscreteGenerator;
import com.project.eduni.distributions.Normal;
import com.project.eduni.distributions.Uniform;
import com.project.simu.model.Tyyppi;

/*
 * Käyttäjän parametri singletonin datan kääntäjät staattiseen utility classiin ripoteltuna
 * @author Rasmus Hyyppä
 */
public class ParametriUtilities {

    /**
     * Käytetään alustuksessa jotta saadaan oikea määrä palvelupisteitä luotua
     * 
     * @return Palauttaa asiakaspalvelupisteiden kokonaismäärän
     * @author Rasmus Hyyppä
     */
    public static int getPpKokonaismaara() {
        return getYksityisPalvelupisteita() + getYksityisPalvelupisteita();
    }

    public static int getYksityisPalvelupisteita() {
        UserParametrit uP = UserParametrit.getInstance();
        return uP.getPriSalesPpMaara() + uP.getPriNetworkPpMaara() + uP.getPriSubscriberPpMaara()
                + uP.getPriInvoicePpMaara();
    }

    public static int getYritysPalvelupisteita() {
        UserParametrit uP = UserParametrit.getInstance();
        return uP.getCoSalesPpMaara() + uP.getCoNetworkPpMaara() + uP.getCoSubscriberPpMaara()
                + uP.getCoInvoicePpMaara();
    }

    /**
     * Arpoo uniform jakaumalla tuleeko asiakas menemään väärään jonoon
     * 
     * @return True mikäli on, false mikäli ei
     * @author Rasmus Hyyppä
     */
    public static boolean onkoVaaraValinta() {
        if (new Uniform(1, 100).sample() < (UserParametrit.getInstance().getVaaraValintaProsentti() * 100)) {
            return true;
        }
        return false;
    }

    /**
     * Käyttäjä voi valita kuinka monta kappaletta asiakaspalvelioita on missäkin
     * linjassa
     * 
     * @return Palauttaa haetun Palvelupistetyypin kpl maaran
     * @author Rasmus Hyyppä
     */
    public static int getPalveluPisteMaara(Tyyppi t) {
        UserParametrit uP = UserParametrit.getInstance();
        int i;
        switch (t.getTyyppiValue()) {
            case 1:
                i = uP.getPriSalesPpMaara();
                break;
            case 2:
                i = uP.getPriNetworkPpMaara();
                break;
            case 3:
                i = uP.getPriSubscriberPpMaara();
                break;
            case 4:
                i = uP.getPriInvoicePpMaara();
                break;
            case 5:
                i = uP.getCoSalesPpMaara();
                break;
            case 6:
                i = uP.getCoNetworkPpMaara();
                break;
            case 7:
                i = uP.getCoSubscriberPpMaara();
                break;
            case 8:
                i = uP.getCoInvoicePpMaara();
                break;
            default:
                i = 1;
        }
        return i;
    }

    /**
     * Käyttäjän syöttämä arvo yhdistetään Binomial jakaumaan
     * Tätä käytetään Asiakas luokassa
     * 
     * @return palauttaa Binomial discretegeneraattorin
     * @author Rasmus Hyyppä
     */
    public static DiscreteGenerator getAsiakasJakauma() {
        double jakaumaNumero = UserParametrit.getInstance().getAsiakasTyyppiJakauma();
        System.out.println("Jakauma numero: " + jakaumaNumero); // 0.5 < Henkilöasiakas, 0.5 > Yritysasiakas
        // Jakauma i, 100 yritystä (100%)
        return new Binomial(jakaumaNumero, 100);
    }

    /**
     * 
     * @param Ottaa vastaan Tyypin jolla tunnistetaan minkä aika annetaan
     * @return Palauttaa kayttajan antaman keskimääräisen palveluajan
     * @author Rasmus Hyyppä
     */
    public static Normal getPalveluPisteJakauma(Tyyppi t) {
        UserParametrit uP = UserParametrit.getInstance();

        if (t == Tyyppi.PRI_SALES_DEPART) {
            return getNormalPriSales(uP.getPriSalesKkPAika());
        }

        if (t == Tyyppi.PRI_NETWORK_DEPART) {
            return getNormalPriNetwork(uP.getPriNetworkKkPAika());
        }

        if (t == Tyyppi.PRI_SUBSCRIBER_DEPART) {
            return getNormalPriSubscriber(uP.getPriSubscriberKkPAika());
        }

        if (t == Tyyppi.PRI_INVOICE_DEPART) {
            return getNormalPriInvoice(uP.getPriInvoiceKkPAika());
        }

        if (t == Tyyppi.CO_SALES_DEPART) {
            return getNormalCoSales(uP.getCoSalesKkPAika());
        }

        if (t == Tyyppi.CO_NETWORK_DEPART) {
            return getNormalCoNetwork(uP.getCoNetworkKkPAika());
        }

        if (t == Tyyppi.CO_SUBSCRIBER_DEPART) {
            return getNormalCoSubscriber(uP.getCoSubscriberKkPAika());
        }

        if (t == Tyyppi.CO_INVOICE_DEPART) {
            return getNormalCoInvoice(uP.getCoInvoiceKkPAika());
        }

        return null;

    }

    /**
     * Puhelinvalikkojen odotusaika Normalina jakaumana
     * 
     * @return Normal return the pValikkoAika
     * @author Rasmus Hyyppä
     */
    public static Normal getNormalValikko() {
        double aika = UserParametrit.getInstance().getPValikkoAika();
        return new Normal(aika - (aika / 2), aika + (aika / 2));
    }

    /**
     * Normaalijakauma Pri sales tyypistä
     * 
     * @return Normal return the priSalesKkPAika
     * @author Rasmus Hyyppä
     */
    public static Normal getNormalPriSales(double aika) {
        return new Normal(aika - (aika / 2), aika + (aika / 2));
    }

    /**
     * Normaalijakauma Pri network tyypistä
     * 
     * @return Normal return the priSalesKkPAika
     * @author Rasmus Hyyppä
     */
    public static Normal getNormalPriNetwork(double aika) {
        return new Normal(aika - (aika / 2), aika + (aika / 2));
    }

    /**
     * Normaalijakauma Pri subscriber tyypistä
     * 
     * @return Normal return the priSalesKkPAika
     * @author Rasmus Hyyppä
     */
    public static Normal getNormalPriSubscriber(double aika) {
        return new Normal(aika - (aika / 2), aika + (aika / 2));
    }

    /**
     * Normaalijakauma Pri invoice tyypistä
     * 
     * @return Normal return the priSalesKkPAika
     * @author Rasmus Hyyppä
     */
    public static Normal getNormalPriInvoice(double aika) {
        return new Normal(aika - (aika / 2), aika + (aika / 2));
    }

    /**
     * Normaalijakauma Co sales tyypistä
     * 
     * @return Normal return the priSalesKkPAika
     * @author Rasmus Hyyppä
     */
    public static Normal getNormalCoSales(double aika) {
        return new Normal(aika - (aika / 2), aika + (aika / 2));
    }

    /**
     * Normaalijakauma Co network tyypistä
     * 
     * @return Normal return the priSalesKkPAika
     * @author Rasmus Hyyppä
     */
    public static Normal getNormalCoNetwork(double aika) {
        return new Normal(aika - (aika / 2), aika + (aika / 2));
    }

    /**
     * Normaalijakauma Co subscriber tyypistä
     * 
     * @return Normal return the priSalesKkPAika
     * @author Rasmus Hyyppä
     */
    public static Normal getNormalCoSubscriber(double aika) {
        return new Normal(aika - (aika / 2), aika + (aika / 2));
    }

    /**
     * Normaalijakauma Co invoice tyypistä
     * 
     * @return Normal return the priSalesKkPAika
     * @author Rasmus Hyyppä
     */
    public static Normal getNormalCoInvoice(double aika) {
        return new Normal(aika - (aika / 2), aika + (aika / 2));
    }
}
