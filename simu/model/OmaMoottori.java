package simu.model;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import eduni.distributions.Uniform;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;

	// OmaMoottori

	public OmaMoottori() {

		palvelupisteet = new Palvelupiste[3];

		palvelupisteet[0] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.DEPART1);
		palvelupisteet[1] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.DEPART2);
		palvelupisteet[2] = new Palvelupiste(new Normal(5, 3), tapahtumalista, TapahtumanTyyppi.DEPART3);

		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.ARRIVAL1);

	}

	// Alustukset

	@Override
	protected void alustukset() {

		// Ensimmäinen saapuminen järjestelmään.

		saapumisprosessi.generoiSeuraava(); 
	}

	// suoritaTapahtuma (B-vaiheen tapahtumat)

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {

		Asiakas a;
		switch (t.getTyyppi()) {

			case ARRIVAL1:
				// TODO: Uniform testausta varten, tämä asetetaan käyttäjäparametreistä valmiina
				// Asetetaan kayttajan valitsema jakauma asiakastyypin luomiseen.

				ContinuousGenerator kayttajanParametriJakaumalle = new Uniform(1, 8);
				a = new Asiakas(kayttajanParametriJakaumalle);
				palvelupisteet[0].lisaaJonoon(a);
				saapumisprosessi.generoiSeuraava();
				break;
			case DEPART1:
				a = palvelupisteet[0].otaJonosta();
				palvelupisteet[1].lisaaJonoon(a);
				break;
			case DEPART2:
				a = palvelupisteet[1].otaJonosta();
				palvelupisteet[2].lisaaJonoon(a);
				break;
			case DEPART3:
				a = palvelupisteet[2].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
		}
	}

	// tulokset

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
	}

}
