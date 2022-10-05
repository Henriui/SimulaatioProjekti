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
    private String tableName;
    private String user;
    private String password;
    SimulaationSuureet ss;

    public TuloksetDAO() {
        // Hae käyttäjän määrittämä tietokanta, username ja password.
        up = UserParametrit.getInstance();
        tableName = up.getTableName();
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

            // See if we have a table in the given database.

            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet result = dbm.getTables(null, null, tableName, null);

            // Create if not found.

            if (!result.next()) {
                System.out.println("Table " + tableName + " not found. Creating table...");
                createTable();
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
    private boolean createTable() throws SQLException {

        // Not good since opens project for sql injection attack but required for +3
        // points junit tests. -Henri

        statement = connection.prepareStatement("CREATE TABLE " + tableName + " ( "
                + "    id                   INT  NOT NULL  AUTO_INCREMENT  PRIMARY KEY   COMMENT 'Simulointikerta',"
                + "    kesto                DOUBLE UNSIGNED NOT NULL                     COMMENT 'Simulaation kesto',"
                + "    palveluprosentti     DOUBLE UNSIGNED NOT NULL                     COMMENT 'Palveluprosentti simulaatiossa.',"
                + "    as_count             INT UNSIGNED NOT NULL                        COMMENT 'Asiakkaiden määrä simulaatiossa.',"
                + "    as_lisatyt           INT UNSIGNED NOT NULL                        COMMENT 'Simulaatioon lisätyt asiakkaat.',"
                + "    as_palveltu          INT UNSIGNED NOT NULL                        COMMENT 'Simulaatiossa palvellut asiakkaat.',"
                + "    as_routed            INT UNSIGNED NOT NULL                        COMMENT 'Asiakkaat jotka valitsi väärän linjan ja ohjattiin uudelleen.',"
                + "    as_poistunut         INT UNSIGNED NOT NULL                        COMMENT 'Simulaatiosta poistuneet asiakkaat.',"
                + "    as_jono_aika         DOUBLE UNSIGNED NOT NULL                     COMMENT 'Asiakkaan kokonaisjonotusaika.' ,"
                + "    as_palvelu_aika      DOUBLE UNSIGNED NOT NULL                     COMMENT 'Asiakkaiden kokonaispalveluaika.',"
                + "    as_kok_aika          DOUBLE UNSIGNED NOT NULL                     COMMENT 'Asiakkaiden kokonaisaika simulaatiossa.',"
                + "    as_avg_aika          DOUBLE UNSIGNED NOT NULL                     COMMENT 'Keskimääräinen oleskeluaika simulaatiossa.',"
                + "    pp_count             INT UNSIGNED NOT NULL                        COMMENT 'Palvelupisteiden määrä simulaatiossa.',"
                + "    pp_jonotus_aika      DOUBLE UNSIGNED NOT NULL                     COMMENT 'Keskiarvo jonotusajasta simulaatiossa.'"
                + " ) engine=InnoDB; ");
        if (statement.execute() == true) {
            return true;
        }
        return false;

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

        // Get values from SimulaationSuureet, create sql statement and execute.

        try {
            statement = connection.prepareStatement("INSERT INTO " + tableName
                    + " (kesto, palveluprosentti, as_count, as_lisatyt, as_palveltu, as_routed, as_poistunut, as_jono_aika, as_palvelu_aika, as_kok_aika, as_avg_aika, pp_count, pp_jonotus_aika ) VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )");

            statement.setDouble(1, suureet.getSimulointiAika()); // kesto
            statement.setDouble(2, suureet.getPalveluprosentti()); // palveluprosentti
            statement.setInt(3, suureet.getAsJonoon()); // as_count
            statement.setInt(4, suureet.getAsJonoon()); // as_lisatyt
            statement.setInt(5, suureet.getAsPalveltu()); // as_palveltu
            statement.setInt(6, suureet.getAsReRouted()); // as_routed
            statement.setInt(7, suureet.getAsPoistunut()); // as_poistunut
            statement.setDouble(8, suureet.getAvgJonotusAika()); // as_jono_aika
            statement.setDouble(9, suureet.getAvgPPOleskeluAika()); // as_palvelu_aika
            statement.setDouble(10, suureet.getAsTotalAikaPP()); // as_kok_aika
            statement.setDouble(11, suureet.getAvgAsAika()); // as_avg_aika
            statement.setInt(12, suureet.getPPTotalMaara()); // pp_count
            statement.setDouble(13, suureet.getAvgJonotusAika()); // pp_jonotus_astatement set

            // Return true if INSERT successful;

            if (statement.executeUpdate() >= 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Returns true if row with given id is deleted successfully. Otherwise returns
     * false.
     * 
     * @param id
     * @return boolean
     * @Author Henri
     */
    @Override
    public boolean removeTulos(int id) {
        // Delete given id.

        try {
            statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ( ? )");
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
     * Sets the retrieved data to the singleton class SimulaationSuureet.
     * Returns true if row found and retrieved. Otherwise returns false.
     * 
     * @param id
     * @return boolean
     * @Author Henri
     */
    public boolean queryTulos(int id) {

        // TODO: lisää haku tietokannasta kun tiedetään mitä haetaan.

        ss = SimulaationSuureet.getInstance();
        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ( ? )");
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
     * 
     * @return boolean
     * @Author Henri
     */
    public boolean dropTable() {
        try {
            statement = connection.prepareStatement("DROP TABLE " + tableName);
            statement.execute();
            System.out.println("Table dropped.");
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
        int result;
        try{
            statement = connection.prepareStatement("Select id from " + tableName);
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
