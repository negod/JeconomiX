/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event;

import java.util.function.Consumer;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public interface EventRegistry<T> {

    public void registerAsObserver(Enum event, Consumer<T> action);

    public void removeAsObserver();

}
