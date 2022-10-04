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
	private UserParametrit uP;
	private SimulaationSuureet sS;

	// OmaMoottori
	public OmaMoottori(INewSimulationControllerMtoV kontrolleri) {
		super(kontrolleri);
		sS = SimulaationSuureet.getInstance();
		uP = UserParametrit.getInstance();

		palvelupisteet = new Palvelupiste[uP.getAllPPMaara()];
		palvelupisteet[0] = new Palvelupiste(uP.getPAJakauma(11), tapahtumalista,
				Tyyppi.BLENDER_VALIKKO_DEPART);
		palvelupisteet[1] = new Palvelupiste(uP.getPAJakauma(9), tapahtumalista,
				Tyyppi.PRI_VALIKKO_DEPART);
		palvelupisteet[2] = new Palvelupiste(uP.getPAJakauma(10), tapahtumalista,
				Tyyppi.CO_VALIKKO_DEPART);

		int ppIndex = UserParametrit.getMinimiPPMaara();
		for (int j = 0; j < 8; j++) {
			int ppType = Tyyppi.values()[j].getTypeValue();
			for (int i = 0; i < uP.getPPMaara(ppType); i++) {
				palvelupisteet[ppIndex] = new Palvelupiste(uP.getPAJakauma(ppType),
						tapahtumalista,
						Tyyppi.values()[j]);
				ppIndex++;
			}
		}
		// Asiakkaitten tulomäärän määräävä jakauma
		// Poisson(tunti/asiakasmäärä tunnissa) -> käytetään real life examplena
		saapumisprosessi = new Saapumisprosessi(new Poisson(3600 / uP.getAsMaara()), tapahtumalista,
				Tyyppi.ARRIVAL);
		// Kontrollerille tieto palvelupisteiden määrästä
		kontrolleri.ilmoitaPalveluPisteet(sS.getYritysPP(),
				sS.getYksityisPP());
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

		Asiakas as;
		Tyyppi tapahtuma = t.getTyyppi();

		kontrolleri.ilmoitaJononKoko(ppJonoStatus(1), ppJonoStatus(5));

		// Saapumistapahtumat
		if (tapahtuma == Tyyppi.ARRIVAL) {
			as = new Asiakas();
			palvelupisteet[haeAs(Tyyppi.BLENDER_VALIKKO_DEPART.getTypeValue())].addJonoon(as);
			saapumisprosessi.generoiSeuraava();
		}

		// Blendervalikko
		else if (tapahtuma == Tyyppi.BLENDER_VALIKKO_DEPART) {
			as = palvelupisteet[otaPalveltuAs(tapahtuma)].otaJonosta();

			// Lisätään jonoon
			palvelupisteet[haeAs(as.getAsType())].addJonoon(as);

		}

		// Henkilö- ja yritysasiakas valikon poistumiset
		else if (tapahtuma == Tyyppi.CO_VALIKKO_DEPART || tapahtuma == Tyyppi.PRI_VALIKKO_DEPART) {
			as = palvelupisteet[otaPalveltuAs(tapahtuma)].otaJonosta();

			// Lisätään jonoon
			palvelupisteet[haeAs(as.setAsType())].addJonoon(as);
		}

		// Asiakaspalvelija pisteiden poistumiset
		else {
			// Otetaan jonosta ja asetetaan poistumisaika
			as = palvelupisteet[otaPalveltuAs(tapahtuma)].otaJonosta();
			if (as.getReRouted()) {
				palvelupisteet[haeAs(as.setReRouted())].addJonoon(as);
				return;
			}

			as.setAsPoistumisaika(Kello.getInstance().getAika());
			if (as.isJonotukseenKyllastynyt()) {
				sS.asLahtenytJonostaKpl();
			} else {
				// Updatetaan tämän hetkinen tilanne
				kontrolleri.asPPMaara(ppPalveltuStatus(1), ppPalveltuStatus(5));
				sS.asPalveltuJonosta();
			}

			// Kuinka monta asiakasta on ulkona
			kontrolleri.ulkonaAs(sS.getAsPalveltuJonostaKpl() +
					sS.getAsLahtenytJonostaKpl());
			// Asiakas ulkona -> Raportoidaan
			as.raportti();
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
	public int haeAs(int ppType) {
		Palvelupiste[] typeVastaavatPPt = new Palvelupiste[uP.getPPMaara(ppType)];
		int i = 0;
		for (Palvelupiste p : palvelupisteet) {
			if (p.getPPTyyppi().getTypeValue() == ppType) {
				typeVastaavatPPt[i] = p;
				i++;
			}
		}

		if (typeVastaavatPPt.length != 1) {
			return typeVastaavatPPt[(int) new Uniform(0, typeVastaavatPPt.length).sample()].getPPNum();
		} else {
			// Mikäli vain 1 kpl ppTyyppiä niin haetaan ensimmäisestä solusta arvot
			return typeVastaavatPPt[0].getPPNum();
		}
	}

	/**
	 * Hakee varattu tilassa olevan palvelupisteen Tyyppiä vasten
	 * 
	 * @return palvelupisteeet[] arraystä oikean index numeron palvelupisteelle
	 * @author Rasmus Hyyppä
	 */
	public int otaPalveltuAs(Tyyppi ppType) {
		for (Palvelupiste p : palvelupisteet) {
			if ((p.getPPTyyppi() == ppType) && p.onVarattu()) {
				return p.getPPNum();
			}
		}
		return -1;
	}

	// Method joka palauttaa 4:n palvelupisteen jono tilanteen. (ppType 1 tai 5)
	public int ppJonoStatus(int ppType) {
		int jonossaAsiakkaita = 0;
		for (int i = 0; i < 4; i++) {
			for (Palvelupiste p : palvelupisteet) {
				if ((p.getPPTyyppi().getTypeValue() == (ppType + i))) {
					jonossaAsiakkaita += p.getJonossaOlevatAs();
				}
			}
		}
		return jonossaAsiakkaita;
	}

	// Method joka palauttaa tilanteen palveluista asiakkaista (ppType 1 tai 5)
	public int ppPalveltuStatus(int ppType) {
		int palveltujaAsiakkaita = 0;
		for (int i = 0; i < 4; i++)
			for (Palvelupiste p : palvelupisteet) {
				if ((p.getPPTyyppi().getTypeValue() == (ppType + i))) {
					palveltujaAsiakkaita += p.getAsPalveltuJonosta();
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
		sS.setAsLisattyJonoon(palvelupisteet[0].getAsLisattyJonoon());
		sS.setSimulointiAika(Kello.getInstance().getAika());
		sS.tulosteet();
	}
}
