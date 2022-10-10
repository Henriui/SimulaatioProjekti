package com.project.database.interfaces;

import com.project.simu.model.Tulokset;
import com.project.simu.model.PalvelupisteTulokset;

public interface ITuloksetDAO {
    // Connection open/close.
    public abstract boolean openConnection();
    public abstract boolean closeConnection();

    // Methods to use database.
    public abstract boolean addAsiakasTulos(Tulokset data);
    public abstract boolean addPalvelupisteTulos(PalvelupisteTulokset ppTulos);
    public abstract boolean removeTulos(int id);
    public abstract boolean queryTulos(int id);
    public abstract boolean dropTable();
    public abstract int     getRowCount();
}
