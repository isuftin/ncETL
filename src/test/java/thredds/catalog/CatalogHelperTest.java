package thredds.catalog;

import java.util.List;
import java.net.URI;
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
        if (tempLocation.exists()) FileUtils.forceDelete(tempLocation);
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
    
    /**
     * Here we want to test that if we feed the setupCatalog() function an input of a location
     * with an existing catalog, it won't try to set it up again
     * 
     * @throws URISyntaxException
     * @throws FileNotFoundException
     * @throws IOException 
     */
    @Test
    public void testSetupCatalogUsingExistingCatalogAsInput() throws URISyntaxException, FileNotFoundException, IOException {
        CatalogHelper.setupCatalog(tempLocation, knownName);
        assertThat(tempLocation.exists(), is(true));
        // We now have our catalog. But let's try to do that again with a different catalog name
        
        CatalogHelper.setupCatalog(tempLocation, "OH HAI I AM A CATALOG");
        assertThat(tempLocation.exists(), is(true));
        // At this point this catalog should not have been created
        
        String catalog = IOUtils.toString(new FileInputStream(tempLocation));
        assertThat(catalog.contains(knownName), is(true));
        assertThat(catalog.contains("OH HAI I AM A CATALOG"), is(false));
    }
    
    @Test
    public void testCreateCatalogImpl() throws URISyntaxException, FileNotFoundException, IOException {
        URI uri = new URI("file://" + tempDir + CatalogHelper.getDefaultCatalogFilename());
        InvCatalogImpl test = CatalogHelper.createCatalogImpl(knownName, uri);
        assertThat(test, is(notNullValue()));
        assertThat(test.name, is(equalTo(knownName)));
    }
    
    @Test
    public void testAddDataset() throws URISyntaxException, FileNotFoundException, IOException {
        URI uri = new URI("file://" + tempDir + CatalogHelper.getDefaultCatalogFilename());
        InvCatalogImpl cat = CatalogHelper.createCatalogImpl(knownName, uri);
        InvDatasetBuilder ds = new InvDatasetBuilder("test");
        CatalogHelper.addDataset(cat, ds.getInternalInvDataset());
        List<InvDataset> test = cat.getDatasets();
        assertThat(test.size(), is(equalTo(1)));
        assertThat(test.get(0).name, is(equalTo("test")));
    }
    
}
