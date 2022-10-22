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


		this.ppInfoStr = this.getClass().getSimpleName() + ": " + this.ppTyyppi + "," + this.ppId;
	}

	
	/** 
	 * Lisataan asiakas palvelupisteen jonoon
	 * @param as
	 */
	public void addJonoon(Asiakas as) {
		as.setAsSaapumisaikaPP(Kello.getInstance().getAika());
		this.asLisattyJonoon++; // Asiakkaita pp jonossa kokonaisuudessa
		this.jono.add(as);
	}

	
	/** 
	 * Poistetaan palvelussa ollut asiakas
	 * ja lisätään asiakkaan poistumisaika palvelupisteessa.
	 * @return Palveltu asiakas
	 * @author
	 */
	public Asiakas otaJonosta() { 
		Asiakas as = this.jono.poll();

		as.setAsPoistumisaikaPP(Kello.getInstance().getAika());
		this.varattu = false;

		return as;
	}

	/**
	 * Palvelee asiakkaan palvelupisteen jonosta,
	 * mikali asiakas ei ole kyllastynyt jonottamaan
	 */
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

	
	/** 
	 * Tarkistetaan onko asiakas kyllastynyt jonottamaan ja poistuu jarjelmesta ilman palvelua
	 * @param as palveltava asiakas
	 * @param jAika jonotettu ai
	 * @return boolean true jos kyllastyi
	 */
	protected boolean kyllastyiJonoon(Asiakas as, double jAika) {
		if (jAika > maxJononPituus) {
			Trace.out(Trace.Level.INFO, "Asiakas kyllästyi jonottamaan: " + as.getId());
			jAika = as.getAsSaapumisaikaPP() + maxJononPituus;
			this.jonoAika += maxJononPituus;
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

	
	/** 
	 * @return boolean
	 */
	public boolean onVarattu() {
		return this.varattu;
	}

	
	/** 
	 * @return boolean
	 */
	public boolean onJonossa() {
		return this.jono.size() != 0;
	}

	
	/** 
	 * @return int
	 */
	public int getJonoKoko() {
		return this.jono.size();
	}

	
	/** 
	 * Onko pp paikalla
	 * @param aika
	 * @return boolean
	 */
	public boolean getOnPaikalla() { 
		double aika = Kello.getInstance().getAika();
		if (this.ppSaapumisAika < aika && this.ppPoistumisAika > aika) {
			return true;
		}

		return false;
	}

	
	/** 
	 * @return Tyyppi
	 */
	public Tyyppi getPPTyyppi() {
		return this.ppTyyppi;
	}

	
	/** 
	 * @return int
	 */
	public int getPPId() {
		return this.ppId;
	}

	
	/** 
	 * @return double
	 */
	public double getPalveluAika() {
		return this.palveluAika;
	}

	
	/** 
	 * @return double
	 */
	public double getJonoAika() {
		return this.jonoAika;
	}

	
	/** 
	 * @return double
	 */
	public double getPpSaapumisAika() {
		return this.ppSaapumisAika;
	}

	
	/** 
	 * @return double
	 */
	public double getPpPoistumisAika() {
		return this.ppPoistumisAika;
	}

	
	/** 
	 * @return double
	 */
	public double getMaxJononPituus() {
		return this.maxJononPituus;
	}

	
	/** 
	 * @return int
	 */
	public int getAsPoistunutJonosta() {
		return this.asPoistunutJonosta;
	}

	
	/** 
	 * @return int
	 */
	public int getAsLisattyJonoon() {
		return this.asLisattyJonoon;
	}

	
	/** 
	 * @return int
	 */
	public int getAsPalveltuJonosta() {
		return this.asPalveltuJonosta;
	}

	
	/** 
	 * @return double
	 */
	public double getAvgPalveluAika() {
		if (palveluAika == 0) {
			return 0;
		}
		return this.palveluAika / (double) this.asPalveltuJonosta;
	}

	
	/** 
	 * @return double
	 */
	public double getPProsentti() {
		if (this.asLisattyJonoon == 0) {
			return 100;
		}
		return ((double) this.asPalveltuJonosta / (double) this.asLisattyJonoon) * 100;
	}

	


	
	/** 
	 * @return double
	 */
	public double getAvgJonotusAika() {
		return this.jonoAika / (double) this.asLisattyJonoon;
	}

	public void raportti() {
		Trace.out(Trace.Level.INFO, "\n" + ppInfoStr + " saapui asiakkaita: " + getAsLisattyJonoon());
		Trace.out(Trace.Level.INFO, ppInfoStr + " palveluaika keskimääräisesti: " + getAvgPalveluAika());
		Trace.out(Trace.Level.INFO, ppInfoStr + " jonotettiin keskimäärin: " + getAvgJonotusAika());
		Trace.out(Trace.Level.INFO, ppInfoStr + " palveluprosentti: " + getPProsentti() + " %");

		Trace.out(Trace.Level.INFO, ppInfoStr + " palveli: " + getAsPalveltuJonosta());
		Trace.out(Trace.Level.INFO, ppInfoStr + " kyllästyi jonottamaan: " + getAsPoistunutJonosta());
	}

	
	/** 
	 * @param p
	 * @return int
	 */
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
