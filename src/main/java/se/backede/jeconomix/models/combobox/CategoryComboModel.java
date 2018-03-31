/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.combobox;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.CategoryDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryComboModel extends AbstractListModel implements ComboBoxModel {

    List<CategoryDto> categories = new LinkedList<>();
    CategoryDto selection = null;

    public CategoryComboModel(CategoryTypeEnum... category) {
        for (CategoryTypeEnum categoryTypeEnum : category) {
            Optional<List<CategoryDto>> filteredCategories = CategoryHandler.getInstance().getFilteredCategories(categoryTypeEnum);
            if (filteredCategories.isPresent()) {
                this.categories.addAll(filteredCategories.get());
            }
        }
    }

    public CategoryComboModel() {
        Optional<List<CategoryDto>> filteredCategories = CategoryHandler.getInstance().getAllCategories();
        if (filteredCategories.isPresent()) {
            this.categories = filteredCategories.get();
        }
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
        selection = (CategoryDto) anItem;
        fireContentsChanged(this, -1, -1);
    } // item from the pull-down list

    // Methods implemented from the interface ComboBoxModel
    @Override
    public Object getSelectedItem() {
        return selection; // to add the selection to the combo box
    }

    public void addElement(CategoryDto category) {
        categories.add(category);
        fireContentsChanged(this, categories.size(), categories.size());
    }
}
