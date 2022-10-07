package com.project.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

import com.project.database.DAO.TuloksetDAO;
import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.SimulaationSuureet;
import com.project.simu.model.UserParametrit;

public class DAOtest {
    private static ITuloksetDAO dao;

    @BeforeEach
    public void avaaYhteys() {
        assertTrue(dao.openConnection(), "Avaus ei oonistu.");
    }

    @Test
    @DisplayName("closeConnection testi")
    public void closeTest() {
        assertTrue(dao.closeConnection(), "Sulkeminen ei toimi.");
    }

    @Test
    @DisplayName("addTulos testi")
    public void addTulosTesti() {
        SimulaationSuureet ss = new SimulaationSuureet();// SimulaationSuureet.getInstance();
        assertTrue(dao.addTulos(ss), "Tuloksen lisäys ei onnistu.");
    }

    @Test
    @DisplayName("addTulos testi")
    public void queryTulosTesti() {
        SimulaationSuureet ss = new SimulaationSuureet();// SimulaationSuureet.getInstance();
        assertTrue(dao.queryTulos(1), "Tuloksen lisäys ei onnistu.");
    }

    @Test
    @DisplayName("removeTulos testi")
    public void removeTulosTesti() {
        SimulaationSuureet ss = new SimulaationSuureet();// SimulaationSuureet.getInstance();
        dao.dropTable();
        dao.openConnection();
        dao.addTulos(ss);
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
        SimulaationSuureet ss = new SimulaationSuureet();
        dao.addTulos(ss);
        dao.addTulos(ss);
        dao.addTulos(ss); // add 3 rows.
        assertEquals(3, dao.getRowCount(), "Rows not counted well.");
    }
}
