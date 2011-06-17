package gov.usgs.cida.ncetl.servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This filter checks the current error state of the application (did it initialize correctly?)
 * If not, the user is redirected to an error page. If so, continue as normal.
 * @author isuftin
 */
public class CheckErrorStateFilter implements Filter {
    public static Logger log = LoggerFactory.getLogger(CheckErrorStateFilter.class);
    public CheckErrorStateFilter() {
    }    
    
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
         if (request instanceof HttpServletRequest) {
            String errorStateString = (StringUtils.isBlank(System.getProperty("errors-encountered"))) ? "false" : System.getProperty("errors-encountered");
            boolean errorsEncountered = Boolean.parseBoolean(errorStateString);
            if (errorsEncountered) {
                log.debug("User tried to access application while running in an error state. Sending error information to user.");
                sendProcessingError(response);
                return;
            }
            chain.doFilter(request, response);
        }
    }
    /**
     * Destroy method for this filter 
     */
    @Override
    public void destroy() {        
    }

    /**
     * Init method for this filter 
     * @param filterConfig 
     */
    @Override
    public void init(FilterConfig filterConfig) {        
    }
    
    private void sendProcessingError(ServletResponse response) throws IOException {
            response.setContentType("text/html");
            PrintStream ps = new PrintStream(response.getOutputStream());
            PrintWriter pw = new PrintWriter(ps);                
            pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n");
            pw.print("<h1>The resource did not initialize correctly</h1>\n<pre>\n");                
            pw.print("The application is currently running in an error state.<br />"
                    + " There may have been errors during initialization. Please check the logs.");                
            pw.print("</pre></body>\n</html>"); //NOI18N
            pw.close();
            ps.close();
            response.getOutputStream().close();
    }
}
