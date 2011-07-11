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
public class IngestControlSpec extends AbstractNcetlSpec {
    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "ingest";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String FTP_LOCATION = "ftpLocation";
    private static final String RESCAN_EVERY = "rescanEvery";
    private static final String FILE_REGEX = "fileRegex";
    private static final String SUCCESS_DATE = "successDate";
    private static final String SUCCESS_TIME = "successTime";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ACTIVE = "active";
    private static final String INSERTED = "inserted";
    private static final String UPDATED = "updated";

    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(NAME, NAME),
                    new ColumnMapping(FTP_LOCATION, FTP_LOCATION),
                    new ColumnMapping(RESCAN_EVERY, RESCAN_EVERY),
                    new ColumnMapping(FILE_REGEX, FILE_REGEX),
                    new ColumnMapping(SUCCESS_DATE, SUCCESS_DATE),
                    new ColumnMapping(SUCCESS_TIME, SUCCESS_TIME),
                    new ColumnMapping(USERNAME, USERNAME),
                    new ColumnMapping(PASSWORD, PASSWORD),
                    new ColumnMapping(ACTIVE, ACTIVE),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + NAME, NAME, NAME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + FTP_LOCATION, FTP_LOCATION, FTP_LOCATION, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + RESCAN_EVERY, RESCAN_EVERY, RESCAN_EVERY, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + FILE_REGEX, FILE_REGEX, FILE_REGEX, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + SUCCESS_DATE, SUCCESS_DATE, SUCCESS_DATE, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + SUCCESS_TIME, SUCCESS_TIME, SUCCESS_TIME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + USERNAME, USERNAME, USERNAME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + PASSWORD, PASSWORD, PASSWORD, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + ACTIVE, ACTIVE, ACTIVE, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }

    @Override
    public ResultSet getUpdatedRows(Connection con) throws SQLException {
        ResultSet result = null;
        Spec spec = new IngestControlSpec();
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("s_" + UPDATED, new String[] {"true"});
        Spec.loadParameters(spec, params);
        List<String> names = ServiceUtils.getStringsFromDB(spec, con, ID);
        
        params = new HashMap<String, String[]>();
        params.put(UPDATED, new String[] {"false"});
        params.put(ID, names.toArray(new String[names.size()]));
        spec = new IngestControlSpec();
        Spec.loadParameters(spec, params);
        Spec.updateRow(spec, con);
        Spec.loadParameters(spec, params);
        
        spec = new IngestControlSpec();
        params = new HashMap<String, String[]>();
        params.put(ID, names.toArray(new String[names.size()]));
        Spec.loadParameters(spec, params);
        
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
        List<String> names = ServiceUtils.getStringsFromDB(spec, con, ID);
        
        params = new HashMap<String, String[]>();
        params.put(INSERTED, new String[] {"false"});
        params.put(ID, names.toArray(new String[names.size()]));
        spec = new IngestControlSpec();
        Spec.loadParameters(spec, params);
        Spec.updateRow(spec, con);
        
        spec = new IngestControlSpec();
        params = new HashMap<String, String[]>();
        params.put(ID, names.toArray(new String[names.size()]));
        Spec.loadParameters(spec, params);
        
        result = Spec.getResultSet(spec, con);
        return result;
    }
    
    
}
