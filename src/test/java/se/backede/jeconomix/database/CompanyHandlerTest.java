/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import java.util.Optional;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.backede.jeconomix.database.dao.TestCacheInitializer;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.event.TestDatabaseHandler;
import se.backede.jeconomix.mock.TestMock;
import static se.backede.jeconomix.mock.TestMock.getCategory;
import static se.backede.jeconomix.mock.TestMock.getCompany;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyHandlerTest {

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

}
