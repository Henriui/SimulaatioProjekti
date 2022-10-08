package com.project.simu.model;

import com.project.view.INewSimulationControllerMtoV;

import java.util.ArrayList;
import java.util.Collections;

import com.project.eduni.distributions.Poisson;
import com.project.simu.constants.Tyovuoro;
import com.project.simu.constants.Tyyppi;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Moottori;
import com.project.simu.framework.Saapumisprosessi;
import com.project.simu.framework.Tapahtuma;
import com.project.simu.utilities.ArrayUtilities;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;
	private Parametrit uP;
	private SimulaatioData sS;

	// OmaMoottori
	public OmaMoottori(INewSimulationControllerMtoV kontrolleri) {
		super(kontrolleri);
		sS = new SimulaatioData();
		uP = Parametrit.getInstance();

		palvelupisteet = new Palvelupiste[uP.getAllPPMaara()];
		palvelupisteet[0] = new Puhelinvalikko(uP.getPAJakauma(11), tapahtumalista,
				Tyyppi.BLENDER_VALIKKO_DEPART, uP.getMaxJononPituus());
		palvelupisteet[1] = new Puhelinvalikko(uP.getPAJakauma(9), tapahtumalista,
				Tyyppi.PRI_VALIKKO_DEPART, uP.getMaxJononPituus());
		palvelupisteet[2] = new Puhelinvalikko(uP.getPAJakauma(10), tapahtumalista,
				Tyyppi.CO_VALIKKO_DEPART, uP.getMaxJononPituus());

		// Muuttujat asiakaspalvelijoiden asettamiseksi
		int[] tyoVuoroArr = new int[Tyovuoro.size];
		int ppIndex = Parametrit.getMinPPMaara();
		for (int j = 0; j < 8; j++) {
			int ppType = Tyyppi.values()[j].getTypeValue();
			for (int i = 0; i < uP.getPPMaara(ppType); i++) {
				palvelupisteet[ppIndex] = luoAsiakaspalvelija(ppType, j, tyoVuoroArr);
				ppIndex++;
			}
		}

		// Poisson(tunti/asiakasmäärä tunnissa) -> käytetään real life examplena
		saapumisprosessi = new Saapumisprosessi(new Poisson(3600 / uP.getAsMaara()), tapahtumalista,
				Tyyppi.ARRIVAL);
	}

	// Alustukset
	@Override
	protected void alustukset() {
		Kello.getInstance().setAika(0);
		// Ensimmäinen saapuminen järjestelmään.
		saapumisprosessi.generoiSeuraava();
	}

	// suoritaTapahtuma (B-vaiheen tapahtumat)
	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {

		Asiakas as;
		Tyyppi tapahtuma = t.getTyyppi();
		kontrolleri.paivitaPalveluPisteet(sS.getPPStatus(palvelupisteet));

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
			palvelupisteet[haeAs(as.getAsType())].addJonoon(as);
		}

		// Henkilö- ja yritysasiakas valikon poistumiset
		else if (tapahtuma == Tyyppi.CO_VALIKKO_DEPART || tapahtuma == Tyyppi.PRI_VALIKKO_DEPART) {
			as = palvelupisteet[otaPalveltuAs(tapahtuma)].otaJonosta();
			palvelupisteet[haeAs(as.setAsType())].addJonoon(as);
		}

		// Asiakaspalvelija pisteiden poistumiset
		else {
			// Otetaan jonosta ja asetetaan poistumisaika
			as = palvelupisteet[otaPalveltuAs(tapahtuma)].otaJonosta();
			if (as.getReRouted() && !as.isJonotukseenKyllastynyt()) {
				sS.addAsReRouted();
				palvelupisteet[haeAs(as.setReRouted())].addJonoon(as);
				return;
			}

			as.setAsPoistumisaika(Kello.getInstance().getAika());
			if (as.isJonotukseenKyllastynyt()) {
				sS.addAsPoistunut();
			} else {
				sS.addAsPalveltu();
			}

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
		for (Palvelupiste p : palvelupisteet) {
			if (p.getPPTyyppi().getTypeValue() == ppType && p.getOnPaikalla()) {
				typeVastaavatPPt.add(p);
			}
		}

		if (typeVastaavatPPt.size() != 0) {
			Collections.sort(typeVastaavatPPt);
			return typeVastaavatPPt.get(0).getPPId();
		}

		// Mikäli ei ole vuorossa ketään lisätään asiakas töissä
		// olleen jonoon mistä hän poistuu
		for (Palvelupiste p : palvelupisteet) {
			if (p.getPPTyyppi().getTypeValue() == ppType) {
				typeVastaavatPPt.add(p);
			}
		}
		return typeVastaavatPPt.get(0).getPPId();
	}

	/**
	 * Hakee varattu tilassa olevan palvelupisteen Tyyppiä vasten
	 * 
	 * @return palvelupisteeet[] arraystä oikean index numeron palvelupisteelle
	 * @author Rasmus Hyyppä
	 */
	private int otaPalveltuAs(Tyyppi ppType) {
		int indexOfPalvelupiste = 0;

		for (Palvelupiste p : palvelupisteet) {
			if ((p.getPPTyyppi() == ppType) && p.onVarattu()) {
				// indexOfPalvelupiste =
				return p.getPPId();// indexOfPalvelupiste;
			}
		}
		return -1; // Error
	}

	// Method luo asiakaspalvelijan ja antaa työvuoron simulaatioajasta
	private Asiakaspalvelija luoAsiakaspalvelija(int ppType, int j, int[] tyoVuoroArr) {
		int simAikaTvIndex = (int) uP.getSimulaationAika() - 8; // 8 h
		// Asetetaan ensimmäiseen työvuoroon joka päättyy 8h päästä
		Asiakaspalvelija aP = new Asiakaspalvelija(uP.getPAJakauma(ppType), tapahtumalista, Tyyppi.values()[j],
				uP.getMaxJononPituus(), Tyovuoro.EIGHT);
		// Mikäli simulointiaika ei ylitä normaalia työpäivää
		if (simAikaTvIndex <= 0) {
			tyoVuoroArr[aP.getTvIndex()]++;
			return aP;
		}

		for (Palvelupiste p : palvelupisteet) {
			if (p != null) {
				// Asetetaan vikaan vuoroon vähintään yksi
				if (p.getPPTyyppi().getTypeValue() == ppType && aP.getTv() == Tyovuoro.EIGHT) {
					aP.setTv(simAikaTvIndex);
				}
				// Mikäli työvuorot kattavat koko simuloinnin ajan
				else if (aP.getTv() == Tyovuoro.values()[simAikaTvIndex]) {
					aP.setTv(ArrayUtilities.getMinValue(tyoVuoroArr));
				}
			}
		}
		// Lopuksi
		tyoVuoroArr[aP.getTvIndex()]++;
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

		kontrolleri.showTulokset(sS);
	}
}
