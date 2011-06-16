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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import thredds.catalog.DataFormatType;
import thredds.catalog.InvCatalogFactory;
import static org.junit.Assert.*;
import org.junit.Ignore;
import thredds.catalog.InvCatalogImpl;
import thredds.catalog.InvDataset;
import thredds.catalog.parser.jdom.InvCatalogFactory10;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class CatalogTest {

    private static InvCatalogFactory factory = null;
    

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
    public void writeCatalog() throws URISyntaxException, FileNotFoundException, IOException {
        URI uri = new URI("file://" + FileHelper.FILE_STORE + "catalog_new.xml");
        InvCatalogImpl impl = new InvCatalogImpl("Test Catalog", "1.0", uri);
        //InvCatalogFactory10 converter = new InvCatalogFactory10();
        impl.setCatalogFactory(factory);
        impl.setCatalogConverterToVersion1();
        File file = new File(impl.getBaseURI());
        FileOutputStream fos = new FileOutputStream(file);
        impl.writeXML(fos);
        assertTrue(file.exists());
    }
}
