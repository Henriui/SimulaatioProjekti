package com.project.database.interfaces;

import com.project.simu.model.SimulaationSuureet;

public interface ITuloksetDAO {
    // Connection open/close
    public abstract boolean openConnection();
    public abstract boolean closeConnection();

    // 
    public abstract boolean addTulos(SimulaationSuureet suureet);
    public abstract boolean removeTulos(int id);
    public abstract boolean queryTulos(int id);
    public abstract boolean dropTable();
}
