package gov.usgs.cida.ncetl.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author isuftin
 */
public class FileHelperTest {
    
    private static final String tmpDir = System.getProperty("java.io.tmpdir") + File.separator + "test_delete_me" + File.separator;
    
    public FileHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        FileUtils.deleteDirectory(new File(tmpDir));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        FileUtils.deleteDirectory(new File(tmpDir));
    }
    
    @Before
    public void beforeTest() throws Exception {
        FileUtils.deleteDirectory(new File(tmpDir));
    }
    
    @After
    public void afterTest() throws Exception {
        FileUtils.deleteDirectory(new File(tmpDir));
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getBaseDirectory() {
        String test = FileHelper.getBaseDirectory();
        assertThat(test, is(notNullValue()));
    }
    
    @Test
    public void getDatabaseDirectory() {
        String test = FileHelper.getDatabaseDirectory();
        assertThat(test, is(notNullValue()));
    }
    
    @Test
    public void createDirectory() {
        File testFile = new File(tmpDir);
        try {
            FileHelper.createDirectory(testFile);
        } catch (IOException ex) {
            Logger.getLogger(FileHelperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertThat(testFile.exists(), is(true));
    }      
    
    @Test
    public void createDirectoryWithSubdirectories() {
        File testFile = new File(tmpDir);
        String sub1 = "sub1";
        String sub2 = "sub2";
        String sub3 = "sub3";
        
        try {
            FileHelper.createDirectory(testFile, sub1, sub2, sub3);
        } catch (IOException ex) {
            Logger.getLogger(FileHelperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertThat(testFile.exists(), is(true));
        assertThat(new File(testFile.getPath() + File.separator + sub1).exists(), is(true));
        assertThat(new File(testFile.getPath() + File.separator + sub2).exists(), is(true));
        assertThat(new File(testFile.getPath() + File.separator + sub3).exists(), is(true));
    }      
    
    @Test
    public void testDirAppend() {
        String directoryWithoutSep = "test";
        String directoryWithSep = "test/";
        String result = FileHelper.dirAppend(directoryWithoutSep, directoryWithoutSep);
        String result2 = FileHelper.dirAppend(directoryWithSep, directoryWithoutSep);
        assertThat(result, is(equalTo(result2)));
    }
    
    /**
     * This tests to make sure that createDirectory does not touch
     * the existing directory if it already exists
     */
    @Test
    public void createExistingDirectory() {
        File testFile = new File(tmpDir);
        File testFile2 = new File(tmpDir);
        
        try {
            FileHelper.createDirectory(testFile);
        } catch (IOException ex) {
            Logger.getLogger(FileHelperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long lastModified = testFile.lastModified();
        
        try {
            FileHelper.createDirectory(testFile2);
        } catch (IOException ex) {
            Logger.getLogger(FileHelperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long lastModified2 = testFile2.lastModified();
        
        assertThat(lastModified, is(equalTo(lastModified2)));               
        
    }     
    
    @Test
    public void testGetTempDirectory() {
        String test = FileHelper.getTempDirectory();
        assertThat(test, is(notNullValue()));
        assertThat(test.endsWith(File.separator), is(true));
    }
}
