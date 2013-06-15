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
import org.negod.DB.Entities.userhandling.Groups;
import org.negod.ejb.userhandling.UserHandling;

/**
 *
 * @author Joakim
 */
@FacesConverter(forClass = Groups.class, value = "userGroupConverter")
public class UserGroupConverter implements Converter {

    UserHandling db;
    List<Groups> data;

    public UserGroupConverter() {
        try {
            db = (UserHandling) new InitialContext().lookup("java:global/org.negod_JeconomiX-web_war_1.0-SNAPSHOT/UserHandlingImpl");
            data = db.getAllGroups();
        } catch (Exception ex) {
            Logger.getLogger(UserGroupConverter.class.getName()).log(Level.SEVERE, "Failed to get Usergroups (UserGroupConverter)", ex);
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                for (Groups entity : data) {
                    if (entity.getGroupName().equals(submittedValue)) {
                        return entity;
                    }
                }
            } catch (NumberFormatException exception) {
                Logger.getLogger(UserGroupConverter.class.getName()).log(Level.SEVERE, "Failed to get Usergroups (UserGroupConverter.getAsObject)", exception);
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
            Groups sentValue = (Groups) value;
            for (Groups entity : data) {
                if (entity.getGroupName().equals(sentValue.getGroupName())) {
                    return entity.getGroupName();
                }
            }
        }
        return null;
    }
}
