package gov.usgs.cida.ncetl.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.joda.time.DateTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author jwalker
 */
public final class FTPIngestTask extends TimerTask {
    private static final Logger LOG = Logger.getLogger(FTPIngestTask.class.getName());
    public static final long DEFAULT_RESCAN_PERIOD = 1000 * 60 * 60;
    public static final String DEFAULT_PASSWORD = "anonymous";
    public static final String DEFAULT_USER = "anonymous";
    public static final boolean DEFAULT_ACTIVE = false;
    public static final String EVERYTHING_REGEX = ".*";

    private FTPIngestTask() {
        client = new FTPClient();
        lastSuccessfulRun = new DateTime(0L);
    }

    public long getRescanEvery() {
        return rescanEvery;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public String getName() {
        return ingestName;
    }

    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("ftpLocation", ftpLocation);
        json.put("rescanEvery", rescanEvery);
        json.put("filePattern", fileRegex.pattern());
        json.put("lastSuccess", lastSuccessfulRun);
        json.put("username", username);
        json.put("password", password);
        return json.toJSONString();
    }

    public String toXMLString() {
        StringBuilder str = new StringBuilder();
        str.append("<?xml");
        return str.toString();
    }

    @Override
    public void run() {
        if (!active) {
            return;
        }
        boolean everythingIsGood = false;
        try {
            int port = (ftpLocation.getPort() != -1) ? ftpLocation.getPort() : ftpLocation.getDefaultPort();
            FTPClientConfig config = new FTPClientConfig(
                    FTPClientConfig.SYST_UNIX);
            client.configure(config);
            client.connect(ftpLocation.getHost(), port);
            client.login(username, password);
            String status = client.getStatus();
            LOG.info(status);
            client.changeWorkingDirectory(ftpLocation.getPath());
            everythingIsGood = ingestDirectory(".");
        }
        catch (SocketException ex) {
            LOG.error(ex.getMessage());
        }
        catch (IOException ex) {
            LOG.error(ex.getMessage());
        }

        if (everythingIsGood) {
            lastSuccessfulRun = new DateTime();
        }
    }

    private boolean ingestDirectory(String dir) throws IOException {
        boolean completedSuccessfully = true;
        FTPFile[] files = client.listFiles(dir, new ModifiedSinceFilter(
                lastSuccessfulRun));
        for (FTPFile file : files) {
            if (file.isDirectory()) {
                if (!ingestDirectory(dir + File.separator + file.getName())) {
                    completedSuccessfully = false;
                }
            } else {
                Matcher matcher = fileRegex.matcher(file.getName());
                if (matcher.matches() && !client.retrieveFile(file.getName(),
                        new FileOutputStream(
                        FileHelper.getDatasetsDirectory() + File.separator + file.getName()))) {
                    // TODO keep a list of files that failed to try to correct next time
                    completedSuccessfully = false;
                }
            }
        }
        return completedSuccessfully;
    }

    public static FTPIngestTask fromRequest(HttpServletRequest request) throws
            MalformedURLException {
        String name = request.getParameter("name");
        String ftpLoc = request.getParameter("ftpLocation");
        long rescan = Long.parseLong(request.getParameter("rescanEvery"));
        String regex = request.getParameter("fileRegex");
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        boolean active = ("true".equalsIgnoreCase(request.getParameter("active"))) ? true : false;
        FTPIngestTask ftpObj = (new Builder(name, ftpLoc)).rescanEvery(rescan).fileRegex(
                regex).username(user).password(pass).active(active).build();
        return ftpObj;
    }
    
    private String ingestName;
    private URL ftpLocation;
    private long rescanEvery;
    private FTPClient client;
    private Pattern fileRegex;
    private DateTime lastSuccessfulRun;
    private String username;
    private String password;
    private boolean active;

    public static class Builder {

        public Builder fileRegex(String fileRegex) {
            this.fileRegex = Pattern.compile(fileRegex);
            return this;
        }
        
        public Builder name(String name) {
            this.ingestName = name;
            return this;
        }

        public Builder location(String ftpLocation) throws MalformedURLException {
            this.ftpLocation = new URL(ftpLocation);
            return this;
        }

        public Builder rescanEvery(long rescanEvery) {
            this.rescanEvery = rescanEvery;
            return this;
        }

        public Builder startDate(DateTime start) {
            this.startDate = start;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }
        
        private String ingestName;
        private URL ftpLocation;
        private long rescanEvery;
        private FTPClient client;
        private Pattern fileRegex;
        private DateTime startDate;
        private String username;
        private String password;
        private boolean active;

        public Builder(String name, String url) throws MalformedURLException {
            ingestName = name;
            location(url);
            rescanEvery = DEFAULT_RESCAN_PERIOD;
            fileRegex = Pattern.compile(EVERYTHING_REGEX);
            // start at Jan 1, 1970 0Z
            startDate = new DateTime(0L);
            username = DEFAULT_USER;
            password = DEFAULT_PASSWORD;
            active = DEFAULT_ACTIVE;
        }

        public FTPIngestTask build() {
            FTPIngestTask ingest = new FTPIngestTask();
            ingest.ingestName = ingestName;
            ingest.ftpLocation = ftpLocation;
            ingest.rescanEvery = rescanEvery;
            ingest.fileRegex = fileRegex;
            ingest.lastSuccessfulRun = startDate;
            ingest.username = username;
            ingest.password = password;
            return ingest;
        }
    }
}
