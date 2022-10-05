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
	}
}
