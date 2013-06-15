/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.DB.Enums;

import java.io.Serializable;

/**
 *
 * @author Joakim
 */
public enum TransactionTypeEnum implements Serializable{

    income("income"),
    expense("expense");
    private String label;

    private TransactionTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
