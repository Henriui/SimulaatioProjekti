package simu.model;

import java.util.LinkedList;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava

public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus

	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private Tyyppi skeduloitavanTapahtumanTyyppi;
	private static int palvelupisteenUID = 0;
	private int palvelupisteenID;
	private int asiakkaitaLisattyJonoon;
	private int asiakkaitaPalveltuJonosta;

	// JonoStartegia strategia;
	// optio: asiakkaiden järjestys

	private boolean varattu = false;

	// PalveluPiste

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, Tyyppi tyyppi) {
		palvelupisteenID = palvelupisteenUID;
		palvelupisteenUID++;
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;

	}

	// lisaaJonoon

	public void lisaaJonoon(Asiakas a) { // Jonon 1. asiakas aina palvelussa
		asiakkaitaLisattyJonoon++;
		jono.add(a);
	}

	// otaJonosta

	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		varattu = false;
		asiakkaitaPalveltuJonosta++;
		return jono.poll();
	}

	// aloitaPalvelu

	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
		varattu = true;
		double palveluaika = generator.sample();
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika() + palveluaika));
	}

	public int getPalvelupisteenTyyppi() {
		return skeduloitavanTapahtumanTyyppi.getTyyppiValue();
	}

	public int getPalveluPisteenNumero() {
		return palvelupisteenID;
	}

	// onVarattu

	public boolean onVarattu() {
		return varattu;
	}

	// onJonossa

	public boolean onJonossa() {
		return jono.size() != 0;
	}

	@Override
	public String toString() {
		String str = "Palvelupiste tyypiltään: " + skeduloitavanTapahtumanTyyppi.name() + ", " + palvelupisteenID
				+ " nro, on lisannyt jonoon: " + asiakkaitaLisattyJonoon + " asiakasta, sekä palvellut: "
				+ asiakkaitaPalveltuJonosta + " kpl yhteensä.";
		return str;
	}

}
