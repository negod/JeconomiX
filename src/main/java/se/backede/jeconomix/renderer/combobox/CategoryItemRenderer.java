/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.renderer.combobox;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import se.backede.jeconomix.constants.ComboBoxRenderer;
import se.backede.jeconomix.dto.CategoryDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryItemRenderer extends BasicComboBoxRenderer {

    ComboBoxRenderer renderer;

    public CategoryItemRenderer(ComboBoxRenderer render) {
        this.renderer = render;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        CategoryDto category = (CategoryDto) value;
        if (category != null) {

            switch (renderer) {
                case MULTIPLE:
                    if (category.getCategoryType() != null) {
                        setText(category.getCategoryType().getType().name().concat(" | ").concat(category.getName()));
                    } else {
                        setText(category.getName());
                    }
                    break;
                case SINGLE:
                    setText(category.getName());
                    break;
                default:
                    throw new AssertionError();
            }

        }

        return this;
    }
}
