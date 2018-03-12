/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.renderer.combobox;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import se.backede.jeconomix.database.entity.BillCategory;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BillCategoryItemRenderer extends BasicComboBoxRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected,
                cellHasFocus);

        BillCategory category = (BillCategory) value;
        if (category != null) {
            setText(category.getName());
        }

        return this;
    }
}
