/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.dao.TestCacheInitializer;
import se.backede.jeconomix.database.entity.Transaction;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.event.TestDatabaseHandler;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class TransactionHandlerTest {

    public TransactionHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Clearing database and createing new from Liquibase");
        TestDatabaseHandler.getInstance().deleteAndCreateNewDatabase();
        System.out.println("Spooling up cache");
        TestCacheInitializer CACHE = new TestCacheInitializer();
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
        System.out.println("transactionExists");
        TransactionDto transaction = null;
        TransactionHandler instance = new TransactionHandler();
        boolean expResult = false;
        boolean result = instance.transactionExists(transaction);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decideBudgetMonth method, of class TransactionHandler.
     */
    @Test
    public void testDecideBudgetMonth() {
        System.out.println("decideBudgetMonth");
        Transaction transaction = null;
        CategoryTypeEnum category = null;
        TransactionHandler instance = new TransactionHandler();
        Transaction expResult = null;
        Transaction result = instance.decideBudgetMonth(transaction, category);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createTransaction method, of class TransactionHandler.
     */
    @Test
    public void testCreateTransaction() {
        System.out.println("createTransaction");
        TransactionDto transaction = null;
        TransactionHandler instance = new TransactionHandler();
        Optional<TransactionDto> expResult = null;
        Optional<TransactionDto> result = instance.createTransaction(transaction);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllTransactions method, of class TransactionHandler.
     */
    @Test
    public void testGetAllTransactions() {
        System.out.println("getAllTransactions");
        TransactionHandler instance = new TransactionHandler();
        Optional<List<TransactionDto>> expResult = null;
        Optional<List<TransactionDto>> result = instance.getAllTransactions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
