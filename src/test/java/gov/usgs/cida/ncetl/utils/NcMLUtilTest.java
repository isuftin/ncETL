package gov.usgs.cida.ncetl.utils;

import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ucar.nc2.Attribute;
import ucar.nc2.Group;
import ucar.nc2.WrapperNetcdfFile;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author jwalker
 */
public class NcMLUtilTest {
    
    private static String testFile;
    private static WrapperNetcdfFile wrapper;
    
    public NcMLUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        wrapper = new WrapperNetcdfFile();
        URL resource = NcMLUtilTest.class.getClassLoader().getResource("QPE.20110214.009.105");
        testFile = resource.getFile();
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

    /**
     * Test of writeNetCDFFile method, of class NcMLUtilTest.
     
    @Test
    public void testWriteNetCDFFile() throws Exception {
        System.out.println("writeNetCDFFile");
        InputStream ncmlIn = null;
        String outfile = "";
        NcMLUtilTest.writeNetCDFFile(ncmlIn, outfile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */

    /**
     * Test of globalAttributesToMeta method, of class NcMLUtilTest.
     */
    @Test
    public void testGlobalAttributesToMeta() throws Exception {
        Group metaGroup = NcMLUtil.globalAttributesToMeta(testFile, wrapper);
        Attribute attr = metaGroup.findAttribute("history");
        // TODO review the generated test code and remove the default call to fail.
        assertThat(attr, is(notNullValue()));
    }
}
