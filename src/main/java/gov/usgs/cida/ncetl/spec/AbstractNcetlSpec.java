package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.Spec;
import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.util.ServiceUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author isuftin
 */
public abstract class AbstractNcetlSpec extends Spec {
    public static final String ID = "id";
    protected static final String INSERTED = "inserted";
    protected static final String UPDATED = "updated";
    
    
    @Override
    public boolean setupAccess_DELETE() {
        return true;
    }

    @Override
    public boolean setupAccess_INSERT() {
        return true;
    }

    @Override
    public boolean setupAccess_READ() {
        return true;
    }

    @Override
    public boolean setupAccess_UPDATE() {
        return true;
    }

    @Override
    public String setupDocTag() {
        return "success";
    }

    @Override
    public String setupRowTag() {
        return "data";
    }

    @Override
    public abstract String setupTableName();
    
    @Override
    public abstract SearchMapping[] setupSearchMap();
    
    @Override
    public abstract ColumnMapping[] setupColumnMap();
    
    @Override
    public ResultSet getUpdatedRows(Connection con) throws SQLException {
        return getChangedRows(con, UPDATED, this.getClass());
    }
    
    @Override
    public ResultSet getInsertedRows(Connection con) throws SQLException {
        return getChangedRows(con, INSERTED, this.getClass());
    }
    
    protected static ResultSet getChangedRows(Connection con, String action, Class<? extends AbstractNcetlSpec> clazz)  throws SQLException {
        ResultSet result = null;
        Spec spec;
        try {
            spec = clazz.newInstance();
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        Map<String, String[]> params = new HashMap<String, String[]>(1);
        params.put("s_" + action, new String[] {"true"});
        Spec.loadParameters(spec, params);
        List<String> changedIds = ServiceUtils.getStringsFromDB(spec, con, ID);
        
        params = new HashMap<String, String[]>(2);
        params.put(action, new String[] {"false"});
        params.put(ID, changedIds.toArray(new String[changedIds.size()]));
        try {
            spec = clazz.newInstance();
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        Spec.loadParameters(spec, params);
        Spec.updateRow(spec, con);
        
        try {
            spec = clazz.newInstance();
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        params = new HashMap<String, String[]>(1);
        params.put(ID, changedIds.toArray(new String[changedIds.size()]));
        Spec.loadParameters(spec, params);
        
        result = Spec.getResultSet(spec, con);
        return result;
    }
    
}
