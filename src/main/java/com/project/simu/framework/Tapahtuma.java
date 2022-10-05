package com.project.simu.framework;

import com.project.simu.constants.Tyyppi;

public class Tapahtuma implements Comparable<Tapahtuma> {
	
		
	private Tyyppi tyyppi;
	private double aika;
	
	public Tapahtuma(Tyyppi tyyppi, double aika){
		this.tyyppi = tyyppi;
		this.aika = aika;
	}
	
	public void setTyyppi(Tyyppi tyyppi) {
		this.tyyppi = tyyppi;
	}
	public Tyyppi getTyyppi() {
		return tyyppi;
	}
	public void setAika(double aika) {
		this.aika = aika;
	}
	public double getAika() {
		return aika;
	}

	@Override
	public int compareTo(Tapahtuma arg) {
		if (this.aika < arg.aika) return -1;
		else if (this.aika > arg.aika) return 1;
		return 0;
	}
	
	
	

}
