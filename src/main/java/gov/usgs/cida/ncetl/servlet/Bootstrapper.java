package gov.usgs.cida.ncetl.servlet;

import gov.usgs.cida.ncetl.utils.DatabaseUtil;
import gov.usgs.cida.ncetl.utils.FileHelper;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
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

    private static final String ERRORS_ENCOUNTERED = "errors-encountered";
    private static final String FALSE = "false";
    private static final String TRUE = "true";
    private static Logger log = LoggerFactory.getLogger(Bootstrapper.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("\n*************** ncETL is starting up.");
        System.setProperty(ERRORS_ENCOUNTERED, FALSE);
        // Place all startup hooks here

        try {
            FileHelper.createDirectories();
        }
        catch (IOException ioe) {
            log.error(
                    "*************** Application could not initialize directory structure. The application will not be able to continue functioning. Error follows.",
                    ioe);
            System.setProperty(ERRORS_ENCOUNTERED, TRUE);
            return;
        }

        try {
            DatabaseUtil.setupDatabase();
        }
        catch (Exception e) {
            log.error(
                    "*************** Application could not initialize database. The application will not be able to continue functioning. Error follows.",
                    e);
            System.setProperty(ERRORS_ENCOUNTERED, TRUE);
            return;
        }

        try {
            CatalogHelper.setupCatalog();
        }
        catch (Exception ex) {
            log.error(
                    "*************** Application could not initialize catalog. The application will not work correctly. Error follows.",
                    ex);
            System.setProperty(ERRORS_ENCOUNTERED, TRUE);
            return;
        }
        
        log.info("*************** ncETL has started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            // Place all shut down hooks here
            DatabaseUtil.shutdownDatabase();
        }
        catch (SQLException ex) {
            log.debug("Couldn't shut down", ex);
        }
        catch (NamingException ex) {
            log.debug("Couldn't shut down", ex);
        }
        catch (ClassNotFoundException ex) {
            log.debug("Couldn't shut down", ex);
        }
        log.info("*************** ncETL is shutting down.\n");
    }
}
