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
import org.negod.DB.Entities.budget.Company;
import org.negod.DB.Entities.budget.TransactionType;
import org.negod.ejb.budgethandling.CompanyHandling;
import org.negod.ejb.budgethandling.TransactionHandling;

/**
 *
 * @author Joakim
 */
@FacesConverter(forClass = Company.class, value = "companyConverter")
public class CompanyConverter implements Converter {

    CompanyHandling db;
    List<Company> data;

    public CompanyConverter() {
        try {
            db = (CompanyHandling) new InitialContext().lookup("java:global/org.negod_JeconomiX-web_war_1.0-SNAPSHOT/CompanyHandlingImpl");
            data = db.getAllCompanies("admin");
        } catch (Exception ex) {
            Logger.getLogger(TransactionTypeConverter.class.getName()).log(Level.SEVERE, "Failed to get Companies (CompanyConverter)", ex);
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                for (Company entity : data) {
                    if (entity.getCompanyName().equals(submittedValue)) {
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
            Company sentValue = (Company) value;
            for (Company entity : data) {
                if (entity.getCompanyName().equals(sentValue.getCompanyName())) {
                    return entity.getCompanyName();
                }
            }
        }
        return null;
    }
}
