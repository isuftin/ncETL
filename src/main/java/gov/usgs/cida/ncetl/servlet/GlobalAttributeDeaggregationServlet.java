package gov.usgs.cida.ncetl.servlet;

import gov.usgs.cida.ncetl.utils.NcMLUtil;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import ucar.nc2.Group;
import ucar.nc2.WrapperNetcdfFile;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class GlobalAttributeDeaggregationServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/xml;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        WrapperNetcdfFile globals = new WrapperNetcdfFile();
        Group rootGroup = new Group(globals, null, "Aggregated global attributes");
        globals.addGroup(null, rootGroup);
        try {
            String directory = request.getParameter("directory");
            boolean recurse = ("true".equalsIgnoreCase(request.getParameter("recurse")));
            File file = new File(directory);
            addDirectoryContents(file, globals, recurse);
            globals.writeNcML(out);
        }
        finally {            
            IOUtils.closeQuietly(out);
        }
    }
    
    private void addDirectoryContents(File directory, WrapperNetcdfFile ncml, boolean recurse) throws IOException {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory() && recurse) {
                    addDirectoryContents(directory, ncml, recurse);
                }
                else {
                    addFileContents(file, ncml);
                }
            }
        }
        else {
            addFileContents(directory, ncml);
        }
    }
    
    private void addFileContents(File file, WrapperNetcdfFile globals) throws IOException {
        Group globalGroup = NcMLUtil.globalAttributesToMeta(file, globals);
        globals.addGroup(globals.getRootGroup(), globalGroup);
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
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
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
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
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


}
