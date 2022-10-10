package com.project.database.DAO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.SimulaationSuureet;
import com.project.simu.model.UserParametrit;

public class TuloksetDAO implements ITuloksetDAO {
    private UserParametrit up;
    private Connection connection;
    private PreparedStatement statement;
    private String dbName;
    private String tableName1;
    private String tableName2;
    private String user;
    private String password;
    SimulaationSuureet ss;

    public TuloksetDAO() {
        // Hae käyttäjän määrittämä tietokanta, username ja password.
        up = UserParametrit.getInstance();
        tableName1 = up.getTableName1();
        tableName1 = up.getTableName2();
        dbName = up.getDbName();
        user = up.getUsername();
        password = up.getPassword();
        openConnection();
    }

    /**
     * Returns true if connection opened successfully Otherwise returns false.
     * 
     * @return boolean
     * @author Henri
     */
    @Override
    public boolean openConnection() {
        System.out.println("Opening connection...");

        // Check if connection open.

        if (connection != null) {
            System.out.println("connection already open.");
        }

        // Open connection

        try {
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/" + dbName, user, password);

            // See if we have a table 1 in the given database.

            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet result = dbm.getTables(null, null, tableName1, null);

            // Create table 1 if not found.

            if (!result.next()) {
                System.out.println("Table " + tableName1 + " not found. Creating table...");
                createTables(1);
                System.out.println("Table created.");
            }

            result = dbm.getTables(null, null, tableName2, null);

            // Create table 1 if not found.

            if (!result.next()) {
                System.out.println("Table " + tableName2 + " not found. Creating table...");
                createTables(2);
                System.out.println("Table created.");
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Connection failed. SQLException. Check database name, username and password.");
        }
        return false;
    }

    /**
     * Returns true if table created successfully. Otherwise returns false.
     * 
     * @return boolean
     * @throws SQLException
     * @author Henri
     */
    private boolean createTables(int table) throws SQLException {
        if ( table == 1){
            statement = connection.prepareStatement("CREATE TABLE " + tableName1 + " ( "
                    + " simulaatiokerta      INT  NOT NULL  AUTO_INCREMENT  PRIMARY KEY   COMMENT 'Simulointikerta',"
                    + " kesto                DOUBLE UNSIGNED NOT NULL                     COMMENT 'Simulaation kesto',"
                    + " palveluprosentti     DOUBLE UNSIGNED NOT NULL                     COMMENT 'Palveluprosentti simulaatiossa.',"
                    + " as_maara             INT    UNSIGNED NOT NULL                     COMMENT 'Asiakkaiden määrä simulaatiossa.',"
                    + " as_palveltu          INT    UNSIGNED NOT NULL                     COMMENT 'Simulaatiossa palvellut asiakkaat.',"
                    + " as_routed            INT    UNSIGNED NOT NULL                     COMMENT 'Asiakkaat jotka valitsi väärän linjan ja ohjattiin uudelleen.',"
                    + " as_poistunut         INT    UNSIGNED NOT NULL                     COMMENT 'Simulaatiosta poistuneet asiakkaat.',"
                    + " as_keskijonoaika     DOUBLE UNSIGNED NOT NULL                     COMMENT 'Asiakkaan keskimääräinen jonotusaika.' ,"
                    + " as_keskilapimeno     DOUBLE UNSIGNED NOT NULL                     COMMENT 'Asiakkaan keskimääräinen läpimenoaika.' ,"
                    + " ) engine=InnoDB; ");
            if (statement.execute() != true) {
                return false;
            }
        }
        else if (table == 2){
            statement = connection.prepareStatement("CREATE  "+ tableName2 +" ( "
                    + "id                   INT             NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'Id, primary key.',"
                    + "simulaatiokerta      INT    UNSIGNED NOT NULL                            COMMENT 'Linkitys asiakkaiden pöytään.',"
                    + "tyyppi               INT    UNSIGNED NOT NULL                            COMMENT 'Palvelupistetyyppi.',"
                    + "palvellut_as         INT    UNSIGNED NOT NULL                            COMMENT 'Kuinka monta asiakasta palveltu.',"
                    + "keskipalveluaika     DOUBLE UNSIGNED NOT NULL                            COMMENT 'Keskipalveluaika palvelupisteelle.',"
                    + "keskijonotusaika     DOUBLE UNSIGNED NOT NULL                            COMMENT 'Keskijonotusaika palvelupisteelle.' "
                    + " ) engine=InnoDB;");
            if (statement.execute() != true) {
                return false;
            }
        }
        else
            return false;
        return true;

    }

    /**
     * Returns true if connection closed succesfully. Otherwise returns false.
     * 
     * @return boolean
     * @author Henri
     */
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

    /**
     * Returns true if parameter read and added to a row in database. Otherwise
     * returns false.
     * 
     * @param suureet
     * @return boolean
     * @author Henri
     */
    @Override
    public boolean addTulos(SimulaationSuureet suureet) {

        boolean success = false;
        // Get values from SimulaationSuureet, create sql statement and execute.

        try {
            statement = connection.prepareStatement("INSERT INTO " + tableName1
                    + "( kesto, palveluprosentti, as_maara, as_palveltu, as_routed, as_poistunut, as_keskijonoaika, as_keskilapimeno) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )");

            statement.setDouble(1, suureet.getSimulointiAika());    // kesto
            statement.setDouble(2, suureet.getPalveluprosentti());  // palveluprosentti
            statement.setInt(3, (int) suureet.getAsTotalMaara());   // as_maara
            statement.setInt(4, suureet.getAsPalveltu());           // as_palveltu
            statement.setInt(5, suureet.getAsReRouted());           // as_routed
            statement.setInt(6, suureet.getAsPoistunut());          // as_poistunut
            statement.setDouble(7, suureet.getAvgAsAikaSim());      // as_keskijonoaika
            statement.setDouble(7, suureet.getAvgAsAikaSim());      // as_keskiläpimeno

            // Get id of last created row of table 1.
            
            int table1Id;
            statement = connection.prepareStatement("Select id from " + tableName1);
            ResultSet rs = statement.executeQuery();
            rs.last();
            
            table1Id = rs.getInt(1);

            // Return true if INSERT successful;

            if (statement.executeUpdate() >= 1)
                success = true;

                statement = connection.prepareStatement("INSERT INTO " + tableName1
                + "simulaatiokerta, tyyppi, palvellutas, keskipalveluaika, keskijonotusaika VALUES ( ?, ?, ?, ?, ? )");
                
                statement.setInt(1,table1Id);
                statement.setInt(2, );    // tyyppi
                statement.setInt(3, );    // palvellutas
                statement.setDouble(4, ); // keskipalveluaika
                statement.setDouble(5, ); // keskijonotusaika

                if (statement.executeUpdate() >= 1)
                success = true;

                if (success)
                    return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return false;
    }

    /**
     * Returns true if row with given id is deleted successfully. Otherwise returns false.
     * 
     * @param id
     * @return boolean
     * @Author Henri
     */
    @Override
    public boolean removeTulos(int id) {
        // Delete given id.

        try {
            statement = connection.prepareStatement("DELETE FROM " + tableName1 + " WHERE id = ( ? ); DELETE FROM " + tableName2 + " WHERE id = ( ? )");
            statement.setInt(1, id);
            statement.setInt(2, id);
            
            // Return true if DELETE successful;

            if (statement.executeUpdate() >= 1) {
                System.out.println("Delete successful.");
                return true;
            } else {
                System.out.println("Id not in range of database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Fetches a row with given id from the database.
     * Sets the retrieved data to the singleton class SimulaationSuureet.
     * Returns true if row found and retrieved. Otherwise returns false.
     * 
     * @param id
     * @return boolean
     * @Author Henri
     */
    public boolean queryTulos(int id) {

        // TODO: lisää haku tietokannasta kun tiedetään mitä haetaan.

        ss = new SimulaationSuureet(); // SimulaationSuureet.getInstance();
        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName1 + " WHERE id = ( ? )");
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                /*
                 * ss.setSuure(); jokaiselle kun keksin mihin mikäkin menee.
                 */
                results.getDouble(2); // kesto
                results.getDouble(3); // palveluprosentti
                results.getInt(4); // as_count
                results.getInt(5); // as_lisatyt
                results.getInt(6); // as_palveltu
                results.getInt(7); // as_routed
                results.getInt(8); // as_poistunut
                results.getDouble(9); // as_jono_aika
                results.getDouble(10); // as_palvelu_aika
                results.getDouble(11); // as_kok_aika
                results.getDouble(12); // as_avg_aika
                results.getInt(13); // pp_count
                results.getDouble(14); // pp_jonotus_aika
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Drops table from the database.
     * Returns true if successful. Otherwise returns false.
     * 
     * @return boolean
     * @Author Henri
     */
    public boolean dropTable() {
        try {
            statement = connection.prepareStatement("DROP TABLE " + tableName1 + "; DROP TABLE " + tableName2);
            statement.execute();
            System.out.println("Table(s) dropped.");
            return true;
        } catch (SQLException e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Fetches column count of database.
     * Returns int value if successful, otherwise retuns null.
     * 
     * @return int
     * @Author Henri
     */
    public int getRowCount(){
        int result, result2;
        try{
            statement = connection.prepareStatement("Select id from " + tableName1);
            ResultSet rs = statement.executeQuery();
            rs.last();
            
            result = rs.getInt(1);

            statement = connection.prepareStatement("Select id from " + tableName2);
            rs = statement.executeQuery();
            rs.last();
            
            result2 = rs.getInt(1);

            if(result == result2)
                return result;
            else
                System.out.println("Database tables out of sync. Suggestion: Drop tables.");
        } catch (SQLException e) {
            System.out.println("No rows or something went wrong.");
            e.printStackTrace();
        }
        return 0;
    }
}
