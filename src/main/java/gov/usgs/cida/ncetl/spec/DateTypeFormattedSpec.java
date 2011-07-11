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
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DateTypeFormattedSpec extends AbstractNcetlSpec {

    private static final long serialVersionUID = 1L;
    private static final String TABLE_NAME = "date_type_formatted";
    private static final String ID = "id";
    private static final String FORMAT = "format";
    private static final String VALUE = "value";
    private static final String DATE_TYPE_ENUM_ID = "date_type_enum_id";
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
                    new ColumnMapping(FORMAT, FORMAT),
                    new ColumnMapping(VALUE, VALUE),
                    new ColumnMapping(DATE_TYPE_ENUM_ID, DATE_TYPE_ENUM_ID),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
                    new SearchMapping(ID, ID, null, WhereClauseType.equals, null,
                                      null, null),
                    new SearchMapping("s_" + FORMAT, FORMAT, FORMAT,
                                      WhereClauseType.equals, null, null, null),
                    new SearchMapping("s_" + VALUE, VALUE,
                                      VALUE, WhereClauseType.equals, null,
                                      null, null),
                    new SearchMapping("s_" + DATE_TYPE_ENUM_ID, DATE_TYPE_ENUM_ID,
                                      DATE_TYPE_ENUM_ID, WhereClauseType.equals,
                                      null, null, null),
                    new SearchMapping("s_" + INSERTED, INSERTED, INSERTED,
                                      WhereClauseType.equals, null, null, null),
                    new SearchMapping("s_" + UPDATED, UPDATED, UPDATED,
                                      WhereClauseType.equals, null, null, null)
                };
    }

    @Override
    public ResultSet getUpdatedRows(Connection con) throws SQLException {
        ResultSet result = null;
        Spec spec = new DateTypeFormattedSpec();
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("s_" + UPDATED, new String[] { "true" });
        Spec.loadParameters(spec, params);
        List<String> names = ServiceUtils.getStringsFromDB(spec, con, ID);

        params = new HashMap<String, String[]>();
        params.put(UPDATED, new String[] { "false" });
        params.put(ID, names.toArray(new String[names.size()]));
        spec = new DateTypeFormattedSpec();
        Spec.loadParameters(spec, params);
        Spec.updateRow(spec, con);

        spec = new DateTypeFormattedSpec();
        params = new HashMap<String, String[]>();
        params.put(ID, names.toArray(new String[names.size()]));
        Spec.loadParameters(spec, params);
        
        result = Spec.getResultSet(spec, con);
        return result;
    }

    @Override
    public ResultSet getInsertedRows(Connection con) throws SQLException {
        ResultSet result = null;
        Spec spec = new DateTypeFormattedSpec();
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("s_" + INSERTED, new String[] { "true" });
        Spec.loadParameters(spec, params);
        List<String> names = ServiceUtils.getStringsFromDB(spec, con, ID);

        params = new HashMap<String, String[]>();
        params.put(INSERTED, new String[] { "false" });
        params.put(ID, names.toArray(new String[names.size()]));
        spec = new DateTypeFormattedSpec();
        Spec.loadParameters(spec, params);
        Spec.updateRow(spec, con);

        spec = new DateTypeFormattedSpec();
        params = new HashMap<String, String[]>();
        params.put(ID, names.toArray(new String[names.size()]));
        Spec.loadParameters(spec, params);

        result = Spec.getResultSet(spec, con);
        return result;
    }
}
