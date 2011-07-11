package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class CatalogSpec extends AbstractNcetlSpec {

    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "catalog";
    private static final String LOCATION = "location";
    private static final String NAME = "name";
    private static final String EXPIRES = "expires";
    private static final String VERSION = "version";
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(LOCATION, LOCATION),
                    new ColumnMapping(NAME, NAME),
                    new ColumnMapping(EXPIRES, EXPIRES),
                    new ColumnMapping(VERSION, VERSION),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    
    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
            
    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + LOCATION, LOCATION, LOCATION, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + NAME, NAME, NAME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + EXPIRES, EXPIRES, EXPIRES, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + VERSION, VERSION, VERSION, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }
}
