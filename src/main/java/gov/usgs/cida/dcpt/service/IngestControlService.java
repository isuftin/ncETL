/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.usgs.cida.dcpt.service;

import gov.usgs.cida.ncetl.spec.IngestControlSpec;
import gov.usgs.webservices.jdbc.routing.InvalidServiceException;
import gov.usgs.webservices.jdbc.routing.UriRouter;
import gov.usgs.webservices.jdbc.service.WebService;
import gov.usgs.webservices.jdbc.spec.Spec;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jwalker
 */
public class IngestControlService extends WebService {

    public IngestControlService() {
        this.enableCaching = false;
        this.specMapping.put("default", IngestControlSpec.class);
    }

    @Override
    protected void checkForValidParams(Spec spec) {
    } 

    @Override
    protected Map<String, String[]> defineParameters(HttpServletRequest req,
                                                     UriRouter router,
                                                     Map<String, String[]> params)
            throws InvalidServiceException {
        return super.defineParameters(req, router, params);
    }

 
}
