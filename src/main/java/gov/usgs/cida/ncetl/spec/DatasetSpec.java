package gov.usgs.cida.ncetl.spec;

import com.google.common.collect.Lists;
import gov.usgs.webservices.jdbc.spec.Spec;
import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import thredds.catalog.InvCatalog;
import thredds.catalog.InvDataset;
import thredds.catalog.InvDatasetWrapper;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class DatasetSpec extends AbstractNcetlSpec {
    private static final long serialVersionUID = 1L;
    // id int, catalog_id int, collection_type_id int, data_type_id int, name varchar(64), ncid varchar(128), authority varchar(64), inserted boolean, updated boolean
    private static final String TABLE_NAME = "datasets";
    public static final String CATALOG_ID = "catalog_id";
    public static final String COLLECTION_TYPE_ID = "collection_type_id";
    public static final String DATA_TYPE_ID = "data_type_id";
    public static final String NCID = "ncid";
    public static final String AUTHORITY = "authority";
    public static final String NAME = "name";

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
    
    public static List<InvDataset> unmarshal(int id, InvCatalog cat, Connection con) throws SQLException {
        List<InvDataset> result = Lists.newLinkedList();
        DatasetSpec spec = new DatasetSpec();
        Map<String, String[]> params = new HashMap<String, String[]>(1);
        params.put("s_catalog_id", new String[]{"" + id});
        Spec.loadParameters(spec, params);
        ResultSet rs = Spec.getResultSet(spec, con);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            InvDatasetWrapper invDsWrapper = null;
            InvDataset findDatasetByID = cat.findDatasetByID(rs.getString("NCID"));
            if (findDatasetByID == null) {
                invDsWrapper = new InvDatasetWrapper("", ""); // name and id are set below
            } else {
                invDsWrapper = new InvDatasetWrapper(findDatasetByID);
            }
            int datasetId = -1;

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                if (ID.equalsIgnoreCase(columnName)) {
                    datasetId = rs.getInt(columnName);
                } else if (AUTHORITY.equalsIgnoreCase(columnName)) {
                    invDsWrapper.setAuthority(rs.getString(columnName));
                } else if (NAME.equalsIgnoreCase(columnName)) {
                    invDsWrapper.setName(rs.getString(columnName));
                } else if (COLLECTION_TYPE_ID.equalsIgnoreCase(columnName)) {
                    int ctId = rs.getInt(columnName);
                    // TODO: do lookup on ctId
                } else if (DATA_TYPE_ID.equalsIgnoreCase(columnName)) {
                    int dtId = rs.getInt(columnName);
                    // TODO: do lookup on dtId
                } else if (NCID.equalsIgnoreCase(columnName)) {
                    invDsWrapper.setID(rs.getString(columnName));
                }
            }
            invDsWrapper.setServiceName(ServiceSpec.lookupServiceNameByCatalogId(id, con));
            result.add(invDsWrapper);
        }
        
        return result;
        // search database for datasets with id=id
        // set all the attributes and leaf nodes
        // search for children
        // recurse!
    }

    
}
