/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.exporter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class ExporterTest {
    
    public ExporterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createExportListData method, of class Exporter.
     */
    @Test
    public void testCreateExportListData() {
        System.out.println("createExportListData");
        Exporter instance = null;
        ExportListData expResult = null;
        ExportListData result = instance.createExportListData(null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of export method, of class Exporter.
     */
    @Test
    public void testExport() {
        System.out.println("export");
        Exporter instance = null;
        instance.export();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
