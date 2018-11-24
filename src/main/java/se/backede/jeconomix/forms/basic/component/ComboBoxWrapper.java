/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.basic.component;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import lombok.Getter;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import se.backede.jeconomix.constants.ComboBoxOptions;
import se.backede.jeconomix.forms.basic.NegodComboBox;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 * @param <T> interface for DTOs that define what value to e shown in the
 * DropDown
 * @param <M> model that will be used for data in the DropDown
 * @param <CompanyDto>
 */
@Getter
public class ComboBoxWrapper<T, M> implements NegodComboBox<T, M> {

    private static final long serialVersionUID = 1L;

    Class<?> objectClass;

    JComboBox<T> comboBox;

    public ComboBoxWrapper(JComboBox comboBox, M model, ComboBoxOptions options) {
        this.comboBox = comboBox;
        comboBox.setModel((ComboBoxModel) model);
        objectClass = getParameterizedClass();

        switch (options) {
            case AUTOCOMPLETE:
                AutoCompleteDecorator.decorate(comboBox, getStringConverterForAutoComplete());
                break;
            case RENDRER:
                setComboBoxRenderer();
                break;
            case AUTOCOMPLETE_AND_RENDERER:
                setComboBoxRenderer();
                AutoCompleteDecorator.decorate(comboBox, getStringConverterForAutoComplete());
                break;
            default:
                throw new AssertionError();
        }

    }

    public Class<?> getParameterizedClass() {
        return comboBox.getItemAt(0).getClass();
    }

    @Override
    public void setSelectedItem(T item) {
        comboBox.getModel().setSelectedItem(item);
    }

    @Override
    public T getSelectedItem() {
        return (T) comboBox.getSelectedItem();
    }

    @Override
    public void setComboBoxModel(M model) {
        comboBox.setModel((ComboBoxModel) model);
    }

    @Override
    public void setItem(T item) {
        comboBox.getModel().setSelectedItem(item);
    }

    @Override
    public void setComboBoxRenderer() {
        comboBox.setRenderer(new NegodRenderer<T>());
    }

    @Override
    public M getComboBoxModel() {
        return (M) comboBox.getModel();
    }

    @Override
    public Class<?> getObjectClass() {
        return objectClass;
    }

}
