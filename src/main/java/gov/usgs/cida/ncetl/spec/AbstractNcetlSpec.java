package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.Spec;
import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author isuftin
 */
public abstract class AbstractNcetlSpec extends Spec {
    @Override
    public boolean setupAccess_DELETE() {
        return true;
    }

    @Override
    public boolean setupAccess_INSERT() {
        return true;
    }

    @Override
    public boolean setupAccess_READ() {
        return true;
    }

    @Override
    public boolean setupAccess_UPDATE() {
        return true;
    }

    @Override
    public String setupDocTag() {
        return "success";
    }

    @Override
    public String setupRowTag() {
        return "data";
    }

    @Override
    public abstract String setupTableName();
    
    @Override
    public abstract SearchMapping[] setupSearchMap();
    
    @Override
    public abstract ColumnMapping[] setupColumnMap();
    
    @Override
    public abstract ResultSet getUpdatedRows(Connection con) throws SQLException;
    
    @Override
    public abstract ResultSet getInsertedRows(Connection con) throws SQLException;
    
    
    
}
