package com.project.database.interfaces;

public interface ILoppunakymaDAO {
    // Connection open/close
    public abstract boolean openConnection();
    public abstract boolean closeConnection();

    //
    public abstract boolean addSuure();
}
