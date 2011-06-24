/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.usgs.cida.ncetl.utils;

import java.util.Calendar;
import org.apache.commons.net.ftp.FTPFile;
import org.joda.time.DateTime;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class ModifiedSinceFilterTest {

    public ModifiedSinceFilterTest() {
    }

    @Test
    public void testConstructor() {
        ModifiedSinceFilter test = new ModifiedSinceFilter(new DateTime());
        assertThat(test, is(notNullValue()));
    }

    @Test
    public void testAccept() {
        ModifiedSinceFilter obj = new ModifiedSinceFilter(new DateTime());
        FTPFile ftpFile = new FTPFile();
        
        Calendar cal = Calendar.getInstance();
        
        cal.add(Calendar.YEAR, 1);
        ftpFile.setTimestamp(cal);
        boolean test = obj.accept(ftpFile);
        assertThat(test, is(true));
        
        cal.add(Calendar.YEAR, -2);
        ftpFile.setTimestamp(cal);
        test = obj.accept(ftpFile);
        assertThat(test, is(false));
    }
}
