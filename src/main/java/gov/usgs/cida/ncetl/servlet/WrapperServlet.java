package gov.usgs.cida.ncetl.servlet;

import gov.noaa.eds.threddsutilities.exception.ThreddsUtilitiesException;
import gov.usgs.cida.ncetl.utils.NetCDFUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import static gov.usgs.cida.ncetl.utils.FileHelper.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Ivan Suftin
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
            String action = request.getParameter("action");

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
                    fileNCML = NetCDFUtil.createNcML(location);
                }
                catch (ThreddsUtilitiesException tdse) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                       createErrorXML(Arrays.asList("TDS_ERROR: problem creating NcML"), tdse));
                    return;  
                }
                
            }
            
            // Create the Document object 
            Document dom = null;
            try {
                dom = getDocument(location);
            }
            catch (IOException ioe) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                                   createErrorXML(Arrays.asList("FILE_ERROR: IOException while parsing document"), ioe));
                return;
            }
            catch (ParserConfigurationException pce) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                                   createErrorXML(Arrays.asList("FILE_ERROR: ParserConfigurationException while parsing document"), pce));
                return;
            }
            catch (SAXException saxe) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                                   createErrorXML(Arrays.asList("FILE_ERROR: SAXException while parsing document"), saxe));
                return;
            }
            
            if ("add".equalsIgnoreCase(action)) {
                // XPath to tell us where to add - if blank, is a root element
//                String 
                String childOf = request.getParameter("child-of");
                //dom.add(attribute, something);
                //dom.save();
            }
            
            if ("remove".equalsIgnoreCase(action)) {
                // XPath of where to remove
                String removeAt = request.getParameter("remove-at");
                // What to remove 
                String variable = request.getParameter("variable");
                String value = request.getParameter("value");
                String attribute = request.getParameter("attribute");
                
                //dom.remove(removeAt);
                //dom.save
            }
            
            if ("edit".equalsIgnoreCase(action)) {
                String editAt = request.getParameter("edit-at");
                // What to edit 
                String variable = request.getParameter("variable");
                String value = request.getParameter("value");
                String attribute = request.getParameter("attribute");
                String newValue = request.getParameter("new-value");
                //dom.edit(attribute);
                //dom.save
            }
            
            // Read from the augmented or newly created NCML file and output the contents to the caller
            List<String> fileNCMLString = FileUtils.readLines(fileNCML);
            for (String line : fileNCMLString) {
                out.println(line);
            }
        } finally {
            out.close();
        }
    }
    
    private String createErrorXML(List<String> errors) {
        return createErrorXML(errors, null);
    }
    
    private String createErrorXML(List<String> errors, Throwable throwable) {
        StringBuilder errorXML = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        errorXML.append("<errors>");
        for (String error : errors) {
            errorXML.append(error);
        }
        if (throwable != null) {
            errorXML.append("<trace>");
            errorXML.append(throwable.getMessage());
            errorXML.append("</trace>");
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
    
    private Document getDocument(String location) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        return builder.parse(new File(location));
    }
}
