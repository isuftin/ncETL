package gov.usgs.cida.ncetl.utils;

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
    private static List<String> createTablesDDL = new ArrayList<String>();
    private static List<String> populateTablesDML = new ArrayList<String>();

    private DatabaseUtil() {}

    static {
        InputStream createTablesInputStream = null;
        InputStream populateTablesInputStream = null;
        // Read in populate table DDL from file
        try {
            createTablesInputStream = DatabaseUtil.class.getClassLoader().getResourceAsStream("gov/usgs/cida/ddl/create_tables.ddl");
            if (createTablesInputStream != null) {
                createTablesDDL = readDxL(createTablesInputStream);
            } else {
                List<String> defaultDDL = new ArrayList<String>();
                defaultDDL.add("Some Insert Statements Here");
                createTablesDDL = defaultDDL;
            }

            // Read in populate table DDL from file
            populateTablesInputStream = DatabaseUtil.class.getClassLoader().getResourceAsStream("gov/usgs/cida/ddl/populate_tables.dml");
            if (populateTablesInputStream != null) {
                populateTablesDML = readDxL(populateTablesInputStream);
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

    public static void setupDatabase(final String dbConnection, final String dbClassName)  throws SQLException, NamingException, ClassNotFoundException {
        LOG.info("*************** Initializing database.");

        System.setProperty("dbuser", "");
        System.setProperty("dbpass", "");
        System.setProperty("dburl", dbConnection);
        System.setProperty("dbclass", dbClassName);

        Connection myConn = null;
        // Create the tables
        try {
            myConn = getConnection();
            
            // Create the tables
            writeDDL(createTablesDDL, myConn);
                
            // Populate the tables
             writeDML(populateTablesDML, myConn);
            
            
            
            LOG.info("*************** Database has been initialized.");
        }
        finally {
            SqlUtils.closeConnection(myConn);
        }
    }

    /**
     * Reads DDL/DML from an InputStream and converts it to a List of type String
     * with each entry representing an atomic statement
     * 
     * @param input
     * @return 
     */
    public static List<String> readDxL(InputStream input) {
        List<String> result = new ArrayList<String>();
        Scanner scanner = new Scanner(input);
        scanner.useDelimiter(";");
        
        while (scanner.hasNext()) {
            String ddlStatement = scanner.next().replaceAll("\n", " ").trim();
            if (StringUtils.isNotBlank(ddlStatement) && !ddlStatement.startsWith("--")) {
                result.add(ddlStatement);
            }
        }
        scanner.close();
        return result;
    }
    
    public static void writeDDL(List<String> ddlStatements, Connection connection) throws SQLException {
            connection.setAutoCommit(false);
            for (String createTableDDL : createTablesDDL) {
                try {
                    connection.createStatement().execute(createTableDDL);
                } catch (SQLException sqe) {
                    if (sqe.getErrorCode() == TABLE_OR_VIEW_ALREADY_EXISTS) {
                        LOG.info(sqe.getMessage() + " -- Skipping");
                    } else {
                        connection.rollback();
                        throw sqe;
                    }
                }
            }
            connection.commit();
    }
    
    /**
     * Writes DML to lookup tables.
     * 
     * @param ddlStatements
     * @param connection
     * @throws SQLException 
     */
    public static void writeDML(List<String> ddlStatements, Connection connection) throws SQLException {
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
                    throw sqlException;
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
            try {if (rs != null) {rs.close();}} catch (Exception e) { /* ignore */ };
            SqlUtils.closeConnection(connection);
        }
        return rowMap;
    }

    public static List<Map<String, String>> getDatasetInfos(String id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
