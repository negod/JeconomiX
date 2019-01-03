/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event;

import java.time.Month;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.event.events.BudgetEvent;
import se.backede.jeconomix.event.events.CategoryEvent;
import se.backede.jeconomix.event.events.CompanyEvent;
import se.backede.jeconomix.event.events.ProgressEvent;
import se.backede.jeconomix.event.events.TransactionEvent;
import se.backede.jeconomix.event.events.UiEvent;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class EventController {

    private static EventController instance = null;

    private Map<Class, Optional<EventContainer>> events = new HashMap<>();
    private Map<Class, Optional<EventContainer>> syncedEventMap;

    protected EventController() {

        events.put(BudgetEvent.class, Optional.ofNullable(new EventContainer<>(BudgetEvent.class)));
        events.put(CategoryEvent.class, Optional.ofNullable(new EventContainer<>(CategoryEvent.class)));
        events.put(CompanyEvent.class, Optional.ofNullable(new EventContainer<>(CompanyEvent.class)));
        events.put(ProgressEvent.class, Optional.ofNullable(new EventContainer<>(ProgressEvent.class)));
        events.put(TransactionEvent.class, Optional.ofNullable(new EventContainer<>(TransactionEvent.class)));
        events.put(Month.class, Optional.ofNullable(new EventContainer<>(Month.class)));
        events.put(UiEvent.class, Optional.ofNullable(new EventContainer<>(UiEvent.class)));

        syncedEventMap = Collections.unmodifiableMap(events);
    }

    public static EventController getInstance() {
        if (instance == null) {
            instance = new EventController();
        }
        return instance;
    }

    public void addObserver(Enum event, Consumer action) {
        synchronized (syncedEventMap) {
            syncedEventMap.getOrDefault(event.getClass(), Optional.empty()).ifPresent(container -> {
                container.listenForEvent(event, action);
            });
        }
        log.debug("Observer added {}.{}", event.getClass().getName(), event.name());
    }

    /**
     * Notifies all the observers with the eventtype selected from the class
     * that fires the event.
     *
     * @param event
     * @param supplier
     */
    public void notifyObservers(Enum event, Supplier supplier) {
        log.debug("Notifying observers {}.{}", event.getClass().getName(), event.name());
        synchronized (syncedEventMap) {
            syncedEventMap.getOrDefault(event.getClass(), Optional.empty())
                    .ifPresent(consumer -> consumer.fireEvent(event, supplier));
        }
    }

}
