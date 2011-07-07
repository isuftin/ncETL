package gov.usgs.cida.ncetl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gov.noaa.eds.controller.ServiceController;
import java.net.URLDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jwalker
 */
public class DecorateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(DecorateServlet.class);
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
   			String tsUrl = request.getParameter("ts");
			if (null != tsUrl) { // TODO: This might want to be a check against isBlank, not just null
				tsUrl = URLDecoder.decode(tsUrl, "UTF-8");
			}
			else {
				tsUrl = "http://igsarm-cida-javatest2.er.usgs.gov:8080/thredds/catalog.xml";
			}
   			boolean isoExtract = ("true".equalsIgnoreCase(request.getParameter("iso")));
   			boolean customExtract = ("true".equalsIgnoreCase(request.getParameter("custom")));
   			String xsltFile = request.getParameter("xsl");
   			String wafDir = request.getParameter("waf");
   			String wafScoreDir = request.getParameter("wafScore");
			int sampleNum = Integer.parseInt(request.getParameter("num"));
			int depth = Integer.parseInt(request.getParameter("depth"));

			ServiceController serviceController = new ServiceController();
                    if (tsUrl!=null) { // TODO: This is never going to be null
   		    	if (wafDir==null) {
   		            serviceController.launch(tsUrl, sampleNum, depth, isoExtract, customExtract, xsltFile);
   		    	} else {
   		    		//Generate flat WAF
   		    		serviceController.createWaf(tsUrl, sampleNum, depth, isoExtract, customExtract, xsltFile, wafDir);
   		    	}
   		    } else {
   		    	//Check to see if wafScore option set
   		    	serviceController.calcWafScore(wafScoreDir);
   		    }
			
			//serviceController.createWaf("http://igsarm-cida-javatest2.er.usgs.gov:8080/thredds", 2, 2, false, false, null, "/home/scratch");
			out.print("{success:true}");
        }
		finally {
			out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Does the ncISO decoration";
    }// </editor-fold>

}
