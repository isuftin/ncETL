package gov.usgs.cida.ncetl.service;

import gov.usgs.cida.ncetl.spec.PropertySpec;
import gov.usgs.webservices.jdbc.routing.ActionType;
import gov.usgs.webservices.jdbc.routing.InvalidServiceException;
import gov.usgs.webservices.jdbc.routing.UriRouter;
import gov.usgs.webservices.jdbc.service.WebService;
import gov.usgs.webservices.jdbc.spec.Spec;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class PropertyService extends WebService {
    private static final long serialVersionUID = 1L;

    public PropertyService() {
        this.enableCaching = false;
        this.specMapping.put("default", PropertySpec.class);
    }

    @Override
    protected void checkForValidParams(Spec spec) {
    } 

    @Override
    protected Map<String, String[]> defineParameters(HttpServletRequest req,
                                                     UriRouter router,
                                                     Map<String, String[]> params)
            throws InvalidServiceException {
       Map<String, String[]> tmpParams = new HashMap<String, String[]>();
       tmpParams.putAll(super.defineParameters(req, router, params));
       
       ActionType action = router.getActionTypeFromUri();
       
       if (ActionType.create == action) {
           tmpParams.put("inserted", new String[] {"true"});
       } else if (ActionType.update == action) {
           tmpParams.put("updated", new String[] {"true"});
       }
       
       return tmpParams;
    }

}
