package gov.usgs.cida.ncetl.utils;

import static gov.usgs.cida.ncetl.spec.CatalogSpec.*;
import gov.usgs.cida.ncetl.spec.CatalogSpec;
import gov.usgs.webservices.jdbc.spec.Spec;
import java.net.URI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import thredds.catalog.CatalogHelper;
import thredds.catalog.InvCatalog;
import thredds.catalog.InvCatalogModifier;
import ucar.nc2.units.DateType;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public final class SyncWithDatabase {
    public static InvCatalog syncCatalog(URI location, Connection con)
            throws SQLException, NamingException, ClassNotFoundException {
        //DatabaseUtil.getC
        InvCatalog cat = CatalogHelper.readCatalog(location);
        InvCatalogModifier modifyCat = new InvCatalogModifier(cat);
        CatalogSpec spec = new CatalogSpec();
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("s_location", new String[] { location.toString() });
        Spec.loadParameters(spec, params);
        ResultSet result = Spec.getResultSet(spec, con);

        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();
        int catalog_id = -1;
        if (result.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                if (ID.equalsIgnoreCase(columnName)) {
                    catalog_id = result.getInt(columnName);
                }
                else if (NAME.equalsIgnoreCase(columnName)) {
                    String value = result.getString(i);
                    modifyCat.setName(value);
                }
                else if (EXPIRES.equalsIgnoreCase(columnName)) {
                    Date value = result.getDate(columnName);
                    modifyCat.setExpires(new DateType(false, value));
                }
                else if (VERSION.equalsIgnoreCase(columnName)) {
                    String value = result.getString(columnName);
                    modifyCat.setVersion(value);
                }
            }
        }
        // Recursively do syncCatalog for sub-catalogs, datasets, services
        syncServices(catalog_id, cat, con);
        syncDatasets(catalog_id, cat, con);

        return cat;
    }

    private static void syncDatasets(int id, InvCatalog cat,
                                     Connection con) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static void syncServices(int id, InvCatalog cat,
                                     Connection con) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
