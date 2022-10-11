package com.project.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.*;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.Tulokset;
import com.project.simu.model.UserAsetukset;
import com.project.simu.model.PalvelupisteTulos;

public class DAOtest {
    private static ITuloksetDAO dao;
    private static Tulokset tulos;
    PalvelupisteTulos ppTulos;
    ArrayList<PalvelupisteTulos> ppList = new ArrayList<PalvelupisteTulos>();
    
    @BeforeEach
    public void avaaYhteys() {
        UserAsetukset up = new UserAsetukset("simulaattori", "root", "root");
        dao = new TuloksetDAO(up, false);
        assertTrue(dao.openConnection(), "Avaus ei oonistu.");

        ppTulos = new PalvelupisteTulos(5,1,7,8,9,10,70);

        ppList.add(ppTulos);

        tulos = new Tulokset(1, 2, 3, 4, 5, 
                         6, 7, 8, 9, ppList);
    }

    @Test
    @DisplayName("closeConnection testi")
    public void closeTest() {
        assertTrue(dao.closeConnection(), "Sulkeminen ei toimi.");
    }

    @Test
    @DisplayName("addTulos testi")
    public void addTulosTesti(){ 
        dao.dropTable();
        dao.openConnection();
        assertTrue(dao.addTulos(tulos), "Asiakas tuloksen lisäys ei onnistu.");
    }
    
    @Test
    @DisplayName("addTulos testi")
    public void queryTulosTesti() {
        Tulokset tulos1 = null;
        dao.dropTable();
        dao.openConnection();

        dao.addTulos(tulos);

        try {
            tulos1 = dao.queryTulos(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(tulos1.getSimulaatiokerta());
        assertEquals(1, tulos1.getSimulaatiokerta(), "gay");
        assertEquals(5, tulos1.getPalvellutAsiakkaat());


    }

    @Test
    @DisplayName("removeTulos testi")
    public void removeTulosTesti() {
        dao.dropTable();
        dao.openConnection();
        dao.addTulos(tulos);
        assertTrue(dao.removeTulos(1), "Tuloksen lisäys ei onnistu.");
    }
    
    @Test
    @DisplayName("dropTable testi")
    public void dropTableTesti() {
        assertTrue(dao.dropTable(), "Table not dropped properly.");
    }

    @Test
    @DisplayName("getRowCount testi")
    public void getRowCountTest() {
        dao.dropTable();
        dao.openConnection();
        dao.addTulos(tulos);
        dao.addTulos(tulos);
        dao.addTulos(tulos);
        assertEquals(3, dao.getRowCount(), "Rows not counted well.");
    }
    @Test
    @DisplayName("getRowIndexTesti testi")
    public void getRowIndexTesti() {
        dao.dropTable();
        dao.openConnection();
        dao.addTulos(tulos);
        dao.addTulos(tulos);
        dao.addTulos(tulos);
        assertEquals(3, dao.getRowIndex(), "Rows not counted well.");
    }
}
