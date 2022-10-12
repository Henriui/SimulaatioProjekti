package com.project.simu.model;

import java.util.LinkedList;

import com.project.eduni.distributions.ContinuousGenerator;
import com.project.simu.constants.Tyyppi;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Tapahtuma;
import com.project.simu.framework.Tapahtumalista;
import com.project.simu.framework.Trace;

// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public abstract class Palvelupiste implements Comparable<Palvelupiste> {

	protected static int ppUId = 0;

	protected LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	protected ContinuousGenerator generator;
	protected Tapahtumalista tapahtumalista;
	protected Tyyppi ppTyyppi;
	protected String ppInfoStr; // trace.info string

	protected int ppId;
	protected int asLisattyJonoon;
	protected int asPalveltuJonosta;
	protected int asPoistunutJonosta;

	protected double palveluAika;
	protected double jonoAika;
	protected double asViipyminenPP;

	protected double ppSaapumisAika;
	protected double ppPoistumisAika;
	protected double maxJononPituus;

	protected boolean varattu = false;

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, Tyyppi tyyppi,
			double maxJonoPituus) {
		this.ppId = ppUId;
		Palvelupiste.ppUId++;

		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.ppTyyppi = tyyppi;
		this.maxJononPituus = maxJonoPituus;

		this.asLisattyJonoon = 0;
		this.asPalveltuJonosta = 0;
		this.asPoistunutJonosta = 0;
		this.palveluAika = 0;
		this.jonoAika = 0;
		this.asViipyminenPP = 0;

		this.ppInfoStr = this.getClass().getSimpleName() + ": " + this.ppTyyppi + "," + this.ppId;
	}

	public void addJonoon(Asiakas as) {
		as.setAsSaapumisaikaPP(Kello.getInstance().getAika());
		this.asLisattyJonoon++; // Asiakkaita pp jonossa kokonaisuudessa
		this.jono.add(as);
	}

	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		Asiakas as = this.jono.poll();
		// Lisätään asiakkaan poistumisaika pp:ssä
		as.setAsPoistumisaikaPP(Kello.getInstance().getAika());
		this.varattu = false;
		this.asViipyminenPP += as.getAsPoistumisaikaPP() - as.getAsSaapumisaikaPP();
		return as;
	}

	public void aloitaPalvelu() {
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
		Asiakas as = this.jono.peek();
		double jAika = Kello.getInstance().getAika() - as.getAsSaapumisaikaPP();
		double pAika = generator.sample();
		this.varattu = true;
		if (kyllastyiJonoon(as, jAika)) {
			return;
		}

		this.asPalveltuJonosta++;
		this.palveluAika += pAika;
		this.jonoAika += jAika;

		this.tapahtumalista.lisaa(new Tapahtuma(this.ppTyyppi, Kello.getInstance().getAika() + pAika));
	}

	protected boolean kyllastyiJonoon(Asiakas as, double jAika) {
		if (jAika > maxJononPituus) {
			Trace.out(Trace.Level.INFO, "Asiakas kyllästyi jonottamaan: " + as.getId());
			this.jonoAika += jAika;
			jAika = as.getAsSaapumisaikaPP() + maxJononPituus;
			as.setJonotukseenKyllastynyt();
			this.asPoistunutJonosta++;
			this.tapahtumalista.lisaa(new Tapahtuma(this.ppTyyppi, jAika));
			return true;
		}
		return false;
	}

	public static void resetPPUID() {
		Palvelupiste.ppUId = 0;
	}

	public boolean onVarattu() {
		return this.varattu;
	}

	public boolean onJonossa() {
		return this.jono.size() != 0;
	}

	public int getJonoKoko() {
		return this.jono.size();
	}

	public boolean getOnPaikalla() { // Onko pp paikalla
		double aika = Kello.getInstance().getAika();
		if (this.ppSaapumisAika < aika && this.ppPoistumisAika > aika) {
			return true;
		}

		return false;
	}

	public Tyyppi getPPTyyppi() {
		return this.ppTyyppi;
	}

	public int getPPId() {
		return this.ppId;
	}

	public double getPalveluAika() {
		return this.palveluAika;
	}

	public double getJonoAika() {
		return this.jonoAika;
	}

	public double getPpSaapumisAika() {
		return this.ppSaapumisAika;
	}

	public double getPpPoistumisAika() {
		return this.ppPoistumisAika;
	}

	public double getMaxJononPituus() {
		return this.maxJononPituus;
	}

	public int getAsPoistunutJonosta() {
		return this.asPoistunutJonosta;
	}

	public int getAsLisattyJonoon() {
		return this.asLisattyJonoon;
	}

	public int getAsPalveltuJonosta() {
		return this.asPalveltuJonosta;
	}

	public double getAvgPalveluAika() {
		if (palveluAika == 0) {
			return 0;
		}
		return this.palveluAika / (double) this.asPalveltuJonosta;
	}

	public double getPProsentti() {
		if (this.asLisattyJonoon == 0) {
			return 100;
		}
		return ((double) this.asPalveltuJonosta / (double) this.asLisattyJonoon) * 100;
	}

	public double getAvgViipyminenPP() {
		return this.asViipyminenPP / (double) this.asLisattyJonoon;
	}

	public double getAvgJonotusAika() {
		return this.jonoAika / (double) this.asLisattyJonoon;
	}

	public void raportti() {
		Trace.out(Trace.Level.INFO, "\n" + ppInfoStr + " saapui asiakkaita: " + getAsLisattyJonoon());
		Trace.out(Trace.Level.INFO, ppInfoStr + " palveluaika keskimääräisesti: " + getAvgPalveluAika());
		Trace.out(Trace.Level.INFO, ppInfoStr + " jonotettiin keskimäärin: " + getAvgJonotusAika());
		Trace.out(Trace.Level.INFO, ppInfoStr + " palveluprosentti: " + getPProsentti() + " %");
		Trace.out(Trace.Level.INFO, ppInfoStr + " asiakkaitten total oleskeluaika: " + this.asViipyminenPP);
		Trace.out(Trace.Level.INFO, ppInfoStr + " palveli: " + getAsPalveltuJonosta());
		Trace.out(Trace.Level.INFO, ppInfoStr + " kyllästyi jonottamaan: " + getAsPoistunutJonosta());
	}

	@Override
	public int compareTo(Palvelupiste p) {
		if (this.asPalveltuJonosta < p.asPalveltuJonosta) { // Less
			return -1;
		} else if (this.asPalveltuJonosta > p.asPalveltuJonosta) { // More
			return 1;
		}
		return 0; // Equal
	}
}
