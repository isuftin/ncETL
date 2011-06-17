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
    public static final String FILE_STORE = System.getProperty("user.home") + File.separator + ".ncetl" + File.separator;
    public static final String DATABASE_DIRECTORY = FILE_STORE + "database" + File.separator;

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
        File databaseDirectory = new File(FILE_STORE);
        createDirectory(databaseDirectory);
    }
    
    /**
     * Creates a directory and all directories leading up to it
     * 
     * @param directory
     * @throws IOException Directory could not be created or location already exists but is not a directory 
     */
    public static void createDirectory(final File directory) throws IOException {
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
        
    }
}
