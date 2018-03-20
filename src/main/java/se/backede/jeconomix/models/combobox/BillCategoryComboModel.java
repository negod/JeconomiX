/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.combobox;

import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import se.backede.jeconomix.dto.BillCategoryDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BillCategoryComboModel extends AbstractListModel implements ComboBoxModel {

    List<BillCategoryDto> categories;
    BillCategoryDto selection = null;

    public BillCategoryComboModel(List<BillCategoryDto> categories) {
        this.categories = categories;
    }

    @Override
    public Object getElementAt(int index) {
        return categories.get(index);
    }

    @Override
    public int getSize() {
        return categories.size();
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (BillCategoryDto) anItem;
        fireContentsChanged(this, -1, -1);
    } // item from the pull-down list

    // Methods implemented from the interface ComboBoxModel
    @Override
    public Object getSelectedItem() {
        return selection; // to add the selection to the combo box
    }
}
