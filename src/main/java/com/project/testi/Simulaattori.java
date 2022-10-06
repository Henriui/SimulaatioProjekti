package com.project.testi;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.SimulaationSuureet;
import com.project.simu.model.UserParametrit;

//Tämä muutetaan test filuks jossain vaiheessa 
public class Simulaattori { // Tekstipohjainen

	public static void main(String[] args) {

		UserParametrit uP = UserParametrit.getInstance();
		uP.setDbParameters("olso", "simulaatio", "root", "root");
		ITuloksetDAO db = new TuloksetDAO();
		SimulaationSuureet ss = new SimulaationSuureet(); // SimulaationSuureet.getInstance();
		uP.setDbParameters("olso", "testi", "root", "root");
		db.openConnection();
		db.dropTable();
		db.openConnection();
		db.addTulos(ss);
		db.addTulos(ss);
		db.addTulos(ss);
		System.out.println(db.getRowCount());
		/*
		 * Trace.setTraceLevel(Level.INFO);
		 * Moottori m = new OmaMoottori();
		 * m.setSimulointiaika(uP.getSimulaationAika());
		 * m.start();
		 */
	}
}
