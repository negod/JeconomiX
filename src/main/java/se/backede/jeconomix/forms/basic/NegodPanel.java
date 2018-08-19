/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.basic;

import javax.swing.JPanel;
import se.backede.jeconomix.event.EventObserver;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public abstract class NegodPanel extends JPanel implements EventObserver {

    public NegodPanel() {
        registerAsObserver();
    }

    /**
     * Removes component as observer
     */
    public void close() {
        removeAsObserver();
    }

    public abstract void init();

}
