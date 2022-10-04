package com.project.simu.model;

import java.io.Serializable;

/**
 * Olion avulla käsitellään ja tallennetaan käyttäjän määrittämiä asetuksia
 */
public class UserAsetukset implements Serializable{
    
    private String dbName, username, password;

    /**
     * 
     * @param dbName Name of database
     * @param username Username in database
     * @param password Password to database
     * @author Lassi Bågman
     */
    public UserAsetukset(String dbName, String username, String password) {
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
