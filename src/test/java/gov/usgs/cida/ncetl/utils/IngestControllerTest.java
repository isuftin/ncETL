package gov.usgs.cida.ncetl.utils;

import java.net.MalformedURLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class IngestControllerTest {
    
    public IngestControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of restartIngestTimer method, of class IngestController.
     */
    @Test
    public void testStartIngestTimer() throws MalformedURLException, InterruptedException {
        FTPIngestTask ingest = new FTPIngestTask.Builder("test", "ftp://ftp.hpc.ncep.noaa.gov/npvu/rfcqpe/20110710/").rescanEvery(5 * 1000).fileRegex(".*").active(true).build();
        IngestController.startIngestTimer(ingest);
        Thread.sleep(1000 * 15);
        
        // TODO review the generated test code and remove the default call to fail.

    }
}
