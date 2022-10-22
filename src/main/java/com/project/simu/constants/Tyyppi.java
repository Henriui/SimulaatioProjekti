package com.project.simu.constants;

/**
 * Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
 * 
 * @author Rasmus Hyyppä
 */
public enum Tyyppi {

	// Poistumiset asiakaspalveluista
	PRI_SALES_DEPART(1),
	PRI_NETWORK_DEPART(2),
	PRI_SUBSCRIBER_DEPART(3),
	PRI_INVOICE_DEPART(4),
	CO_SALES_DEPART(5),
	CO_NETWORK_DEPART(6),
	CO_SUBSCRIBER_DEPART(7),
	CO_INVOICE_DEPART(8),
	// Puhelinvalikkojen tapahtumatyypit
	PRI_VALIKKO_DEPART(9),
	CO_VALIKKO_DEPART(10),
	BLENDER_VALIKKO_DEPART(11),
	ARRIVAL(12);// Saapuminen simulaatioon

	private int tapahtumanTypeNumero;

	Tyyppi(int tapahtumanTypeNumero) {
		this.tapahtumanTypeNumero = tapahtumanTypeNumero;
	}

	public int getTypeValue() {
		return this.tapahtumanTypeNumero;
	}

	public static final int maxSize;
	static {
		maxSize = values().length - 1; // -1 from arrival
	}
}
