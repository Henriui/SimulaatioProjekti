package com.project.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.Tulokset;
import com.project.simu.model.UserAsetukset;
import com.project.simu.model.PalvelupisteTulokset;

public class DAOtest {
    private static ITuloksetDAO dao;
    private static Tulokset tulos;
    ArrayList<PalvelupisteTulokset> ppList = new ArrayList<>();
    
    @BeforeEach
    public void avaaYhteys() {
        UserAsetukset up = new UserAsetukset("olso", "root", "root");
        dao = new TuloksetDAO(up, false);
        assertTrue(dao.openConnection(), "Avaus ei oonistu.");
        tulos = new Tulokset(1, 2, 3, 4, 5, 6, 7, 8, 9, ppList);
    }

    @Test
    @DisplayName("closeConnection testi")
    public void closeTest() {
        assertTrue(dao.closeConnection(), "Sulkeminen ei toimi.");
    }

    @Test
    @DisplayName("addTulos testi")
    public void addTulosTesti() {
        assertTrue(dao.addAsiakasTulos(tulos), "Tuloksen lisäys ei onnistu.");
    }
    
    @Test
    @DisplayName("addTulos testi")
    public void queryTulosTesti() {
        dao.dropTable();
        dao.openConnection();
        dao.addAsiakasTulos(tulos);
        tulos = dao.queryTulos(2);
        assertEquals(1, tulos.getSimulaatiokerta(), "gay");
        assertEquals(2, tulos.getPalvellutAsiakkaat());


    }

    @Test
    @DisplayName("removeTulos testi")
    public void removeTulosTesti() {
        dao.dropTable();
        dao.openConnection();
        dao.addAsiakasTulos(tulos);
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
        dao.addAsiakasTulos(tulos);
        dao.addAsiakasTulos(tulos);
        dao.addAsiakasTulos(tulos);
        assertEquals(3, dao.getRowCount(), "Rows not counted well.");
    }
}
