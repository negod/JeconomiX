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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.event.events.TransactionEvent;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class EventContainerIT {

    public EventContainerIT() {
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
     * Test of getEvent method, of class EventContainer.
     */
    @Test
    public void testGetEvent() {
        EventContainer<TransactionEvent, TransactionDto> container = new EventContainer(TransactionEvent.class);

    }

    /**
     * Test of listenForEvent method, of class EventContainer.
     */
    @Test
    public void testListenForEvent() {
        System.out.println("listenForEvent");

        EventContainer<EventEnum, MockDto> container = new EventContainer<>(EventEnum.class);

        Consumer<MockDto> setMockDtoAction = budget -> {
            System.out.println("Consumer used and Event fired");
            Assert.assertSame("SenderValue", budget.getValue());
        };
        container.listenForEvent(EventEnum.TEST_EVENT, setMockDtoAction);

        Assert.assertTrue(container.getEvents().size() == 1);

        Supplier<MockDto> getMockDto = () -> new MockDto("SenderValue");
        container.fireEvent(EventEnum.TEST_EVENT, getMockDto);

    }

    /**
     * Test of fireEvent method, of class EventContainer.
     */
    @Ignore
    @Test
    public void testFireEvent() {
        System.out.println("fireEvent");
        EventContainer instance = null;
        //instance.fireEvent(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEventClass method, of class EventContainer.
     */
    @Ignore
    @Test
    public void testGetEventClass() {
        System.out.println("getEventClass");
        EventContainer instance = null;
        Class expResult = null;
        Class result = instance.getEventClass();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
