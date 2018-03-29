/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.combobox;

import java.util.List;
import java.util.Optional;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import se.backede.jeconomix.database.CategoryTypeHandler;
import se.backede.jeconomix.dto.CategoryTypeDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryTypeComboBoxModel extends AbstractListModel implements ComboBoxModel {

    List<CategoryTypeDto> categoryTypes;
    CategoryTypeDto selection = null;

    public CategoryTypeComboBoxModel() {
        Optional<List<CategoryTypeDto>> allCategoryTypes = CategoryTypeHandler.getInstance().getAllCategoryTypes();
        if (allCategoryTypes.isPresent()) {
            this.categoryTypes = allCategoryTypes.get();
        }
    }

    @Override
    public Object getElementAt(int index) {
        return categoryTypes.get(index);
    }

    @Override
    public int getSize() {
        return categoryTypes.size();
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (CategoryTypeDto) anItem;
        fireContentsChanged(this, -1, -1);
    } // item from the pull-down list

    // Methods implemented from the interface ComboBoxModel
    @Override
    public Object getSelectedItem() {
        return selection; // to add the selection to the combo box
    }

    public void addElement(CategoryTypeDto category) {
        categoryTypes.add(category);
        fireContentsChanged(this, categoryTypes.size(), categoryTypes.size());
    }
}
