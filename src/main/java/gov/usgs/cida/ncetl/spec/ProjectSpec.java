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
public class ProjectSpec extends AbstractNcetlSpec {

    private static final long serialVersionUID = 1L;
    private static final String TABLE_NAME = "creator";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CONTACT_URL = "contact_url";
    private static final String CONTACT_EMAIL = "contact_email";
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
                    new ColumnMapping(CONTACT_URL, CONTACT_URL),
                    new ColumnMapping(CONTACT_EMAIL, CONTACT_EMAIL),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
                    new SearchMapping(ID, ID, null, WhereClauseType.equals, null,
                                      null, null),
                    new SearchMapping("s_" + NAME, NAME, NAME,
                                      WhereClauseType.equals, null, null, null),
                    new SearchMapping("s_" + CONTACT_URL, CONTACT_URL,
                                      CONTACT_URL, WhereClauseType.equals, null,
                                      null, null),
                    new SearchMapping("s_" + CONTACT_EMAIL, CONTACT_EMAIL,
                                      CONTACT_EMAIL, WhereClauseType.equals,
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
        Spec spec = new ProjectSpec();
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("s_" + UPDATED, new String[] { "true" });
        Spec.loadParameters(spec, params);
        List<String> names = ServiceUtils.getStringsFromDB(spec, con, ID);

        params = new HashMap<String, String[]>();
        params.put(UPDATED, new String[] { "false" });
        params.put(ID, names.toArray(new String[0]));
        spec = new ProjectSpec();
        Spec.loadParameters(spec, params);
        Spec.updateRow(spec, con);

        spec = new ProjectSpec();
        params = new HashMap<String, String[]>();
        params.put(ID, names.toArray(new String[0]));

        result = Spec.getResultSet(spec, con);
        return result;
    }

    @Override
    public ResultSet getInsertedRows(Connection con) throws SQLException {
        ResultSet result = null;
        Spec spec = new ProjectSpec();
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("s_" + INSERTED, new String[] { "true" });
        Spec.loadParameters(spec, params);
        List<String> names = ServiceUtils.getStringsFromDB(spec, con, ID);

        params = new HashMap<String, String[]>();
        params.put(INSERTED, new String[] { "false" });
        params.put(ID, names.toArray(new String[0]));
        spec = new ProjectSpec();
        Spec.loadParameters(spec, params);
        Spec.updateRow(spec, con);

        spec = new ProjectSpec();
        params = new HashMap<String, String[]>();
        params.put(ID, names.toArray(new String[0]));
        Spec.loadParameters(spec, params);

        result = Spec.getResultSet(spec, con);
        return result;
    }
}
