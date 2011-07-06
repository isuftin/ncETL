package gov.usgs.cida.ncetl.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import thredds.server.metadata.exception.ThreddsUtilitiesException;
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
    private static String destination = FileHelper.getTempDirectory() +  "test_delete_me" + File.separator;
    private static String destinationFile = destination + "temp.QPE.20110214.009.105";
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
    public void setUp() throws IOException {
        FileUtils.forceMkdir(new File(destination));
        FileUtils.copyFile(new File(testFile), new File(destinationFile));
    }
    
    @After
    public void tearDown() throws IOException {
        FileUtils.forceDelete(new File(destination));
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
     * @throws Exception 
     */
    @Test
    public void testGlobalAttributesToMeta() throws Exception {
        Group metaGroup = NcMLUtil.globalAttributesToMeta(testFile, wrapper);
        Attribute attr = metaGroup.findAttribute("history");
        // TODO review the generated test code and remove the default call to fail.
        assertThat(attr, is(notNullValue()));
        assertThat(metaGroup.getAttributes().size(), is(equalTo(11)));
        assertThat(metaGroup.getShortName().contains("QPE.20110214.009.105"), is(true));
    }
    
    @Test
    public void testCreateNCML() throws ThreddsUtilitiesException {
        File test = NcMLUtil.createNcML(destinationFile);
        assertThat(test, is(notNullValue()));
        assertThat(test.length(), is(not(new Long(0))));
        
    }
}
