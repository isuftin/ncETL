package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class PublisherSpec extends AbstractNcetlSpec {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "publisher";
    public static final String NAME = "name";
    public static final String CONTACT_URL = "contact_url";
    public static final String CONTACT_EMAIL = "contact_email";

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
    
//    static Source lookup(int datasetId, Connection con)  throws SQLException, ParseException {
//        Spec spec = new PublisherSpec();
//        Map<String, String[]> params = Maps.newHashMap();
//        Source source = null;
//        
//        params.put("s_" + ID, new String[] { "" + datasetId });
//        Spec.loadParameters(spec, params);
//        ResultSet rs = Spec.getResultSet(spec, con);
//        
//        if (rs.next()) {
//            String name = rs.getString(NAME);
//            String contactUrl = rs.getString(CONTACT_URL);
//            String contactEmail = rs.getString(CONTACT_EMAIL);
//            
//            //TODO: What to put for the vocabulary parameter in Vocab constructor?
//            source = new Source(new Vocab(name, ""), contactUrl, contactEmail);
//        }
//        
//        return source;
//    }

}
