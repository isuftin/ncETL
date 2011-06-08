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
                out.println(createErrorXML(Arrays.asList("location parameter can not be empty")));
                return;
            }

            // Check that the file exists. If not, send error.
            File file = new File(location);
            if (!file.exists()) {
                out.println(createErrorXML(Arrays.asList("file specified by location does not exist")));
                return;
            }
            
            File fileNCML = new File(location + ".ncml");
            if (!fileNCML.exists()) {
                try {
                    fileNCML = createNCML(fileNCML);
                } catch (IOException ioe) {
                    out.println(createErrorXML(Arrays.asList("NCML file could not be written to")));
                    return;  
                }
            }
            List<String> fileNCMLString = FileUtils.readLines(fileNCML);
            for (String line : fileNCMLString) {
                out.println(line);
                return;
            }
        } finally {
            out.close();
        }
//        try {
//            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//            out.println("<Datasets>");
//            File[] datasets = getDirContents(FILE_STORE);
//            for (File dataset : datasets) {
//                out.println("<Dataset><path>" + dataset.toString() + "</path></Dataset>");
//            }
//            out.println("</Datasets>");
//        } finally {
//            out.close();
//        }
    }

    private File createNCML(File file) throws IOException {
        byte[] ncmlXML =  ("<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
            + "<netcdf xmlns=\"http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2\" location=\"...\" > "
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
