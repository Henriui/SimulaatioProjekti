package com.project.testi;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.SimulaationSuureet;
import com.project.simu.model.UserParametrit;

//Tämä muutetaan test filuks jossain vaiheessa 
public class Simulaattori { // Tekstipohjainen

	public static void main(String[] args) {

		UserParametrit uP = UserParametrit.getInstance();
		uP.setDbParameters("olso", "simulaatio","root", "root");
		ITuloksetDAO db = new TuloksetDAO();
		SimulaationSuureet ss = SimulaationSuureet.getInstance();
		db.addTulos(ss);
		db.removeTulos(1);

		
		/*
		 * Trace.setTraceLevel(Level.INFO);
		 * Moottori m = new OmaMoottori();
		 * m.setSimulointiaika(uP.getSimulaationAika());
		 * m.start();
		 */

		/*
		 * int value;
		 * UserParametrit uP = UserParametrit.getInstance();
		 * Uniform uni = new Uniform(1, 100);
		 * 
		 * for (int i = 0; i < 10; i++) {
		 * value = uP.getProbability(AsiakasTyyppi.CO, (int) uni.sample());
		 * if (value > 4) {
		 * System.out.println("Probability for " + AsiakasTyyppi.CO + ": "
		 * + value + ", %: "
		 * + (uP.getCoAsiakasTyyppiArr()[value - 3]));
		 * } else {
		 * System.out.println("Probability for " + AsiakasTyyppi.CO + ": "
		 * + value + ", %: "
		 * + (uP.getCoAsiakasTyyppiArr()[value - 3] - uP.getCoAsiakasTyyppiArr()[value -
		 * 3]));
		 * }
		 * }
		 * 
		 * for (int i = 0; i < 10; i++) {
		 * value = uP.getProbability(AsiakasTyyppi.PRI, (int) uni.sample());
		 * if (value == 0) {
		 * System.out.println("Probability: " + AsiakasTyyppi.PRI + ": "
		 * + value + ", %: " + (uP.getPriAsiakasTyyppiArr()[value]));
		 * } else {
		 * System.out.println("Probability: " + AsiakasTyyppi.PRI + ": "
		 * + value + ", %: "
		 * + (uP.getPriAsiakasTyyppiArr()[value] - uP.getPriAsiakasTyyppiArr()[value -
		 * 1]));
		 * }
		 * }
		 * 
		 */

	}
}
