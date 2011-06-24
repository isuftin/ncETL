/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucar.nc2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.ncml.NcMLWriter;
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
    public void setUp() throws IOException {
        FileUtils.forceMkdir(new File(tmpDir));
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(new File(tmpDir));
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

    @Test
    public void testWriteEmptyNetCdfDataset() throws IOException {
        NetcdfDataset ncfds = new NetcdfDataset();
        NcMLWriter ncmlWriter = new NcMLWriter();
        String result = ncmlWriter.writeXML(ncfds);
        assertThat(result, is(notNullValue()));
    }
    
    @Test
    public void testWriteNcMLWithNullVariables() throws FileNotFoundException, IOException {
        File tmpLocation = File.createTempFile("test", "ncml", new File(tmpDir));
        tmpLocation.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(tmpLocation);
        WrapperNetcdfFile test = new WrapperNetcdfFile();
        test.writeNcML(fos);
        fos.flush();
        fos.close();
        assertThat(tmpLocation.exists(), is(true));
    }
    
    @Test
    public void testWriteNcMLWithNonNullVariables() throws FileNotFoundException, IOException {
        File tmpLocation = File.createTempFile("test", "ncml", new File(tmpDir));
        tmpLocation.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(tmpLocation);
        WrapperNetcdfFile test = new WrapperNetcdfFile();
        test.location = "testLocation";
        test.id = "testId";
        test.title = "testTitle";
        test.writeNcML(fos);
        fos.flush();
        fos.close();
        assertThat(tmpLocation.exists(), is(true));
        
        String fileString = FileUtils.readFileToString(tmpLocation);
        assertThat(fileString.contains("testLocation"), is(true));
        assertThat(fileString.contains("testId"), is(true));
        assertThat(fileString.contains("testTitle"), is(true));
    }
}
