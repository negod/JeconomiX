/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event;

import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import se.backede.jeconomix.event.events.CategoryEvent;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class EventControllerIT {

    public EventControllerIT() {
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
     * Test of getInstance method, of class EventController.
     */
    @Test
    public void testGetInstance() {
        Consumer<String> setBudget = budget -> {
            Assert.assertSame("Test from Sender", budget);
        };
        EventController.getInstance().addObserver(CategoryEvent.SET_SELECTED, setBudget);

        Supplier<String> getBudget = () -> "Test from Sender";
        EventController.getInstance().notifyObservers(CategoryEvent.SET_SELECTED, getBudget);
    }

    /**
     * Test of addObserver method, of class EventController.
     */
    @Test
    public void testAddObserver() {

    }

    /**
     * Test of removeObserver method, of class EventController.
     */
    @Test
    public void testRemoveObserver() {

    }

    /**
     * Test of notifyObservers method, of class EventController.
     */
    @Test
    public void testNotifyObservers() {

    }

}
