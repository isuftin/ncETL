package gov.usgs.cida.ncetl.servlet;

import gov.usgs.cida.ncetl.utils.DerbyHelper;
import gov.usgs.cida.ncetl.utils.FileHelper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
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

    public static Logger log = LoggerFactory.getLogger(Bootstrapper.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("*************** ncETL is starting up.");
        System.setProperty("errors-encountered", "false");
        // Place all startup hooks here

        try {
            FileHelper.setupDirectories();
        }
        catch (IOException ioe) {
            log.error(
                    "Application could not initialize directory structure. The application will not be able to continue functioning. Error follows.",
                      ioe);
            System.setProperty("errors-encountered", "true");
            return;
        }

        try {
            DerbyHelper.setupDatabase();
        }
        catch (SQLException sqle) {
            log.error(
                    "Application could not initialize database. The application will not be able to continue functioning. Error follows.",
                      sqle);
            System.setProperty("errors-encountered", "true");
            return;
        }
        catch (Exception e) {
            log.error(
                    "Application could not initialize database. The application will not be able to continue functioning. Error follows.",
                      e);
            System.setProperty("errors-encountered", "true");
            return;
        }
        
        try {
            CatalogHelper.setupCatalog();
        }
        catch (URISyntaxException ex) {
            log.error(
                    "Application could not initialize catalog. The application will not work correctly. Error follows.",
                      ex);
            System.setProperty("errors-encountered", "true");
        }
        catch (FileNotFoundException ex) {
            log.error(
                    "Application could not initialize catalog. The application will not work correctly. Error follows.",
                      ex);
            System.setProperty("errors-encountered", "true");
        }
        catch (IOException ex) {
            log.error(
                    "Application could not initialize catalog. The application will not work correctly. Error follows.",
                      ex);
            System.setProperty("errors-encountered", "true");
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Place all shut down hooks here
        log.info("*************** ncETL is shutting down.");
    }
}
