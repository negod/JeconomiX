/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.mock;

import java.math.BigDecimal;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CategoryTypeDto;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class TestMock {

    public static CategoryDto getCategory(String name) {
        CategoryDto dto = new CategoryDto();
        dto.setName(name);
        return dto;
    }

    public static CategoryDto getCategory(CategoryTypeEnum category) {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryType(getCategoryType(category));
        return dto;
    }

    public static CategoryTypeDto getCategoryType(CategoryTypeEnum category) {
        CategoryTypeDto dto = new CategoryTypeDto();
        dto.setType(category);
        return dto;
    }

    public static BudgetExpenseDto getBudgetExpense(CategoryTypeEnum category, BigDecimal sum) {
        BudgetExpenseDto dto = new BudgetExpenseDto();
        dto.setEstimatedsum(sum);
        dto.setCategory(getCategory(category));
        return dto;
    }

    public static CompanyDto getCompany(String name) {
        CompanyDto dto = new CompanyDto(name);
        return dto;
    }

}
