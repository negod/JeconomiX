/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.combobox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.forms.basic.component.GenericComboBoxModel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryComboBoxModel extends GenericComboBoxModel<CategoryDto, CategoryTypeEnum> {

    public CategoryComboBoxModel(CategoryTypeEnum filter) {
        super(filter);
    }

    public CategoryComboBoxModel() {
        super();
    }

    @Override
    public void getAllData() {
        CategoryHandler.getInstance().getAllCategories().ifPresent(categories -> {
            addElements(categories);
            Collections.sort(getItems());
        });
    }

    @Override
    public void getAllData(CategoryTypeEnum filter) {
        CategoryHandler.getInstance().getFilteredCategories(filter).ifPresent(categories -> {
            addElements(new ArrayList<>(categories));
            Collections.sort(getItems());
        });

    }

    public void getAllData(CategoryTypeEnum... filter) {
        for (CategoryTypeEnum categoryTypeEnum : filter) {
            getAllData(categoryTypeEnum);
        }
    }

}
