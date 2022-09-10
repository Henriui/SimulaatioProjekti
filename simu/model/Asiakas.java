package simu.model;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Trace;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	private AsiakasTyyppi asType;
	private ContinuousGenerator generaattori;

	public Asiakas(ContinuousGenerator g) {
		generaattori = g;
		id = i++;
		saapumisaika = Kello.getInstance().getAika();
		asType = arvoAsiakasTyyppi(); // Generaattori valitse minkälainen asiakastyyppi asiakkaasta tulee
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika);
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	public int getId() {
		return id;
	}

	/*
	 * Mikäli asiakas valitsi väärän linjan,
	 * voidaan asettaa asiakkaalle oikea asiakastyyppi
	 * 
	 * @author Rasmus Hyyppä
	 */
	public void setAsiakasTyyppi(AsiakasTyyppi asType) {
		this.asType = asType;
	}

	/*
	 * Asiakastyyppi alustetaan valitsemalla tyyppi
	 * enum listasta. Valintoja on kahdeksan joten
	 * jakauma arpoo näitten kahdeksan valinnan väliltä.
	 * Mikäli asiakas on valinnut väärän linjan ja hänet yhdistetään
	 * oikeaan linjaan, arvotaan asiakkaalle uusi eroava tyyppi.
	 * 
	 * @author Rasmus Hyyppä
	 */
	public AsiakasTyyppi arvoAsiakasTyyppi() {
		int arvottuAsType = (int) generaattori.sample(); // generoidaan asiakastyyppi

		// Mikäli generoitu asiakastyyppi on sama kuin jo asetettu arvo
		if (asType == AsiakasTyyppi.values()[arvottuAsType]) {
			while (asType == AsiakasTyyppi.values()[arvottuAsType]) {
				arvottuAsType = (int) generaattori.sample(); // Loopataan uusi tyyppi asiakkaalle
			}
			System.out.println("Asiakkaan uusi tyyppi on: " + AsiakasTyyppi.values()[arvottuAsType]);
		} else {
			System.out.println("Asiakkaan alustettu tyyppi on: " + AsiakasTyyppi.values()[arvottuAsType]);
		}

		return AsiakasTyyppi.values()[arvottuAsType];
	}

	public void raportti() {
		Trace.out(Trace.Level.INFO, "\nAsiakas " + id + ", " + " tyyppi: " + asType + " on valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + saapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + poistumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (poistumisaika - saapumisaika));
		sum += (poistumisaika - saapumisaika);
		double keskiarvo = sum / id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
	}

}
