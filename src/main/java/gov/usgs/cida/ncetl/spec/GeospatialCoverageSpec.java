package gov.usgs.cida.ncetl.spec;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gov.usgs.webservices.jdbc.spec.Spec;
import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import thredds.catalog.SpatialRangeType;
import thredds.catalog.ThreddsMetadata.GeospatialCoverage;
import thredds.catalog.ThreddsMetadata.Range;
import thredds.catalog.ThreddsMetadata.Vocab;
import static thredds.catalog.SpatialRangeType.*;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class GeospatialCoverageSpec  extends AbstractNcetlSpec {
    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "geospatial_coverage";
    public static final String DATASET_ID = "dataset_id";
    public static final String CONTROLLED_VOCAB_ID = "controlled_vocabulary_id";
    public static final String NAME = "name";
    public static final String ZPOSITIVE_ID = "zpositive_id";
    
    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(DATASET_ID, DATASET_ID),
                    new ColumnMapping(CONTROLLED_VOCAB_ID, CONTROLLED_VOCAB_ID),
                    new ColumnMapping(NAME, NAME),
                    new ColumnMapping(ZPOSITIVE_ID, ZPOSITIVE_ID),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + DATASET_ID, DATASET_ID, DATASET_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + CONTROLLED_VOCAB_ID, CONTROLLED_VOCAB_ID, CONTROLLED_VOCAB_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + NAME, NAME, NAME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + ZPOSITIVE_ID, ZPOSITIVE_ID, ZPOSITIVE_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }
    
    public static GeospatialCoverage unmarshal(int datasetId, Connection con) throws SQLException {
        Spec spec = new GeospatialCoverageSpec();
        Map<String, String[]> params = Maps.newHashMap();
        params.put("s_" + DATASET_ID, new String[] { "" + datasetId });
        Spec.loadParameters(spec, params);
        ResultSet rs = Spec.getResultSet(spec, con);
        
        GeospatialCoverage gc = null;
        if (rs.next()) {
            String name = rs.getString(NAME);
            int vocabId = rs.getInt(CONTROLLED_VOCAB_ID);
            Vocab vocab = ControlledVocabularySpec.lookupAndAddText(vocabId, name, con);
            
            int zId = rs.getInt(ZPOSITIVE_ID);
            String upOrDown = UpDownTypeSpec.lookup(zId, con);
            
            int id = rs.getInt(ID);
            Map<SpatialRangeType, Range> ranges = SpatialRangeSpec.unmarshal(id, con);
            
            gc = new GeospatialCoverage(ranges.get(EAST_WEST), ranges.get(NORTH_SOUTH),
                    ranges.get(UP_DOWN), Lists.asList(vocab, new Vocab[0]), upOrDown);
        }
        return gc;
    }
}
