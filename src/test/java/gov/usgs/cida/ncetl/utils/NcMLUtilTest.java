package gov.usgs.cida.ncetl.utils;

import thredds.server.metadata.util.ThreddsTranslatorUtil;
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
    
    private static File testFile;
    private static String destination = FileHelper.getTempDirectory() +  "test_delete_me" + File.separator;
    private static String destinationFile = destination + "temp.QPE.20110214.009.105";
    private static WrapperNetcdfFile wrapper;
    
    public NcMLUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        wrapper = new WrapperNetcdfFile();
        URL resource = NcMLUtilTest.class.getClassLoader().getResource("QPE.20110214.009.105");
        testFile = new File(resource.getFile());
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() throws IOException {
        FileUtils.forceMkdir(new File(destination));
        FileUtils.copyFile(testFile, new File(destinationFile));
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
    
    @Test
    public void testTransformNCMLtoHTML() throws ThreddsUtilitiesException, IOException {
       File ncmlFile = NcMLUtil.createNcML(destinationFile);
       String _xsltMetadataAssessmentToHTML = NcMLUtilTest.class.getClassLoader().getResource("UnidataDDCount-HTML.xsl").getPath();
       File htmlFile = ThreddsTranslatorUtil.transform(_xsltMetadataAssessmentToHTML, ncmlFile.getCanonicalPath(), destination + "output.html");
       htmlFile.deleteOnExit();
       assertThat(htmlFile.exists(), is(true));
       assertThat(htmlFile.length(), is(not(new Long(0))));
    }
    
        @Test
    public void testTransformNCMLtoXML() throws ThreddsUtilitiesException, IOException {
       File ncmlFile = NcMLUtil.createNcML(destinationFile);
        String _xsltMetadataAssessmentToXML = NcMLUtilTest.class.getClassLoader().getResource("UnidataDDCount-xml.xsl").getPath();
        File xmlFile = ThreddsTranslatorUtil.transform(_xsltMetadataAssessmentToXML, ncmlFile.getCanonicalPath(), destination + "output.xml");
       xmlFile.deleteOnExit();
       assertThat(xmlFile.exists(), is(true));
       assertThat(xmlFile.length(), is(not(new Long(0))));
    }
}
