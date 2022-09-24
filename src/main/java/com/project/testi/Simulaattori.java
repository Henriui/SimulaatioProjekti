package com.project.testi;

<<<<<<< HEAD:testi/Simulaattori.java
import simu.framework.*;
import simu.framework.Trace.Level;
import simu.model.OmaMoottori;
import simu.model.UserParametrit;
=======
import com.project.simu.framework.*;
import com.project.simu.framework.Trace.Level;
import com.project.simu.model.OmaMoottori;
>>>>>>> main:src/main/java/com/project/testi/Simulaattori.java

public class Simulaattori { // Tekstipohjainen

	public static void main(String[] args) {
		UserParametrit uP = UserParametrit.getInstance();
		Trace.setTraceLevel(Level.INFO);
		Moottori m = new OmaMoottori();
		m.setSimulointiaika(uP.getSimulaationAika());
		m.start();
	}
}
