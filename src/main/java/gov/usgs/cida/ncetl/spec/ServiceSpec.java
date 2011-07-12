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
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.DbUtils;
import thredds.catalog.InvCatalog;
import thredds.catalog.InvService;
import thredds.catalog.ServiceType;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class ServiceSpec extends AbstractNcetlSpec {
    
    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "service";
    public static final String SERVICE_ID = "service_id";
    public static final String CATALOG_ID = "catalog_id";
    public static final String SERVICE_TYPE_ID = "service_type_id";
    public static final String NAME = "name";
    public static final String BASE = "base";
    public static final String DESCRIPTION = "description";
    public static final String SUFFIX = "suffix";
    
    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
    
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(SERVICE_ID, SERVICE_ID),
                    new ColumnMapping(CATALOG_ID, CATALOG_ID),
                    new ColumnMapping(SERVICE_TYPE_ID, SERVICE_TYPE_ID),
                    new ColumnMapping(NAME, NAME),
                    new ColumnMapping(BASE, BASE),
                    new ColumnMapping(DESCRIPTION, DESCRIPTION),
                    new ColumnMapping(SUFFIX, SUFFIX),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + SERVICE_ID, SERVICE_ID, SERVICE_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + CATALOG_ID, CATALOG_ID, CATALOG_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + SERVICE_TYPE_ID, SERVICE_TYPE_ID, SERVICE_TYPE_ID, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + NAME, NAME, NAME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + BASE, BASE, BASE, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + DESCRIPTION, DESCRIPTION, DESCRIPTION, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + SUFFIX, SUFFIX, SUFFIX, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }
    
    protected static String lookupServiceNameByCatalogId(int catalogId, Connection con) throws SQLException {
        Spec spec = new ServiceSpec();
        Map<String, String[]> params = Maps.newHashMap();
        params.put("s_" + CATALOG_ID, new String[]{"" + catalogId});
        Spec.loadParameters(spec, params);
        ResultSet rs = null;
        try {
            rs = Spec.getResultSet(spec, con);
            if (rs.next()) {
                return rs.getString(NAME.toUpperCase());
            }
        } finally {
            DbUtils.closeQuietly(rs);
        }
        
        return null;
    }
    
    private static List<InvService> unmarshalWithColumn(int id, String column, Connection con) throws SQLException {
        List<InvService> services = Lists.newLinkedList();
        Spec spec = new ServiceSpec();
        Map<String, String[]> params = Maps.newHashMap();
        params.put("s_" + column, new String[]{"" + id});
        Spec.loadParameters(spec, params);
        ResultSet rs = Spec.getResultSet(spec, con);
        
        while (rs.next()) {
            int serviceId = rs.getInt(SERVICE_ID);
            List<InvService> subservices = unmarshalWithColumn(serviceId, SERVICE_ID, con);
            ServiceType type = ServiceTypeSpec.lookup(rs.getInt(SERVICE_TYPE_ID), con);
            String name = rs.getString(NAME);
            String base = rs.getString(BASE);
            String description = rs.getString(DESCRIPTION);
            String suffix = rs.getString(SUFFIX);
            InvService service = new InvService(name, type.toString(), base, suffix, description);
            for(InvService subservice : subservices) {
                service.addService(subservice);
            }
            services.add(service);
        }
        
        return services;
    }
    
    protected static List<InvService> unmarshal(int id, Connection con) throws SQLException {
        return unmarshalWithColumn(id, CATALOG_ID, con);
    }
}
