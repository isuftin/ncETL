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

/**
 *
 * @author jwalker
 */
public class DerbyHelper {

    public static final String dbName = "/NCETL";
    public static final String SCHEMA = FileHelper.FILE_STORE + dbName;
    private static final String dbClassName = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String dbConnection = "jdbc:derby:" + SCHEMA + ";create=true;";
    private static final String context = "java:comp/env/jdbc" + dbName;
//    private static Connection conn = null;
    //private static List<String> tablenames = Lists.newArrayList("test", "ingests");
    private static Map<String, String> createMap;

    static {
        // Switch to using ddl at some point
        createMap = Maps.newHashMap();
        createMap.put("CONFIG",
                      "CREATE TABLE config (base_dir varchar(512), thredds_dir varchar(512))");
        createMap.put("INGESTS",
                      "CREATE TABLE ingests (name varchar(128), ftpLocation varchar(512), rescanEvery bigint, fileRegex varchar(64), successDate date, successTime time, username varchar(64), password varchar(64), active boolean, inserted boolean, updated boolean)");
    }

//    public static Connection getConnection() {
//        try {
//            if (conn == null) {
//                conn = getDSConnection("java:comp/env/jdbc" + dbName);
//                if (conn == null) {
//                    Class.forName(dbClassName).newInstance();
//                    conn = DriverManager.getConnection(dbConnection);
//                }
//            }
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        finally {
//            return conn;
//        }
//    }
    public static void setupDatabase() throws SQLException, NamingException,
                                              ClassNotFoundException {

        System.setProperty("dbuser", "");
        System.setProperty("dbpass", "");
        System.setProperty("dburl", dbConnection);
        System.setProperty("dbclass", dbClassName);
        Connection myConn = SqlUtils.getConnection(context);

        if (myConn != null) {
            DatabaseMetaData dbMeta = myConn.getMetaData();
            ResultSet schemas = dbMeta.getSchemas();
            while (schemas.next()) {
                System.out.println(schemas.getString(1));
            }
            for (String table : createMap.keySet()) {
                ResultSet rs = dbMeta.getTables(null, "APP", table, null);
                if (!rs.next()) {
                    createTable(table);
                }
            }
        }

    }

//    public static Connection getDSConnection(String jndiName) {
//        Context ctx = null;
//        Connection connection = null;
//        DataSource ds = null;
//        try {
//            ctx = new InitialContext();
//            ds = (DataSource) ctx.lookup(jndiName);
//            connection = ds.getConnection();
//        }
//        catch (Exception e) {
//            return null;
//        }
//
////        connections++;
////        log.trace("Open connections: " + connections);
//        return connection;
//    }
    private static void createTable(String table) throws SQLException,
                                                         NamingException,
                                                         ClassNotFoundException {
        Connection c = SqlUtils.getConnection(context);
        Statement stmt = c.createStatement();
        stmt.execute(createMap.get(table));
        stmt.close();
    }
}
