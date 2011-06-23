/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thredds.catalog;

import java.io.FileInputStream;
import org.junit.After;
import org.junit.Before;
import gov.usgs.cida.ncetl.utils.FileHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class CatalogHelperTest {
    public static String tempDir = FileHelper.getTempDirectory();
    public static File tempLocation;
    public static String knownName = "Derpy Derpy Doo";
    public CatalogHelperTest() {
    }

    
    @Before
    public void setUpClass() throws Exception {
        tempLocation = new File(tempDir + CatalogHelper.getDefaultCatalogFilename());
        tempLocation.deleteOnExit();
    }

    @After
    public void tearDownClass() throws Exception {
        FileUtils.forceDelete(tempLocation);
    }
    
    @Test
    public void testSetupCatalogUsingDefaultCatalogName() throws URISyntaxException, FileNotFoundException, IOException {
        CatalogHelper.setupCatalog(tempLocation);
        assertThat(tempLocation.exists(), is(true));
        
        String catalog = IOUtils.toString(new FileInputStream(tempLocation));
        assertThat(catalog.contains(CatalogHelper.getDefaultCatalogName()), is(true));
    }
    
    @Test
    public void testSetupCatalogUsingNoDefaults() throws URISyntaxException, FileNotFoundException, IOException {
        CatalogHelper.setupCatalog(tempLocation, knownName);
        assertThat(tempLocation.exists(), is(true));
        
        String catalog = IOUtils.toString(new FileInputStream(tempLocation));
        assertThat(catalog.contains(knownName), is(true));
    }
}
