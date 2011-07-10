package gov.usgs.cida.ncetl.spec;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.commons.io.FileUtils;
import java.sql.Connection;
import java.io.File;
import gov.usgs.cida.ncetl.utils.FileHelper;
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
public class IngestControlSpecTest {

    IngestControlSpec test = new IngestControlSpec();
    String DB_LOCATION = FileHelper.getTempDirectory() +  "test_delete_me" + File.separator;
    Connection connection;
    String DB_FULL_LOCATION = DB_LOCATION + "test.db";
        
    public IngestControlSpecTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws IOException, SQLException, NamingException, ClassNotFoundException {
        test = new IngestControlSpec();
        FileUtils.forceMkdir(new File(DB_LOCATION));
    }

    @After
    public void tearDown() throws SQLException, IOException {
        test = null;
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        FileUtils.forceDelete(new File(DB_LOCATION));
    }

    @Test
    public void testCreate() {
        assertThat(test, is(notNullValue()));
    }

    @Test
    public void testAccessDelete() {
        assertThat(test.setupAccess_DELETE(), is(true));
    }

    @Test
    public void testAccessInsert() {
        assertThat(test.setupAccess_INSERT(), is(true));
    }

    @Test
    public void testAccessRead() {
        assertThat(test.setupAccess_READ(), is(true));
    }

    @Test
    public void testAccessUpdate() {
        assertThat(test.setupAccess_UPDATE(), is(true));
    }
    
//    @Test
//    public void testGetUpdatedRows() throws SQLException, NamingException, ClassNotFoundException {
//         DatabaseUtil.setupDatabase("jdbc:derby:" + DB_FULL_LOCATION + ";create=true;", "org.apache.derby.jdbc.EmbeddedDriver");
//        connection = DatabaseUtil.getConnection("java:comp/env/jdbc/test.db");
//        ResultSet rsTest = test.getUpdatedRows(connection);
//        assertThat(rsTest, is(notNullValue()));
//    }
}
