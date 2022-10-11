package com.project.database.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import com.project.simu.model.Tulokset;


public interface ITuloksetDAO {
    // Connection open/close.
    public abstract boolean openConnection();
    public abstract boolean closeConnection();

    // Methods to use database.
    public abstract boolean addTulos(Tulokset data);
    public abstract boolean removeTulos(int id);
    public abstract Tulokset queryTulos(int id) throws SQLException;
    public abstract ArrayList<Tulokset> queryTulokset() throws SQLException;
    public abstract boolean dropTable();
    public abstract int     getRowCount();
    public abstract int     getRowIndex();
}
