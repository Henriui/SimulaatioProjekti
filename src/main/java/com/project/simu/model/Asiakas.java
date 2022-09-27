package com.project.simu.model;

import com.project.eduni.distributions.ContinuousGenerator;
import com.project.eduni.distributions.DiscreteGenerator;
import com.project.eduni.distributions.Uniform;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Trace;
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)

public class Asiakas {

	// Staattiset muuttujat.

	private static int i = 1;
	private static long sum = 0;

	// Luokkamuuttujat.

	private int id;
	private double saapumisaikaSimulaatiossa;
	private double poistumisaikaSimulaatiossa;
	private double saapumisaikaPp;
	private double poistumisaikaPp;
	private boolean reRouted;
	private boolean jonotukseenKyllastynyt = false;
	private boolean normaaliJakauma;

	private AsiakasTyyppi asType;
	private DiscreteGenerator asiakasJakauma;
	private ContinuousGenerator tyyppiJakauma;
	private SimulaationSuureet sS;
	private UserParametrit uP;

	// Asiakas

	public Asiakas(DiscreteGenerator asiakasJakauma, boolean reRouted) {
		sS = SimulaationSuureet.getInstance();
		uP = UserParametrit.getInstance();
		normaaliJakauma = uP.isNormaaliJakauma();
		this.reRouted = reRouted; // Soittiko asiakas väärää linjaan = True
		this.asiakasJakauma = asiakasJakauma;
		this.id = i++;
		this.saapumisaikaSimulaatiossa = Kello.getInstance().getAika();
		this.asType = alustaAsiakasTyyppi(); // Generaattori valitse minkälainen asiakastyyppi asiakkaasta tulee
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaikaSimulaatiossa);
	}

	/**
	 * Asiakastyyppi alustetaan jakamalla heidät kahteen eri ryhmään,
	 * Tämä on säädettävä arvo jonka parametrinä annetaan.
	 * Tämän jälkeen asetetaan tyyppiJakaumalle asiakkaan lopullinen
	 * määränpää simulaatiossa.
	 * 
	 * @return AsiakasTyyppi.PRI tai AsiakasTyyppi.CO
	 * 
	 * @author Rasmus Hyyppä
	 */

	public AsiakasTyyppi alustaAsiakasTyyppi() {
		int arvottuAsType = (int) asiakasJakauma.sample();
		if (arvottuAsType < 50) {
			asType = AsiakasTyyppi.PRI;
			tyyppiJakauma = new Uniform(0, 4);
		} else {
			asType = AsiakasTyyppi.CO;
			tyyppiJakauma = new Uniform(4, 8);
		}

		if (!normaaliJakauma) {
			tyyppiJakauma = new Uniform(1, 100);
		}
		Trace.out(Trace.Level.INFO, "\n\n Alustettu AsTypeNum(0-100): " + arvottuAsType + ", Id: " + id);
		return asType;
	}

	// getPoistumisaika

	public double getPoistumisaika() {
		return poistumisaikaSimulaatiossa;
	}

	// setPoistumisaika

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaikaSimulaatiossa = poistumisaika;
	}

	// getSaapumisaika

	public double getSaapumisaika() {
		return saapumisaikaSimulaatiossa;
	}

	// setSaapumisaika

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaikaSimulaatiossa = saapumisaika;
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

	/**
	 * @return boolean return the normaaliJakauma
	 */
	public boolean isNormaaliJakauma() {
		return normaaliJakauma;
	}

	/**
	 * @return Integer returned from the asType that defines private or corporate
	 *         customer
	 */
	public int getAsType() {
		return asType.getAsiakasTypeNumero();
	}

	/**
	 * @return double return the saapumisaikaPp
	 */
	public double getSaapumisaikaPp() {
		return saapumisaikaPp;
	}

	/**
	 * @param saapumisaikaPp the saapumisaikaPp to set
	 */
	public void setSaapumisaikaPp(double saapumisaikaPp) {
		this.saapumisaikaPp = saapumisaikaPp;
	}

	/**
	 * @return double return the poistumisaikaPp
	 */
	public double getPoistumisaikaPp() {
		return poistumisaikaPp;
	}

	/**
	 * @param poistumisaikaPp the poistumisaikaPp to set
	 */
	public void setPoistumisaikaPp(double poistumisaikaPp) {
		this.poistumisaikaPp = poistumisaikaPp;
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
	 * 
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
		sS.setAsiakkaitaReRoutattuJonostaKpl();
		tyyppiJakauma = new Uniform(0, 8);
		int arvottuAsType = (int) tyyppiJakauma.sample();
		// Mikäli generoitu asiakastyyppi on sama kuin jo asetettu arvo
		while (asType == AsiakasTyyppi.values()[arvottuAsType]) {
			// Loopataan uusi tyyppi asiakkaalle joka ei ole sama kuin aikasemmin
			arvottuAsType = (int) tyyppiJakauma.sample();
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
	public int setAsiakasTyyppi() {
		int arvottuAsType = (int) tyyppiJakauma.sample(); // generoidaan asiakastyyppi
		if (!normaaliJakauma) {
			arvottuAsType = uP.getProbability(asType, (int) tyyppiJakauma.sample());
		}
		asType = AsiakasTyyppi.values()[arvottuAsType];
		Trace.out(Trace.Level.INFO, "Asiakkaan tyyppi on: " + asType + ", id: " + id);
		return asType.getAsiakasTypeNumero();
	}

	// raportti
	public void raportti() {

		Trace.out(Trace.Level.INFO, "\nAsiakas " + id + ",  tyyppi: " + asType + " on valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + saapumisaikaSimulaatiossa);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + poistumisaikaSimulaatiossa);
		Trace.out(Trace.Level.INFO,
				"Asiakas " + id + " viipyi: " + (poistumisaikaSimulaatiossa - saapumisaikaSimulaatiossa));
		sum += (poistumisaikaSimulaatiossa - saapumisaikaSimulaatiossa);
		double keskiarvo = sum / id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
		sS.setAsiakkaanKeskiArvoViipyminenSimulaatiossa(keskiarvo);
	}
}