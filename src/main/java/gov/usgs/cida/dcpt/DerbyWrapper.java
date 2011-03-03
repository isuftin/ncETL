package gov.usgs.cida.dcpt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author jwalker
 */
public class DerbyWrapper {
	public static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	public static Logger log =  Logger.getLogger(DerbyWrapper.class.getName());

	static {
		try {
			Class.forName(driver).newInstance();
		}
		catch (InstantiationException ex) {
			log.error("Could not instantiate Derby");
		}
		catch (IllegalAccessException ex) {
			log.error("Illegal access of Derby Database");
		}
		catch (ClassNotFoundException ex) {
			log.error("Derby Embedded Driver not found");
		}
	}

	public static Connection getConnection() throws SQLException {
		String protocol = "jdbc:derby:";
		Properties props = new Properties();
		return DriverManager.getConnection(protocol + "derbyDB;create=true", props);
	}

	public static void shutdown() {
		
	}

}
