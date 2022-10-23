package com.project.simu.model;

import com.project.eduni.distributions.ContinuousGenerator;
import com.project.simu.constants.Tyyppi;
import com.project.simu.framework.Tapahtumalista;

public class Puhelinvalikko extends Palvelupiste {

    public Puhelinvalikko(ContinuousGenerator generator, Tapahtumalista tapahtumalista, Tyyppi tyyppi,
            double maxJononPituus) {
        super(generator, tapahtumalista, tyyppi, maxJononPituus);
    }

    @Override
    public void addJonoon(Asiakas as) {
        super.addJonoon(as);
    }

    /**
     * Puhelinvalikot ovat aina paikalla 
     * @return True
     * @author Rasmus Hyyppä
     */
    @Override
    public boolean getOnPaikalla() {
        return true;
    }

    @Override
    public Asiakas otaJonosta() {
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
    }

}
