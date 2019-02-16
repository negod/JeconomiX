/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.exporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.backede.jeconomix.mock.XmlMock;
import se.backede.jeconomix.mock.dto.AddresDto;
import se.backede.jeconomix.mock.dto.CompanyDto;
import se.backede.jeconomix.mock.dto.PersonDto;
import se.backede.jeconomix.utils.ImportExportUtils;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class ImportExportUtilsTest {

    public ImportExportUtilsTest() {
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
     * Test of extractClassesFromObject method, of class Exporter.
     */
    @Test
    public void testExtractClassesFromObject() {
        System.out.println("extractClassesFromObject");

        ImportExportUtils<PersonDto> instance = new ImportExportUtils();

        PersonDto person = XmlMock.getPerson();
        Set<Class<?>> result = instance.extractClassesFromObject(person);

        assertEquals("Set should contain all annotated classes", 3, result.size());
        System.out.println(result.size());

        assertTrue("Set should contain PersonDto class", result.contains(PersonDto.class));
        assertTrue("Set should contain CompanyDto class", result.contains(CompanyDto.class));
        assertTrue("Set should contain AddresDto class", result.contains(AddresDto.class));

    }

    @Test
    public void testExtractClassesFromObjectWithExportListData() {
        System.out.println("testExtractClassesFromObjectWithExportListData");

        ImportExportUtils<PersonDto> instance = new ImportExportUtils();

        PersonDto person = XmlMock.getPerson();
        ExportListData data = new ExportListData(new ArrayList<>(Arrays.asList(person)));
        Set<Class<?>> result = instance.extractClassesFromObject(data);

        assertEquals("Set should contain all annotated classes", 4, result.size());
        System.out.println(result.size());

        assertTrue("Set should contain PersonDto class", result.contains(PersonDto.class));
        assertTrue("Set should contain CompanyDto class", result.contains(CompanyDto.class));
        assertTrue("Set should contain AddresDto class", result.contains(AddresDto.class));
        assertTrue("Set should contain ExportListData class", result.contains(ExportListData.class));

    }

    /**
     * Test of getObjectFromList method, of class Exporter.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetObjectFromList() {
        System.out.println("getObjectFromList");

        PersonDto person = XmlMock.getPerson();

        ImportExportUtils<PersonDto> instance = new ImportExportUtils();
        Optional<PersonDto> objectFromList = instance.getObjectFromList(Arrays.asList(new PersonDto[]{person}));
        assertTrue("Should get an object back from array", objectFromList.isPresent());
    }

    /**
     * Test of getObjectFromList method, of class Exporter.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetObjectFromEmptyList() {
        System.out.println("getObjectFromList");

        PersonDto person = XmlMock.getPerson();

        ImportExportUtils<PersonDto> instance = new ImportExportUtils();
        Optional<PersonDto> objectFromEmptyList = instance.getObjectFromList(Arrays.asList(new PersonDto[0]));
    }

}
