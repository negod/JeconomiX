/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.dao;

import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.backede.jeconomix.database.entity.Company;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.event.TestDatabaseHandler;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyDaoIT {

    public CompanyDaoIT() {
    }

    static CompanyDao DAO;

    @BeforeClass
    public static void setUpClass() {
        TestDatabaseHandler.getInstance().deleteAndCreateNewDatabase();
        TestCacheInitializer CACHE = new TestCacheInitializer();
        DAO = new CompanyDao();
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
     * Test of getEntityManager method, of class CompanyDao.
     */
    @Test
    public void testGetEntityManager() {

        EntityManager manager = DAO.getEntityManager("TestPu");

        Assert.assertNotNull(manager);
        Assert.assertTrue(manager.isOpen());

    }

}
