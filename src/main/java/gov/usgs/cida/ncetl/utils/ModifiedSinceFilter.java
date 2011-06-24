package gov.usgs.cida.ncetl.utils;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.joda.time.DateTime;

/**
 *
 * @author jwalker
 */
public class ModifiedSinceFilter implements FTPFileFilter {

	private DateTime startTime;

	public ModifiedSinceFilter(DateTime startTime) {
		this.startTime = startTime;
	}

	@Override
	public boolean accept(FTPFile ftpf) {
		DateTime ftpTime = new DateTime(ftpf.getTimestamp());
		return ftpTime.isAfter(startTime);
	}
}
