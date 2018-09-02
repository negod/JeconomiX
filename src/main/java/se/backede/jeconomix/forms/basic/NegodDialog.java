/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.basic;

import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JDialog;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public abstract class NegodDialog extends JDialog {

    public NegodDialog() {
        this.setLocationRelativeTo(null);
    }

    public NegodDialog(Frame owner, boolean modal) {
        super(owner, modal);
    }

    public NegodDialog(Dialog owner, boolean modal) {
        super(owner, modal);
    }

    /**
     * Removes component as observer and disposes
     */
    public void close() {
        this.dispose();
    }

}
