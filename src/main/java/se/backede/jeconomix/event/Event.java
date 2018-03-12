/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event;

import se.backede.jeconomix.event.dto.Dto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class Event implements EventSubscriber {

    EventController events = new EventController();

    @Override
    public void addObserver(EventObserver frame) {
        events.addObserver(frame);
    }

    @Override
    public void removeObserver(EventObserver frame) {
        events.removeObserver(frame);
    }

    @Override
    public void notifyObservers(Enum event, Dto data) {
        events.notifyObservers(event, data);
    }
}
