package gov.usgs.cida.ncetl.spec;

import com.google.common.collect.Maps;
import gov.usgs.webservices.jdbc.spec.Spec;
import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import thredds.catalog.ThreddsMetadata.Vocab;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class KeywordSpec  extends AbstractNcetlSpec {
    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "keyword";
    public static final String CONTROLLED_VOCABULARY_ID = "controlled_vocabulary_id";
    public static final String VALUE = "value";

    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(CONTROLLED_VOCABULARY_ID, CONTROLLED_VOCABULARY_ID),
                    new ColumnMapping(VALUE, VALUE),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + CONTROLLED_VOCABULARY_ID, CONTROLLED_VOCABULARY_ID, CONTROLLED_VOCABULARY_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + VALUE, VALUE, VALUE, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }

    static Vocab unmarshal(int id, Connection con) throws SQLException {
        Spec spec = new KeywordSpec();
        Map<String, String[]> params = Maps.newHashMap();
        params.put(ID, new String[] { "" + id });
        Spec.loadParameters(spec, params);
        ResultSet rs = Spec.getResultSet(spec, con);
        
        Vocab vocab = null;
        if (rs.next()) {
            String value = rs.getString(VALUE);
            int vocabId = rs.getInt(CONTROLLED_VOCABULARY_ID);
            vocab = ControlledVocabularySpec.lookupAndAddText(vocabId, value, con);
        }
        return vocab;
    }
    
}
