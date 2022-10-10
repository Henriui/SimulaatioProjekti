package com.project.simu.constants;

// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella

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

	// Saapuminen simulaatioon
	ARRIVAL(12);

	// Saapumiset valikoihin animointia varten
	/*
	 * BLENDER_VALIKKO_ARR,
	 * PRI_VALIKKO_ARR,
	 * CO_VALIKKO_ARR,
	 */

	// Saapumiset asiakaspalveluihin
	/*
	 * PRI_SALES_ARR,
	 * PRI_INVOICE_ARR,
	 * PRI_SUBSCRIBER_ARR,
	 * PRI_NETWORK_ARR,
	 * 
	 * CO_SALES_ARR,
	 * CO_INVOICE_ARR,
	 * CO_SUBSCRIBER_ARR,
	 * CO_NETWORK_ARR,
	 * 
	 * // Asiakaspoistuu järjestelmästä
	 * DEPART
	 */

	private int tapahtumanTypeNumero;

	Tyyppi(int tapahtumanTypeNumero) {
		this.tapahtumanTypeNumero = tapahtumanTypeNumero;
	}

	public int getTypeValue() {
		return this.tapahtumanTypeNumero;
	}

	// Kokonais tyyppi koko - arrival
	public static final int maxSize;
	static {
		maxSize = values().length - 1;
	}
}
