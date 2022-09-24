package com.project.simu.framework;

import com.project.SecondaryController;
import com.project.simu.model.Palvelupiste;

public abstract class Moottori extends Thread {

	private double simulointiaika = 0;
	private long viive = 0;
	private Kello kello;

	protected Tapahtumalista tapahtumalista;
	protected Palvelupiste[] palvelupisteet;
	protected SecondaryController kontrolleri;

	public Moottori(SecondaryController kontrolleri) {

		this.kontrolleri = kontrolleri;

		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia

		tapahtumalista = new Tapahtumalista();

		// Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa

	}

	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}

	public void setViive(long viive) {
		this.viive = viive;
	}

	public long getViive() {
		return viive;
	}

	public void run() {
		alustukset(); // luodaan mm. ensimmäinen tapahtuma
		while (simuloidaan()) {

			Trace.out(Trace.Level.INFO, "\nA-vaihe: kello on " + nykyaika());
			kello.setAika(nykyaika());

			Trace.out(Trace.Level.INFO, "\nB-vaihe:");
			suoritaBTapahtumat();

			Trace.out(Trace.Level.INFO, "\nC-vaihe:");
			yritaCTapahtumat();

		}
		tulokset();

	}

	private void suoritaBTapahtumat() {
		while (tapahtumalista.getSeuraavanAika() == kello.getAika()) {
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	private void yritaCTapahtumat() {
		for (Palvelupiste p : palvelupisteet) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			}
		}
	}

	private double nykyaika() {
		return tapahtumalista.getSeuraavanAika();
	}

	private boolean simuloidaan() {
		return kello.getAika() < simulointiaika;
	}

	private void viive() { // UUSI
		Trace.out(Trace.Level.INFO, "Viive " + viive);
		try {
			sleep(viive);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected abstract void alustukset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void suoritaTapahtuma(Tapahtuma t); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void tulokset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

}