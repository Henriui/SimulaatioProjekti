package com.project.simu.model;

import java.util.LinkedList;

import com.project.eduni.distributions.ContinuousGenerator;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Tapahtuma;
import com.project.simu.framework.Tapahtumalista;
import com.project.simu.framework.Trace;

// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava

public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus

	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private Tyyppi skeduloitavanTapahtumanTyyppi;
	private static int palvelupisteenUID = 0;

	private SimulaationSuureet sS;
	private int palvelupisteenID;
	private int asiakkaitaLisattyJonoon;
	private int asiakkaitaPalveltuJonosta;
	private int asiakkaitaPoistunutJonosta;
	private int asiakkaitaReRoutattuJonosta;
	private double palveluAikaSuurre;
	private double jonoAikaSuurre;
	private double asiakkaittenKokonaisAikaSuurre;
	private double maxJonoParametri;

	// JonoStartegia strategia;
	// optio: asiakkaiden järjestys

	private boolean varattu = false;

	// PalveluPiste

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, Tyyppi tyyppi,
			double maxJonoParametri) {
		sS = SimulaationSuureet.getInstance();
		palvelupisteenID = palvelupisteenUID;
		palvelupisteenUID++;
		this.maxJonoParametri = maxJonoParametri;
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;

	}

	// lisaaJonoon

	public void lisaaJonoon(Asiakas a) { // Jonon 1. asiakas aina palvelussa

		a.setSaapumisaikaPp(Kello.getInstance().getAika());
		// Lisätään palvelupisteen jonossa olleet asiakkaat suurre
		asiakkaitaLisattyJonoon++;
		sS.setAsiakkaitaLisattyJonoonKpl();
		jono.add(a);
	}

	// otaJonosta

	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut

		Asiakas a = jono.poll();
		varattu = false;
		if (a.getPoistumisaikaPp() != (a.getSaapumisaika() + maxJonoParametri)) {
			a.setPoistumisaikaPp(Kello.getInstance().getAika());
		}
		// Lisätään asiakkaan palvelupisteen oleskeluaika suurre
		asiakkaittenKokonaisAikaSuurre += a.getPoistumisaikaPp() - a.getSaapumisaikaPp();

		return a;
	}

	// aloitaPalvelu

	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
		varattu = true;
		Asiakas a = jono.peek();
		// Lisätään jonotusaika & palveluaika suureet muuttujiin
		double jonotusAika = Kello.getInstance().getAika() - a.getSaapumisaikaPp();
		double palveluaika = generator.sample();

		// Mikäli jonotusaika ylitti niin asiakas poistui ennen palvelua.
		if (jonotusAika > maxJonoParametri) {
			Trace.out(Trace.Level.INFO, "Asiakas kyllästyi jonottamaan: " + a.getId());
			asiakkaitaPoistunutJonosta++;
			sS.setAsiakkaitaPoistunutJonostaKpl();

			jonotusAika = a.getSaapumisaikaPp() + maxJonoParametri;
			jonoAikaSuurre += maxJonoParametri;
			a.setPoistumisaikaPp(jonotusAika);
			tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, jonotusAika));
			return;
		}

		if (a.getReRouted() && a.getAsType() < 9) {
			Trace.out(Trace.Level.INFO, "Asiakas siirretään oikeaan jonoon: " + a.getId());
			// TODO: mahdollinen parametri? 30 sekunttia reroute keskustelu
			palveluaika = 30;
			asiakkaitaReRoutattuJonosta++;
			sS.setAsiakkaitaReRoutattuJonostaKpl();
		} else {
			asiakkaitaPalveltuJonosta++;
			sS.setAsiakkaitaPalveltuJonostaKpl();
		}

		palveluAikaSuurre += palveluaika;
		jonoAikaSuurre += jonotusAika;
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika() + palveluaika));
	}

	public int getPalvelupisteenTyyppi() {
		return skeduloitavanTapahtumanTyyppi.getTyyppiValue();
	}

	public int getPalveluPisteenNumero() {
		return palvelupisteenID;
	}

	// onVarattu

	public boolean onVarattu() {
		return varattu;
	}

	// onJonossa

	public boolean onJonossa() {
		return jono.size() != 0;
	}

	public static void resetPpID() {
		Palvelupiste.palvelupisteenUID = 0;
	}

	/**
	 * @return int return the asiakkaitaLisattyJonoon
	 */
	public int getAsiakkaitaLisattyJonoon() {
		return asiakkaitaLisattyJonoon;
	}

	/**
	 * @return int return the asiakkaitaPalveltuJonosta
	 */
	public int getAsiakkaitaPalveltuJonosta() {
		return asiakkaitaPalveltuJonosta;
	}

	public int getAsiakkaitaPoistunutJonosta() {
		return asiakkaitaPoistunutJonosta;
	}

	/**
	 * @return double return the palveluAikaSuurre
	 */
	public double getPalveluAikaSuurre() {
		return palveluAikaSuurre;
	}

	/**
	 * @return double return the jonoAikaSuurre
	 */
	public double getJonoAikaSuurre() {
		return jonoAikaSuurre;
	}

	public int getJonossaOlevatAsiakkaat() {
		return jono.size();
	}

	/**
	 * @return double return the asiakkaittenKokonaisAikaSuurre
	 */
	public double getAsiakkaittenKokonaisAikaSuurre() {
		return asiakkaittenKokonaisAikaSuurre;
	}

	public double getAvgPalveluAika() {
		return palveluAikaSuurre / asiakkaitaPalveltuJonosta;
	}

	public double getAvgOleskeluAika() {
		return asiakkaittenKokonaisAikaSuurre / asiakkaitaPalveltuJonosta;
	}

	public double getAvgJonotusAika() {
		return jonoAikaSuurre / asiakkaitaLisattyJonoon;
	}

	public double getPalveluprosentti() {
		return (1 / ((double) asiakkaitaLisattyJonoon / (double) asiakkaitaPalveltuJonosta)) * 100;
	}

	public void raportti() {

		Trace.out(Trace.Level.INFO, "\nPalvelupisteeseen " + skeduloitavanTapahtumanTyyppi + "," + palvelupisteenID
				+ " saapui: " + asiakkaitaLisattyJonoon);
		Trace.out(Trace.Level.INFO,
				"Palvelupisteessä  " + skeduloitavanTapahtumanTyyppi + "," + palvelupisteenID + " palveltiin: "
						+ asiakkaitaPalveltuJonosta);
		Trace.out(Trace.Level.INFO,
				"Palvelupisteessä  " + skeduloitavanTapahtumanTyyppi + "," + palvelupisteenID
						+ " kyllästyi jonottamaan: "
						+ asiakkaitaPoistunutJonosta);
		Trace.out(Trace.Level.INFO,
				"Palvelupisteessä  " + skeduloitavanTapahtumanTyyppi + "," + palvelupisteenID
						+ " siirretty oikeaan paikkaan: "
						+ asiakkaitaReRoutattuJonosta);

		Trace.out(Trace.Level.INFO,
				"Palvelupisteen " + skeduloitavanTapahtumanTyyppi + "," + palvelupisteenID
						+ " palveluaika keskimääräisesti: "
						+ getAvgPalveluAika());

		Trace.out(Trace.Level.INFO,
				"Palvelupisteessä: " + skeduloitavanTapahtumanTyyppi + "," + palvelupisteenID
						+ " kokonaisoleskeluaika keskiarvo on: "
						+ getAvgOleskeluAika());
		Trace.out(Trace.Level.INFO,
				"Palvelupisteeseen: " + skeduloitavanTapahtumanTyyppi + "," + palvelupisteenID
						+ " jonotettiin keskimäärin: "
						+ getAvgJonotusAika());
		Trace.out(Trace.Level.INFO,
				"Palvelupisteen: " + skeduloitavanTapahtumanTyyppi + "," + palvelupisteenID + " palveluprosentti: "
						+ getPalveluprosentti() + " %");

		sS.setKokonaisPalveluAikaPalvelupisteessa(palveluAikaSuurre);
		sS.setKokonaisJonoAikaPalvelupisteessa(jonoAikaSuurre);
		sS.setAsiakkaittenKokonaisAikaPalvelupisteessa(asiakkaittenKokonaisAikaSuurre);
		sS.setKeskiarvoJonotusAika(getAvgPalveluAika());
		sS.setKeskimaarainenOleskeluAika(getAvgOleskeluAika());
		sS.setKeskimaaranenPalveluAika(getAvgJonotusAika());
		sS.setPalveluprosentti(getPalveluprosentti());
	}
}
