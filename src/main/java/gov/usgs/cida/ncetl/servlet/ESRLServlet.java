/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.usgs.cida.ncetl.servlet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"ncmls.zip\"");
        Map<String, List<String>> variables = null;
        String url = request.getParameter("url");
        String opendapRoot = request.getParameter("opendapRoot");
        String regex = request.getParameter("regex");
        
        if (url != null) {
            variables = getVariables(new URL(url), regex);
        }
        //PrintWriter out = response.getWriter();
        ServletOutputStream out = response.getOutputStream();
        ZipOutputStream zip = new ZipOutputStream(out);
        //BufferedWriter zip = new BufferedWriter(new OutputStreamWriter(zip));
        try {
            if (variables != null && opendapRoot != null) {
                zip.putNextEntry(new ZipEntry("union.ncml"));
                zip.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
                zip.write("<netcdf xmlns=\"http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2\">\n".getBytes());
                zip.write("\t<aggregation type=\"union\">\n".getBytes());
                for (String key : variables.keySet()) {
                    zip.write(("\t\t<netcdf location=\"" + key + ".ncml\" />\n").getBytes());
                }
                zip.write("\t</aggregation>\n".getBytes());
                zip.write("</netcdf>\n".getBytes());
                zip.closeEntry();

                for (String key : variables.keySet()) {
                    zip.putNextEntry(new ZipEntry(key + ".ncml"));
                    zip.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
                    zip.write("<netcdf xmlns=\"http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2\">\n".getBytes());
                    zip.write("\t<aggregation type=\"joinExisting\" dimName=\"time\">\n".getBytes());
                    for (String ds : variables.get(key)) {
                        String location = opendapRoot + ds;
                        zip.write(("\t\t<netcdf location=\"" + location + "\"/>\n").getBytes());
                    }
                    zip.write("\t</aggregation>\n".getBytes());
                    zip.write("</netcdf>\n".getBytes());
                    zip.closeEntry();
                    zip.flush();
                }

            }
            else {
                response.setContentType("text/plain");
                PrintWriter textWriter = new PrintWriter(out);
                textWriter.write("could not parse document");
                IOUtils.closeQuietly(textWriter);
            }
        }
        finally {
            IOUtils.closeQuietly(zip);
        }
    }
    
    /**
     * Parses a catalog into a map of variables to endpoints
     * @param catalog URL of catalog
     * @param regex must have one capture group corresponding to the variable
     * @return map of variables to endpoints associated with that variable
     * @throws IOException 
     */
    private Map<String,List<String>> getVariables(URL catalog, String regex) throws IOException {
        InputStream inputStream = null;
        Document doc = null;
        // maps the variable to the dataset
        Map<String,List<String>> vars = Maps.newTreeMap();
        Pattern datasetPattern = Pattern.compile(regex);
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
                    if (vars.containsKey(var)) {
                        vars.get(var).add(nodeValue);
                    } 
                    else {
                        LinkedList<String> datasetList = Lists.newLinkedList();
                        datasetList.add(nodeValue);
                        vars.put(var, datasetList);
                    }
                }
            }
            return vars;
        }
        catch (SAXException ex) {

        }
        catch (ParserConfigurationException ex) {
        
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null; // something went wrong
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
