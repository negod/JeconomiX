/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.backing.converter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import org.negod.DB.Entities.budget.TransactionType;
import org.negod.ejb.budgethandling.TransactionHandling;

/**
 *
 * @author Joakim
 */
@FacesConverter(forClass = TransactionType.class, value = "transactionTypeConverter")
public class TransactionTypeConverter implements Converter {

    TransactionHandling db;
    List<TransactionType> data;

    public TransactionTypeConverter() {
        try {
            db = (TransactionHandling) new InitialContext().lookup("java:global/org.negod_JeconomiX-web_war_1.0-SNAPSHOT/TransactionHandlingImpl");
            data = db.getAllTransactionTypes("admin");
        } catch (Exception ex) {
            Logger.getLogger(TransactionTypeConverter.class.getName()).log(Level.SEVERE, "Failed to get TransactionTypes (TransactionTypeConverter)", ex);
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                for (TransactionType entity : data) {
                    if (entity.getTransactionName().equals(submittedValue)) {
                        return entity;
                    }
                }
            } catch (NumberFormatException exception) {
                Logger.getLogger(TransactionHandling.class.getName()).log(Level.SEVERE, "Failed to get TransactionTypes (TransactionTypeConverter.getAsObject)", exception);
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Entity"));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value instanceof String) {
            return "";
        } else {
            TransactionType sentValue = (TransactionType) value;
            for (TransactionType entity : data) {
                if (entity.getTransactionName().equals(sentValue.getTransactionName())) {
                    return entity.getTransactionName();
                }
            }
        }
        return null;
    }
}
