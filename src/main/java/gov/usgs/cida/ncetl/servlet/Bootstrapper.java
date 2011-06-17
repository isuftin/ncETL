package gov.usgs.cida.ncetl.servlet;

import gov.usgs.cida.ncetl.utils.DerbyHelper;
import gov.usgs.cida.ncetl.utils.FileHelper;
import java.io.IOException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thredds.catalog.CatalogHelper;

/**
 * Web application lifecycle listener.
 * @author jwalker
 */
public class Bootstrapper implements ServletContextListener {
    private final static String ERRORS_ENCOUNTERED = "errors-encountered";
    private final static String FALSE = "false";
    private final static String TRUE = "true";
    private static Logger log = LoggerFactory.getLogger(Bootstrapper.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("*************** ncETL is starting up.");
        System.setProperty(ERRORS_ENCOUNTERED, FALSE);
        // Place all startup hooks here

        try {
            FileHelper.setupDirectories();
        }
        catch (IOException ioe) {
            log.error(
                    "Application could not initialize directory structure. The application will not be able to continue functioning. Error follows.",
                      ioe);
<<<<<<< HEAD
            System.setProperty(ERRORS_ENCOUNTERED, TRUE);
=======
            System.setProperty("errors-encountered", "true");
>>>>>>> some database refactoring mostly.
            return;
        }

        try {
            DerbyHelper.setupDatabase();
        }
<<<<<<< HEAD
=======
        catch (SQLException sqle) {
            log.error(
                    "Application could not initialize database. The application will not be able to continue functioning. Error follows.",
                      sqle);
            System.setProperty("errors-encountered", "true");
            return;
        }
>>>>>>> some database refactoring mostly.
        catch (Exception e) {
            log.error(
                    "Application could not initialize database. The application will not be able to continue functioning. Error follows.",
                      e);
<<<<<<< HEAD
            System.setProperty(ERRORS_ENCOUNTERED, TRUE);
=======
            System.setProperty("errors-encountered", "true");
>>>>>>> some database refactoring mostly.
            return;
        }
        
        try {
            CatalogHelper.setupCatalog();
        }
<<<<<<< HEAD
        catch (Exception ex) {
            log.error(
                    "Application could not initialize catalog. The application will not work correctly. Error follows.",
                      ex);
            System.setProperty(ERRORS_ENCOUNTERED, TRUE);
=======
        catch (URISyntaxException ex) {
            log.error(
                    "Application could not initialize catalog. The application will not work correctly. Error follows.",
                      ex);
            System.setProperty("errors-encountered", "true");
            return;
        }
        catch (FileNotFoundException ex) {
            log.error(
                    "Application could not initialize catalog. The application will not work correctly. Error follows.",
                      ex);
            System.setProperty("errors-encountered", "true");
            return;
        }
        catch (IOException ex) {
            log.error(
                    "Application could not initialize catalog. The application will not work correctly. Error follows.",
                      ex);
            System.setProperty("errors-encountered", "true");
            return;
>>>>>>> some database refactoring mostly.
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Place all shut down hooks here
        log.info("*************** ncETL is shutting down.");
    }
}
