package com.project.database.DAO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.project.database.interfaces.ITuloksetDAO;
import com.project.simu.model.Tulokset;
import com.project.simu.model.UserAsetukset;
import com.project.simu.model.PalvelupisteTulos;
import com.project.simu.model.TallennettavatParametrit;

public class TuloksetDAO implements ITuloksetDAO {
    private Connection connection;
    private PreparedStatement statement;
    private String dbName;
    private String tableName1;
    private String tableName2;
    private String tableName3;
    private String user;
    private String password;

    public TuloksetDAO(UserAsetukset ua, boolean simulaatio) {
        
        dbName = ua.getDbName();
        user = ua.getUsername();
        password = ua.getPassword();
        if (simulaatio){
            tableName1 = "asiakkaat";
            tableName2 = "palvelupisteet";
            tableName3 = "asetukset";
        }
        else{
            tableName1 = "testiasiakkaat";
            tableName2 = "testipalvelupisteet";
            tableName3 = "testiasetukset";
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
            
            // Create table 1 if not found.

            ResultSet result = dbm.getTables(null, null, tableName1, null);
            
            if (!result.next()) {
                System.out.println("Table one " + tableName1 + " not found. Creating table...");
                createTables(1);
                System.out.println("Table created.");
            }

            // Create table 2 if not found.
            
            result = dbm.getTables(null, null, tableName2, null);
            
            if (!result.next()) {
                System.out.println("Table two " + tableName2 + " not found. Creating table...");
                createTables(2);
                System.out.println("Table created.");
            }
            
            // Create table 3 if not found.

            result = dbm.getTables(null, null, tableName3, null);
            
            if (!result.next()) {
                System.out.println("Table three " + tableName3 + " not found. Creating table...");
                createTables(3);
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
            if ( !statement.execute() ) {
                return false;
            }
        }
        else if (table == 2){
            statement = connection.prepareStatement("CREATE TABLE "+ tableName2 +" ( "
                    + "id                   INT    NOT NULL                                     COMMENT 'Id, primary key.',"
                    + "simulaatiokerta      INT    NOT NULL                                     COMMENT 'Linkitys asiakkaiden pöytään.',"
                    + "tyyppi               INT    UNSIGNED NOT NULL                            COMMENT 'Palvelupistetyyppi.',"
                    + "palvellut_as         INT    UNSIGNED NOT NULL                            COMMENT 'Kuinka monta asiakasta palveltu.',"
                    + "keskipalveluaika     DOUBLE UNSIGNED NOT NULL                            COMMENT 'Keskipalveluaika palvelupisteelle.',"
                    + "keskijonotusaika     DOUBLE UNSIGNED NOT NULL                            COMMENT 'Keskijonotusaika palvelupisteelle.' ,"
                    + "palveluprosentti     DOUBLE UNSIGNED NOT NULL                            COMMENT 'Palveluprosentti palvelupisteelle.' ,"
                    + "FOREIGN KEY (simulaatiokerta) REFERENCES "+ tableName1 +" (simulaatiokerta) ON DELETE CASCADE ON UPDATE RESTRICT"
                    + " ) engine=InnoDB;");
            if ( !statement.execute() ) {
                return false;
            }
        }
        else if (table == 3){
            statement = connection.prepareStatement("CREATE TABLE "+ tableName3 +" ( "
                + "simulaatiokerta      INT         ,"
                + "yksmyyntipisteita    INT         ,"
                + "yksnettipisteita     INT         ,"
                + "yksliittymapisteita  INT         ,"
                + "ykslaskutuspisteita  INT         ,"
                + "yrimyyntipisteita    INT         ,"
                + "yrinettipisteita     INT         ,"
                + "yriliittymapisteita  INT         ,"
                + "yrilaskutuspisteita  INT         ,"
                + "yksmyyntiaika        DOUBLE      ,"
                + "yksnettiaika         DOUBLE      ,"
                + "yksliittymaaika      DOUBLE      ,"
                + "ykslaskutusaika      DOUBLE      ,"
                + "yrimyyntiaika        DOUBLE      ,"
                + "yrinettiaika         DOUBLE      ,"
                + "yriliittymaaika      DOUBLE      ,"
                + "yrilaskutusaika      DOUBLE      ,"
                + "yksmyyntijakauma     DOUBLE      ,"
                + "yksnettijakauma      DOUBLE      ,"
                + "yksliittymajakauma   DOUBLE      ,"
                + "ykslaskutusjakauma   DOUBLE      ,"
                + "yrimyyntijakauma     DOUBLE      ,"
                + "yrinettijakauma      DOUBLE      ,"
                + "yriliittymajakauma   DOUBLE      ,"
                + "yrilaskutusjakauma   DOUBLE      ,"
                + "simuloinninaika      DOUBLE      ,"
                + "yksyrijakauma        DOUBLE      ,"
                + "karsimaatomyysaika   DOUBLE      ,"
                + "vaaravalintaprosentti DOUBLE     ,"
                + "asikasmaaratunti     DOUBLE      ,"
                + "FOREIGN KEY (simulaatiokerta) REFERENCES "+ tableName1 +" (simulaatiokerta) ON DELETE CASCADE ON UPDATE RESTRICT"
                + ") engine=InnoDB;");
            if ( !statement.execute() ) {
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
     * @param Tulokset
     * @return boolean
     * @author Henri
     */
    @Override
    public boolean addTulos(Tulokset data) {

        // Get values from SimulaationData, create sql statement and execute.

        try {
            statement = connection.prepareStatement("INSERT INTO " + tableName1
                    + "( kesto, palveluprosentti, as_maara, as_palveltu, as_routed, as_poistunut, as_keskijonoaika, as_keskilapimeno ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )");

            statement.setDouble(1, data.getKesto());                    // kesto
            statement.setDouble(2, data.getPalveluProsentti());         // palveluprosentti
            statement.setInt(3, data.getAsMaara());                     // as_maara
            statement.setInt(4, data.getPalvellutAsiakkaat());          // as_palveltu
            statement.setInt(5, data.getUudelleenOhjatutAsiakkaat());   // as_routed
            statement.setInt(6, data.getPoistuneetAsiakkaat());         // as_poistunut
            statement.setDouble(7, data.getKeskiJonotusAika());         // as_keskijonoaika
            statement.setDouble(8, data.getKeskiLapiMenoAika());        // as_keskiläpimeno
            statement.executeUpdate();

            if (!addPalvelupisteTulos(data.getPalveluPisteTulokset()))
                return false;
       
            return addAsetusTulos(data.getTallennettavatParametrit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return false;
    }

    /**
     * Returns true if palvelupistetulos added successfully. Otherwise returns false.
     * 
     * @param PalvelupisteTulos
     * @return boolean
     * @throws SQLException
     * @Author Henri
     */
    private boolean addPalvelupisteTulos(ArrayList<PalvelupisteTulos> ppTulos) throws SQLException{
            for (PalvelupisteTulos PalvelupisteTulos : ppTulos) {
            
                statement = connection.prepareStatement("INSERT INTO " + tableName2
                + " ( id, simulaatiokerta, tyyppi, palvellut_as, keskipalveluaika, keskijonotusaika, palveluprosentti ) VALUES ( ?, ?, ?, ?, ?, ?, ? )");
                statement.setInt(1, PalvelupisteTulos.getId());                  // id
                statement.setInt(2, PalvelupisteTulos.getSimulaatiokerta());     // Sim kerta
                statement.setInt(3, PalvelupisteTulos.getTyyppi());              // tyyppi
                statement.setInt(4, PalvelupisteTulos.getPalvellutAsiakkaat());  // palvellut_as
                statement.setDouble(5, PalvelupisteTulos.getKeskiPalveluAika()); // keskipalveluaika
                statement.setDouble(6, PalvelupisteTulos.getKeskiJonotusAika()); // keskijonotusaika
                statement.setDouble(7, PalvelupisteTulos.getPalveluProsentti()); // keskijonotusaika

                statement.executeUpdate();
            }
            return true;
    }
    private boolean addAsetusTulos(TallennettavatParametrit parametrit) throws SQLException{

                statement = connection.prepareStatement("INSERT INTO " + tableName3
                + " ( simulaatiokerta, yksmyyntipisteita, yksnettipisteita, yksliittymapisteita, ykslaskutuspisteita, yrimyyntipisteita, yrinettipisteita, yriliittymapisteita, yrilaskutuspisteita"
                + ", yksmyyntiaika, yksnettiaika, yksliittymaaika, ykslaskutusaika, yrimyyntiaika, yrinettiaika, yriliittymaaika, yrilaskutusaika, yksmyyntijakauma, yksnettijakauma, yksliittymajakauma"
                + ", ykslaskutusjakauma, yrimyyntijakauma, yrinettijakauma, yriliittymajakauma, yrilaskutusjakauma, simuloinninaika, yksyrijakauma, karsimaatomyysaika, vaaravalintaprosentti"
                + ", asikasmaaratunti) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );");
                statement.setInt(1, parametrit.getSimulointiKerta());
                statement.setInt(2, parametrit.getYksMyyntiPisteita());
                statement.setInt(3, parametrit.getYksNettiPisteita());
                statement.setInt(4, parametrit.getYksLiittymaPisteita());
                statement.setInt(5, parametrit.getYksLaskutusPisteita());
                statement.setInt(6, parametrit.getYriMyyntiPisteita());
                statement.setInt(7, parametrit.getYriNettiPisteita());
                statement.setInt(8, parametrit.getYriLiittymaPisteita());
                statement.setInt(9, parametrit.getYriLaskutusPisteita());
                statement.setDouble(10, parametrit.getYksMyyntiAika());
                statement.setDouble(11, parametrit.getYksNettiAika());
                statement.setDouble(12, parametrit.getYksLiittymaAika());
                statement.setDouble(13, parametrit.getYksLaskutusAika());
                statement.setDouble(14, parametrit.getYriMyyntiAika());
                statement.setDouble(15, parametrit.getYriNettiAika());
                statement.setDouble(16, parametrit.getYriLiittymaAika());
                statement.setDouble(17, parametrit.getYriLaskutusAika());
                statement.setDouble(18, parametrit.getYksMyyntiJakauma());
                statement.setDouble(19, parametrit.getYksNettiJakauma());
                statement.setDouble(20, parametrit.getYksLiittymaJakauma());
                statement.setDouble(21, parametrit.getYksLaskutusJakauma());
                statement.setDouble(22, parametrit.getYriMyyntiJakauma());
                statement.setDouble(23, parametrit.getYriNettiJakauma());
                statement.setDouble(24, parametrit.getYriLiittymaJakauma());
                statement.setDouble(25, parametrit.getYriLaskutusJakauma());
                statement.setDouble(26, parametrit.getSimuloinninAika());
                statement.setDouble(27, parametrit.getYksYriJakauma());
                statement.setDouble(28, parametrit.getKarsimaatomyysAika());
                statement.setDouble(29, parametrit.getVaaraValintaProsentti());
                statement.setDouble(30, parametrit.getAsikasmaaraTunti());
                
                statement.executeUpdate();
        
            return true;
    }

    /**
     * Returns true if row with given id is deleted successfully. Otherwise returns false.
     * 
     * @param simulaatiokerta
     * @return boolean
     * @Author Henri
     */
    @Override
    public boolean removeTulos(int simulaatiokerta) {
        // Delete given id.

        try {
            statement = connection.prepareStatement("DELETE FROM " + tableName1 + " WHERE simulaatiokerta = ( ? )");
            statement.setInt(1, simulaatiokerta);
            
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
     * Retrieves and packs a Tulokset object with given id.
     * Returns Tulokset object if successful. Otherwise returns null.
     * 
     * @param id
     * @return Tulokset
     * @throws SQLException
     * @Author Henri
     */
    public Tulokset queryTulos(int id) throws SQLException {

        Tulokset tulos = null;
        TallennettavatParametrit asetukset = null;
        ArrayList<PalvelupisteTulos> pptulosList = new ArrayList<>();

        // Prepare statement to select pptulokset.

        statement = connection.prepareStatement("SELECT * FROM " + tableName2 + " WHERE simulaatiokerta = ( ? )");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        // Pack pptulokset into array from db.

        while( results.next() ){
            PalvelupisteTulos ppTulos = new PalvelupisteTulos(
                results.getInt(1),
                results.getInt(2),
                results.getInt(3),
                results.getInt(4),
                results.getDouble(6), //TODO: FIX!
                results.getDouble(5), 
                results.getDouble(7) 
                );
                
                pptulosList.add(ppTulos);
            }
            

            // Prepare statement to get simulaatio asetukset from db.

            statement = connection.prepareStatement("SELECT * FROM " + tableName3 + " WHERE simulaatiokerta = ( ? )");
            statement.setInt(1, id);
            results = statement.executeQuery();
            if (results.next()) {
            asetukset = new TallennettavatParametrit(
                results.getInt(1),
                results.getInt(2),
                results.getInt(3),
                results.getInt(4),
                results.getInt(5),
                results.getInt(6),
                results.getInt(7),
                results.getInt(8),
                results.getInt(9),
                results.getDouble(10),
                results.getDouble(11),
                results.getDouble(12),
                results.getDouble(13),
                results.getDouble(14),
                results.getDouble(15),
                results.getDouble(16),
                results.getDouble(17),
                results.getDouble(18),
                results.getDouble(19),
                results.getDouble(20),
                results.getDouble(21),
                results.getDouble(22),
                results.getDouble(23),
                results.getDouble(24),
                results.getDouble(25),
                results.getDouble(26),
                results.getDouble(27),
                results.getDouble(28),
                results.getDouble(29),
                results.getDouble(30)
            );}
            // Prepare statement to get asiakastiedot from db.
            
            
            statement = connection.prepareStatement("SELECT * FROM " + tableName1 + " WHERE simulaatiokerta = ( ? )");
            statement.setInt(1, id);
            results = statement.executeQuery();
            
            // Pack all together for return.
            
            if (results.next()) {
            tulos = new Tulokset(
                results.getInt(1),
            results.getDouble(2),
            results.getDouble(3),
            results.getInt(4),
            results.getInt(5),
            results.getInt(6),
            results.getInt(7),
            results.getDouble(8),
            results.getDouble(9), 
            pptulosList,
            asetukset);
        }
        
        return tulos;
    }

    public ArrayList<Tulokset> queryTulokset() throws SQLException{

        ArrayList<Tulokset> tulosList = new ArrayList<>();
        statement = connection.prepareStatement("SELECT * FROM " + tableName1);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Tulokset tulos = new Tulokset(
            results.getInt(1),
            results.getDouble(2),
            results.getDouble(3),
            results.getInt(4),
            results.getInt(5),
            results.getInt(6),
            results.getInt(7),
            results.getDouble(8),
            results.getDouble(9)
            );
            tulosList.add(tulos);
        }
        return tulosList;
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
            statement = connection.prepareStatement("DROP TABLE IF EXISTS " + tableName3 + ", " + tableName2 +", " + tableName1 );
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
    public int getRowIndex(){
        int result;
        try{
            statement = connection.prepareStatement("Select simulaatiokerta from " + tableName1);
            ResultSet rs = statement.executeQuery();
            if (!rs.last())
                return 0;
                
            result = rs.getInt(1);

            return result;

        } catch (SQLException e) {
            System.out.println("Row index error.");
            e.printStackTrace();
        }
        return 0;
    }
    public int getRowCount(){
        int result;
        try{
            statement = connection.prepareStatement("SELECT COUNT(*) FROM "+ tableName1);
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                return 0;
            result = rs.getInt(1);
            return result;
        } catch (SQLException e) {
            System.out.println("Row count error.");
            e.printStackTrace();
        }
        return 0;
    }
}
