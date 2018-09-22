/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.combobox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.forms.basic.component.GenericComboBoxModel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryComboBoxModel extends GenericComboBoxModel<CategoryDto, CategoryTypeEnum> {

    private static final long serialVersionUID = 1L;

    public CategoryComboBoxModel(CategoryTypeEnum filter) {
        super(filter);
    }

    public CategoryComboBoxModel() {
        super();
    }

    @Override
    public Optional<List<CategoryDto>> getAllData() {
        return CategoryHandler.getInstance().getAllCategories().map(categories -> {
            return categories.stream()
                    .sorted()
                    .collect(Collectors.toList());
        });
    }

    @Override
    public Optional<List<CategoryDto>> getAllData(CategoryTypeEnum filter) {
        return CategoryHandler.getInstance().getFilteredCategories(filter);

    }

    @Override
    public Optional<List<CategoryDto>> getAllData(CategoryTypeEnum... filter) {
        return CategoryHandler.getInstance().getFilteredCategories(filter);
    }

}
