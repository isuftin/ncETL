package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class DateTypeFormattedSpec extends AbstractNcetlSpec {

    private static final long serialVersionUID = 1L;
    private static final String TABLE_NAME = "date_type_formatted";
    private static final String FORMAT = "format";
    private static final String VALUE = "value";
    private static final String DATE_TYPE_ENUM_ID = "date_type_enum_id";

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
}
