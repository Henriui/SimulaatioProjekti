package simu.model;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import eduni.distributions.Uniform;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;
	private final static int MAKSIMI_PALVELUPISTE_MAARA = 27; // test luku

	// OmaMoottori

	public OmaMoottori() {

		palvelupisteet = new Palvelupiste[MAKSIMI_PALVELUPISTE_MAARA];

		palvelupisteet[0] = new Palvelupiste(new Normal(10, 6), tapahtumalista, Tyyppi.BLENDER_VALIKKO_DEPART);
		palvelupisteet[1] = new Palvelupiste(new Normal(10, 10), tapahtumalista, Tyyppi.PRI_VALIKKO_DEPART);
		palvelupisteet[2] = new Palvelupiste(new Normal(5, 3), tapahtumalista, Tyyppi.CO_VALIKKO_DEPART);

		// Testausta varten jokainen palvelupiste asetettu yksitellen
		palvelupisteet[3] = new Palvelupiste(new Normal(50, 50), tapahtumalista, Tyyppi.PRI_SALES_DEPART);
		palvelupisteet[4] = new Palvelupiste(new Normal(50, 50), tapahtumalista, Tyyppi.PRI_SALES_DEPART);
		palvelupisteet[5] = new Palvelupiste(new Normal(50, 50), tapahtumalista, Tyyppi.PRI_SALES_DEPART);

		palvelupisteet[6] = new Palvelupiste(new Normal(10, 10), tapahtumalista, Tyyppi.PRI_NETWORK_DEPART);
		palvelupisteet[7] = new Palvelupiste(new Normal(10, 10), tapahtumalista, Tyyppi.PRI_NETWORK_DEPART);
		palvelupisteet[8] = new Palvelupiste(new Normal(10, 10), tapahtumalista, Tyyppi.PRI_NETWORK_DEPART);

		palvelupisteet[9] = new Palvelupiste(new Normal(20, 20), tapahtumalista, Tyyppi.PRI_SUBSCRIBER_DEPART);
		palvelupisteet[10] = new Palvelupiste(new Normal(20, 20), tapahtumalista, Tyyppi.PRI_SUBSCRIBER_DEPART);
		palvelupisteet[11] = new Palvelupiste(new Normal(20, 20), tapahtumalista, Tyyppi.PRI_SUBSCRIBER_DEPART);

		palvelupisteet[12] = new Palvelupiste(new Normal(30, 30), tapahtumalista, Tyyppi.PRI_INVOICE_DEPART);
		palvelupisteet[13] = new Palvelupiste(new Normal(30, 30), tapahtumalista, Tyyppi.PRI_INVOICE_DEPART);
		palvelupisteet[14] = new Palvelupiste(new Normal(30, 30), tapahtumalista, Tyyppi.PRI_INVOICE_DEPART);

		palvelupisteet[15] = new Palvelupiste(new Normal(50, 50), tapahtumalista, Tyyppi.CO_SALES_DEPART);
		palvelupisteet[16] = new Palvelupiste(new Normal(50, 50), tapahtumalista, Tyyppi.CO_SALES_DEPART);
		palvelupisteet[17] = new Palvelupiste(new Normal(50, 50), tapahtumalista, Tyyppi.CO_SALES_DEPART);

		palvelupisteet[18] = new Palvelupiste(new Normal(10, 10), tapahtumalista, Tyyppi.CO_NETWORK_DEPART);
		palvelupisteet[19] = new Palvelupiste(new Normal(10, 10), tapahtumalista, Tyyppi.CO_NETWORK_DEPART);
		palvelupisteet[20] = new Palvelupiste(new Normal(10, 10), tapahtumalista, Tyyppi.CO_NETWORK_DEPART);

		palvelupisteet[21] = new Palvelupiste(new Normal(20, 20), tapahtumalista, Tyyppi.CO_SUBSCRIBER_DEPART);
		palvelupisteet[22] = new Palvelupiste(new Normal(20, 20), tapahtumalista, Tyyppi.CO_SUBSCRIBER_DEPART);
		palvelupisteet[23] = new Palvelupiste(new Normal(20, 20), tapahtumalista, Tyyppi.CO_SUBSCRIBER_DEPART);

		palvelupisteet[24] = new Palvelupiste(new Normal(30, 30), tapahtumalista, Tyyppi.CO_INVOICE_DEPART);
		palvelupisteet[25] = new Palvelupiste(new Normal(30, 30), tapahtumalista, Tyyppi.CO_INVOICE_DEPART);
		palvelupisteet[26] = new Palvelupiste(new Normal(30, 30), tapahtumalista, Tyyppi.CO_INVOICE_DEPART);

		System.out.println(palvelupisteet.length);
		System.out.println(palvelupisteet[palvelupisteet.length - 1].getPalveluPisteenNumero());

		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, Tyyppi.ARRIVAL);

	}

	// Alustukset

	@Override
	protected void alustukset() {

		// Ensimmäinen saapuminen järjestelmään.
		saapumisprosessi.generoiSeuraava();
	}

	// suoritaTapahtuma (B-vaiheen tapahtumat)

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {

		Asiakas a;
		Tyyppi tapahtuma = t.getTyyppi();

		// TODO: Käyttäjä määrittelee jakaumaParametrin ohjelmassa. Uniform asetettu testausta varten. (Poista kun tehty).

		ContinuousGenerator jakaumaParametri;

		// Hae tapahtuman arvo.

		int tapahtumaValue = tapahtuma.getTyyppiValue();

		// Saapumistapahtumat

		if (tapahtuma == Tyyppi.ARRIVAL){
			jakaumaParametri = new Uniform(1, 8);
			a = new Asiakas(jakaumaParametri);
			palvelupisteet[Tyyppi.BLENDER_VALIKKO_DEPART.getTyyppiValue()].lisaaJonoon(a);
			saapumisprosessi.generoiSeuraava();
		}

		// Blendervalikko

		else if (tapahtuma == Tyyppi.BLENDER_VALIKKO_DEPART){
			a = palvelupisteet[tapahtumaValue].otaJonosta();

			// Tähän jonoon varmaan siirrettään uudelleen asiakkaat?

			// Mikäli on alle 5 niin pri- jos yli 4 niin co-valikkoon
			
			if (a.getAsType() < 5) {
				palvelupisteet[Tyyppi.PRI_VALIKKO_DEPART.getTyyppiValue()].lisaaJonoon(a);
			} else {
				palvelupisteet[Tyyppi.CO_VALIKKO_DEPART.getTyyppiValue()].lisaaJonoon(a);
			}
		}

		// Lähtötapahtumat

		else if( tapahtuma == Tyyppi.CO_VALIKKO_DEPART || tapahtuma == Tyyppi.PRI_VALIKKO_DEPART ){

			a = palvelupisteet[tapahtumaValue].otaJonosta();
			palvelupisteet[lisaaPalvelupisteeseen(a.getAsType())].lisaaJonoon(a);

		}

		else{
			a = palvelupisteet[haePalvelupiste(tapahtumaValue)]
					.otaJonosta();
			a.setPoistumisaika(Kello.getInstance().getAika());
			a.raportti();
		}
	}

	// lisaaPalvelupisteeseen
	
	public int lisaaPalvelupisteeseen(int ppType) {

		// palvelupisteiden määrä yhtä luokkaa vasten, default 3.
		int ppTypeMaara = 3;

		Palvelupiste[] typeaVastaavatPalvelupisteet = new Palvelupiste[ppTypeMaara];

		// Muuttuja i toimii arrayn lenghtinä mikäli ppTypeMaaraa ei käytetty kokonaan.
		int i = 0;
		for (Palvelupiste p : palvelupisteet) {
			if (p.getPalvelupisteenTyyppi() == ppType) {
				typeaVastaavatPalvelupisteet[i] = p;
				i++;
			}
		}

		// Palvelupisteen lengthi voi olla eroava todellisesta määrästä palvelupisteitä,
		// math.random varmaan poistuu tästä.
		Palvelupiste asiakkaanPalvelupiste = typeaVastaavatPalvelupisteet[(int) (Math.random()
				* i)];
		return asiakkaanPalvelupiste.getPalveluPisteenNumero();
	}

	// haePalvelupiste

	public int haePalvelupiste(int ppType) {

		int asiakkaanPalvelupiste = 0;
		for (Palvelupiste p : palvelupisteet) {
			if (p.getPalvelupisteenTyyppi() == ppType && p.onVarattu() == true) {
				asiakkaanPalvelupiste = p.getPalveluPisteenNumero();
			}
		}
		return asiakkaanPalvelupiste;
	}

	// tulokset

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");

	}

}
