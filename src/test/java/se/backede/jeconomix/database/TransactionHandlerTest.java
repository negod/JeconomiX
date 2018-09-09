/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
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

    }

    /**
     * Test of getAllTransactions method, of class TransactionHandler.
     */
    @Test
    public void testGetAllTransactions() {

    }

}
