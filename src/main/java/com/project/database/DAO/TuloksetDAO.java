package com.project.database.DAO;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.SimulaatioData;
import com.project.simu.model.Tulokset;
import com.project.simu.model.UserAsetukset;
import com.project.simu.model.PalvelupisteTulokset;
import com.project.simu.model.Parametrit;

public class TuloksetDAO implements ITuloksetDAO {
    private Parametrit up;
    private Connection connection;
    private PreparedStatement statement;
    private String dbName;
    private String tableName1;
    private String tableName2;
    private String user;
    private String password;
    private SimulaatioData ss;
    private int simulaatiokerta;

    public TuloksetDAO(UserAsetukset asetukset, boolean simulaatio) {
        
        dbName = asetukset.getDbName();
        user = asetukset.getUsername();
        password = asetukset.getPassword();
        if (simulaatio){
            tableName1 = "asiakkaat";
            tableName2 = "palvelupisteet";
        }
        else{
            tableName1 = "testiasiakkaat";
            tableName2 = "testipalvelupisteet";
        }

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
                System.out.println("Table one" + tableName1 + " not found. Creating table...");
                createTables(1);
                System.out.println("Table created.");
            }

            result = dbm.getTables(null, null, tableName2, null);

            // Create table 1 if not found.

            if (!result.next()) {
                System.out.println("Table two " + tableName2 + " not found. Creating table...");
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
                    + " as_keskilapimeno     DOUBLE UNSIGNED NOT NULL                     COMMENT 'Asiakkaan keskimääräinen läpimenoaika.' "
                    + " ) engine=InnoDB; ");
            if (statement.execute() != true) {
                return false;
            }
        }
        else if (table == 2){
            statement = connection.prepareStatement("CREATE TABLE "+ tableName2 +" ( "
                    + "id                   INT    NOT NULL AUTO_INCREMENT PRIMARY KEY          COMMENT 'Id, primary key.',"
                    + "simulaatiokerta      INT    NOT NULL                            COMMENT 'Linkitys asiakkaiden pöytään.',"
                    + "tyyppi               INT    UNSIGNED NOT NULL                            COMMENT 'Palvelupistetyyppi.',"
                    + "palvellut_as         INT    UNSIGNED NOT NULL                            COMMENT 'Kuinka monta asiakasta palveltu.',"
                    + "keskipalveluaika     DOUBLE UNSIGNED NOT NULL                            COMMENT 'Keskipalveluaika palvelupisteelle.',"
                    + "keskijonotusaika     DOUBLE UNSIGNED NOT NULL                            COMMENT 'Keskijonotusaika palvelupisteelle.' ,"
                    + "FOREIGN KEY (simulaatiokerta) REFERENCES "+ tableName1 +" (simulaatiokerta) ON DELETE CASCADE ON UPDATE RESTRICT"
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
     * @param data
     * @return boolean
     * @author Henri
     */
    @Override
    public boolean addAsiakasTulos(Tulokset data) {

        // Get values from SimulaationData, create sql statement and execute.

        try {
            statement = connection.prepareStatement("INSERT INTO " + tableName1
                    + "( kesto, palveluprosentti, as_maara, as_palveltu, as_routed, as_poistunut, as_keskijonoaika, as_keskilapimeno) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )");

            statement.setDouble(1, data.getKesto());    // kesto
            statement.setDouble(2, data.getPalveluProsentti());  // palveluprosentti
            statement.setInt(3, data.getAsMaara());                // as_maara
            statement.setInt(4, data.getPalvellutAsiakkaat());           // as_palveltu
            statement.setInt(5, data.getUudelleenOhjatutAsiakkaat());           // as_routed
            statement.setInt(6, data.getPoistuneetAsiakkaat());          // as_poistunut
            statement.setDouble(7, data.getKeskiJonotusAika());      // as_keskijonoaika
            statement.setDouble(8, data.getKeskiLapiMenoAika());      // as_keskiläpimeno

            statement.
            // Store value of simulaatiokerta for palvelupiste insertion.
            simulaatiokerta = data.getSimulaatiokerta();

           
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return false;
    }

public boolean addPalvelupisteTulos(PalvelupisteTulokset ppTulos){


    statement = connection.prepareStatement("INSERT INTO " + tableName1
    + "simulaatiokerta, tyyppi, palvellutas, keskipalveluaika, keskijonotusaika VALUES ( ?, ?, ?, ?, ? )");

    statement.setInt(1,simulaatiokerta);
    statement.setInt(2, );    // tyyppi
    statement.setInt(3, );    // palvellutas
    statement.setDouble(4, ); // keskipalveluaika
    statement.setDouble(5, ); // keskijonotusaika

    if (statement.executeUpdate() >= 1)
        return true;
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
            statement = connection.prepareStatement("DELETE FROM " + tableName1 + " WHERE id = ( ? )");
            statement.setInt(1, id);
            
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
     * Sets the retrieved data to the singleton class Simulaationdata.
     * Returns true if row found and retrieved. Otherwise returns false.
     * 
     * @param id
     * @return boolean
     * @Author Henri
     */
    public boolean queryTulos(int id) {

        // TODO: lisää haku tietokannasta kun tiedetään mitä haetaan.

        ss = new SimulaatioData(up); // Simulaationdata.getInstance();
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
            statement = connection.prepareStatement("DROP TABLE IF EXISTS " + tableName2 +", " + tableName1 );
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
     * Fetches row count of asiakas table from database.
     * Returns int value if successful, otherwise retuns 0.
     * 
     * @return int
     * @Author Henri
     */
    public int getRowCount(){
        int result;
        try{
            statement = connection.prepareStatement("Select id from " + tableName1);
            ResultSet rs = statement.executeQuery();
            rs.last();
            
            result = rs.getInt(1);

            return result;

        } catch (SQLException e) {
            System.out.println("No rows or something went wrong.");
            e.printStackTrace();
        }
        return 0;
    }
}
