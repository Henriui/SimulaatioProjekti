package com.project.testi;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.SimulaatioData;
import com.project.simu.model.Parametrit;

//Tämä muutetaan test filuks jossain vaiheessa 
public class Simulaattori { // Tekstipohjainen

	public static void main(String[] args) {

		Parametrit uP = new Parametrit();
		uP.setDbParameters("olso", "simulaatio", "root", "root");
		ITuloksetDAO db = new TuloksetDAO();
		SimulaatioData ss = new SimulaatioData(uP); // SimulaationSuureet.getInstance();
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
