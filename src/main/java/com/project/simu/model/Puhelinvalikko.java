package com.project.simu.model;

import com.project.eduni.distributions.ContinuousGenerator;
import com.project.simu.constants.Tyyppi;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Tapahtuma;
import com.project.simu.framework.Tapahtumalista;
import com.project.simu.framework.Trace;

public class Puhelinvalikko extends Palvelupiste {

    private SimulaationSuureet sS;

    public Puhelinvalikko(ContinuousGenerator generator, Tapahtumalista tapahtumalista, Tyyppi tyyppi,
            double maxJononPituus) {
        super(generator, tapahtumalista, tyyppi, maxJononPituus);
        this.sS = SimulaationSuureet.getInstance();
    }

    @Override
    public void addJonoon(Asiakas as) {
        super.addJonoon(as);
        sS.asLisattyJonoon();
    }

    @Override
    public boolean getOnPaikalla() {
        return true; // Puhelinvalikko on aina paikalla
    }

    // **************************************************
    // Muut toiminnot periytyvät Palvelupiste luokalta en poista vielä jos käytetään

    @Override
    public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
        return super.otaJonosta();
    }

    @Override
    public void aloitaPalvelu() {
        super.aloitaPalvelu();
    }

    @Override
    public boolean kyllastyiJonoon(Asiakas as, double jAika) {
        return super.kyllastyiJonoon(as, jAika);
    }

    @Override
    public void raportti() {
        super.raportti();
        // Puhelinvalikkojen suureet?
        // sS.addTotalPAPV(palveluAika);
        // sS.addTotalJonoAikaPV(jonoAika);
        // sS.addAsTotalAikaPV(asTotalAika);
        // sS.addAvgJonotusAikaPV(getAvgPalveluAika());
        // sS.addAvgPPOleskeluAikaPV(getAvgOleskeluAika());
        // sS.addAvgTotalPAPV(getAvgJonotusAika());
        // sS.addPalveluprosenttiPV(getPProsentti());
    }

}
