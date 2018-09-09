/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import se.backede.jeconomix.database.dao.TestCacheInitializer;
import se.backede.jeconomix.database.entity.CategoryType;
import se.backede.jeconomix.dto.CategoryTypeDto;
import se.backede.jeconomix.event.TestDatabaseHandler;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryTypeHandlerTest {

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Clearing database and createing new from Liquibase");
        TestDatabaseHandler.getInstance().deleteAndCreateNewDatabase();
        System.out.println("Spooling up cache");
        TestCacheInitializer CACHE = new TestCacheInitializer();
    }

    /**
     * Test of getInstance method, of class CategoryTypeHandler.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        CategoryTypeHandler result = CategoryTypeHandler.getInstance();
        assertNotNull(result);
    }

    /**
     * Test of getAllCategoryTypes method, of class CategoryTypeHandler.
     */
    @Test
    public void testGetAllCategoryTypes() {
        System.out.println("testGetAllCategoryTypes");
        Optional<List<CategoryTypeDto>> allCategoryTypes = CategoryTypeHandler.getInstance().getAllCategoryTypes();
        Assert.assertTrue("There should be Categorytypes from createion of DB", allCategoryTypes.isPresent());
        Assert.assertEquals("Categorytype list should be of size 4", 4, allCategoryTypes.get().size());
    }

    /**
     * Test of getById method, of class CategoryTypeHandler.
     */
    @Test
    public void testGetById() {
        System.out.println("testGetById");

        //CONSTANT IDS FROM LIQUIBASE SCRIPT
        String INCOME = "d5852002-1da1-45b9-86c0-9368bb51b3a0";
        String EXPENSE = "f042f6cf-c9a8-4130-8704-b675605145f5";
        String BILL = "1c13c92d-2a77-4020-902e-2a80af380c80";
        String TRANSFER = "3944c107-7a37-4bac-aad2-5aab57e428d8";

        Optional<CategoryType> byIdINCOME = CategoryTypeHandler.getInstance().getById(INCOME);
        assertTrue("INCOME category type must be present", byIdINCOME.isPresent());
        assertEquals(byIdINCOME.get().getId(), INCOME);

        Optional<CategoryType> byIdEXPENSE = CategoryTypeHandler.getInstance().getById(EXPENSE);
        assertTrue("EXPENSE category type must be present", byIdEXPENSE.isPresent());
        assertEquals(byIdEXPENSE.get().getId(), EXPENSE);

        Optional<CategoryType> byIdBILL = CategoryTypeHandler.getInstance().getById(BILL);
        assertTrue("BILL category type must be present", byIdBILL.isPresent());
        assertEquals(byIdBILL.get().getId(), BILL);

        Optional<CategoryType> byIdTRANSFER = CategoryTypeHandler.getInstance().getById(TRANSFER);
        assertTrue("TRANSFER category type must be present", byIdTRANSFER.isPresent());
        assertEquals(byIdTRANSFER.get().getId(), TRANSFER);

    }

}
