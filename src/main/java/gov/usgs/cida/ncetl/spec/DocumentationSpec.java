package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class DocumentationSpec  extends AbstractNcetlSpec {
    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "documentation";
    private static final String DOCUMENTATION_TYPE_ID = "documentation_type_id";
    private static final String DATASET_ID = "dataset_id";
    private static final String XLINK_HREF = "xlink_href";
    private static final String XLINK_TITLE = "xlink_title";
    private static final String TEXT = "text";
    
    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(DOCUMENTATION_TYPE_ID, DOCUMENTATION_TYPE_ID),
                    new ColumnMapping(DATASET_ID, DATASET_ID),
                    new ColumnMapping(XLINK_HREF, XLINK_HREF),
                    new ColumnMapping(XLINK_TITLE, XLINK_TITLE),
                    new ColumnMapping(TEXT, TEXT),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + DOCUMENTATION_TYPE_ID, DOCUMENTATION_TYPE_ID, DOCUMENTATION_TYPE_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + DATASET_ID, DATASET_ID, DATASET_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + XLINK_HREF, XLINK_HREF, XLINK_HREF, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + XLINK_TITLE, XLINK_TITLE, XLINK_TITLE, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + TEXT, TEXT, TEXT, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }
}
