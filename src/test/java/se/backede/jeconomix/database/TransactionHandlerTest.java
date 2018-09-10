/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import se.backede.jeconomix.database.dao.TestCacheInitializer;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CategoryTypeDto;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.event.TestDatabaseHandler;
import se.backede.jeconomix.mock.TestMock;
import static se.backede.jeconomix.mock.TestMock.getCompany;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class TransactionHandlerTest {

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Clearing database and createing new from Liquibase");
        TestDatabaseHandler.getInstance().deleteAndCreateNewDatabase();
        System.out.println("Spooling up cache");
        TestCacheInitializer CACHE = new TestCacheInitializer();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of getInstance method, of class TransactionHandler.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        TransactionHandler result = TransactionHandler.getInstance();
        assertNotNull(result);
    }

    /**
     * Test of transactionExists method, of class TransactionHandler.
     */
    @Test
    public void testTransactionExists() {

    }

    /**
     * Test of decideBudgetMonth method, of class TransactionHandler.
     */
    @Test
    public void testDecideBudgetMonth() {

    }

    /**
     * Test of createTransaction method, of class TransactionHandler.
     */
    @Test
    public void testCreateTransaction() {

        Optional<List<CategoryTypeDto>> allCategoryTypes = CategoryTypeHandler.getInstance().getAllCategoryTypes();

        Optional<CategoryDto> newCategory = CategoryTypeHandler.getInstance().getAllCategoryTypes().map(type -> {
            CategoryDto category = TestMock.getCategory("NewCategory");
            category.setCategoryType(allCategoryTypes.get().get(0));
            return CategoryHandler.getInstance().createCategory(category).get();
        });

        CompanyDto company = getCompany("NewCompany");
        company.setCategory(newCategory.get());

        Optional<CompanyDto> createCompany = CompanyHandler.getInstance().createCompany(company);

        TransactionDto transaction = new TransactionDto();
        transaction.setCompany(createCompany.get());
        transaction.setOriginalValue("OriginalValue");
        transaction.setSaldo(BigDecimal.valueOf(5.0));
        transaction.setSum(BigDecimal.valueOf(10.0));
        transaction.setTransDate(Date.valueOf(LocalDate.now()));

        Optional<TransactionDto> createTransaction = TransactionHandler.getInstance().createTransaction(transaction);
        assertTrue(createTransaction.isPresent());

    }

    /**
     * Test of getAllTransactions method, of class TransactionHandler.
     */
    @Test
    public void testGetAllTransactions() {

    }

}
