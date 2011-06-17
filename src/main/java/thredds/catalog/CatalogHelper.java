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
    
    public static final String DEFAULT_CATALOG_LOCATION = FileHelper.FILE_STORE + "catalog.xml";
    public static final String DEFAULT_CATALOG_NAME = "ncETL generated THREDDS catalog";

    private CatalogHelper() {
    }

    public static void setupCatalog() throws URISyntaxException, FileNotFoundException, IOException {
        File catalog = new File(DEFAULT_CATALOG_LOCATION);
        if (!catalog.exists()) {
            createNewCatalog(DEFAULT_CATALOG_NAME, DEFAULT_CATALOG_LOCATION);
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
    
    /* Some ideas
     * **********
     * Allow for catalog versioning here (jgit, filenames with dates, etc)
     * Start of with more than bare catalog, prepopulate with some defaults
     * Is this the right place to add setters for the catalog editing?
     */
}
