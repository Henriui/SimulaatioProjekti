package testi;

import simu.framework.*;
import simu.framework.Trace.Level;
import simu.model.OmaMoottori;
import simu.model.UserParametrit;

public class Simulaattori { // Tekstipohjainen

	public static void main(String[] args) {
		UserParametrit uP = UserParametrit.getInstance();
		Trace.setTraceLevel(Level.INFO);
		Moottori m = new OmaMoottori();
		m.setSimulointiaika(uP.getSimulaationAika());
		m.start();
	}
}
