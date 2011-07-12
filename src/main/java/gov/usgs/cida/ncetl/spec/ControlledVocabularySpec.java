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
public class ControlledVocabularySpec extends AbstractNcetlSpec {
    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "controlled_vocabulary";
    public static final String VOCAB = "vocab";
    
    @Override
    public boolean setupAccess_DELETE() {
        return false;
    }

    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(VOCAB, VOCAB),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + VOCAB, VOCAB, VOCAB, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }
    
    public static Vocab lookupAndAddText(int id, String text, Connection con) throws SQLException {
        Spec spec = new ControlledVocabularySpec();
        Map<String, String[]> params = Maps.newHashMap();
        params.put(ID, new String[] { "" + id });
        Spec.loadParameters(spec, params);
        ResultSet rs = Spec.getResultSet(spec, con);

        if (rs.next()) {
            String vocab = rs.getString(VOCAB);
            return new Vocab(text, vocab);
        }
        else {
            return null;
        }
    }
}
