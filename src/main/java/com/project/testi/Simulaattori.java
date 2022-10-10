package com.project.testi;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.SimulaatioData;
import com.project.simu.model.UserAsetukset;
import com.project.simu.model.Parametrit;

//Tämä muutetaan test filuks jossain vaiheessa 
public class Simulaattori { // Tekstipohjainen

	public static void main(String[] args) {
		UserAsetukset ua = new UserAsetukset("olso","root","root");

		ITuloksetDAO db = new TuloksetDAO(ua, false);
		Parametrit up = new Parametrit();
		SimulaatioData ss = new SimulaatioData(up); // SimulaationSuureet.getInstance();
		db.openConnection();
		db.dropTable();
		db.openConnection();
		db.addTulos(ss);
		db.addTulos(ss);
		db.addTulos(ss);
		// System.out.println(db.getRowCount());
		/*
		 * Trace.setTraceLevel(Level.INFO);
		 * Moottori m = new OmaMoottori();
		 * m.setSimulointiaika(uP.getSimulaationAika());
		 * m.start();
		 */
	}
}
