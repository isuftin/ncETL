package thredds.catalog;

import gov.usgs.cida.ncetl.spec.DatasetSpec;
import gov.usgs.webservices.jdbc.spec.Spec;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public final class DatasetHelper {
    
    private DatasetHelper() {}
    
    public static InvDataset syncWithDatabase(int id, InvCatalog parent, Connection con) {
        InvDataset editMe = parent.topDataset;
        
        DatasetSpec spec = new DatasetSpec();
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("s_catalog_id", new String[] {"" + id});
        Spec.loadParameters(spec, params);
        try {
            ResultSet result = Spec.getResultSet(spec, con);
        }
        catch (SQLException ex) {
            Logger.getLogger(DatasetHelper.class.getName()).log(Level.SEVERE,
                                                                null, ex);
        }
        // search database for datasets with id=id
        // set all the attributes and leaf nodes
        // search for children
        // recurse!
        return editMe;
    }
}
