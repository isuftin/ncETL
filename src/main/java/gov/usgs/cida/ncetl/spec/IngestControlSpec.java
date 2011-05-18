package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.Spec;
import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;
import gov.usgs.webservices.jdbc.util.CleaningOption;

/**
 *
 * @author jwalker
 */
public class IngestControlSpec extends Spec {

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
    public ColumnMapping[] setupColumnMap() {
        // CREATE TABLE ingests (ftpLocation varchar(128), rescanEvery bigint, fileRegex varchar(64), successDate date, successTime time, username varchar(64), password varchar(64), active boolean)
        return new ColumnMapping[] {
            //new ColumnMapping("name", "name"),
            new ColumnMapping("ftpLocation", "ftpLocation"),
            new ColumnMapping("rescanEvery", "rescanEvery"),
            new ColumnMapping("fileRegex", "fileRegex"),
            new ColumnMapping("successDate", "successDate"),
            new ColumnMapping("successTime", "successTime"),
            new ColumnMapping("username", "username"),
            new ColumnMapping("password", "password"),
            new ColumnMapping("active", "active")
        };
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
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping("ftpLocation", "ftpLocation", null,
                                      WhereClauseType.equals,
                                      CleaningOption.none, null,
                                      null)
        };
    }

    @Override
    public String setupTableName() {
        return "ingests";
    }
    
}
