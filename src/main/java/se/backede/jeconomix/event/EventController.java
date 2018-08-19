/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.event.dto.Dto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class EventController implements EventSubscriber {

    private final ArrayList<EventObserver> observers = new ArrayList<>();
    private final List<EventObserver> syncedList = Collections.synchronizedList(observers);

    private static EventController instance = null;

    protected EventController() {
    }

    public static EventController getInstance() {
        if (instance == null) {
            instance = new EventController();
        }
        return instance;
    }

    /**
     * Adds a class that implements the interface EventObserver to the list
     *
     * @param observer
     */
    @Override
    public void addObserver(EventObserver observer) {
        observers.add(observer);
        log.info("Observer added {}", observer.getClass().getName());
    }

    /**
     * Removes the observer if it exists in the list
     *
     * @param observer
     */
    @Override
    public void removeObserver(EventObserver observer) {
        synchronized (syncedList) {
            for (int i = 0; i < syncedList.size(); i++) {
                if (syncedList.get(i).equals(observer)) {
                    syncedList.remove(i);
                    log.info("Observer {} removed ", observer.getClass().getSimpleName());
                    break;
                }
            }
        }

    }

    /**
     * Notifies all the observers with the eventtype selected from the class
     * that fires the event.
     *
     * @param eventType
     * @param data
     */
    @Override
    public void notifyObservers(Enum event, Dto data) {
        synchronized (syncedList) {
            for (int i = 0; i < syncedList.size(); i++) {
                try {
                    syncedList.get(i).onEvent(new NegodEvent(event, data));
                } catch (EventNotImplementedException e) {
                    log.info("Event {} not implemeted for class {}", event.name(), syncedList.get(i).getClass().getSimpleName());
                } catch (Exception e) {
                    log.error("Failed to notify: {} with event: {}.{} due to error {} ", syncedList.get(i).getClass().getSimpleName(), event.getClass().getSimpleName(), event.name(), e.getMessage());
                }
            }
        }
    }

}
