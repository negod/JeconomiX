/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@EqualsAndHashCode
@Slf4j
public class EventContainer<E extends Enum<E>, T> {

    private final EnumMap<E, Optional<List<Consumer<T>>>> events;

    public EventContainer(Class<E> event) {
        log.debug("Event container for eventclass {} starting..", event.getName());

        this.events = new EnumMap(event);

        for (E enumConstant : event.getEnumConstants()) {
            events.put(enumConstant, Optional.ofNullable(new ArrayList<>()));
        }

    }

    public void listenForEvent(E event, Consumer<T> action) {
        events.getOrDefault(event, Optional.empty()).ifPresent(list -> list.add(action));
    }

    public void fireEvent(E event, Supplier<T> supplier) {

        events.getOrDefault(event, Optional.empty()).ifPresent(list
                -> list.forEach(action -> action.accept(supplier.get()))
        );
    }

}
