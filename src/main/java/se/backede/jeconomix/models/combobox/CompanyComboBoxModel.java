/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.combobox;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.forms.basic.component.GenericComboBoxModel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyComboBoxModel extends GenericComboBoxModel<CompanyDto, CategoryTypeEnum> {

    private static final long serialVersionUID = 1L;

    public CompanyComboBoxModel() {
        super();
    }

    public CompanyComboBoxModel(CategoryTypeEnum filter) {
        super(filter);
    }

    @Override
    public Optional<List<CompanyDto>> getAllData() {
        return CompanyHandler.getInstance().getAllCompanies().map(comp -> {
            return comp.stream()
                    .sorted()
                    .collect(Collectors.toList());
        });
    }

    @Override
    public Optional<List<CompanyDto>> getAllData(CategoryTypeEnum... filter) {
        return CategoryHandler.getInstance().getFilteredCategories(filter).map(categories -> {
            return categories.stream()
                    .flatMap(category -> category.getCompanies().stream())
                    .sorted()
                    .collect(Collectors.toList());
        });
    }

}
