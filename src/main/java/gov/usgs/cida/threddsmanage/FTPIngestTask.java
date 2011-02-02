/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.usgs.cida.threddsmanage;

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

/**
 *
 * @author jwalker
 */
public class FTPIngestTask extends TimerTask implements Runnable {

	private static final Logger log = Logger.getLogger(FTPIngestTask.class.getName());

	public static final long DEFAULT_RESCAN_PERIOD = 1000 * 60 * 60;
	public static final String DEFAULT_PASSWORD = "anonymous";
	public static final String DEFAULT_USER = "anonymous";
	public static final String EVERYTHING_REGEX = ".*";

	private FTPIngestTask() {
		client = new FTPClient();
		lastSuccessfulRun = new DateTime(0L);
	}

	public long getRescanEvery() {
		return rescanEvery;
	}

	@Override
	public void run() {
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
				if (!ingestDirectory(file.getName())) {
					completedSuccessfully = false;
				}
			}
			else {
				Matcher matcher = fileRegex.matcher(file.getName());
				if (matcher.matches()) {
					if (!client.retrieveFile(dir, new FileOutputStream(file.getName()))) {
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

		private URL ftpLocation;
		private long rescanEvery;
		private FTPClient client;
		private Pattern fileRegex;
		private DateTime startDate;
		private String username;
		private String password;

		public Builder(String ftpLocation) throws MalformedURLException {
			location(ftpLocation);
			rescanEvery = DEFAULT_RESCAN_PERIOD;
			fileRegex = Pattern.compile(EVERYTHING_REGEX);
			// start at Jan 1, 1970 0Z
			startDate = new DateTime(0L);
			username = DEFAULT_USER;
			password = DEFAULT_PASSWORD;
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
