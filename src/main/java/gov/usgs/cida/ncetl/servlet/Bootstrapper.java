/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.usgs.cida.ncetl.servlet;

import gov.usgs.cida.ncetl.utils.DerbyHelper;
import gov.usgs.cida.ncetl.utils.FileHelper;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web application lifecycle listener.
 * @author jwalker
 */
public class Bootstrapper implements ServletContextListener {
    
    public static Logger log = LoggerFactory.getLogger(Bootstrapper.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            FileHelper.setupDirectories();
        }
        catch (IOException ioe) {
            // do some logging
        }
        try {
            DerbyHelper.setupDatabase();
        }
        catch (SQLException sqle) {
            // log this
        }
        catch (Exception e) {
            // also log this
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // clean stuff up
    }
}
