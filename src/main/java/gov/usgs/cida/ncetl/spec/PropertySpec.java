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
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Source;
import thredds.catalog2.Property;
import thredds.catalog2.simpleImpl.PropertyImplBuilder;
//import thredds.catalog2.simpleImpl.PropertyImpl;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class PropertySpec  extends AbstractNcetlSpec {
    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "property";
    public static final String DATASET_ID = "dataset_id";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    
    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(DATASET_ID, DATASET_ID),
                    new ColumnMapping(NAME, NAME),
                    new ColumnMapping(VALUE, VALUE),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + DATASET_ID, DATASET_ID, DATASET_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + NAME, NAME, NAME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + VALUE, VALUE, VALUE, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }
    
        public static List<Property> unmarshal(int datasetId, Connection con) throws SQLException, ParseException {
        List<Property> result = Lists.newLinkedList();
        Spec spec = new PublisherJoinSpec();
        Map<String, String[]> params = Maps.newHashMap();
        params.put("s_" + DATASET_ID, new String[] { "" + datasetId });
        Spec.loadParameters(spec, params);
        ResultSet rs = Spec.getResultSet(spec, con);

        while (rs.next()) {
            String name = rs.getString(NAME);
            String value = rs.getString(VALUE);
            PropertyImplBuilder propertyImpl = new PropertyImplBuilder(NAME, VALUE);
            Property property = propertyImpl.build();
            result.add(property);
        }
        
        return result;
    }
}
