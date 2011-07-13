package gov.usgs.cida.ncetl.spec;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gov.usgs.cida.ncetl.utils.FTPIngestTask;
import gov.usgs.webservices.jdbc.spec.Spec;
import gov.usgs.webservices.jdbc.spec.mapping.ColumnMapping;
import gov.usgs.webservices.jdbc.spec.mapping.SearchMapping;
import gov.usgs.webservices.jdbc.spec.mapping.WhereClauseType;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jwalker
 */
public class IngestControlSpec extends AbstractNcetlSpec {

    private static final long serialVersionUID = 1L;
    private static final String TABLE_NAME = "ingest";
    public static final String CATALOG_ID = "catalog_id";
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
                    new ColumnMapping(CATALOG_ID, CATALOG_ID),
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
                    new SearchMapping(ID, ID, null, WhereClauseType.equals, null,
                                      null, null),
                    new SearchMapping("s_" + CATALOG_ID, CATALOG_ID, CATALOG_ID,
                                      WhereClauseType.equals, null, null, null),
                    new SearchMapping("s_" + NAME, NAME, NAME,
                                      WhereClauseType.equals, null, null, null),
                    new SearchMapping("s_" + FTP_LOCATION, FTP_LOCATION,
                                      FTP_LOCATION, WhereClauseType.equals, null,
                                      null, null),
                    new SearchMapping("s_" + RESCAN_EVERY, RESCAN_EVERY,
                                      RESCAN_EVERY, WhereClauseType.equals, null,
                                      null, null),
                    new SearchMapping("s_" + FILE_REGEX, FILE_REGEX, FILE_REGEX,
                                      WhereClauseType.equals, null, null, null),
                    new SearchMapping("s_" + SUCCESS_DATE, SUCCESS_DATE,
                                      SUCCESS_DATE, WhereClauseType.equals, null,
                                      null, null),
                    new SearchMapping("s_" + SUCCESS_TIME, SUCCESS_TIME,
                                      SUCCESS_TIME, WhereClauseType.equals, null,
                                      null, null),
                    new SearchMapping("s_" + USERNAME, USERNAME, USERNAME,
                                      WhereClauseType.equals, null, null, null),
                    new SearchMapping("s_" + PASSWORD, PASSWORD, PASSWORD,
                                      WhereClauseType.equals, null, null, null),
                    new SearchMapping("s_" + ACTIVE, ACTIVE, ACTIVE,
                                      WhereClauseType.equals, null, null, null),
                    new SearchMapping("s_" + INSERTED, INSERTED, INSERTED,
                                      WhereClauseType.equals, null, null, null),
                    new SearchMapping("s_" + UPDATED, UPDATED, UPDATED,
                                      WhereClauseType.equals, null, null, null)
                };
    }

    @Override
    public ResultSet getInsertedRows(Connection con) throws SQLException {
        ResultSet rs = super.getInsertedRows(con);
        // TODO For new rows, start up ingestor
        return rs;
    }

    @Override
    public ResultSet getUpdatedRows(Connection con) throws SQLException {
        ResultSet rs = super.getUpdatedRows(con);
        // TODO for updated rows, restart ingestors
        return rs;
    }

    public static List<FTPIngestTask> unmarshalAllIngestors(Connection con) throws SQLException, MalformedURLException {
        Map<String, String[]> params = Maps.newHashMap();
        return unmarshal(params, con);
    }
    
    public static FTPIngestTask unmarshalSpecificIngestor(String name, Connection con) throws SQLException, MalformedURLException {
        Map<String, String[]> params = Maps.newHashMap();
        params.put("s_" + NAME, new String[] {name});
        List<FTPIngestTask> specificTask = unmarshal(params, con);
        return (specificTask.size() > 0) ? specificTask.get(0) : null;
    }
    
    private static List<FTPIngestTask> unmarshal(Map<String, String[]> params, Connection con) throws SQLException, MalformedURLException {
        List<FTPIngestTask> taskList = Lists.newLinkedList();
        Spec spec = new IngestControlSpec();
        
        Spec.loadParameters(spec, params); // everything
        ResultSet rs = Spec.getResultSet(spec, con);

        FTPIngestTask task = null;
        while (rs.next()) {
            String name = rs.getString(NAME);
            String location = rs.getString(FTP_LOCATION);
            task = new FTPIngestTask.Builder(name, location)
                .active(rs.getBoolean(ACTIVE))
                .fileRegex(rs.getString(FILE_REGEX))
                .username(rs.getString(USERNAME))
                .password(rs.getString(PASSWORD))
                .rescanEvery(rs.getLong(RESCAN_EVERY))
                .build();
            taskList.add(task);
        }
        return taskList;
    }
}
