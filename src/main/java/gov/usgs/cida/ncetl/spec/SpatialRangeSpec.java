package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class SpatialRangeSpec  extends AbstractNcetlSpec {
    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "spatial_range";
    private static final String GEOSPATIAL_COVERAGE_ID = "geospatial_coverage_id";
    private static final String SPATIAL_RANGE_TYPE_ID = "spatial_range_type_id";
    private static final String START = "start";
    private static final String SIZE = "size";
    private static final String RESOLUTION = "resolution";
    private static final String UNITS = "units";
    
    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(GEOSPATIAL_COVERAGE_ID, GEOSPATIAL_COVERAGE_ID),
                    new ColumnMapping(SPATIAL_RANGE_TYPE_ID, SPATIAL_RANGE_TYPE_ID),
                    new ColumnMapping(START, START),
                    new ColumnMapping(SIZE, SIZE),
                    new ColumnMapping(RESOLUTION, RESOLUTION),
                    new ColumnMapping(UNITS, UNITS),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + GEOSPATIAL_COVERAGE_ID, GEOSPATIAL_COVERAGE_ID, GEOSPATIAL_COVERAGE_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + SPATIAL_RANGE_TYPE_ID, SPATIAL_RANGE_TYPE_ID, SPATIAL_RANGE_TYPE_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + START, START, START, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + SIZE, SIZE, SIZE, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + RESOLUTION, RESOLUTION, RESOLUTION, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UNITS, UNITS, UNITS, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }
}
