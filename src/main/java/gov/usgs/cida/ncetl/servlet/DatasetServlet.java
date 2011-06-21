package gov.usgs.cida.ncetl.servlet;

import gov.usgs.cida.ncetl.utils.FileHelper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jwalker
 */
public class DatasetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final static String requestedAction = "action";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter(requestedAction);
        
        if ("getDatasets".equals(action)) {
            response.setContentType("text/xml;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                            out.println("<Datasets>");
                            File[] datasets = getDirContents(FileHelper.getDatasetsDirectory());
                            for (File dataset : datasets) {
                                    out.println("<Dataset><path>"+dataset.getName()+"</path></Dataset>");
                            }
                            out.println("</Datasets>");
            } finally { 
                out.close();
            }
        } else if ("addMetadata".equals(action)) {
            addMetaData(request);
        }
    }

	private File[] getDirContents(String dir) {
		File directory = new File(dir);
		if (!directory.isDirectory()) {
			return new File[0];
		}
		return directory.listFiles();
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
        return "Short description";
    }// </editor-fold>

    private void addMetaData(HttpServletRequest request) {
        String id = null;
        String coverage = null;
        String datasetShortName = request.getParameter("name");
        String datasetName = request.getParameter("description");
        String docXlink = request.getParameter("doc-xlink");
        String docRights = request.getParameter("doc-rights");
        String docSummary = request.getParameter("doc-summary");
        String docReference = request.getParameter("doc-reference");
        String servicesOpenDap = request.getParameter("services-opendap");
        String servicesNetCDFSubset = request.getParameter("services-netcdfsubset");
        String servicesHttpDownload = request.getParameter("services-httpdownload");
        String servicesWCS = request.getParameter("services-wcs");
        String servicesWMS = request.getParameter("services-wms");
        String servicesNcisoNCML = request.getParameter("services-nciso-ncml");
        String servicesNcisoIso = request.getParameter("services-nciso-iso");
        String servicesNcisoUDDC = request.getParameter("services-nciso-uddc");
        String dataFormat = request.getParameter("dataFormat");
        String keywords = request.getParameter("keywords");
        String creator = request.getParameter("creator");
    }
    
}
