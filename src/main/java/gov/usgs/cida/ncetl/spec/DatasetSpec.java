package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class DatasetSpec extends AbstractNcetlSpec {
    private static final long serialVersionUID = 1L;
    // id int, catalog_id int, collection_type_id int, data_type_id int, name varchar(64), ncid varchar(128), authority varchar(64), inserted boolean, updated boolean
    private static final String TABLE_NAME = "datasets";
    private static final String CATALOG_ID = "catalog_id";
    private static final String COLLECTION_TYPE_ID = "collection_type_id";
    private static final String DATA_TYPE_ID = "data_type_id";
    private static final String NCID = "ncid";
    private static final String AUTHORITY = "authority";
    private static final String NAME = "name";

    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(CATALOG_ID, CATALOG_ID),
                    new ColumnMapping(COLLECTION_TYPE_ID, COLLECTION_TYPE_ID),
                    new ColumnMapping(DATA_TYPE_ID, DATA_TYPE_ID),
                    new ColumnMapping(NCID, NCID),
                    new ColumnMapping(AUTHORITY, AUTHORITY),
                    new ColumnMapping(NAME, NAME),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + CATALOG_ID, CATALOG_ID, CATALOG_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + COLLECTION_TYPE_ID, COLLECTION_TYPE_ID, COLLECTION_TYPE_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + DATA_TYPE_ID, DATA_TYPE_ID, DATA_TYPE_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + NCID, NCID, NCID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + AUTHORITY, AUTHORITY, AUTHORITY, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + NAME, NAME, NAME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }
}
