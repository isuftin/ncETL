package gov.usgs.cida.ncetl.spec;

import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;

/**
 *
 * @author jwalker
 */
public class IngestControlSpec extends AbstractNcetlSpec {
    private static final long serialVersionUID = 1L;
    
    private static final String TABLE_NAME = "ingest";
    public static final String NAME = "name";
    public static final String FTP_LOCATION = "ftpLocation";
    public static final String RESCAN_EVERY = "rescanEvery";
    public static final String FILE_REGEX = "fileRegex";
    public static final String SUCCESS_DATE = "successDate";
    public static final String SUCCESS_TIME = "successTime";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ACTIVE = "active";
    

    @Override
    public String setupTableName() {
        return TABLE_NAME;
    }
    
    @Override
    public ColumnMapping[] setupColumnMap() {
        return new ColumnMapping[] {
                    new ColumnMapping(ID, ID),
                    new ColumnMapping(NAME, NAME),
                    new ColumnMapping(FTP_LOCATION, FTP_LOCATION),
                    new ColumnMapping(RESCAN_EVERY, RESCAN_EVERY),
                    new ColumnMapping(FILE_REGEX, FILE_REGEX),
                    new ColumnMapping(SUCCESS_DATE, SUCCESS_DATE),
                    new ColumnMapping(SUCCESS_TIME, SUCCESS_TIME),
                    new ColumnMapping(USERNAME, USERNAME),
                    new ColumnMapping(PASSWORD, PASSWORD),
                    new ColumnMapping(ACTIVE, ACTIVE),
                    new ColumnMapping(INSERTED, null),
                    new ColumnMapping(UPDATED, null)
                };
    }

    @Override
    public SearchMapping[] setupSearchMap() {
        return new SearchMapping[] {
            new SearchMapping(ID, ID, null, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + NAME, NAME, NAME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + FTP_LOCATION, FTP_LOCATION, FTP_LOCATION, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + RESCAN_EVERY, RESCAN_EVERY, RESCAN_EVERY, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + FILE_REGEX, FILE_REGEX, FILE_REGEX, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + SUCCESS_DATE, SUCCESS_DATE, SUCCESS_DATE, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + SUCCESS_TIME, SUCCESS_TIME, SUCCESS_TIME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + USERNAME, USERNAME, USERNAME, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + PASSWORD, PASSWORD, PASSWORD, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + ACTIVE, ACTIVE, ACTIVE, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + INSERTED, INSERTED, INSERTED, WhereClauseType.equals, null, null, null),
            new SearchMapping("s_" + UPDATED, UPDATED, UPDATED, WhereClauseType.equals, null, null, null)
        };
    }
}
