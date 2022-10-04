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
	private Tyyppi ppTyyppi;
	private static int ppUId = 0;

	private SimulaationSuureet sS;
	private UserParametrit uP;
	private int ppId;
	private int asLisattyJonoon;
	private int asPalveltuJonosta;
	private int asPoistunutJonosta;
	private int asReRoutedJonosta;
	private double palveluAika;
	private double jonoAika;
	private double asTotalAika;

	// JonoStartegia strategia;
	// optio: asiakkaiden järjestys

	private boolean varattu = false;

	// PalveluPiste

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, Tyyppi tyyppi) {
		sS = SimulaationSuureet.getInstance();
		uP = UserParametrit.getInstance();
		ppId = ppUId;
		ppUId++;
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.ppTyyppi = tyyppi;

	}

	// lisaaJonoon

	public void addJonoon(Asiakas as) { // Jonon 1. asiakas aina palvelussa

		as.setAsSaapumisaikaPP(Kello.getInstance().getAika());
		// Lisätään palvelupisteen jonossa olleet asiakkaat suurre
		asLisattyJonoon++;
		sS.asLisattyJonoon();
		jono.add(as);
	}

	// otaJonosta

	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut

		Asiakas as = jono.poll();
		varattu = false;
		if (as.getAsPoistumisaikaPP() != (as.getAsSaapumisaikaPP() + uP.getMaxJononPituus())) {
			as.setAsPoistumisaikaPP(Kello.getInstance().getAika());
		}
		// Lisätään asiakkaan palvelupisteen oleskeluaika suurre
		asTotalAika += as.getAsPoistumisaikaPP() - as.getAsSaapumisaikaPP();
		return as;
	}

	// aloitaPalvelu

	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
		varattu = true;
		Asiakas as = jono.peek();
		// Lisätään jonotusaika & palveluaika suureet muuttujiin
		double jAika = Kello.getInstance().getAika() - as.getAsSaapumisaikaPP();
		double pAika = generator.sample();

		// Mikäli jonotusaika ylitti niin asiakas poistui ennen palvelua.
		if (jAika > uP.getMaxJononPituus()) {
			Trace.out(Trace.Level.INFO, "Asiakas kyllästyi jonottamaan: " + as.getId());
			asPoistunutJonosta++;
			as.setJonotukseenKyllastynyt();
			jAika = as.getAsSaapumisaikaPP() + uP.getMaxJononPituus();
			jonoAika += uP.getMaxJononPituus();
			as.setAsPoistumisaikaPP(jAika);
			tapahtumalista.lisaa(new Tapahtuma(ppTyyppi, jAika));
			return;
		}

		if (as.getReRouted() && as.getAsType() < 9) {
			Trace.out(Trace.Level.INFO, "Asiakas siirretään oikeaan jonoon: " + as.getId());
			pAika = 30; // 30 sekunttia reroute keskustelu
			asReRoutedJonosta++;
		} else {
			asPalveltuJonosta++;
		}

		palveluAika += pAika;
		jonoAika += jAika;
		tapahtumalista.lisaa(new Tapahtuma(ppTyyppi, Kello.getInstance().getAika() + pAika));
	}

	public Tyyppi getPPTyyppi() {
		return ppTyyppi;
	}

	public int getPPNum() {
		return ppId;
	}

	// onVarattu

	public boolean onVarattu() {
		return varattu;
	}

	// onJonossa

	public boolean onJonossa() {
		return jono.size() != 0;
	}

	public static void resetPPUID() {
		Palvelupiste.ppUId = 0;
	}

	/**
	 * @return int return the asiakkaitaLisattyJonoon
	 */
	public int getAsLisattyJonoon() {
		if (asLisattyJonoon == 0) {
			return 0;
		}
		return asLisattyJonoon;
	}

	/**
	 * @return int return the asiakkaitaPalveltuJonosta
	 */
	public int getAsPalveltuJonosta() {
		if (asPalveltuJonosta == 0) {
			return 0;
		}
		return asPalveltuJonosta;
	}

	public int getAsPoistunutJonosta() {
		if (asPoistunutJonosta == 0) {
			return 0;
		}
		return asPoistunutJonosta;
	}

	/**
	 * @return double return the palveluAikaSuurre
	 */
	public double getPalveluAikaSuurre() {
		return palveluAika;
	}

	/**
	 * @return double return the jonoAikaSuurre
	 */
	public double getJonoAikaSuurre() {
		return jonoAika;
	}

	public int getJonossaOlevatAs() {
		return jono.size();
	}

	public int getAsReRoutedJonosta() {
		return asReRoutedJonosta;
	}

	/**
	 * @return double return the asiakkaidenKokonaisAikaSuurre
	 */
	public double getAsTotalAika() {
		return asTotalAika;
	}

	public double getAvgPalveluAika() {
		if (asLisattyJonoon == 0) {
			return 0;
		}
		return palveluAika / asPalveltuJonosta;
	}

	public double getAvgOleskeluAika() {
		if (asLisattyJonoon == 0) {
			return 0;
		}
		return asTotalAika / asPalveltuJonosta;
	}

	public double getAvgJonotusAika() {
		if (asLisattyJonoon == 0) {
			return 0;
		}
		return jonoAika / asLisattyJonoon;
	}

	// Palveluprosentti
	public double getPProsentti() {
		if (asLisattyJonoon == 0) {
			return 100;
		}
		return (1 / ((double) (asLisattyJonoon)
				/ (double) (asPalveltuJonosta + asReRoutedJonosta))) * 100;
	}

	public void raportti() {

		Trace.out(Trace.Level.INFO, "\nPalvelupisteeseen " + ppTyyppi + "," + ppId
				+ " saapui: " + asLisattyJonoon);
		Trace.out(Trace.Level.INFO,
				"Palvelupisteessä  " + ppTyyppi + "," + ppId + " palveltiin: "
						+ asPalveltuJonosta);
		Trace.out(Trace.Level.INFO,
				"Palvelupisteessä  " + ppTyyppi + "," + ppId
						+ " kyllästyi jonottamaan: "
						+ asPoistunutJonosta);
		Trace.out(Trace.Level.INFO,
				"Palvelupisteessä  " + ppTyyppi + "," + ppId
						+ " siirretty oikeaan paikkaan: "
						+ asReRoutedJonosta);
		Trace.out(Trace.Level.INFO,
				"Palvelupisteen " + ppTyyppi + "," + ppId
						+ " palveluaika keskimääräisesti: "
						+ getAvgPalveluAika());

		Trace.out(Trace.Level.INFO,
				"Palvelupisteessä: " + ppTyyppi + "," + ppId
						+ " kokonaisoleskeluaika on: "
						+ asTotalAika);
		Trace.out(Trace.Level.INFO,
				"Palvelupisteeseen: " + ppTyyppi + "," + ppId
						+ " jonotettiin keskimäärin: "
						+ getAvgJonotusAika());
		Trace.out(Trace.Level.INFO,
				"Palvelupisteen: " + ppTyyppi + "," + ppId + " palveluprosentti: "
						+ getPProsentti() + " %");

		sS.addTotalPAPP(palveluAika);
		sS.addTotalJonoAikaPP(jonoAika);
		sS.addAsTotalAikaPP(asTotalAika);
		sS.addAvgJonotusAika(getAvgPalveluAika());
		sS.addAvgPPOleskeluAika(getAvgOleskeluAika());
		sS.addAvgTotalPA(getAvgJonotusAika());
		sS.addPalveluprosentti(getPProsentti());
	}
}
