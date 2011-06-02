/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.usgs.cida.ncetl.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author jwalker
 */
public class ESRLServlet extends HttpServlet {

    private static final Pattern datasetPattern;
    
    static {
        datasetPattern = Pattern.compile("^(.*)\\.(\\d{4})\\.nc$");
    }
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
        response.setContentType("text/xml;charset=UTF-8");
        String url = request.getParameter("url");
        if (url != null) {
            getVariables(new URL(url));
        }
        PrintWriter out = response.getWriter();
        try {
        }
        finally {            
            out.close();
        }
    }
    
    private void getVariables(URL catalog) throws IOException {
        InputStream inputStream = null;
        Document doc = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            inputStream = catalog.openStream();
            doc = db.parse(inputStream);
            NodeList datasets = doc.getElementsByTagName("dataset");
            for (int i=0; i<datasets.getLength(); i++) {
                Node item = datasets.item(i);
                NamedNodeMap attributes = item.getAttributes();
                Node name = attributes.getNamedItem("name");
                String nodeValue = name.getNodeValue();
                // acpcp.1979.nc
                Matcher matcher = datasetPattern.matcher(nodeValue);
                if (matcher.matches()) {
                    String var = matcher.group(1);
                    String year = matcher.group(2);
                }
            }
        }
        catch (SAXException ex) {

        }
        catch (ParserConfigurationException ex) {
        
        }
        finally {
            IOUtils.closeQuietly(inputStream);
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
