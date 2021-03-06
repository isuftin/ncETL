package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.Spec;
import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import ucar.nc2.units.DateRange;
import ucar.nc2.units.DateType;
import ucar.nc2.units.TimeDuration;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class TimeCoverageSpec  extends AbstractNcetlSpec {
    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "time_coverage";
    public static final String DATASET_ID = "dataset_id";
    public static final String START_ID = "start_id";
    public static final String END_ID = "end_id";
    public static final String DURATION = "duration";
    public static final String RESOLUTION = "resolution";

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

    public static DateRange unmarshal(int datasetId, Connection con) throws SQLException, ParseException {
        Spec spec = new TimeCoverageSpec();
        Map<String, String[]> params = new HashMap<String, String[]>(1);
        params.put("s_" + DATASET_ID, new String[] { "" + datasetId });
        Spec.loadParameters(spec, params);
        ResultSet rs = Spec.getResultSet(spec, con);

        if (rs.next()) {
            DateType start = DateTypeFormattedSpec.lookup(rs.getInt(START_ID), con);
            DateType end = DateTypeFormattedSpec.lookup(rs.getInt(END_ID), con);
            String duration = rs.getString(DURATION);
            String resolution = rs.getString(RESOLUTION);
            TimeDuration td = TimeDuration.parseW3CDuration(duration);
            TimeDuration tr = TimeDuration.parseW3CDuration(resolution);
            return new DateRange(start, end, td, tr);
        }
        else {
            return null;
        }
    }
    
}
