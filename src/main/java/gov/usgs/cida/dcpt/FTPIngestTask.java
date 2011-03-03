package gov.usgs.cida.dcpt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.joda.time.DateTime;
import org.apache.log4j.Logger;
import javax.xml.bind.JAXB;

/**
 *
 * @author jwalker
 */
public class FTPIngestTask extends TimerTask implements Runnable {

	private static final Logger log = Logger.getLogger(FTPIngestTask.class.getName());

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

	public String toJSONString() {
		StringBuilder str = new StringBuilder();
		str.append("{")
			.append("ftpLocation: '").append(ftpLocation).append("', ")
			.append("rescanEvery: ").append(rescanEvery).append(", ")
			.append("filePattern: '").append(fileRegex.pattern()).append("', ")
			.append("lastSuccess: '").append(lastSuccessfulRun.toString()).append("', ")
			.append("username: '").append(username).append("', ")
			.append("password: '").append(password.replaceAll(".", "\\*")).append("'")
			.append("}");
		return str.toString();
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
			FTPClientConfig config = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
			client.configure(config);
			client.connect(ftpLocation.getHost(), port);
			client.login(username, password);
			String status = client.getStatus();
			log.info(status);
			client.changeWorkingDirectory(ftpLocation.getPath());
			everythingIsGood = ingestDirectory(".");
		}
		catch (SocketException ex) {
			log.error(ex.getMessage());
		}
		catch (IOException ex) {
			log.error(ex.getMessage());
		}

		if (everythingIsGood) {
			lastSuccessfulRun = new DateTime();
		}
	}

	private boolean ingestDirectory(String dir) throws IOException {
		boolean completedSuccessfully = true;
		FTPFile[] files = client.listFiles(dir, new ModifiedSinceFilter(lastSuccessfulRun));
		for (FTPFile file : files) {
			if (file.isDirectory()) {
				if (!ingestDirectory(dir + File.separator + file.getName())) {
					completedSuccessfully = false;
				}
			}
			else {
				Matcher matcher = fileRegex.matcher(file.getName());
				if (matcher.matches()) {
					if (!client.retrieveFile(file.getName(), new FileOutputStream(DCPTConfig.FILE_STORE + File.separator + file.getName()))) {
						// TODO keep a list of files that failed to try to correct next time
						completedSuccessfully = false;
					}
				}
			}
		}
		return completedSuccessfully;
	}

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

		private URL ftpLocation;
		private long rescanEvery;
		private FTPClient client;
		private Pattern fileRegex;
		private DateTime startDate;
		private String username;
		private String password;
		private boolean active;

		public Builder(String ftpLocation) throws MalformedURLException {
			location(ftpLocation);
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
