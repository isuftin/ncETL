package gov.usgs.cida.ncetl.utils;

import com.google.common.collect.Maps;
import gov.usgs.webservices.jdbc.util.SqlUtils;
import java.io.InputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.naming.NamingException;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jwalker
 */
public final class DatabaseUtil {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseUtil.class);
    private static final int TABLE_OR_VIEW_ALREADY_EXISTS = 20000;
    private static final String DB_NAME = "NCETL";
    private static final String DB_LOCATION = FileHelper.getDatabaseDirectory() + DB_NAME;
    private static final String DB_CLASS_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_URL = "jdbc:derby:" + DB_LOCATION;
    private static final String DB_STARTUP = DB_URL + ";create=true;";
    private static final String DB_SHUTDOWN = DB_URL + ";shutdown=true";
    private static final String JNDI_CONTEXT = "java:comp/env/jdbc/" + DB_NAME;
    private static final Map<String, String> CREATE_MAP;
    private static List<String> createTablesDDL = new ArrayList<String>();
    private static List<String> populateTablesDML = new ArrayList<String>();

    private DatabaseUtil() {}

    static {
        // TODO Switch to using ddl at some point
        CREATE_MAP = Maps.newLinkedHashMap();
        
        // Lookup Tables
        CREATE_MAP.put("COLLECTION_TYPE", 
                       "CREATE TABLE collection_type (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id))");
        CREATE_MAP.put("DATA_TYPE", 
                       "CREATE TABLE data_type (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id))");
        CREATE_MAP.put("DATA_FORMAT", 
                       "CREATE TABLE data_format (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id))");
        CREATE_MAP.put("DOCUMENTATION_TYPE", 
                       "CREATE TABLE documentation_type (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id))");
        CREATE_MAP.put("SERVICE_TYPE", 
                       "CREATE TABLE service_type (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id))");
        CREATE_MAP.put("CONTROLLED_VOCABULARY", 
                       "CREATE TABLE controlled_vocabulary (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), vocab varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id))");
        
        CREATE_MAP.put("GLOBAL_CONFIG",
                      "CREATE TABLE global_config (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), base_dir varchar(512), thredds_dir varchar(512), PRIMARY KEY (id))");
        CREATE_MAP.put("CATALOG", 
                       "CREATE TABLE catalog (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), location varchar(512), name varchar(64), expires date, version varchar(8), inserted boolean, updated boolean, PRIMARY KEY (id))"); //TODO- service, property and dataset can be subtables
        CREATE_MAP.put("INGEST",
                      "CREATE TABLE ingest (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), catalog_id INT CONSTRAINT CATALOG2_FK REFERENCES catalog, name varchar(128), ftpLocation varchar(512), rescanEvery bigint, fileRegex varchar(64), successDate date, successTime time, username varchar(64), password varchar(64), active boolean, inserted boolean, updated boolean, PRIMARY KEY (id))");
        CREATE_MAP.put("DATASET", 
                       "CREATE TABLE dataset (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), catalog_id INT CONSTRAINT CATALOG3_FK REFERENCES catalog, collection_type_id INT CONSTRAINT COLLECTION1_FK REFERENCES collection_type, data_type_id INT CONSTRAINT DATATYPE_FK REFERENCES data_type, name varchar(64), ncid varchar(128), authority varchar(64), inserted boolean, updated boolean, PRIMARY KEY (id))");
        CREATE_MAP.put("SERVICE", 
                       "CREATE TABLE service (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), catalog_id INT CONSTRAINT CATALOG4_FK REFERENCES catalog, service_id INT CONSTRAINT SERVICE1_FK REFERENCES service,  service_type_id INT CONSTRAINT SERVICETYPE_FK REFERENCES service_type, name varchar(64), base varchar(32),  description varchar(512), suffix varchar(32), inserted boolean, updated boolean, PRIMARY KEY (id))");
        CREATE_MAP.put("ACCESS", 
                       "CREATE TABLE access (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dataset_id INT CONSTRAINT DATASET1_FK REFERENCES dataset, service_id INT CONSTRAINT SERVICE2_FK REFERENCES service, dataformat_id INT CONSTRAINT DATAFORMAT_FK REFERENCES data_format, url_path varchar(512), inserted boolean, updated boolean, PRIMARY KEY (id))"); //TODO - Can have data_size
        
        CREATE_MAP.put("DOCUMENTATION", 
                       "CREATE TABLE documentation (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dataset_id INT CONSTRAINT DATASET2_FK REFERENCES dataset, documentation_type_id INT CONSTRAINT DOCTYPE_FK REFERENCES documentation_type, xlink_href varchar(256), xlink_title varchar(256), text varchar(1024), inserted boolean, updated boolean, PRIMARY KEY (id))");
        CREATE_MAP.put("PROPERTY", 
                       "CREATE TABLE property (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dataset_id INT CONSTRAINT DATASET3_FK REFERENCES dataset, name varchar(128), value varchar(512), inserted boolean, updated boolean, PRIMARY KEY (id))");
        CREATE_MAP.put("KEYWORD", 
                       "CREATE TABLE keyword (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), value varchar(64), controlled_vocabulary_id INT CONSTRAINT VOCAB1_FK REFERENCES controlled_vocabulary, inserted boolean, updated boolean, PRIMARY KEY (id))");
        CREATE_MAP.put("CONTRIBUTOR", 
                       "CREATE TABLE contributor (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), role varchar(64), text varchar(256), inserted boolean, updated boolean, PRIMARY KEY (id))");
        CREATE_MAP.put("CREATOR", 
                       "CREATE TABLE creator (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(256), controlled_vocabulary_id INT CONSTRAINT VOCAB2_FK REFERENCES controlled_vocabulary, contact_url varchar(512), contact_email varchar(256), inserted boolean, updated boolean, PRIMARY KEY (id))");
        
        // Join Tables?
        // helps to manage many-to-one relationships
        CREATE_MAP.put("KEYWORD_JOIN", 
                       "CREATE TABLE keyword_join (dataset_id INT CONSTRAINT DATASET4_FK REFERENCES dataset, keyword_id INT CONSTRAINT KEYWORD_FK REFERENCES keyword, inserted boolean, updated boolean)");
        CREATE_MAP.put("CONTRIBUTOR_JOIN", 
                       "CREATE TABLE contributor_join (dataset_id INT CONSTRAINT DATASET5_FK REFERENCES dataset, contributor_id INT CONSTRAINT CONTRIB_FK REFERENCES contributor, inserted boolean, updated boolean)");
        CREATE_MAP.put("CREATOR_JOIN", 
                       "CREATE TABLE creator_join (dataset_id INT CONSTRAINT DATASET6_FK REFERENCES dataset, creator_id INT CONSTRAINT CREATOR_FK REFERENCES creator, inserted boolean, updated boolean)");
        
        InputStream createTablesInputStream = null;
        InputStream populateTablesInputStream = null;
        // Read in populate table DDL from file
        try {
            createTablesInputStream = DatabaseUtil.class.getClassLoader().getResourceAsStream("gov/usgs/cida/ddl/create_tables.ddl");
            if (createTablesInputStream != null) {
                createTablesDDL = readDDL(createTablesInputStream);
            } else {
                List<String> defaultDDL = new ArrayList<String>();
                defaultDDL.add("Some Insert Statements Here");
                createTablesDDL = defaultDDL;
            }

            // Read in populate table DDL from file
            populateTablesInputStream = DatabaseUtil.class.getClassLoader().getResourceAsStream("gov/usgs/cida/ddl/populate_tables.dml");
            if (populateTablesInputStream != null) {
                populateTablesDML = readDDL(populateTablesInputStream);
            } else {
                List<String> defaultDDL = new ArrayList<String>();
                defaultDDL.add("Some Insert Statements Here");
                populateTablesDML = defaultDDL;
            }
        } finally {
            IOUtils.closeQuietly(populateTablesInputStream);
            IOUtils.closeQuietly(createTablesInputStream);
        }
    }

    public static void setupDatabase() throws SQLException, NamingException, ClassNotFoundException {
        setupDatabase(DB_STARTUP, DB_CLASS_NAME);
    }

    public static void setupDatabase(final String dbConnection, final String dbClassName) throws SQLException, NamingException, ClassNotFoundException {
        LOG.info("*************** Initializing database.");

        System.setProperty("dbuser", "");
        System.setProperty("dbpass", "");
        System.setProperty("dburl", dbConnection);
        System.setProperty("dbclass", dbClassName);

        Connection myConn = null;
        // Create the tables
        try {
            myConn = getConnection();
            if (myConn != null) {
                myConn.setAutoCommit(false);
                for (String createTableDDL : createTablesDDL) {
                    try {
                        myConn.createStatement().execute(createTableDDL);
                    } catch (SQLException sqe) {
                        if (sqe.getErrorCode() == TABLE_OR_VIEW_ALREADY_EXISTS) {
                            LOG.info(sqe.getMessage() + " -- Skipping");
                        } else {
                            myConn.rollback();
                            throw sqe;
                        }
                    }
                }
                myConn.commit();
                
                // Populate the tables
//                writeDML(populateTablesDML, myConn);
            }
            
            
            
            LOG.info("*************** Database has been initialized.");
        }
        finally {
            SqlUtils.closeConnection(myConn);
        }
        
        
    }

    public static List<String> readDDL(InputStream input) {
        List<String> result = new ArrayList<String>();
        Scanner scanner = new Scanner(input);
        scanner.useDelimiter(";");
        
        while (scanner.hasNext()) {
            String ddlStatement = scanner.next().replaceAll("\n", " ").trim();
            if (StringUtils.isNotBlank(ddlStatement)) {
                result.add(ddlStatement);
            }
        }
        scanner.close();
        return result;
    }
    
    public static void writeDML(List<String> ddlStatements, Connection connection) throws ClassNotFoundException, NamingException, SQLException {
        Statement stmt = null;
        connection.setAutoCommit(false);
        try {
            if (connection.getMetaData().supportsBatchUpdates()) {
                stmt = connection.createStatement();
                for (String ddlStatement : ddlStatements) {
                    stmt.addBatch(ddlStatement);
                }
                stmt.executeBatch();
            } else {
                for (String ddlStatement : ddlStatements) {
                    connection.createStatement().execute(ddlStatement);
                }
            }
            connection.commit();
        } catch (SQLException sqlException) {
            LOG.error("An error occurred while attempting to write  to the database. Will attempt to rollback changes.", sqlException);
            try {
                if (connection != null && connection.isValid(0)) {
                    connection.rollback();
                    LOG.info("Rolling back changes successful");
                }
            } catch (SQLException ex) { LOG.error("An error occurred while trying to roll back changes.", ex); }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void shutdownDatabase() throws SQLException, NamingException,
                                                 ClassNotFoundException {
        shutdownDatabase(DB_SHUTDOWN);
    }
    
    public static boolean shutdownDatabase(String shutdown) throws SQLException, NamingException,
                                                 ClassNotFoundException {
        System.setProperty("dburl", shutdown);
        Connection myConn = null;
        try {
            myConn = SqlUtils.getConnection(JNDI_CONTEXT);
        }
        catch (SQLNonTransientConnectionException ex) {
            // Derby throws this if database succeeds in shutting down
            return true;
        } finally {
            SqlUtils.closeConnection(myConn);
        }
        return false;
    }

    protected static Connection getConnection() throws SQLException,
                                                       NamingException,
                                                       ClassNotFoundException {
        return getConnection(JNDI_CONTEXT);
    }

    protected static Connection getConnection(String jndiContext) throws
            SQLException, NamingException, ClassNotFoundException {
        return SqlUtils.getConnection(jndiContext);
    }
    
    /**
     * Convenience method to do proper house-keeping of connections
     * @param connection close this
     */
    public static void closeConnection(Connection connection) {
        SqlUtils.closeConnection(connection);
    }

    private static void createTable(Connection c, String table) throws
            SQLException,
                                                                       NamingException,
                                                                       ClassNotFoundException {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            LOG.debug("Creating table: " + table + " ...");
            stmt.execute(CREATE_MAP.get(table));
            LOG.debug("...done");
        }
        finally {
            stmt.close();
        }
    }

    public static Map<String, String> getCatalogInfo(URI location) throws SQLException, NamingException, ClassNotFoundException {
        Connection connection = null;
        Map<String, String> rowMap = new HashMap<String, String>();
        ResultSet rs = null;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM catalog WHERE location = ?");
            stmt.setString(1, location.toString());
            rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = rs.getString(i);
                    rowMap.put(columnName, value);
                }
            }
            
        }
        finally {
            try {if (rs != null) rs.close();} catch (Exception e) { /* ignore */ };
            SqlUtils.closeConnection(connection);
        }
        return rowMap;
    }

    public static List<Map<String, String>> getDatasetInfos(String id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
