/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.basic;

import javax.swing.JPanel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public abstract class NegodPanel extends JPanel {

    public NegodPanel() {
    }

    /**
     * Removes component as observer
     */
    public void close() {
    }

    public abstract void init();

}
