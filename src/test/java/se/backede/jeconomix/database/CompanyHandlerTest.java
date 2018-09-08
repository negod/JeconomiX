/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.backede.jeconomix.database.dao.TestCacheInitializer;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.event.TestDatabaseHandler;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyHandlerTest {

    public CompanyHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Clearing database and createing new from Liquibase");
        TestDatabaseHandler.getInstance().deleteAndCreateNewDatabase();
        System.out.println("Spooling up cache");
        TestCacheInitializer CACHE = new TestCacheInitializer();
    }

    /**
     * Test of getInstance method, of class CompanyHandler.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        CompanyHandler result = CompanyHandler.getInstance();
        assertNotNull(result);
    }

    /**
     * Test of createCompany method, of class CompanyHandler.
     */
    @Test
    public void testCreateCompanyWithoutCategory() {
        System.out.println("testCreateCompanyWithoutCategory");

        Optional<CompanyDto> result = CompanyHandler.getInstance().createCompany(getCompany("NewCompany"));
        assertFalse("Expected non present Company due to no Category", result.isPresent());
    }

    @Test
    public void testCreateCompanyWithCategory() {

        System.out.println("testCreateCompanyWithCategory");

        Optional<CategoryDto> newCategory = CategoryTypeHandler.getInstance().getAllCategoryTypes().map(type -> {
            CategoryDto category = getCategory("NewCategory");
            category.setCategoryType(type.get(0));
            return CategoryHandler.getInstance().createCategory(category).get();
        });

        assertTrue("Category should have been persisted", newCategory.isPresent());

        CompanyDto company = getCompany("NewCompany");
        company.setCategory(newCategory.get());

        Optional<CompanyDto> createCompany = CompanyHandler.getInstance().createCompany(company);
        assertTrue("Company should have been created", createCompany.isPresent());

        Optional<CompanyDto> companyById = CompanyHandler.getInstance().getCompanyById(createCompany.get().getId());
        assertTrue(companyById.isPresent());
        assertEquals("The persisted company name should be same as retrieved", companyById.get().getName(), "NewCompany");

    }

    private CategoryDto getCategory(String name) {
        CategoryDto dto = new CategoryDto();
        dto.setName(name);
        return dto;
    }

    private CompanyDto getCompany(String name) {
        CompanyDto dto = new CompanyDto(name);
        return dto;
    }

    /**
     * Test of updateCompany method, of class CompanyHandler.
     */
    @Test
    public void testUpdateCompany() {
//        System.out.println("updateCompany");
//        CompanyDto companyDto = null;
//        CompanyHandler instance = new CompanyHandler();
//        Optional<CompanyDto> expResult = null;
//        Optional<CompanyDto> result = instance.updateCompany(companyDto);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of addAccociatedCompany method, of class CompanyHandler.
     */
    @Test
    public void testAddAccociatedCompany() {
//        System.out.println("addAccociatedCompany");
//        CompanyDto companyDto = null;
//        CompanyHandler instance = new CompanyHandler();
//        Optional<CompanyDto> expResult = null;
//        Optional<CompanyDto> result = instance.addAccociatedCompany(companyDto);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllCompanies method, of class CompanyHandler.
     */
    @Test
    public void testGetAllCompanies() {
//        System.out.println("getAllCompanies");
//        CompanyHandler instance = new CompanyHandler();
//        Optional<List<CompanyDto>> expResult = null;
//        Optional<List<CompanyDto>> result = instance.getAllCompanies();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getCompanyByName method, of class CompanyHandler.
     */
    @Test
    public void testGetCompanyByName() {
        System.out.println("testGetCompanyByName");
        Optional<CategoryDto> newCategory = CategoryTypeHandler.getInstance().getAllCategoryTypes().map(type -> {
            CategoryDto category = getCategory("NewCategory2");
            category.setCategoryType(type.get(0));
            return CategoryHandler.getInstance().createCategory(category).get();
        });

        assertTrue(newCategory.isPresent());

        CompanyDto company = getCompany("NewCompany2");
        company.setCategory(newCategory.get());

        Optional<CompanyDto> createCompany = CompanyHandler.getInstance().createCompany(company);
        assertTrue("Company should have been persisted", createCompany.isPresent());

        Optional<CompanyDto> companyById = CompanyHandler.getInstance().getCompanyById(createCompany.get().getId());
        assertTrue(companyById.isPresent());
        assertEquals("Persisted company name should be same as retrieved", companyById.get().getName(), "NewCompany2");

        Optional<CompanyDto> companyByName = CompanyHandler.getInstance().getCompanyByName("NewCompany2");
        assertTrue(companyByName.isPresent());
        assertEquals("Getting company by name should have same name as the created one", companyByName.get().getName(), "NewCompany2");
    }

    /**
     * Test of setCategory method, of class CompanyHandler.
     */
    @Test
    public void testSetCategory() {
//        System.out.println("setCategory");
//        CompanyDto companyDto = null;
//        CategoryDto category = null;
//        CompanyHandler instance = new CompanyHandler();
//        Optional<CompanyDto> expResult = null;
//        Optional<CompanyDto> result = instance.setCategory(companyDto, category);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getCompanyById method, of class CompanyHandler.
     */
    @Test
    public void testGetCompanyById() {
//        System.out.println("getCompanyById");
//        String id = "";
//        CompanyHandler instance = new CompanyHandler();
//        Optional<CompanyDto> expResult = null;
//        Optional<CompanyDto> result = instance.getCompanyById(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of reIndex method, of class CompanyHandler.
     */
    @Test
    public void testReIndex() {
//        System.out.println("reIndex");
//        CompanyHandler instance = new CompanyHandler();
//        instance.reIndex();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}
