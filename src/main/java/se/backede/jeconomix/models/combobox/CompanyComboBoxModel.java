/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.combobox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.forms.basic.component.GenericComboBoxModel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyComboBoxModel extends GenericComboBoxModel<CompanyDto, CategoryTypeEnum> {

    public CompanyComboBoxModel() {
        super();
    }

    public CompanyComboBoxModel(CategoryTypeEnum filter) {
        super(filter);
    }

    @Override
    public void getAllData(CategoryTypeEnum type) {
        CategoryHandler.getInstance().getFilteredCategories(type).ifPresent(categories -> {
            List<CompanyDto> companyList = new ArrayList<>();
            categories.forEach((category) -> {
                companyList.addAll(category.getCompanies());
            });
            reInitModelData(new ArrayList<>(companyList));
            Collections.sort(getItems());
        });
    }

    @Override
    public void getAllData() {
        CompanyHandler.getInstance().getAllCompanies().ifPresent(comp -> {
            reInitModelData(comp);
            Collections.sort(getItems());
        });
    }

}
