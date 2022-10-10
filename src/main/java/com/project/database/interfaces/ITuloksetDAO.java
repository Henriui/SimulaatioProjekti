package com.project.database.interfaces;

import com.project.simu.model.Tulokset;


public interface ITuloksetDAO {
    // Connection open/close.
    public abstract boolean openConnection();
    public abstract boolean closeConnection();

    // Methods to use database.
    public abstract boolean addTulos(Tulokset data);
    public abstract boolean removeTulos(int id);
    public abstract Tulokset queryTulos(int id);
    public abstract boolean dropTable();
    public abstract int     getRowCount();
}
