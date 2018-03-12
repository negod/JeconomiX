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
public class NegodEvent {

    private Enum eventEnum;
    private Dto values;

    public NegodEvent(Enum eventEnum, Dto values) {
        this.eventEnum = eventEnum;
        this.values = values;
    }

    public boolean equalsEvent(Enum enumValue) {
        return enumValue.equals(eventEnum);
    }

    public Enum getEvent() {
        return eventEnum;
    }

    public Dto getValues() {
        return values;
    }
}
