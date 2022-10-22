package com.project.simu.model;
import com.project.eduni.distributions.ContinuousGenerator;
import com.project.eduni.distributions.Uniform;
import com.project.simu.constants.AsiakasTyyppi;
import com.project.simu.framework.Kello;
import com.project.simu.framework.Trace;
/**
 * Simulaattorissa oleva asiakas toimintoineen ja tyyppeineen
 * @author Rasmus Hyyppä
 */
public class Asiakas {

	private static int i = 1;
	private static long sum = 0;

	private int id;
	private double asSaapumisaika; // Simulaatioon tuleminen
	private double asPoistumisaika; // Simulaatiosta poistuminen
	private double asSaapumisaikaPP; // Palvelupisteeseen saapuminen
	private double asPoistumisaikaPP; // Palvelupisteestä poistuminen
	private boolean reRouted; // Valitsiko väärin asiakas valikosta?
	private boolean jonotukseenKyllastynyt = false;

	private AsiakasTyyppi asType;
	private ContinuousGenerator asTypeJakauma;
	private double[] asTyyppiArr;

	public Asiakas(double reRouteChance, double asTyyppiJakauma, double[] asTyyppiArr) {

		this.id = i++;
		this.asSaapumisaika = Kello.getInstance().getAika();
		this.asTypeJakauma = new Uniform(1, 100);
		this.reRouted = onkoVaaraValinta(reRouteChance);
		this.asType = alustaAsType(asTyyppiJakauma); // Generaattori valitse minkälainen asiakastyyppi asiakkaasta
		this.asTyyppiArr = asTyyppiArr;
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + asSaapumisaika);
	}

	/**
	 * Arpoo uniform jakaumalla tuleeko asiakas menemään väärään jonoon
	 * reroutattavaksi.
	 * 
	 * @return True mikäli on, false mikäli ei
	 * @author Rasmus Hyyppä
	 */
	public boolean onkoVaaraValinta(double reRouteChance) {
		if (asTypeJakauma.sample() < reRouteChance) {
			return true;
		}
		return false;
	}

	/**
	 * Asiakastyyppi alustetaan jakamalla heidät kahteen eri ryhmään
	 * 
	 * @return AsiakasTyyppi.PRI tai AsiakasTyyppi.CO
	 * @author Rasmus Hyyppä
	 */
	public AsiakasTyyppi alustaAsType(double asTyyppiJakauma) {
		if ((int) asTypeJakauma.sample() > (int) asTyyppiJakauma) {
			return AsiakasTyyppi.PRI;
		} else {
			return AsiakasTyyppi.CO;
		}

	}

	/**
	 * @param gSample sample generaattorin jakaumasta
	 * @return Asiakkaan tyyppinumeron, joka määrittää mitä palvelua hän haluaa
	 * @author Rasmus Hyyppä
	 */
	public int getAsiakkaanPP(double gSample) {
		int asTypeNum = 4; // astypeNum on 4 mikäli yrityspuoli kyseessä
		int j = 0;

		// Yrityspuoli
		if (asType == AsiakasTyyppi.CO) {
			while (gSample >= asTyyppiArr[j + asTypeNum]) {
				j++;
			}
			asTypeNum += j;
		}
		// Private puoli
		else {
			while (gSample >= asTyyppiArr[j]) {
				j++;
			}
			asTypeNum = j;
		}
		return asTypeNum;
	}

	/**
	 * @return integer number from AsiakasTyyppi that matches Tyyppi integer's
	 * @author Rasmus Hyyppä
	 */
	public int setReRouted() {
		reRouted = false;
		asTypeJakauma = new Uniform(0, 8);
		int arvottuAsType = (int) asTypeJakauma.sample();
		// Mikäli generoitu asiakastyyppi on sama kuin jo asetettu arvo
		while (asType == AsiakasTyyppi.values()[arvottuAsType]) {
			// Loopataan uusi tyyppi asiakkaalle joka ei ole sama kuin aikasemmin
			arvottuAsType = (int) asTypeJakauma.sample();
		}
		asType = AsiakasTyyppi.values()[arvottuAsType];
		Trace.out(Trace.Level.INFO, "Asiakkaan uusi tyyppi: " + asType + ", id " + id);
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

	/**
	 * @return false if we are not rerouted customer, true if we are.
	 * @author Rasmus Hyyppä
	 */
	public boolean getReRouted() {
		return reRouted;
	}

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

	public double getAsPoistumisaika() {
		return asPoistumisaika;
	}

	public void setAsPoistumisaika(double poistumisaika) {
		this.asPoistumisaika = poistumisaika;
	}

	public double getAsSaapumisaika() {
		return asSaapumisaika;
	}

	public void setAsSaapumisaika(double saapumisaika) {
		this.asSaapumisaika = saapumisaika;
	}

	public int getAsType() {
		return asType.getAsiakasTypeNumero();
	}

	public double getAsSaapumisaikaPP() {
		return asSaapumisaikaPP;
	}

	public void setAsSaapumisaikaPP(double saapumisaikaPP) {
		this.asSaapumisaikaPP = saapumisaikaPP;
	}

	public double getAsPoistumisaikaPP() {
		return asPoistumisaikaPP;
	}

	public void setAsPoistumisaikaPP(double poistumisaikaPP) {
		this.asPoistumisaikaPP = poistumisaikaPP;
	}

	/**
	 * @return boolean true jos on kyllastynyt
	 * @author Rasmus Hyyppä
	 */
	public boolean isJonotukseenKyllastynyt() {
		return jonotukseenKyllastynyt;
	}

	public void setJonotukseenKyllastynyt() {
		this.jonotukseenKyllastynyt = true;
	}

	public void raportti() {
		Trace.out(Trace.Level.INFO, "\nAsiakas " + id + ",  tyyppi: " + asType + " on valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + asSaapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + asPoistumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (asPoistumisaika - asSaapumisaika));
		sum += (asPoistumisaika - asSaapumisaika);
		double keskiarvo = sum / id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
	}
}