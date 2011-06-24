/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.usgs.cida.ncetl.utils;

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
public class DerbyHelperTest {
    String DB_LOCATION = FileHelper.getTempDirectory() +  "test_delete_me" + File.separator;
    Connection connection;
    public DerbyHelperTest() {
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
    public void tearDown() throws IOException, SQLException {
        FileUtils.forceDelete(new File(DB_LOCATION));
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testSetupDatabaseConnection() throws SQLException, NamingException, ClassNotFoundException {
        String DB_FULL_LOCATION = DB_LOCATION + "test.db";
        DerbyHelper.setupDatabase("jdbc:derby:" + DB_FULL_LOCATION + ";create=true;", "org.apache.derby.jdbc.EmbeddedDriver");
        connection = DerbyHelper.getConnection("java:comp/env/jdbc/test.db");
        assertThat(connection.isClosed(), is(not(true)));
    }
    
    @Test
    public void testSetupDatabaseConnectionWithIncorrectJNDIContext() throws SQLException, NamingException, ClassNotFoundException {
        String DB_FULL_LOCATION = DB_LOCATION + "test.db";
        DerbyHelper.setupDatabase("jdbc:derby:" + DB_FULL_LOCATION + ";create=true;", "org.apache.derby.jdbc.EmbeddedDriver");
        connection = DerbyHelper.getConnection("java:comp/env/jdbc/wrong.db");
        assertThat(connection.isClosed(), is(not(true)));
    }
    
    @Test
    public void testSetupDatabaseConnectionWithNoContext() throws SQLException, NamingException, ClassNotFoundException {
        String DB_FULL_LOCATION = DB_LOCATION + "derp.db";
        DerbyHelper.setupDatabase("jdbc:derby:" + DB_FULL_LOCATION + ";create=true;", "org.apache.derby.jdbc.EmbeddedDriver");
        connection = DerbyHelper.getConnection();
        assertThat(connection.isClosed(), is(not(true)));
    }
}
