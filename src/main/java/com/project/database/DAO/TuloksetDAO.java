package com.project.database.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.SimulaationSuureet;
import com.project.simu.model.UserParametrit;

public class TuloksetDAO implements ITuloksetDAO{
    private UserParametrit up;
    private Connection connection;
    private PreparedStatement statement;
    private String dbName;
    private String user;
    private String password;
    SimulaationSuureet ss;

    public TuloksetDAO(){
        // Hae käyttäjän määrittämä tietokanta, username ja password.
        up = UserParametrit.getInstance();
        dbName = up.getDbName();
        user = up.getUsername();
        password = up.getPassword();
        openConnection();
    }

    @Override
    public boolean openConnection() {
        System.out.println("Opening connection...");

        // Check if connection open.

        if (connection != null){
            System.out.println("connection already open.");
            return false;
        }

        // Open connection

        try {
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/" + dbName, user, password);
            return true;
        } catch (SQLException e) {
            System.out.println("Connection failed. SQLException. Check database name, username and password.");
        }
        return false;
    }

    @Override
    public boolean closeConnection() {
        // Close connection.

        try {
            connection.close();
            System.out.println("Connection closed.");
            return true;
        } catch (SQLException e) {
            System.out.println("Connection close failed. Access error.");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addTulos(SimulaationSuureet suureet) {

        // Get values from SimulaationSuureet, create sql statement and execute.

        try {
            statement = connection.prepareStatement("INSERT INTO simulaatio (kesto ) VALUES ( ? )");
            statement.setDouble(1, suureet.getSimulointiAika()); 
            
            // Return true if INSERT successful;

            if ( statement.executeUpdate() >= 1 ){
                return true;
            }
        } catch (SQLException e) {e.printStackTrace();}

        return false;
    }

    @Override
    public boolean removeTulos(int id) {
        // Delete given id.

        try {
            statement = connection.prepareStatement("DELETE FROM simulaatio WHERE id = ( ? )");
            statement.setInt(1, id); 
            
            // Return true if DELETE successful;

            if ( statement.executeUpdate() >= 1 ){
                System.out.println("Delete successful.");
                return true;
            }
            else{
                System.out.println("Id not in range of database.");
            }
        } catch (SQLException e) {e.printStackTrace();}
        return false;
    }
    public boolean queryTulos(int id){
        // TODO: lisää haku tietokannasta kun tiedetään mitä haetaan.
        ss = SimulaationSuureet.getInstance();
            try {
                statement = connection.prepareStatement("SELECT FROM simulaatio WHERE id = ( ? )");
                statement.setInt(1, id); 
                ResultSet results = statement.executeQuery();
                if (results.next()){
                   /*
                   * ss.setSuure(); jokaiselle kun keksin mihin mikäkin menee. 
                    */ 
                    results.getDouble(2);   // Kesto
                    results.getDouble(3);   // Palveluprosentti
                    results.getInt(4);      // as_count
                    results.getInt(5);      // as_lisatyt
                    results.getInt(6);      // as_palveltu
                    results.getInt(7);      // as_routed
                    results.getInt(8);      // as_poistunut
                    results.getDouble(9);   // as_jono_aika
                    results.getDouble(10);  // as_palvelu_aika
                    results.getDouble(11);  // as_kok_aika
                    results.getDouble(12);  // as_avg_aika
                    results.getInt(13);     // pp_count
                    results.getDouble(14);  // pp_jonotus_aika
                }

                return true;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }
    
}
