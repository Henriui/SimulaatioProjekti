package com.project.testi;

import java.sql.SQLException;
import java.util.ArrayList;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.Tulokset;
import com.project.simu.model.UserAsetukset;
import com.project.simu.model.PalvelupisteTulos;

//Tämä muutetaan test filuks jossain vaiheessa 
public class Simulaattori { // Tekstipohjainen
	
	
	public static void main(String[] args) {
		UserAsetukset ua = new UserAsetukset("simulaattori","jonne","jonnensalasana");
		ITuloksetDAO db = new TuloksetDAO(ua, false);
		
		db.openConnection();
		db.dropTable();
		db.openConnection();
		
		for(int i=1; i< 5; i++){

			ArrayList<PalvelupisteTulos> ppList = new ArrayList<>();

			Tulokset tulos = new Tulokset( 2, 3, 4, 5, 6, 7, 8, 9, ppList);
			db.addTulos(tulos);
		}
		ArrayList<Tulokset> tulosList = null;

		try {
			 tulosList = db.queryTulokset();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(tulosList.size());
		for (Tulokset tulokset : tulosList) {
			System.out.println("aaaaaaa .... " + tulokset.getSimulaatiokerta());
			
		}
		
		// System.out.println(db.getRowCount());
		/*
		 * Trace.setTraceLevel(Level.INFO);
		 * Moottori m = new OmaMoottori();
		 * m.setSimulointiaika(uP.getSimulaationAika());
		 * m.start();
		 */
	}
}
