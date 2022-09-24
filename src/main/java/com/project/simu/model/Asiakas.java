package com.project.simu.model;

<<<<<<< HEAD:simu/model/Asiakas.java
import eduni.distributions.ContinuousGenerator;
import eduni.distributions.DiscreteGenerator;
import eduni.distributions.Uniform;
import simu.framework.Kello;
import simu.framework.Trace;
=======
import com.project.eduni.distributions.ContinuousGenerator;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Trace;

// TODO:
>>>>>>> main:src/main/java/com/project/simu/model/Asiakas.java
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
	private AsiakasTyyppi asType;
	private DiscreteGenerator asiakasJakauma;
	private ContinuousGenerator tyyppiJakauma;
	private SimulaationSuureet sS;

	// Asiakas

	public Asiakas(DiscreteGenerator asiakasJakauma, boolean reRouted) {
		sS = SimulaationSuureet.getInstance();
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
			tyyppiJakauma = new Uniform(0, 4);
			asType = AsiakasTyyppi.PRI;
		} else {
			tyyppiJakauma = new Uniform(4, 8);
			asType = AsiakasTyyppi.CO;
		}
		System.out.println("\n\n Alustettu AsTypeNum(0-100): " + arvottuAsType + ", asTyyppiId: " + id);
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

	// setAsiakastyyppi

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
		if (reRouted) {
			tyyppiJakauma = new Uniform(0, 8);
			int arvottuAsType = (int) tyyppiJakauma.sample();
			// Mikäli generoitu asiakastyyppi on sama kuin jo asetettu arvo
			while (asType == AsiakasTyyppi.values()[arvottuAsType]) {
				// Loopataan uusi tyyppi asiakkaalle joka ei ole sama kuin aikasemmin
				arvottuAsType = (int) tyyppiJakauma.sample();
			}
			System.out.println("Asiakkaan uusi tyyppi on: " + AsiakasTyyppi.values()[arvottuAsType]);
			asType = AsiakasTyyppi.values()[arvottuAsType];
			reRouted = false;
		}
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
		asType = AsiakasTyyppi.values()[arvottuAsType];
		System.out.println("Asiakkaan tyyppi on: " + asType + ", id: " + id);
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