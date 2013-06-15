/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.utils;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Joakim
 */
public class FacesUtils {

    public enum ResourceBundles {

        userMsg("userMsg"),
        buttonMsg("buttonMsg"),
        viewId("viewId"),
        viewVar("viewVar"),
        facesMessages("facesMessages");
        private final String name;

        ResourceBundles(String name) {
            this.name = name;
        }
    }

    public static String getRemoteUser() {
        return getExternalContext().getRemoteUser();
    }

    private static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static String getValueFromResourceBundle(ResourceBundles bundle, String value) {
        return getValueFromResourceBundle(bundle.name, value);
    }

    public static void addInfoMessage(ResourceBundles bundle, String message, String viewId) {
        addInfoMessage(getValueFromResourceBundle(bundle, message), viewId);
    }

    public static void addWarnMessage(ResourceBundles bundle, String message, String viewId) {
        addWarnMessage(getValueFromResourceBundle(bundle, message), viewId);
    }

    public static void addErrorMessage(ResourceBundles bundle, String message, String viewId) {
        addErrorMessage(getValueFromResourceBundle(bundle, message), viewId);
    }

    public static void addFatalMessage(ResourceBundles bundle, String message, String viewId) {
        FacesUtils.addFatalMessage(getValueFromResourceBundle(bundle, message), viewId);
    }

    private static ResourceBundle getResourceBundle(String name) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, name);
        return bundle;
    }

    private static String getValueFromResourceBundle(String bundle, String value) {
        return getResourceBundle(bundle).getString(value);
    }

    private static void addErrorMessage(String message, String viewId) {
        FacesContext.getCurrentInstance().addMessage(viewId, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, message));
    }

    private static void addInfoMessage(String message, String viewId) {
        FacesContext.getCurrentInstance().addMessage(viewId, new FacesMessage(FacesMessage.SEVERITY_INFO, null, message));
    }

    private static void addFatalMessage(String message, String viewId) {
        FacesContext.getCurrentInstance().addMessage(viewId, new FacesMessage(FacesMessage.SEVERITY_FATAL, null, message));
    }

    private static void addWarnMessage(String message, String viewId) {
        FacesContext.getCurrentInstance().addMessage(viewId, new FacesMessage(FacesMessage.SEVERITY_WARN, null, message));
    }
}
