package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class ServiceSpec extends AbstractNcetlSpec {
    
    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "services";
    private static final String SERVICE_ID = "service_id";
    private static final String CATALOG_ID = "catalog_id";
    private static final String SERVICE_TYPE_ID = "service_type_id";
    private static final String NAME = "name";
    private static final String BASE = "base";
    private static final String DESCRIPTION = "description";
    private static final String SUFFIX = "suffix";
    
    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
    
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(SERVICE_ID, SERVICE_ID),
                    new ColumnMapping(CATALOG_ID, CATALOG_ID),
                    new ColumnMapping(SERVICE_TYPE_ID, SERVICE_TYPE_ID),
                    new ColumnMapping(NAME, NAME),
                    new ColumnMapping(BASE, BASE),
                    new ColumnMapping(DESCRIPTION, DESCRIPTION),
                    new ColumnMapping(SUFFIX, SUFFIX),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + SERVICE_ID, SERVICE_ID, SERVICE_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + CATALOG_ID, CATALOG_ID, CATALOG_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + SERVICE_TYPE_ID, SERVICE_TYPE_ID, SERVICE_TYPE_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + NAME, NAME, NAME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + BASE, BASE, BASE, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + DESCRIPTION, DESCRIPTION, DESCRIPTION, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + SUFFIX, SUFFIX, SUFFIX, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }
}
