/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.backede.jeconomix.database.dao.TestCacheInitializer;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CategoryTypeDto;
import se.backede.jeconomix.event.TestDatabaseHandler;
import se.backede.jeconomix.mock.TestMock;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryHandlerTest {

    public CategoryHandlerTest() {
        System.out.println("Clearing database and createing new from Liquibase");
        TestDatabaseHandler.getInstance().deleteAndCreateNewDatabase();
        System.out.println("Spooling up cache");
        TestCacheInitializer CACHE = new TestCacheInitializer();
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
     * Test of getInstance method, of class CategoryHandler.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        CategoryHandler result = CategoryHandler.getInstance();
        assertNotNull(result);
    }

    /**
     * Test of createCategory method, of class CategoryHandler.
     */
    @Test
    public void testCreateCategory() {
        System.out.println("createCategory");

        Optional<List<CategoryTypeDto>> allCategoryTypes = CategoryTypeHandler.getInstance().getAllCategoryTypes();
        Assert.assertTrue("There should be Categorytypes from createion of DB", allCategoryTypes.isPresent());
        Assert.assertEquals("Categorytype list should be of size 4", 4, allCategoryTypes.get().size());

        Optional<CategoryDto> newCategory = CategoryTypeHandler.getInstance().getAllCategoryTypes().map(type -> {
            CategoryDto category = TestMock.getCategory("NewCategory");
            category.setCategoryType(allCategoryTypes.get().get(0));
            return CategoryHandler.getInstance().createCategory(category).get();
        });

        Assert.assertTrue("Category should be created", newCategory.isPresent());

        Optional<CategoryDto> categoryById = CategoryHandler.getInstance().getCategoryById(newCategory.get().getId());

        Assert.assertTrue("Category should exist in database", categoryById.isPresent());
        Assert.assertEquals("Retrieved category should be same as persisted ( Check hashcode and equals work )", categoryById.get().getName(), newCategory.get().getName());

    }

    /**
     * Test of getAllCategories method, of class CategoryHandler.
     */
    @Test
    public void testGetAllCategories() {

        Integer numberOfCategories = 20;

        Random rn = new Random();

        Optional<List<CategoryTypeDto>> allCategoryTypes = CategoryTypeHandler.getInstance().getAllCategoryTypes();
        Assert.assertTrue("There should be Categorytypes from createion of DB", allCategoryTypes.isPresent());
        Assert.assertEquals("Categorytype list should be of size 4", 4, allCategoryTypes.get().size());

        for (int i = 0; i < numberOfCategories; i++) {
            final int current = i;
            Optional<CategoryDto> newCategory = CategoryTypeHandler.getInstance().getAllCategoryTypes().map(type -> {
                CategoryDto category = TestMock.getCategory("NewCategory".concat(String.valueOf(current)));
                category.setCategoryType(allCategoryTypes.get().get(rn.nextInt(3)));
                return CategoryHandler.getInstance().createCategory(category).get();
            });
        }

        Optional<List<CategoryDto>> allCategories = CategoryHandler.getInstance().getAllCategories();
        Assert.assertTrue("A list of categories should have been created", allCategories.isPresent());
        Assert.assertTrue("Number of categories should be ".concat(numberOfCategories.toString()), allCategories.get().size() == numberOfCategories);

    }

    /**
     * Test of getFilteredCategories method, of class CategoryHandler.
     */
    @Test
    public void testGetFilteredCategories_CategoryTypeEnum_Integer() {
//        System.out.println("getFilteredCategories");
//        CategoryTypeEnum type = null;
//        Integer year = null;
//        CategoryHandler instance = new CategoryHandler();
//        Optional<List<CategoryDto>> expResult = null;
//        Optional<List<CategoryDto>> result = instance.getFilteredCategories(type, year);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getFilteredCategories method, of class CategoryHandler.
     */
    @Test
    public void testGetFilteredCategories_CategoryTypeEnum() {
//        System.out.println("getFilteredCategories");
//        CategoryTypeEnum type = null;
//        CategoryHandler instance = new CategoryHandler();
//        Optional<List<CategoryDto>> expResult = null;
//        Optional<List<CategoryDto>> result = instance.getFilteredCategories(type);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of setCategoryType method, of class CategoryHandler.
     */
    @Test
    public void testSetCategoryType() {
//        System.out.println("setCategoryType");
//        CategoryDto category = null;
//        CategoryTypeDto categoryType = null;
//        CategoryHandler instance = new CategoryHandler();
//        Optional<CategoryDto> expResult = null;
//        Optional<CategoryDto> result = instance.setCategoryType(category, categoryType);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}
