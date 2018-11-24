/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.exception;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class MappingException extends Exception {

    private static final long serialVersionUID = 1L;

    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }

}
