package gov.usgs.cida.ncetl.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jwalker
 */
public final class FileHelper {
    private static final Logger LOG = LoggerFactory.getLogger(FileHelper.class);
    private static final String FILE_STORE = System.getProperty("user.home") + File.separator + ".ncetl" + File.separator;
    private static final String DATABASE_DIRECTORY = FILE_STORE + "database" + File.separator;
    private static final String DATASETS_DIRECTORY = FILE_STORE + "datasets" + File.separator;
    //private static final String DEFAULT_CATALOG_LOCATION = DATASETS_DIRECTORY + File.separator + "catalog.xml";

    private FileHelper(){}
    
    /**
     * Creates the directory structure the application uses during run
     * 
     * @throws IOException 
     */
    public static void createDirectories() throws IOException {
        LOG.debug("Setting up directories");
        createRootDirectory();
        createDatabaseDirectory();
        createDatasetsDirectory();
    }
    
    private static void createRootDirectory() throws IOException {
        LOG.debug("Application root directory is: " + FILE_STORE);

        File rootDirectory = new File(FILE_STORE);
        createDirectory(rootDirectory);
        
        LOG.debug("Checking read permissions for " + FILE_STORE);
        if (rootDirectory.canRead()) {
            LOG.debug("Directory " + FILE_STORE + " is readable.");
            LOG.debug("Checking write permissions.");
            if (rootDirectory.canWrite()) {
                LOG.debug("Directory " + FILE_STORE + " is writable.");
            }
        } else {
            throw new IOException("Directory " + FILE_STORE + " is not readable. The application will not be able to continue functioning.");
        }
    }
    
    private static void createDatabaseDirectory() throws IOException {
        LOG.debug("Database directory is: " + DATABASE_DIRECTORY);
        File databaseDirectory = new File(DATABASE_DIRECTORY);
        createDirectory(databaseDirectory);
    }
    
    private static void createDatasetsDirectory() throws IOException {
        LOG.debug("Datasets directory is: " + DATASETS_DIRECTORY);
        File datasetsDirectory = new File(DATASETS_DIRECTORY);
        createDirectory(datasetsDirectory);
    }
    
    public static File createDatasetDirectory(String datasetName) throws IOException {
        LOG.debug("Creating subdirectory under " + DATASETS_DIRECTORY + " named " + datasetName);
        File file = new File(DATASETS_DIRECTORY + datasetName);
        createDirectory(file, "data", "waf");
        return file;
    }
    
//    private static void createDatasetDataDirectory(File parentDirectory) {
//        LOG.debug("Creating 'data' subdirectory under " + parentDirectory.getPath());
//    }
    
    public static String dirAppend(String dir, String secondDir) {
        if (!dir.endsWith(File.separator)) {
            return dir + File.separator + secondDir;
        } else {
            return dir + secondDir;
        }
    }
    
    /**
     * Creates a directory and all directories leading up to it
     * 
     * @param directory
     * @param subdirectories list of sibling subdirectories to be created under the parent directory
     * @throws IOException Directory could not be created or location already exists but is not a directory 
     */
    public static void createDirectory(final File directory, final String... subdirectories) throws IOException {
        if (directory.exists()) {
            if (directory.isDirectory()) {
                return;
            } else {
                throw new IOException(FILE_STORE + " exists but is not a directory. Directory could not be created");
            }
        }
        
        LOG.debug("Directory " + FILE_STORE + " doesn't exist, creating it now.");
        FileUtils.forceMkdir(directory);
        LOG.debug("Directory " + FILE_STORE + " created."); 
        if (subdirectories.length > 0) {
            String parentDirectory = directory.getPath();
            for (String file : subdirectories) {
                FileHelper.createDirectory(new File(dirAppend(parentDirectory,file)));
            }
        }
    }
    
    /**
     * Attaches an OS specific trailing slash to a request for a temporary
     * directory. Addresses the problem found here:
     * http://rationalpi.wordpress.com/2007/01/26/javaiotmpdir-inconsitency/
     * @return 
     */
    public static String getTempDirectory() {
        String tmpDir = System.getProperty("java.io.tmpdir");
        if (!tmpDir.endsWith(File.separator)) {
            tmpDir += File.separator;
        }
        return tmpDir;
    }
    
    public static String getBaseDirectory() {
        return FILE_STORE;
    }
    
    public static String getDatabaseDirectory() {
        return DATABASE_DIRECTORY;
    }
    
    public static String getDatasetsDirectory() {
        return DATASETS_DIRECTORY;
    }
}
