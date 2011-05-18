package gov.usgs.cida.threddsmanage;

import gov.usgs.cida.dcpt.FTPIngestTask;
import org.junit.Ignore;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Timer;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jwalker
 */
public class FTPIngestTaskTest {

	private static File tmpFile;

	@AfterClass
	public static void tearDown() {
		//if (tmpFile != null) tmpFile.delete();
	}

	@Test
	@Ignore
	public void testIngest() throws InterruptedException, MalformedURLException {
//		Timer timer = new Timer("IngestTimer", true);
//		try {
//			FTPIngestTask ingest = new FTPIngestTask.Builder("ftp://ftp.hpc.ncep.noaa.gov/npvu/rfcqpe/20110201/").rescanEvery(5 * 1000).build();
//			timer.scheduleAtFixedRate(ingest, 0L, ingest.getRescanEvery());
//		}
//		catch (MalformedURLException ex) {
//			// TODO log error / report problem
//		}
//		Thread.sleep(1000 * 60 * 60);
		FTPIngestTask ingest = new FTPIngestTask.Builder("test", "ftp://ftp.hpc.ncep.noaa.gov/npvu/rfcqpe/20110518/").rescanEvery(5 * 1000).fileRegex(".*").build();
		ingest.run();
		tmpFile = new File("/tmp/QPE.20110518.009.105");
		assertTrue(tmpFile.exists());
	}
}
