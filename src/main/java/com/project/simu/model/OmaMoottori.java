package com.project.simu.model;

import com.project.view.INewSimulationControllerMtoV;
import com.project.eduni.distributions.Poisson;
import com.project.eduni.distributions.Uniform;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Moottori;
import com.project.simu.framework.Saapumisprosessi;
import com.project.simu.framework.Tapahtuma;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;
	private final static int MIN_PALVELUPISTE_MAARA = 3;
	private UserParametrit uP;
	private SimulaationSuureet sS;

	// OmaMoottori
	public OmaMoottori(INewSimulationControllerMtoV kontrolleri) {
		super(kontrolleri);
		sS = SimulaationSuureet.getInstance();
		uP = UserParametrit.getInstance();

		palvelupisteet = new Palvelupiste[uP.getAllPPMaara() + MIN_PALVELUPISTE_MAARA];
		palvelupisteet[0] = new Palvelupiste(uP.getPAPuhelinValikolle(), tapahtumalista,
				Tyyppi.BLENDER_VALIKKO_DEPART, uP.getAsiakkaidenKarsivallisyys());
		palvelupisteet[1] = new Palvelupiste(uP.getPAPuhelinValikolle(), tapahtumalista,
				Tyyppi.PRI_VALIKKO_DEPART, uP.getAsiakkaidenKarsivallisyys());
		palvelupisteet[2] = new Palvelupiste(uP.getPAPuhelinValikolle(), tapahtumalista,
				Tyyppi.CO_VALIKKO_DEPART, uP.getAsiakkaidenKarsivallisyys());

		int ppIndex = MIN_PALVELUPISTE_MAARA;
		for (int j = 0; j < 8; j++) {
			Tyyppi t = Tyyppi.values()[j];
			for (int i = 0; i < uP.getPalveluPisteMaara(t); i++) {
				palvelupisteet[ppIndex] = new Palvelupiste(uP.getPAJakauma(t),
						tapahtumalista,
						t, uP.getAsiakkaidenKarsivallisyys());
				ppIndex++;
			}
		}

		/**
		 * Asiakkaitten tulomäärän määräävä jakauma
		 * NexExp -> kasvaa jatkuvasti Negexp(10, 5)
		 * LogNormal -> Säädettävissä asiakas jakauma
		 * LogNormal(new LogNormal(3, 1));
		 * Poisson(tunti/asiakasmäärä tunnissa) -> käytetään real life example
		 */
		saapumisprosessi = new Saapumisprosessi(new Poisson(3600 / uP.getAsiakasMaara()), tapahtumalista,
				Tyyppi.ARRIVAL);
		kontrolleri.ilmoitaPalveluPisteet(sS.getYritysPalvelupisteita(),
				sS.getYksityisPalvelupisteita());
	}

	// Alustukset
	@Override
	protected void alustukset() {
		Kello.getInstance().setAika(0);
		sS.resetSuureet();
		// Ensimmäinen saapuminen järjestelmään.
		saapumisprosessi.generoiSeuraava();
	}

	// suoritaTapahtuma (B-vaiheen tapahtumat)
	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {

		Asiakas a;
		Tyyppi tapahtuma = t.getTyyppi();

		kontrolleri.ilmoitaJononKoko(palveluPisteissaJonoa(1), palveluPisteissaJonoa(5));

		// Saapumistapahtumat
		if (tapahtuma == Tyyppi.ARRIVAL) {
			a = new Asiakas(uP.getAsiakasJakauma(), uP.onkoVaaraValinta());
			palvelupisteet[lisaaAsiakas(Tyyppi.BLENDER_VALIKKO_DEPART.getTyyppiValue())].lisaaJonoon(a);
			saapumisprosessi.generoiSeuraava();
		}

		// Blendervalikko
		else if (tapahtuma == Tyyppi.BLENDER_VALIKKO_DEPART) {
			a = palvelupisteet[otaPalveltuAsiakas(tapahtuma)].otaJonosta();

			// Lisätään jonoon
			palvelupisteet[lisaaAsiakas(a.getAsType())].lisaaJonoon(a);
		}

		// Henkilö- ja yritysasiakas valikon poistumiset
		else if (tapahtuma == Tyyppi.CO_VALIKKO_DEPART || tapahtuma == Tyyppi.PRI_VALIKKO_DEPART) {
			a = palvelupisteet[otaPalveltuAsiakas(tapahtuma)].otaJonosta();

			// Lisätään jonoon
			palvelupisteet[lisaaAsiakas(a.setAsiakasTyyppi())].lisaaJonoon(a);
		}

		// Asiakaspalvelija pisteiden poistumiset
		else {
			// Otetaan jonosta ja asetetaan poistumisaika
			a = palvelupisteet[otaPalveltuAsiakas(tapahtuma)].otaJonosta();
			a.setPoistumisaika(Kello.getInstance().getAika());

			if (a.getReRouted()) {
				palvelupisteet[lisaaAsiakas(a.setReRouted())].lisaaJonoon(a);
				return;
			}

			if (a.isJonotukseenKyllastynyt()) {
				sS.setAsiakkaitaPoistunutJonostaKpl();
			} else {
				// Updatetaan tämän hetkinen tilanne
				kontrolleri.asiakkaitaPalveluPisteella(palveluPisteissaOleskellut(1), palveluPisteissaOleskellut(5));
				sS.setAsiakkaitaPalveltuJonostaKpl();
			}

			// Kuinka monta asiakasta on ulkona
			kontrolleri.ulkonaAsiakkaita(sS.getAsiakkaitaPalveltuJonostaKpl() +
					sS.getAsiakkaitaPoistunutJonostaKpl());
			// Asiakas ulkona -> Raportoidaan
			a.raportti();
		}
	}

	/**
	 * Hakee palvelupisteen asiakaan tyyppinumeroa vasten, otaJonosta() methodin
	 * avuksi
	 * 
	 * @param integer tapahtumatyypin numero tai asiakastyypin numero
	 * @return palvelupisteeet[] arraystä oikean index numeron palvelupisteelle
	 * @author Rasmus Hyyppä
	 */
	public int lisaaAsiakas(int ppType) {

		Tyyppi t = Tyyppi.values()[ppType - 1];
		Palvelupiste[] typeVastaavatPp = new Palvelupiste[uP.getPalveluPisteMaara(t)];

		int i = 0;
		for (Palvelupiste p : palvelupisteet) {
			if (p.getPalvelupisteenTyyppi() == t) {
				typeVastaavatPp[i] = p;
				i++;
			}
		}

		// TODO: Mahdollisesti tasajaukauman sijaan simulaattori jakaakin esim compareTo
		if (typeVastaavatPp.length != 1) {
			return typeVastaavatPp[(int) new Uniform(0, typeVastaavatPp.length).sample()].getPalveluPisteenNumero();
		} else {
			// Mikäli vain 1 kpl ppTyyppiä niin haetaan ensimmäisestä solusta arvot
			return typeVastaavatPp[0].getPalveluPisteenNumero();
		}
	}

	/**
	 * Hakee varattu tilassa olevan palvelupisteen Tyyppiä vasten
	 * 
	 * @return palvelupisteeet[] arraystä oikean index numeron palvelupisteelle
	 * @author Rasmus Hyyppä
	 */
	public int otaPalveltuAsiakas(Tyyppi ppType) {
		for (Palvelupiste p : palvelupisteet) {
			if ((p.getPalvelupisteenTyyppi() == ppType) && p.onVarattu()) {
				return p.getPalveluPisteenNumero();
			}
		}
		return -1;
	}

	// Method joka palauttaa 4:n palvelupisteen jono tilanteen. (ppType 1 tai 5)
	public int palveluPisteissaJonoa(int ppType) {
		int jonossaAsiakkaita = 0;
		for (int i = 0; i < 4; i++) {
			for (Palvelupiste p : palvelupisteet) {
				if ((p.getPalvelupisteenTyyppi().getTyyppiValue() == (ppType + i))) {
					jonossaAsiakkaita += p.getJonossaOlevatAsiakkaat();
				}
			}
		}
		return jonossaAsiakkaita;
	}

	// Method joka palauttaa tilanteen palveluista asiakkaista (ppType 1 tai 5)
	public int palveluPisteissaOleskellut(int ppType) {
		int palveltujaAsiakkaita = 0;
		for (int i = 0; i < 4; i++) {
			for (Palvelupiste p : palvelupisteet) {
				if ((p.getPalvelupisteenTyyppi().getTyyppiValue() == (ppType + i))) {
					palveltujaAsiakkaita += p.getAsiakkaitaPalveltuJonosta() + p.getAsiakkaitaReRoutattuJonosta();
				}
			}
		}
		return palveltujaAsiakkaita;
	}

	// tulokset
	@Override
	protected void tulokset() {

		for (Palvelupiste p : palvelupisteet) {
			p.raportti();
		}
		sS.setAsiakkaitaLisattyJonoon(palvelupisteet[0].getAsiakkaitaLisattyJonoon());
		sS.setSimulointiAika(Kello.getInstance().getAika());
		sS.tulosteet();
	}
}
