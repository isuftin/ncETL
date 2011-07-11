package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class TimeCoverageSpec  extends AbstractNcetlSpec {
    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "time_coverage";
    private static final String DATASET_ID = "dataset_id";
    private static final String START_ID = "start_id";
    private static final String END_ID = "end_id";
    private static final String DURATION = "duration";
    private static final String RESOLUTION = "resolution";
    
    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(DATASET_ID, DATASET_ID),
                    new ColumnMapping(START_ID, START_ID),
                    new ColumnMapping(END_ID, END_ID),
                    new ColumnMapping(DURATION, DURATION),
                    new ColumnMapping(RESOLUTION, RESOLUTION),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + DATASET_ID, DATASET_ID, DATASET_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + START_ID, START_ID, START_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + END_ID, END_ID, END_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + DURATION, DURATION, DURATION, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + RESOLUTION, RESOLUTION, RESOLUTION, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }

}
