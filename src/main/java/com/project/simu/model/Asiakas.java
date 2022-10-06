package com.project.simu.model;

import com.project.eduni.distributions.ContinuousGenerator;
import com.project.eduni.distributions.Uniform;
import com.project.simu.constants.AsiakasTyyppi;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Trace;
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)

public class Asiakas {

	// Staattiset muuttujat.

	private static int i = 1;
	private static long sum = 0;

	// Luokkamuuttujat.

	private int id;
	private double asSaapumisaika; // Simulaatioon tuleminen
	private double asPoistumisaika; // Simulaatiosta poistuminen
	private double asSaapumisaikaPP; // Palvelupisteeseen saapuminen
	private double asPoistumisaikaPP; // Palvelupisteestä poistuminen
	private boolean reRouted; // Valitsiko väärin asiakas valikosta?
	private boolean jonotukseenKyllastynyt = false;

	private AsiakasTyyppi asType;
	private ContinuousGenerator asTypeJakauma;
	private SimulaationSuureet sS;
	private UserParametrit uP;

	// Asiakas
	public Asiakas() {
		sS = SimulaationSuureet.getInstance();
		uP = UserParametrit.getInstance();
		this.reRouted = uP.onkoVaaraValinta(); // Soittiko asiakas väärää linjaan = True
		this.id = i++;
		this.asSaapumisaika = Kello.getInstance().getAika();
		this.asTypeJakauma = new Uniform(1, 100);
		this.asType = alustaAsType(); // Generaattori valitse minkälainen asiakastyyppi asiakkaasta tulee
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + asSaapumisaika);
	}

	/**
	 * Asiakastyyppi alustetaan jakamalla heidät kahteen eri ryhmään
	 * 
	 * @return AsiakasTyyppi.PRI tai AsiakasTyyppi.CO
	 * @author Rasmus Hyyppä
	 */
	public AsiakasTyyppi alustaAsType() {
		if ((int) asTypeJakauma.sample() > (int) uP.getAsTyyppiJakauma()) {
			return AsiakasTyyppi.PRI;
		} else {
			return AsiakasTyyppi.CO;
		}

	}

	/**
	 * Kun käyttäjä haluaa itse valita asiakastyyppien jakautumisen,
	 * niin haetaan tällä methodilla jakauman samplea käyttäen oikea
	 * palvelupiste
	 * 
	 * @param gSample sample generaattorin jakaumasta
	 * @return Asiakkaan tyyppinumeron, joka määrittää mitä palvelua hän haluaa
	 * @author Rasmus Hyyppä
	 */
	public int getAsiakkaanPP(double gSample) {
		int asTypeNum = 4; // astypeNum on 4 mikäli yrityspuoli kyseessä
		int j = 0;
		if (asType == AsiakasTyyppi.CO) {
			while (gSample >= uP.getCoAsTyyppiArr(j)) {
				j++;
			}
			asTypeNum += j;
		} else {
			while (gSample >= uP.getPriAsTyyppiArr(j)) {
				j++;
			}
			asTypeNum = j;
		}
		return asTypeNum;
	}

	// getId
	public int getId() {
		return id;
	}

	public static void resetAsiakasUID() {
		Asiakas.i = 1;
	}

	public static void resetAsiakasSum() {
		Asiakas.sum = 0;
	}

	public static long getAsiakasSum() {
		return Asiakas.sum;
	}

	public static int getAsiakasUID() {
		return Asiakas.i;
	}

	// getPoistumisaika
	public double getAsPoistumisaika() {
		return asPoistumisaika;
	}

	// setPoistumisaika
	public void setAsPoistumisaika(double poistumisaika) {
		this.asPoistumisaika = poistumisaika;
	}

	// getSaapumisaika
	public double getAsSaapumisaika() {
		return asSaapumisaika;
	}

	// setSaapumisaika
	public void setAsSaapumisaika(double saapumisaika) {
		this.asSaapumisaika = saapumisaika;
	}

	/**
	 * @return Integer returned from the asType that defines private or corporate
	 *         customer
	 */
	public int getAsType() {
		return asType.getAsiakasTypeNumero();
	}

	/**
	 * @return double return the saapumisaikaPP
	 */
	public double getAsSaapumisaikaPP() {
		return asSaapumisaikaPP;
	}

	/**
	 * @param saapumisaikaPP the saapumisaikaPP to set
	 */
	public void setAsSaapumisaikaPP(double saapumisaikaPP) {
		this.asSaapumisaikaPP = saapumisaikaPP;
	}

	/**
	 * @return double return the poistumisaikaPP
	 */
	public double getAsPoistumisaikaPP() {
		return asPoistumisaikaPP;
	}

	/**
	 * @param poistumisaikaPP the poistumisaikaPP to set
	 */
	public void setAsPoistumisaikaPP(double poistumisaikaPP) {
		this.asPoistumisaikaPP = poistumisaikaPP;
	}

	/**
	 * @return boolean return the jonotukseenKyllastynyt
	 */
	public boolean isJonotukseenKyllastynyt() {
		return jonotukseenKyllastynyt;
	}

	/**
	 * @param jonotukseenKyllastynyt the jonotukseenKyllastynyt to set
	 */
	public void setJonotukseenKyllastynyt() {
		this.jonotukseenKyllastynyt = true;
	}

	/**
	 * @return false if we are not rerouted customer, true if we are.
	 * @author Rasmus Hyyppä
	 */
	public boolean getReRouted() {
		return reRouted;
	}

	/**
	 * Set customer to be rerouted (customer error)
	 * Checks if its customer that needs to be rerouted.
	 * Sets customer to new line (which is not the same).
	 * 
	 * @return integer number from AsiakasTyyppi that matches Tyyppi integer's
	 * @author Rasmus Hyyppä
	 */
	public int setReRouted() {
		sS.addAsReRouted();
		asTypeJakauma = new Uniform(0, 8);
		int arvottuAsType = (int) asTypeJakauma.sample();
		// Mikäli generoitu asiakastyyppi on sama kuin jo asetettu arvo
		while (asType == AsiakasTyyppi.values()[arvottuAsType]) {
			// Loopataan uusi tyyppi asiakkaalle joka ei ole sama kuin aikasemmin
			arvottuAsType = (int) asTypeJakauma.sample();
		}
		Trace.out(Trace.Level.INFO,
				"Asiakkaan uusi tyyppi on: " + AsiakasTyyppi.values()[arvottuAsType] + ", id " + id);
		asType = AsiakasTyyppi.values()[arvottuAsType];
		reRouted = false;
		return asType.getAsiakasTypeNumero();
	}

	/**
	 * Asiakastyyppi asetetaan annetulla tyyppiJakaumalla
	 * 
	 * @return integer asiakastyypistä, tämä vastaa tapahtuma tyypin integeriä
	 * @author Rasmus Hyyppä
	 */
	public int setAsType() {
		int arvottuAsType = getAsiakkaanPP(asTypeJakauma.sample());
		asType = AsiakasTyyppi.values()[arvottuAsType];
		Trace.out(Trace.Level.INFO, "Asiakkaan tyyppi on: " + asType + ", id: " + id);
		return asType.getAsiakasTypeNumero();
	}

	// raportti
	public void raportti() {

		Trace.out(Trace.Level.INFO, "\nAsiakas " + id + ",  tyyppi: " + asType + " on valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + asSaapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + asPoistumisaika);
		Trace.out(Trace.Level.INFO,
				"Asiakas " + id + " viipyi: " + (asPoistumisaika - asSaapumisaika));
		sum += (asPoistumisaika - asSaapumisaika);
		double keskiarvo = sum / id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
		sS.setAvgAsAika((double) keskiarvo);

	}
}