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
            System.setProperty(ERRORS_ENCOUNTERED, TRUE);
            return;
        }

        try {
            DerbyHelper.setupDatabase();
        }
        catch (Exception e) {
            log.error(
                    "Application could not initialize database. The application will not be able to continue functioning. Error follows.",
                      e);
            System.setProperty(ERRORS_ENCOUNTERED, TRUE);
            return;
        }
        
        try {
            CatalogHelper.setupCatalog();
        }
        catch (Exception ex) {
            log.error(
                    "Application could not initialize catalog. The application will not work correctly. Error follows.",
                      ex);
            System.setProperty(ERRORS_ENCOUNTERED, TRUE);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Place all shut down hooks here
        log.info("*************** ncETL is shutting down.");
    }
}
