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
public interface EventSubscriber {

    public void addObserver(EventObserver observer);

    public void removeObserver(EventObserver observer);

    public void notifyObservers(Enum event, Dto data);
}
