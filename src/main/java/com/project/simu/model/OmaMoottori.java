package com.project.simu.model;

import com.project.simu.utilities.ParametriUtilities;
import com.project.SecondaryController;
import com.project.eduni.distributions.Negexp;
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
	public OmaMoottori(SecondaryController kontrolleri) {
		super(kontrolleri);
		this.setViive(500);
		sS = SimulaationSuureet.getInstance();
		uP = UserParametrit.getInstance();

		palvelupisteet = new Palvelupiste[ParametriUtilities.getPpKokonaismaara() + MIN_PALVELUPISTE_MAARA];
		palvelupisteet[0] = new Palvelupiste(ParametriUtilities.getNormalValikko(), tapahtumalista,
				Tyyppi.BLENDER_VALIKKO_DEPART, uP.getAsiakkaidenKarsivallisyys());
		palvelupisteet[1] = new Palvelupiste(ParametriUtilities.getNormalValikko(), tapahtumalista,
				Tyyppi.PRI_VALIKKO_DEPART, uP.getAsiakkaidenKarsivallisyys());
		palvelupisteet[2] = new Palvelupiste(ParametriUtilities.getNormalValikko(), tapahtumalista,
				Tyyppi.CO_VALIKKO_DEPART, uP.getAsiakkaidenKarsivallisyys());

		int ppIndex = MIN_PALVELUPISTE_MAARA;
		for (int j = 0; j < 8; j++) {
			Tyyppi t = Tyyppi.values()[j];
			for (int i = 0; i < ParametriUtilities.getPalveluPisteMaara(t); i++) {
				palvelupisteet[ppIndex] = new Palvelupiste(ParametriUtilities.getPalveluPisteJakauma(t),
						tapahtumalista,
						t, uP.getAsiakkaidenKarsivallisyys());
				ppIndex++;
			}
		}
		saapumisprosessi = new Saapumisprosessi(new Negexp(150, 5), tapahtumalista, Tyyppi.ARRIVAL);
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

		// Saapumistapahtumat
		if (tapahtuma == Tyyppi.ARRIVAL) {
			a = new Asiakas(ParametriUtilities.getAsiakasJakauma(), ParametriUtilities.onkoVaaraValinta());
			palvelupisteet[lisaaAsiakas(Tyyppi.BLENDER_VALIKKO_DEPART.getTyyppiValue())].lisaaJonoon(a);
			saapumisprosessi.generoiSeuraava();
		}

		// Blendervalikko
		else if (tapahtuma == Tyyppi.BLENDER_VALIKKO_DEPART) {
			a = palvelupisteet[otaPalveltuAsiakas(tapahtuma)].otaJonosta();
			palvelupisteet[lisaaAsiakas(a.getAsType())].lisaaJonoon(a);
		}

		// Henkilö- ja yritysasiakas valikon poistumiset
		else if (tapahtuma == Tyyppi.CO_VALIKKO_DEPART || tapahtuma == Tyyppi.PRI_VALIKKO_DEPART) {
			a = palvelupisteet[otaPalveltuAsiakas(tapahtuma)].otaJonosta();
			Palvelupiste p = palvelupisteet[lisaaAsiakas(a.getAsType())];
			/*
			 * sS.updateSuureet(a.getAsType(), kontrolleri,
			 * p.getAsiakkaitaPalveltuJonosta(),
			 * palveluPisteissaJonoa(1), palveluPisteissaJonoa(5));
			 */
			palvelupisteet[lisaaAsiakas(a.setAsiakasTyyppi())].lisaaJonoon(a);
		}

		// Asiakaspalvelija pisteiden poistumiset
		else {
			a = palvelupisteet[otaPalveltuAsiakas(tapahtuma)].otaJonosta();
			if (a.getReRouted()) {
				// palvelupisteestä haetaan uudella asiakastyyppinumerolla oleva asiakas
				palvelupisteet[lisaaAsiakas(a.setReRouted())].lisaaJonoon(a);
			} else {
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
			}
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
	public int lisaaAsiakas(int ppTypeNum) {

		Tyyppi ppType = Tyyppi.values()[ppTypeNum - 1];
		Palvelupiste[] typeVastaavatPp = new Palvelupiste[ParametriUtilities.getPalveluPisteMaara(ppType)];

		int i = 0;
		for (Palvelupiste p : palvelupisteet) {
			if (p.getPalvelupisteenTyyppi() == ppTypeNum) {
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
			if ((p.getPalvelupisteenTyyppi() == ppType.getTyyppiValue()) && p.onVarattu()) {
				return p.getPalveluPisteenNumero();
			}
		}
		return -1;
	}

	public int palveluPisteissaJonoa(int ppType) {
		int jonossaAsiakkaita = 0;
		int i = ppType;

		while ((i - ppType) != 3) {
			for (Palvelupiste p : palvelupisteet) {
				if ((p.getPalvelupisteenTyyppi() == i)) {
					jonossaAsiakkaita += p.getJonossaOlevatAsiakkaat();
				}
			}
			i++;
		}
		return jonossaAsiakkaita;
	}

	// tulokset
	@Override
	protected void tulokset() {

		for (Palvelupiste p : palvelupisteet) {
			p.raportti();
		}

		sS.setSimulointiAika(Kello.getInstance().getAika());
		sS.tulosteet();
	}
}
