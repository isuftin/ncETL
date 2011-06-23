package thredds.catalog;

import gov.usgs.cida.ncetl.utils.FileHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public final class CatalogHelper {
    private static final String DEFAULT_CATALOG_FILENAME = "catalog.xml";
    private static final String DEFAULT_CATALOG_LOCATION = FileHelper.dirAppend(FileHelper.getDatasetsDirectory(), DEFAULT_CATALOG_FILENAME);
    private static final String DEFAULT_CATALOG_NAME = "ncETL generated THREDDS catalog";

    private CatalogHelper() {
    }

    /**
     * Sets up a location in the default location location.
     * 
     * @throws URISyntaxException
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void setupCatalog() throws URISyntaxException, FileNotFoundException, IOException {
        setupCatalog(new File(DEFAULT_CATALOG_LOCATION), DEFAULT_CATALOG_NAME);
    }
    
    public static void setupCatalog(File location) throws URISyntaxException, FileNotFoundException, IOException {
        setupCatalog(location, DEFAULT_CATALOG_NAME);
    }
    
    public static void setupCatalog(File location, String catalogName) throws URISyntaxException, FileNotFoundException, IOException {
        if (!location.exists()) {
            createNewCatalog(catalogName, location.getPath());
        }
    }
    
    public static void createNewCatalog(String name, String absolutePath) throws
        URISyntaxException, FileNotFoundException, IOException {
        URI uri = new URI("file://" + absolutePath);
        InvCatalogImpl impl = createCatalogImpl(name, uri);
        File file = new File(impl.getBaseURI());
        
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            impl.writeXML(fos);
        }
        finally {
            IOUtils.closeQuietly(fos);
        }

    }
    
    private static InvCatalogImpl createCatalogImpl(String name, URI uri) {
        InvCatalogFactory factory = new InvCatalogFactory("CatalogFactory", true);
        InvCatalogImpl impl = new InvCatalogImpl(name, "1.0", uri);
        impl.setCatalogFactory(factory);
        impl.setCatalogConverterToVersion1();
        return impl;
    }
    
    public static void addDataset(InvCatalog cat, InvDataset dataset) {
        InvCatalogImpl concreteCat = (InvCatalogImpl)cat;
        InvDatasetImpl concreteDataset = (InvDatasetImpl)dataset;
        concreteCat.addDataset(concreteDataset);
    }
    
    public static String getDefaultCatalogFilename() {
        return DEFAULT_CATALOG_FILENAME;
    }
    
    /* Some ideas
     * **********
     * Allow for location versioning here (jgit, filenames with dates, etc)
     * Start of with more than bare location, prepopulate with some defaults
     * Is this the right place to add setters for the location editing?
     */
}
