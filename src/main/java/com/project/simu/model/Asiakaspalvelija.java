package com.project.simu.model;

import com.project.eduni.distributions.ContinuousGenerator;
import com.project.simu.constants.Tyovuoro;
import com.project.simu.constants.Tyyppi;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Tapahtuma;
import com.project.simu.framework.Tapahtumalista;
import com.project.simu.framework.Trace;

/**
 * Asiakaspalvelija palvelupiste simulaattorin tarpeille
 * @author Rasmus Hyyppä
 */
public class Asiakaspalvelija extends Palvelupiste {

    private Tyovuoro tv;

    private int asReRoutedJonosta;
    private double reRouteAika;

    public Asiakaspalvelija(ContinuousGenerator generator, Tapahtumalista tapahtumalista, Tyyppi tyyppi,
            double maxJononPituus, Tyovuoro tv) {
        super(generator, tapahtumalista, tyyppi, maxJononPituus);
        this.tv = tv;
        this.reRouteAika = 30; // 30 sekunttia reroute keskustelu
        this.asReRoutedJonosta = 0;
        tyoVuoronAjat();
    }

    @Override
    public void addJonoon(Asiakas as) {
        super.addJonoon(as);
    }

    /**
     * Asettaa asiakaspalvelian saapumis ja poistumisajat
     * @author Rasmus Hyyppä
     */
    private void tyoVuoronAjat() {
        if (tv.getTyoAlkaa() > 0) {
            this.ppSaapumisAika = tv.getTyoAlkaa();
        } else {
            this.ppSaapumisAika = 0;
        }

        this.ppPoistumisAika = tv.getTyoAlkaa() + 28800;
    }

    /**
     * Asiakaspalveliat tarkistavat ovatko he paikalla,
     * tai onko asiakas soittanut vaaralle linjalle.
     * @author Rasmus Hyyppä
     */
    @Override
    public void aloitaPalvelu() {
        Asiakas as = jono.peek();
        double jAika = Kello.getInstance().getAika() - as.getAsSaapumisaikaPP();
        double pAika = generator.sample();
        this.varattu = true;

        if (!this.getOnPaikalla()) {
            Trace.out(Trace.Level.INFO, "Asiakas ei saanut palvelua enään: " + as.getId());
            maxJononPituus = 14;
            jAika = 15;
        }
        // Mikäli jonotusaika ylitti niin asiakas poistui ennen palvelua.
        if (kyllastyiJonoon(as, jAika)) {
            return;
        }

        Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
        if (as.getReRouted()) {
            Trace.out(Trace.Level.INFO, "Asiakas siirretään oikeaan jonoon: " + as.getId());
            pAika = this.reRouteAika; // 30 sekunttia reroute keskustelu
            this.asReRoutedJonosta++;
        } else {
            this.asPalveltuJonosta++;
        }

        this.palveluAika += pAika;
        this.jonoAika += jAika;
        this.tapahtumalista.lisaa(new Tapahtuma(this.ppTyyppi, Kello.getInstance().getAika() + pAika));
    }

    /** 
     * Getter asiakaspalvelian tyovuorolle
     * @return Tyovuoro
     * @author Rasmus Hyyppä
     */
    public Tyovuoro getTv() {
        return this.tv;
    }

    /** 
     * Getter Tyovuoro luokan enum indexille 
     * @return int
     * @author Rasmus Hyyppä
     */
    public int getTvIndex() {
        return tv.getTyoVuoro(tv);
    }

    /**
     * Setter asiakaspalvelian tyovuorolle kayttaen tyovuoro luokan indexia parametrina
     * @param tvType
     * @author Rasmus Hyyppä
     */
    public void setTv(int tvType) {
        this.tv = Tyovuoro.values()[tvType];
        tyoVuoronAjat();
    }

    
    /** 
     * Getter reroute asiakkaiden maaralle
     * @return int
     * @author Rasmus Hyyppä
     */
    public int getAsReRoutedJonosta() {
        return this.asReRoutedJonosta;
    }

    @Override
    public double getPProsentti() {
        if (this.asLisattyJonoon == 0) {
            return 100;
        }
        return ((double) this.asPalveltuJonosta / (double) this.asLisattyJonoon)
                * 100;
    }

    /**
     * Getter kokonais reroute ajalle
     * @return double return the reRouteAika
     * @author Rasmus Hyyppä
     */
    public double getReRouteAika() {
        return reRouteAika;
    }


    /**
     * Asiakaspalvelian raporttiin lisatyt muuttujat: Reroutatut asiakkaat, asiakaspalvelian saapumisajat toihin ja tyovuorot
     * @author Rasmus Hyyppä
     */
    @Override
    public void raportti() {
        super.raportti();
        Trace.out(Trace.Level.INFO, this.ppInfoStr + " siirsi asiakkaita: " + getAsReRoutedJonosta());
        Trace.out(Trace.Level.INFO, this.ppInfoStr + " aloitti työt: " + getPpSaapumisAika());
        Trace.out(Trace.Level.INFO, this.ppInfoStr + " lopetti työt: " + getPpPoistumisAika());
        Trace.out(Trace.Level.INFO, this.ppInfoStr + " työvuoro: " + getTv());
    }
}
