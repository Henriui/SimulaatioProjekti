package com.project.testi;

import java.sql.SQLException;
import java.util.ArrayList;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.Tulokset;
import com.project.simu.model.UserAsetukset;
import com.project.simu.model.PalvelupisteTulokset;

//Tämä muutetaan test filuks jossain vaiheessa 
public class Simulaattori { // Tekstipohjainen
    private static ArrayList<PalvelupisteTulokset> ppList = new ArrayList<PalvelupisteTulokset>();
    private static PalvelupisteTulokset ppTulos;
    private static Tulokset tulos;

	public static void main(String[] args) {
		UserAsetukset ua = new UserAsetukset("olso","root","root");
		ITuloksetDAO db = new TuloksetDAO(ua, false);
		
		// db.openConnection();
		// db.dropTable();
		db.openConnection();

		ppTulos = new PalvelupisteTulokset(1,1,7,8,9,10);

        ppList.add(ppTulos);

        tulos = new Tulokset(1, 2, 3, 4, 5, 6, 7, 8, 9, ppList);
		
		db.addTulos(tulos);

		Tulokset tulos1 = null;
		try {
			tulos1 = db.queryTulos(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("aaaaaaa" + tulos1.getAsMaara() + "  " + tulos.getPalvellutAsiakkaatString());
		
		// System.out.println(db.getRowCount());
		/*
		 * Trace.setTraceLevel(Level.INFO);
		 * Moottori m = new OmaMoottori();
		 * m.setSimulointiaika(uP.getSimulaationAika());
		 * m.start();
		 */
	}
}
