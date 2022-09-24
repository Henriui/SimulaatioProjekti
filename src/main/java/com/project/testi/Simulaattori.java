package com.project.testi;

import com.project.simu.framework.*;
import com.project.simu.framework.Trace.Level;
import com.project.simu.model.OmaMoottori;

public class Simulaattori { // Tekstipohjainen

	public static void main(String[] args) {

		Trace.setTraceLevel(Level.INFO);
		Moottori m = new OmaMoottori();
		m.setSimulointiaika(1000);
		m.start();
	}
}
