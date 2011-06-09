/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.usgs.cida.ncetl.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import static gov.usgs.cida.ncetl.utils.FileHelper.*;

/**
 *
 * @author isuftin
 */
public class WrapperServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Check that we have a "location" element. If not, send an error
            String location = request.getParameter("location");
            if (StringUtils.isEmpty(location)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                                   createErrorXML(Arrays.asList("MISSING_PARAM: location parameter can not be empty")));
                return;
            }

            // Check that the file exists. If not, send error.
            location = FILE_STORE + location;
            File file = new File(location);
            if (!file.exists()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                   createErrorXML(Arrays.asList("FILE_NOT_FOUND: file specified by location does not exist")));
                return;
            }
            
            // Augment or create the NCML file
            File fileNCML = new File(location + ".ncml");
            if (!fileNCML.exists()) {
                try {
                    fileNCML = createNCML(fileNCML);
                } catch (IOException ioe) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                       createErrorXML(Arrays.asList("FILE_ERROR: NCML file could not be written to")));
                    return;  
                }
            }
            
            // Read from the augmented or newly created NCML file and output the contents to the caller
            List<String> fileNCMLString = FileUtils.readLines(fileNCML);
            for (String line : fileNCMLString) {
                out.println(line);
                return;
            }
        } finally {
            out.close();
        }
    }

    private File createNCML(File file) throws IOException {
        byte[] ncmlXML =  ("<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
            + "<netcdf xmlns=\"http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2\" location=\""+file.getPath()+"\" > "
            + "</netcdf>").getBytes();
        FileUtils.writeByteArrayToFile(file, ncmlXML);
        return file;
        
    }
    
    private String createErrorXML(List<String> errors) {
        StringBuilder errorXML = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        errorXML.append("<errors>");
        for (String error : errors) {
            errorXML.append(error);
        }
        errorXML.append("</errors>");
        return errorXML.toString();
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
}
