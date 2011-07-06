package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.Spec;
import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;
import gov.usgs.webservices.jdbc.util.ServiceUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class CatalogSpec extends Spec {

    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "catalog";
    private static final String ID = "id";
    private static final String CATALOG_ID = "catalog_id";
    private static final String LOCATION = "location";
    private static final String NAME = "name";
    private static final String EXPIRES = "expires";
    private static final String VERSION = "version";
    private static final String INSERTED = "inserted";
    private static final String UPDATED = "updated";
    
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
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(CATALOG_ID, CATALOG_ID),
                    new ColumnMapping(LOCATION, LOCATION),
                    new ColumnMapping(NAME, NAME),
                    new ColumnMapping(EXPIRES, EXPIRES),
                    new ColumnMapping(VERSION, VERSION),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
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
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + CATALOG_ID, CATALOG_ID, CATALOG_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + LOCATION, LOCATION, LOCATION, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + NAME, NAME, NAME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + EXPIRES, EXPIRES, EXPIRES, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + VERSION, VERSION, VERSION, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }

    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }

    @Override
    public ResultSet getUpdatedRows(Connection con) throws SQLException {
        ResultSet result = null;
        Spec spec = new IngestControlSpec();
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("s_" + UPDATED, new String[] {"true"});
        Spec.loadParameters(spec, params);
        List<String> names = ServiceUtils.getStringsFromDB(spec, con, "name");
        
        params = new HashMap<String, String[]>();
        params.put(UPDATED, new String[] {"false"});
        params.put(ID, names.toArray(new String[0]));
        spec = new IngestControlSpec();
        Spec.loadParameters(spec, params);
        Spec.updateRow(spec, con);
        
        spec = new IngestControlSpec();
        params = new HashMap<String, String[]>();
        params.put(ID, names.toArray(new String[0]));
        
        result = Spec.getResultSet(spec, con);
        return result;
    }

    @Override
    public ResultSet getInsertedRows(Connection con) throws SQLException {
        ResultSet result = null;
        Spec spec = new IngestControlSpec();
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("s_" + INSERTED, new String[] {"true"});
        Spec.loadParameters(spec, params);
        List<String> names = ServiceUtils.getStringsFromDB(spec, con, "name");
        
        params = new HashMap<String, String[]>();
        params.put(INSERTED, new String[] {"false"});
        params.put(ID, names.toArray(new String[0]));
        spec = new IngestControlSpec();
        Spec.loadParameters(spec, params);
        Spec.updateRow(spec, con);
        
        spec = new IngestControlSpec();
        params = new HashMap<String, String[]>();
        params.put(ID, names.toArray(new String[0]));
        Spec.loadParameters(spec, params);
        
        result = Spec.getResultSet(spec, con);
        return result;
    }
}
