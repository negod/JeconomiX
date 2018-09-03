/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event;

import java.util.function.Supplier;
import se.backede.jeconomix.dto.ProgressDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class ProgressEvent {

    private static ProgressEvent instance = null;

    protected ProgressEvent() {
    }

    public static ProgressEvent getInstance() {
        if (instance == null) {
            instance = new ProgressEvent();
        }
        return instance;
    }

}
