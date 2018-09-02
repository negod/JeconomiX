/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.basic;

import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public abstract class NegodJFrame extends JFrame {

    public NegodJFrame() throws HeadlessException {
    }

    public NegodJFrame(String title) throws HeadlessException {
        super(title);
    }

    /**
     * Removes component as observer and disposes
     */
    public void close() {
        this.dispose();
    }

}
