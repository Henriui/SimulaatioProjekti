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

		palvelupisteet[0] = new Palvelupiste(new Normal(10, 6), tapahtumalista,
				TapahtumanTyyppi.BLENDER_VALIKKO_DEPART);
		palvelupisteet[1] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.PRI_VALIKKO_DEPART);
		palvelupisteet[2] = new Palvelupiste(new Normal(5, 3), tapahtumalista, TapahtumanTyyppi.CO_VALIKKO_DEPART);

		// Testausta varten jokainen palvelupiste asetettu yksitellen
		palvelupisteet[3] = new Palvelupiste(new Normal(50, 50), tapahtumalista, TapahtumanTyyppi.PRI_SALES_DEPART);
		palvelupisteet[4] = new Palvelupiste(new Normal(50, 50), tapahtumalista, TapahtumanTyyppi.PRI_SALES_DEPART);
		palvelupisteet[5] = new Palvelupiste(new Normal(50, 50), tapahtumalista, TapahtumanTyyppi.PRI_SALES_DEPART);
		palvelupisteet[6] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.PRI_NETWORK_DEPART);
		palvelupisteet[7] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.PRI_NETWORK_DEPART);
		palvelupisteet[8] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.PRI_NETWORK_DEPART);
		palvelupisteet[9] = new Palvelupiste(new Normal(20, 20), tapahtumalista,
				TapahtumanTyyppi.PRI_SUBSCRIBER_DEPART);
		palvelupisteet[10] = new Palvelupiste(new Normal(20, 20), tapahtumalista,
				TapahtumanTyyppi.PRI_SUBSCRIBER_DEPART);
		palvelupisteet[11] = new Palvelupiste(new Normal(20, 20), tapahtumalista,
				TapahtumanTyyppi.PRI_SUBSCRIBER_DEPART);
		palvelupisteet[12] = new Palvelupiste(new Normal(30, 30), tapahtumalista, TapahtumanTyyppi.PRI_INVOICE_DEPART);
		palvelupisteet[13] = new Palvelupiste(new Normal(30, 30), tapahtumalista, TapahtumanTyyppi.PRI_INVOICE_DEPART);
		palvelupisteet[14] = new Palvelupiste(new Normal(30, 30), tapahtumalista, TapahtumanTyyppi.PRI_INVOICE_DEPART);
		palvelupisteet[15] = new Palvelupiste(new Normal(50, 50), tapahtumalista, TapahtumanTyyppi.CO_SALES_DEPART);
		palvelupisteet[16] = new Palvelupiste(new Normal(50, 50), tapahtumalista, TapahtumanTyyppi.CO_SALES_DEPART);
		palvelupisteet[17] = new Palvelupiste(new Normal(50, 50), tapahtumalista, TapahtumanTyyppi.CO_SALES_DEPART);
		palvelupisteet[18] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.CO_NETWORK_DEPART);
		palvelupisteet[19] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.CO_NETWORK_DEPART);
		palvelupisteet[20] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.CO_NETWORK_DEPART);
		palvelupisteet[21] = new Palvelupiste(new Normal(20, 20), tapahtumalista,
				TapahtumanTyyppi.CO_SUBSCRIBER_DEPART);
		palvelupisteet[22] = new Palvelupiste(new Normal(20, 20), tapahtumalista,
				TapahtumanTyyppi.CO_SUBSCRIBER_DEPART);
		palvelupisteet[23] = new Palvelupiste(new Normal(20, 20), tapahtumalista,
				TapahtumanTyyppi.CO_SUBSCRIBER_DEPART);
		palvelupisteet[24] = new Palvelupiste(new Normal(30, 30), tapahtumalista, TapahtumanTyyppi.CO_INVOICE_DEPART);
		palvelupisteet[25] = new Palvelupiste(new Normal(30, 30), tapahtumalista, TapahtumanTyyppi.CO_INVOICE_DEPART);
		palvelupisteet[26] = new Palvelupiste(new Normal(30, 30), tapahtumalista, TapahtumanTyyppi.CO_INVOICE_DEPART);

		System.out.println(palvelupisteet.length);
		System.out.println(palvelupisteet[palvelupisteet.length - 1].getPalveluPisteenNumero());

		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.ARRIVAL);

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
		switch (t.getTyyppi()) {

			case ARRIVAL:
				// TODO: Uniform testausta varten, tämä asetetaan käyttäjäparametreistä valmiina
				// Asetetaan kayttajan valitsema jakauma asiakastyypin luomiseen.
				ContinuousGenerator kayttajanParametriJakaumalle = new Uniform(1, 8);
				a = new Asiakas(kayttajanParametriJakaumalle);
				palvelupisteet[TapahtumanTyyppi.BLENDER_VALIKKO_DEPART.getTapahtumanTypeNumero()].lisaaJonoon(a);
				saapumisprosessi.generoiSeuraava();
				break;

			case BLENDER_VALIKKO_DEPART:
				a = palvelupisteet[TapahtumanTyyppi.BLENDER_VALIKKO_DEPART.getTapahtumanTypeNumero()].otaJonosta();

				// Tähän jonoon varmaan siirrettään uudelleen asiakkaat?

				// Mikäli on alle 5 niin pri- jos yli 4 niin co-valikkoon
				if (a.getAsType() < 5) {
					palvelupisteet[TapahtumanTyyppi.PRI_VALIKKO_DEPART.getTapahtumanTypeNumero()].lisaaJonoon(a);
				} else {
					palvelupisteet[TapahtumanTyyppi.CO_VALIKKO_DEPART.getTapahtumanTypeNumero()].lisaaJonoon(a);
				}
				break;
			case PRI_VALIKKO_DEPART:
				a = palvelupisteet[TapahtumanTyyppi.PRI_VALIKKO_DEPART.getTapahtumanTypeNumero()].otaJonosta();
				palvelupisteet[lisaaPalvelupisteeseen(a.getAsType())].lisaaJonoon(a);
				break;
			case CO_VALIKKO_DEPART:
				a = palvelupisteet[TapahtumanTyyppi.CO_VALIKKO_DEPART.getTapahtumanTypeNumero()].otaJonosta();
				palvelupisteet[lisaaPalvelupisteeseen(a.getAsType())].lisaaJonoon(a);
				break;
			case PRI_SALES_DEPART:
				a = palvelupisteet[haePalvelupiste(TapahtumanTyyppi.PRI_SALES_DEPART.getTapahtumanTypeNumero())]
						.otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				break;
			case PRI_SUBSCRIBER_DEPART:
				a = palvelupisteet[haePalvelupiste(TapahtumanTyyppi.PRI_SUBSCRIBER_DEPART.getTapahtumanTypeNumero())]
						.otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				break;
			case PRI_NETWORK_DEPART:
				a = palvelupisteet[haePalvelupiste(TapahtumanTyyppi.PRI_NETWORK_DEPART.getTapahtumanTypeNumero())]
						.otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				break;
			case PRI_INVOICE_DEPART:
				a = palvelupisteet[haePalvelupiste(TapahtumanTyyppi.PRI_INVOICE_DEPART.getTapahtumanTypeNumero())]
						.otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				break;
			case CO_SALES_DEPART:
				a = palvelupisteet[haePalvelupiste(TapahtumanTyyppi.CO_SALES_DEPART.getTapahtumanTypeNumero())]
						.otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				break;
			case CO_SUBSCRIBER_DEPART:
				a = palvelupisteet[haePalvelupiste(TapahtumanTyyppi.CO_SUBSCRIBER_DEPART.getTapahtumanTypeNumero())]
						.otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				break;
			case CO_NETWORK_DEPART:
				a = palvelupisteet[haePalvelupiste(TapahtumanTyyppi.CO_NETWORK_DEPART.getTapahtumanTypeNumero())]
						.otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				break;
			case CO_INVOICE_DEPART:
				a = palvelupisteet[haePalvelupiste(TapahtumanTyyppi.CO_INVOICE_DEPART.getTapahtumanTypeNumero())]
						.otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				break;
		}
	}

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
