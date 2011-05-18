package gov.usgs.cida.dcpt;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author jwalker
 */
public class DerbyHelper {

	public static final String SCHEMA = "NCETL";
	private static final String dbClassName = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String dbConnection = "jdbc:derby:" + SCHEMA + ";create=true;";
	private static Connection conn = null;
	//private static List<String> tablenames = Lists.newArrayList("test", "ingests");
    private static Map<String, String> createMap;
    
    static
    {
        createMap = Maps.newHashMap();
        createMap.put("TEST", "CREATE TABLE test (kkey varchar(32), vvalue varchar(32))");
        createMap.put("INGESTS", "CREATE TABLE ingests (ftpLocation varchar(128), rescanEvery bigint, fileRegex varchar(64), successDate date, successTime time, username varchar(64), password varchar(64), active boolean)");
    }

	public static Connection getConnection() {
		try {
			if (conn == null) {
				conn = getDSConnection("java:comp/env/jdbc/" + SCHEMA);
				if (conn == null) {
					Class.forName(dbClassName).newInstance();
					conn = DriverManager.getConnection(dbConnection);
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			return conn;
		}
	}

	public static void setupDatabase() {
		try {
			Connection myConn = getConnection();
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
		catch (SQLException sqle) {
            sqle.printStackTrace();
		}
	}

	public static Connection getDSConnection(String jndiName) throws NamingException {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup(jndiName);
		Connection connection = null;
		
		try {
			connection = ds.getConnection();
		}
		catch (SQLException sqle) {
			return null;
		}
		
//        connections++;
//        log.trace("Open connections: " + connections);
		return connection;
	}

    private static void createTable(String table) {
        try {
            Connection c = getConnection();
            Statement stmt = c.createStatement();
            stmt.execute(createMap.get(table));
            stmt.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
