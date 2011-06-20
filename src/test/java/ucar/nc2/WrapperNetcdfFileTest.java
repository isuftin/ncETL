/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucar.nc2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author isuftin
 */
public class WrapperNetcdfFileTest {
    private static final String tmpDir = System.getProperty("java.io.tmpdir") + File.separator + "test_delete_me" + File.separator;
    public WrapperNetcdfFileTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreateSkeleton() throws FileNotFoundException, IOException {
        WrapperNetcdfFile skel = new WrapperNetcdfFile();
        assertThat(skel, is(notNullValue()));
        File tempFile = File.createTempFile("test", "ncml");
        assertThat(tempFile.exists(), is(true));
        tempFile.createNewFile();
        skel.writeNcML(new FileOutputStream(tempFile));
        assertThat(tempFile.length(), is(greaterThan(new Long(0))));
    }
}
