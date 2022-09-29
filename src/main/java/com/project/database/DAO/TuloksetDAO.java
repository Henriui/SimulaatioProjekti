package com.project.database.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.project.database.interfaces.ITuloksetDAO;

public class TuloksetDAO implements ITuloksetDAO{
    private Connection connection;
    String url;
    String user;
    String password;

    @Override
    public boolean openConnection() {
        System.out.println("Opening connection...");
        try {
            connection = DriverManager.getConnection(url, user, password);
            return true;
        } catch (SQLException e) {
            System.out.println("Connection failed. SQLException.");
        }
        return false;
    }

    @Override
    public boolean closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed.");
            return true;
        } catch (SQLException e) {
            System.out.println("Connection close failed. SQLException.");
            e.printStackTrace();
        }
        return false;
    }
    
}
