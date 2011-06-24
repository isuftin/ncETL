/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.usgs.cida.ncetl.spec;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author Ivan Suftin <isuftin@usgs.gov>
 */
public class IngestControlSpecTest {

    IngestControlSpec test = new IngestControlSpec();

    public IngestControlSpecTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        test = new IngestControlSpec();
    }

    @After
    public void tearDown() {
        test = null;
    }

    @Test
    public void testCreate() {
        assertThat(test, is(notNullValue()));
    }

    @Test
    public void testAccessDelete() {
        assertThat(test.setupAccess_DELETE(), is(true));
    }

    @Test
    public void testAccessInsert() {
        assertThat(test.setupAccess_INSERT(), is(true));
    }

    @Test
    public void testAccessRead() {
        assertThat(test.setupAccess_READ(), is(true));
    }

    @Test
    public void testAccessUpdate() {
        assertThat(test.setupAccess_UPDATE(), is(true));
    }
}
