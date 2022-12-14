package com.project.simu.framework;

import com.project.eduni.distributions.*;
import com.project.simu.constants.Tyyppi;

public class Saapumisprosessi {

	private DiscreteGenerator generaattori;
	private Tapahtumalista tapahtumalista;
	private Tyyppi tyyppi;

	public Saapumisprosessi(DiscreteGenerator g, Tapahtumalista tl, Tyyppi tyyppi) {

		this.generaattori = g;
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
	}

	public void generoiSeuraava() {
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika() + generaattori.sample());
		tapahtumalista.lisaa(t);
	}

}
