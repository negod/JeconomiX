/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.combobox;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.CategoryTypeHandler;
import se.backede.jeconomix.dto.CategoryTypeDto;
import se.backede.jeconomix.forms.basic.component.GenericComboBoxModel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryTypeComboBoxModel extends GenericComboBoxModel<CategoryTypeDto, CategoryTypeEnum> {

    private static final long serialVersionUID = 1L;

    @Override
    public Optional<List<CategoryTypeDto>> getAllData() {
        return CategoryTypeHandler.getInstance().getAllCategoryTypes();
    }

    @Override
    public Optional<List<CategoryTypeDto>> getAllData(CategoryTypeEnum filter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<List<CategoryTypeDto>> getAllData(CategoryTypeEnum... filter) {
        return CategoryTypeHandler.getInstance().getAllCategoryTypes().map(categoryTypes -> {
            return categoryTypes.stream()
                    .filter(categoryType -> Arrays.stream(filter)
                    .anyMatch(categoryType.getType()::equals))
                    .collect(Collectors.toList());
        });
    }
}
