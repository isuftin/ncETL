/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thredds.catalog;

import gov.usgs.cida.ncetl.utils.FileHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class CatalogTest {

    private static InvCatalogFactory factory = null;
    private static Logger log = LoggerFactory.getLogger(CatalogTest.class);

    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = new InvCatalogFactory("testFactory", true);
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

    @Ignore
    @Test
    public void readCatalog() {

        InvCatalogImpl readXML = factory.readXML(
                "file://" + FileHelper.FILE_STORE + "catalog.xml");
        List<InvDataset> datasets = readXML.getDatasets();
        assertEquals(datasets.size(), 2);
        InvDataset dataset = datasets.get(0);
        DataFormatType dft = dataset.getDataFormatType();
        assertTrue(dft.equals(DataFormatType.NETCDF));
    }

    //@Ignore
    @Test
    public void writeCatalog() throws URISyntaxException, FileNotFoundException,
                                      IOException {
        File f = File.createTempFile("catalog_new", "xml");
        f.deleteOnExit();
        URI uri = f.toURI();
        InvCatalogImpl impl = new InvCatalogImpl("Test Catalog", "1.0", uri);
        impl.setCatalogFactory(factory);
        impl.setCatalogConverterToVersion1();
        FileOutputStream fos = new FileOutputStream(f);
        impl.writeXML(fos);
        assertTrue(f.exists());

    }
}
