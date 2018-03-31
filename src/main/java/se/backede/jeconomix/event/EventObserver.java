/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public interface EventObserver extends EventRegistry {

    public void update(NegodEvent event);

    @Override
    public default void registerAsObserver() {
        EventController.getInstance().addObserver(this);
    }
}
