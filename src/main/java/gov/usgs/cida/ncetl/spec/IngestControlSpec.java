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
 * @author jwalker
 */
public class IngestControlSpec extends Spec {

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
                    new ColumnMapping("name", "name"),
                    new ColumnMapping("ftpLocation", "ftpLocation"),
                    new ColumnMapping("rescanEvery", "rescanEvery"),
                    new ColumnMapping("fileRegex", "fileRegex"),
                    new ColumnMapping("successDate", "successDate"),
                    new ColumnMapping("successTime", "successTime"),
                    new ColumnMapping("username", "username"),
                    new ColumnMapping("password", "password"),
                    new ColumnMapping("active", "active"),
                    new ColumnMapping("inserted", null),
                    new ColumnMapping("updated", null)
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
            new SearchMapping("s_name", "name", "name", WhereClauseType.equals, null, null, null),
            new SearchMapping(null, "ftpLocation", "ftpLocation", null, null, null, null),
            new SearchMapping(null, "rescanEvery", "rescanEvery", null, null, null, null),
            new SearchMapping(null, "fileRegex", "fileRegex", null, null, null, null),
            new SearchMapping(null, "successDate", "successDate", null, null, null, null),
            new SearchMapping(null, "successTime", "successTime", null, null, null, null),
            new SearchMapping(null, "username", "username", null, null, null, null),
            new SearchMapping(null, "password", "password", null, null, null, null),
            new SearchMapping(null, "active", "active", null, null, null, null),
            new SearchMapping("s_inserted", "inserted", "inserted", WhereClauseType.equals, null, null, null),
            new SearchMapping("s_updated", "updated", "updated", WhereClauseType.equals, null, null, null)
        };
    }

    @Override
    public String setupTableName() {
        return "ingests";
    }

    @Override
    public ResultSet getUpdatedRows(Connection con) throws SQLException {
        ResultSet result = null;
        Spec spec = new IngestControlSpec();
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("s_updated", new String[] {"true"});
        Spec.loadParameters(spec, params);
        List<String> names = ServiceUtils.getStringsFromDB(spec, con, "name");
        
        params = new HashMap<String, String[]>();
        params.put("updated", new String[] {"false"});
        params.put("s_name", names.toArray(new String[0]));
        spec = new IngestControlSpec();
        Spec.loadParameters(spec, params);
        Spec.updateRow(spec, con);
        
        spec = new IngestControlSpec();
        params = new HashMap<String, String[]>();
        params.put("s_name", names.toArray(new String[0]));
        
        result = Spec.getResultSet(spec, con);
        return result;
    }

    @Override
    public ResultSet getInsertedRows(Connection con) throws SQLException {
        ResultSet result = null;
        Spec spec = new IngestControlSpec();
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("s_inserted", new String[] {"true"});
        Spec.loadParameters(spec, params);
        List<String> names = ServiceUtils.getStringsFromDB(spec, con, "name");
        
        params = new HashMap<String, String[]>();
        params.put("inserted", new String[] {"false"});
        params.put("s_name", names.toArray(new String[0]));
        spec = new IngestControlSpec();
        Spec.loadParameters(spec, params);
        Spec.updateRow(spec, con);
        
        spec = new IngestControlSpec();
        params = new HashMap<String, String[]>();
        params.put("s_name", names.toArray(new String[0]));
        Spec.loadParameters(spec, params);
        
        result = Spec.getResultSet(spec, con);
        return result;
    }
    
    
}
