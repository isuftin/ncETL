package gov.usgs.cida.threddsmanage;

import java.net.MalformedURLException;
import java.util.Timer;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jwalker
 */
public class FTPIngestTaskTest {

	@Test
	public void testIngest() throws InterruptedException {
		Timer timer = new Timer("IngestTimer", true);
		try {
			FTPIngestTask ingest = new FTPIngestTask.Builder("ftp://ftp.hpc.ncep.noaa.gov/npvu/rfcqpe/20110201/").rescanEvery(5 * 1000).build();
			timer.scheduleAtFixedRate(ingest, 0L, ingest.getRescanEvery());
		}
		catch (MalformedURLException ex) {
			// TODO log error / report problem
		}
		Thread.sleep(1000 * 60 * 60);
		assertTrue(true);
	}
}
