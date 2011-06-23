package gov.usgs.cida.ncetl.utils;

import com.google.common.collect.Maps;
import gov.usgs.webservices.jdbc.util.SqlUtils;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jwalker
 */
public final class DatabaseUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseUtil.class);
    private static final String DB_NAME = "NCETL";
    private static final String DB_LOCATION = FileHelper.getDatabaseDirectory() + DB_NAME;
    private static final String DB_CLASS_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_URL = "jdbc:derby:" + DB_LOCATION;
    private static final String DB_STARTUP = DB_URL + ";create=true;";
    private static final String DB_SHUTDOWN = DB_URL + ";shutdown=true";
    private static final String JNDI_CONTEXT = "java:comp/env/jdbc/" + DB_NAME;
    
    private final static Map<String, String> CREATE_MAP;
    
    private DatabaseUtil(){};

    static {
        // Switch to using ddl at some point
        CREATE_MAP = Maps.newHashMap();
        CREATE_MAP.put("CONFIG",
                      "CREATE TABLE config (base_dir varchar(512), thredds_dir varchar(512))");
        CREATE_MAP.put("INGESTS",
                      "CREATE TABLE ingests (name varchar(128), ftpLocation varchar(512), rescanEvery bigint, fileRegex varchar(64), successDate date, successTime time, username varchar(64), password varchar(64), active boolean, inserted boolean, updated boolean)");
    }

    public static void setupDatabase() throws SQLException, NamingException, ClassNotFoundException {
        LOG.info("*************** Initializing database.");
        
        System.setProperty("dbuser", "");
        System.setProperty("dbpass", "");
        System.setProperty("dburl", DB_STARTUP);
        System.setProperty("dbclass", DB_CLASS_NAME);
        
        Connection myConn = null;
        try {
            myConn = SqlUtils.getConnection(JNDI_CONTEXT);
            if (myConn != null) {
                DatabaseMetaData dbMeta = myConn.getMetaData();
                ResultSet rs = null;
                try {
                    for (String table : CREATE_MAP.keySet()) {
                        rs = dbMeta.getTables(null, "APP", table, null);
                        if (!rs.next()) {
                            createTable(myConn, table);
                        }
                    }
                }
                finally {
                    rs.close();
                }
            }
            LOG.info("*************** Database has been initialized.");
        }
        finally {
            SqlUtils.closeConnection(myConn);
        }

    }
    
    public static void shutdownDatabase() throws SQLException, NamingException, ClassNotFoundException {
        System.setProperty("dburl", DB_SHUTDOWN);
        Connection myConn = SqlUtils.getConnection(JNDI_CONTEXT);
    }

    private static void createTable(Connection c, String table) throws SQLException,
                                                         NamingException,
                                                         ClassNotFoundException {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            stmt.execute(CREATE_MAP.get(table));
        }
        finally {
            stmt.close();
        }
    }
}
