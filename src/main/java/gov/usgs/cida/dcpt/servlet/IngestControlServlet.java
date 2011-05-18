package gov.usgs.cida.dcpt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gov.usgs.cida.dcpt.FTPIngestTask;
import java.util.ArrayList;
/**
 *
 * @author jwalker
 */
public class IngestControlServlet extends HttpServlet {

	public static List<FTPIngestTask> ingestList;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ingestList = new ArrayList<FTPIngestTask>();
		try {
			FTPIngestTask task = new FTPIngestTask.Builder("ftp://ftp.hpc.ncep.noaa.gov/npvu/rfcqpe/20110206/").fileRegex(".*").rescanEvery(60000).build();
			ingestList.add(task);
		}
		catch (MalformedURLException ex) {
		}
		super.init(config);
	}

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

			String action = request.getParameter("action");
			if (action == null) {
				out.print("action required!");
			}
			else if (action.equalsIgnoreCase("read")) {
				out.print("{");
				out.print("results: ");
				out.print(ingestList.size());
				out.print(", rows: [");
				Iterator<FTPIngestTask> iterator = ingestList.iterator();
				while(iterator.hasNext()) {
					out.print(iterator.next().toJSONString());
					if (iterator.hasNext()) {
						out.print(",");
					}
				}
				out.print("]}");
			}
			else if (action.equalsIgnoreCase("create")) {
                
				out.print("{");
				out.print("results: ");
				out.print(ingestList.size());
				out.print(", rows: [");
				Iterator<FTPIngestTask> iterator = ingestList.iterator();
				while(iterator.hasNext()) {
					out.print(iterator.next().toJSONString());
					if (iterator.hasNext()) {
						out.print(",");
					}
				}
				out.print("]}");
			}
			else if (action.equalsIgnoreCase("update")) {
				out.print("{");
				out.print("results: ");
				out.print(ingestList.size());
				out.print(", rows: [");
				Iterator<FTPIngestTask> iterator = ingestList.iterator();
				while(iterator.hasNext()) {
					out.print(iterator.next().toJSONString());
					if (iterator.hasNext()) {
						out.print(",");
					}
				}
				out.print("]}");
			}
			else if (action.equalsIgnoreCase("delete")) {
				throw new UnsupportedOperationException("Add not yet implemented");
			}
        } finally {
            out.close();
        }
    } 

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
        return "Accesses a list of ingestors in the system, returns JSONP";
    }

}
