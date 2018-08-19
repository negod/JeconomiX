/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.basic;

import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JDialog;
import se.backede.jeconomix.event.EventObserver;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public abstract class NegodDialog extends JDialog implements EventObserver {

    public NegodDialog() {
        registerAsObserver();
        this.setLocationRelativeTo(null);
    }

    public NegodDialog(Frame owner, boolean modal) {
        super(owner, modal);
        registerAsObserver();
    }

    public NegodDialog(Dialog owner, boolean modal) {
        super(owner, modal);
        registerAsObserver();
    }

    /**
     * Removes component as observer and disposes
     */
    public void close() {
        removeAsObserver();
        this.dispose();
    }

}
