/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.basic;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import se.backede.jeconomix.annotations.ComboBoxField;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 * @param <T>
 * @param <M>
 */
public interface NegodComboBox<T, M> {

    public T getSelectedItem();

    public void setSelectedItem(T item);

    public void setItem(T item);

    public void setComboBoxModel(M model);

    public M getComboBoxModel();

    public void setComboBoxRenderer();

    public class NegodRenderer<T> extends BasicComboBoxRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected,
                    cellHasFocus);

            T item = (T) value;
            if (item != null) {

                Field[] fields = item.getClass().getDeclaredFields();

                for (Field field : fields) {
                    ComboBoxField annotation = field.getDeclaredAnnotation(ComboBoxField.class);
                    if (annotation != null) {
                        try {
                            field.setAccessible(true);
                            setText((String) field.get(item));
                            field.setAccessible(false);
                            break;
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(NegodComboBox.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(NegodComboBox.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            return this;
        }
    }

}
