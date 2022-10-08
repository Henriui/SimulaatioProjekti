package com.project.simu.model;

import com.project.view.INewSimulationControllerMtoV;

import java.util.ArrayList;

import com.project.eduni.distributions.Poisson;
import com.project.eduni.distributions.Uniform;
import com.project.simu.constants.Tyovuoro;
import com.project.simu.constants.Tyyppi;
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
		sS = new SimulaationSuureet();
		uP = UserParametrit.getInstance();

		palvelupisteet = new Palvelupiste[uP.getAllPPMaara()];
		palvelupisteet[0] = new Puhelinvalikko(uP.getPAJakauma(11), tapahtumalista,
				Tyyppi.BLENDER_VALIKKO_DEPART, uP.getMaxJononPituus());
		palvelupisteet[1] = new Puhelinvalikko(uP.getPAJakauma(9), tapahtumalista,
				Tyyppi.PRI_VALIKKO_DEPART, uP.getMaxJononPituus());
		palvelupisteet[2] = new Puhelinvalikko(uP.getPAJakauma(10), tapahtumalista,
				Tyyppi.CO_VALIKKO_DEPART, uP.getMaxJononPituus());

		// Muuttujat asiakaspalvelijoiden asettamiseksi
		int ppIndex = UserParametrit.getMinPPMaara();
		int simAikaT = (int) uP.getSimulaationAika() - 8; // 8 h
		Uniform tyoVuoroGenerator = new Uniform(0, simAikaT + 1);
		for (int j = 0; j < 8; j++) {
			int ppType = Tyyppi.values()[j].getTypeValue();
			for (int i = 0; i < uP.getPPMaara(ppType); i++) {
				palvelupisteet[ppIndex] = luoAsiakaspalvelija(ppType, j, simAikaT, tyoVuoroGenerator);
				ppIndex++;
			}
		}

		// Poisson(tunti/asiakasmäärä tunnissa) -> käytetään real life examplena
		saapumisprosessi = new Saapumisprosessi(new Poisson(3600 / uP.getAsMaara()), tapahtumalista,
				Tyyppi.ARRIVAL);
		// Kontrollerille tieto palvelupisteiden määrästä
		// kontrolleri.ilmoitaPalveluPisteet(sS.getYritysPP(), sS.getYksityisPP());
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
			as = new Asiakas(uP.getReRouteChance(), uP.getAsTyyppiJakauma(), uP.getAsTyyppiArr());
			sS.setAsTotalMaara(as.getId());
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
				sS.addAsReRouted();
				palvelupisteet[haeAs(as.setReRouted())].addJonoon(as);
				return;
			}

			as.setAsPoistumisaika(Kello.getInstance().getAika());
			if (as.isJonotukseenKyllastynyt()) {
				sS.addAsPoistunut();
			} else {
				// Updatetaan tämän hetkinen tilanne
				kontrolleri.asPPMaara(ppPalveltuStatus(1), ppPalveltuStatus(5));
				sS.addAsPalveltu();
			}

			// Kuinka monta asiakasta on ulkona
			kontrolleri.ulkonaAs(sS.getAsPalveltu() + sS.getAsPoistunut());
			// Asiakas ulkona -> Raportoidaan
			as.raportti();
			sS.setAvgAsAikaSim((double) (Asiakas.getAsiakasSum() / as.getId()));
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
	private int haeAs(int ppType) {
		ArrayList<Palvelupiste> typeVastaavatPPt = new ArrayList<>();
		int i = 0;
		for (Palvelupiste p : palvelupisteet) {
			if (p != null) {
				if (p.getPPTyyppi().getTypeValue() == ppType && p.getOnPaikalla()) {
					typeVastaavatPPt.add(p);
					i++;
				}
			}
		}

		if (i > 0) {
			return typeVastaavatPPt.get((int) new Uniform(0, i).sample()).getPPNum();
		} else {
			// Mikäli vain 1 kpl ppTyyppiä niin haetaan ensimmäisestä solusta arvot
			return typeVastaavatPPt.get(0).getPPNum();
		}
	}

	/**
	 * Hakee varattu tilassa olevan palvelupisteen Tyyppiä vasten
	 * 
	 * @return palvelupisteeet[] arraystä oikean index numeron palvelupisteelle
	 * @author Rasmus Hyyppä
	 */
	private int otaPalveltuAs(Tyyppi ppType) {
		for (Palvelupiste p : palvelupisteet) {
			if ((p.getPPTyyppi() == ppType) && p.onVarattu()) {
				return p.getPPNum();
			}
		}
		return -1;
	}

	// Method joka palauttaa 4:n palvelupisteen jono tilanteen. (ppType 1 tai 5)
	private int ppJonoStatus(int ppType) {
		int jonossaAsiakkaita = 0;
		for (int i = 0; i < 4; i++) {
			for (Palvelupiste p : palvelupisteet) {
				if (p != null) {
					if ((p.getPPTyyppi().getTypeValue() == (ppType + i))) {
						jonossaAsiakkaita += p.getJonossaOlevatAs();
					}
				}
			}
		}
		return jonossaAsiakkaita;
	}

	// Method joka palauttaa tilanteen palveluista asiakkaista (ppType 1 tai 5)
	private int ppPalveltuStatus(int ppType) {
		int palveltujaAsiakkaita = 0;
		for (int i = 0; i < 4; i++)
			for (Palvelupiste p : palvelupisteet) {
				if (p != null) {
					if ((p.getPPTyyppi().getTypeValue() == (ppType + i))) {
						palveltujaAsiakkaita += p.getAsPalveltuJonosta();
					}
				}
			}
		return palveltujaAsiakkaita;
	}

	// Method luo asiakaspalvelijan ja antaa työvuoron simulaatioajasta
	private Asiakaspalvelija luoAsiakaspalvelija(int ppType, int j, int simAikaT, Uniform tyoVuoroG) {
		// Asetetaan ensimmäiseen työvuoroon joka päättyy 8h päästä
		Asiakaspalvelija aP = new Asiakaspalvelija(uP.getPAJakauma(ppType), tapahtumalista, Tyyppi.values()[j],
				uP.getMaxJononPituus(),
				Tyovuoro.EIGHT);
		// Mikäli simulointiaika ei ylitä normaalia työpäivää
		if (simAikaT == 0) {
			sS.addTyoVuoroArr(0);
			return aP;
		}
		int tVIndex = 0;
		for (Palvelupiste p : palvelupisteet) {
			if (p != null) {
				// Asetetaan vikaan vuoroon vähintään yksi
				if (p.getPPTyyppi().getTypeValue() == ppType && aP.getTv() == Tyovuoro.values()[0]) {
					aP.setTv(simAikaT);
				}
				// Mikäli työvuorot kattavat koko simuloinnin ajan
				else if (aP.getTv() == Tyovuoro.values()[simAikaT]) {
					tVIndex = sS.getMinTyoVuoroArr(); // Jaetaan aina pienimään
					aP.setTv(tVIndex);
				}
			}
		}
		tVIndex = aP.getTvIndex();
		sS.addTyoVuoroArr(tVIndex);
		return aP;
	}

	// tulokset
	@Override
	protected void tulokset() {
		for (Palvelupiste p : palvelupisteet) {
			p.raportti();
			sS.getSuureetPP(p);
		}
		sS.setSimulointiAika(Kello.getInstance().getAika());
		sS.tulosteet();
	}
}
