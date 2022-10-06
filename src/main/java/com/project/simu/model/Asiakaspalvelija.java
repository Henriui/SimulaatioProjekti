package com.project.simu.model;

import com.project.eduni.distributions.ContinuousGenerator;
import com.project.simu.constants.Tyovuoro;
import com.project.simu.constants.Tyyppi;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Tapahtuma;
import com.project.simu.framework.Tapahtumalista;
import com.project.simu.framework.Trace;

public class Asiakaspalvelija extends Palvelupiste {

    // private SimulaationSuureet sS;
    private Tyovuoro tv;

    private int asReRoutedJonosta;
    private double reRouteAika;

    public Asiakaspalvelija(ContinuousGenerator generator, Tapahtumalista tapahtumalista, Tyyppi tyyppi,
            double maxJononPituus, Tyovuoro tv) {
        super(generator, tapahtumalista, tyyppi, maxJononPituus);
        this.tv = tv;

        // this.sS = SimulaationSuureet.getInstance();
        this.reRouteAika = 30; // 30 sekunttia reroute keskustelu
        this.asReRoutedJonosta = 0;
        tyoVuoronAjat();
    }

    @Override
    public void addJonoon(Asiakas as) {
        super.addJonoon(as);
        // sS.addAsJonoon();
    }

    private void tyoVuoronAjat() {
        if (tv.getTyoAlkaa() > 0) {
            this.ppSaapumisAika = tv.getTyoAlkaa();
        } else {
            this.ppSaapumisAika = 0;
        }

        this.ppPoistumisAika = tv.getTyoAlkaa() + 28800;
    }

    @Override
    public void aloitaPalvelu() {
        Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
        Asiakas as = jono.peek();
        // Lisätään jonotusaika & palveluaika suureet muuttujiin
        double jAika = Kello.getInstance().getAika() - as.getAsSaapumisaikaPP();
        double pAika = generator.sample();

        this.varattu = true;
        // Mikäli jonotusaika ylitti niin asiakas poistui ennen palvelua.
        if (kyllastyiJonoon(as, jAika)) {
            return;
        }

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

    public Tyovuoro getTv() {
        return this.tv;
    }

    public int getTvIndex() {
        return tv.getTyoVuoro(tv);
    }

    public void setTv(int tvType) {
        this.tv = Tyovuoro.values()[tvType];
        tyoVuoronAjat();
    }

    public int getAsReRoutedJonosta() {
        return this.asReRoutedJonosta;
    }

    @Override
    public double getPProsentti() {
        if (this.asLisattyJonoon == 0) {
            return 100;
        }
        return (1 / ((double) (this.asLisattyJonoon)
                / (double) (this.asPalveltuJonosta + this.asReRoutedJonosta))) * 100;
    }

    @Override
    public void raportti() {
        super.raportti();
        Trace.out(Trace.Level.INFO, this.ppInfoStr + " siirsi asiakkaita: " + getAsReRoutedJonosta());
        Trace.out(Trace.Level.INFO, this.ppInfoStr + " aloitti työt: " + getPpSaapumisAika());
        Trace.out(Trace.Level.INFO, this.ppInfoStr + " lopetti työt: " + getPpPoistumisAika());
        Trace.out(Trace.Level.INFO, this.ppInfoStr + " työvuoro: " + getTv());
    }
}
