package gov.usgs.cida.ncetl.utils;

import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.io.File;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class DatabaseUtilTest {
    String DB_LOCATION = FileHelper.getTempDirectory() +  "test_delete_me" + File.separator;
    String createDb = null;
    String destroyDb = null;
    
    Connection connection;
    public DatabaseUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() throws IOException {
        FileUtils.forceMkdir(new File(DB_LOCATION));
    }
    
    @After
    public void tearDown() throws IOException, SQLException, NamingException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            DatabaseUtil.closeConnection(connection);
        }
        if (destroyDb != null) {
            DatabaseUtil.shutdownDatabase(destroyDb);
        }
        
        FileUtils.forceDelete(new File(DB_LOCATION));
    }

    @Test
    public void testSetupDatabaseConnection() throws SQLException, NamingException, ClassNotFoundException {
        String DB_FULL_LOCATION = DB_LOCATION + "test1.db";
        createDb = "jdbc:derby:" + DB_FULL_LOCATION + ";create=true;";
        destroyDb = "jdbc:derby:" + DB_FULL_LOCATION + ";shutdown=true;";
        DatabaseUtil.setupDatabase(createDb, "org.apache.derby.jdbc.EmbeddedDriver");
        connection = DatabaseUtil.getConnection("java:comp/env/jdbc/test1.db");
        assertThat(connection.isClosed(), is(false));
    }
    
    
    @Test
    public void testReadDDL() {
        ByteArrayInputStream bais = new ByteArrayInputStream("test one;test two;test three;".getBytes());
        List<String> result = DatabaseUtil.readDDL(bais);
        assertThat(result.size(), is(equalTo(3)));
    }
//    @Test
//    public void testSetupDatabaseConnectionWithIncorrectJNDIContext() throws SQLException, NamingException, ClassNotFoundException {
//        String DB_FULL_LOCATION = DB_LOCATION + "test2.db";
//        createDb = "jdbc:derby:" + DB_FULL_LOCATION + ";create=true;";
//        destroyDb = "jdbc:derby:" + DB_FULL_LOCATION + ";shutdown=true;";
//        DatabaseUtil.setupDatabase(createDb, "org.apache.derby.jdbc.EmbeddedDriver");
//        connection = DatabaseUtil.getConnection("java:comp/env/jdbc/wrong.db");
//        assertThat(connection.isClosed(), is(false));
//    }
//    
//    @Test
//    public void testSetupDatabaseConnectionWithNoContext() throws SQLException, NamingException, ClassNotFoundException {
//        String DB_FULL_LOCATION = DB_LOCATION + "derp.db";
//        createDb = "jdbc:derby:" + DB_FULL_LOCATION + ";create=true;";
//        destroyDb = "jdbc:derby:" + DB_FULL_LOCATION + ";shutdown=true;";
//        DatabaseUtil.setupDatabase(createDb, "org.apache.derby.jdbc.EmbeddedDriver");
//        connection = DatabaseUtil.getConnection();
//        assertThat(connection.isClosed(), is(false));
//    }
}
