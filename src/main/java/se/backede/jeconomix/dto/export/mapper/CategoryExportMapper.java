/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export.mapper;

import java.util.ArrayList;
import java.util.List;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CategoryTypeDto;
import se.backede.jeconomix.dto.export.CategoryExportDto;
import se.backede.jeconomix.dto.export.CategoryTypeExportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryExportMapper {

    public static CategoryExportDto mapToExportDto(CategoryDto input) {
        CategoryExportDto dto = new CategoryExportDto();
        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setUpdatedDate(input.getUpdatedDate());
        dto.setCategoryType(mapToExportCategoryType(input));
        return dto;
    }

    public static CategoryDto mapToDto(CategoryExportDto input) {
        CategoryDto dto = new CategoryDto();
        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setUpdatedDate(input.getUpdatedDate());
        dto.setCategoryType(mapToCategoryType(input));
        return dto;
    }

    public static CategoryTypeExportDto mapToExportCategoryType(CategoryDto dto) {
        if (dto.getCategoryType() != null) {
            return new CategoryTypeExportDto(dto.getCategoryType().getId(), dto.getCategoryType().getType());
        }
        return new CategoryTypeExportDto();

    }

    public static CategoryTypeDto mapToCategoryType(CategoryExportDto dto) {
        if (dto.getCategoryType() != null) {
            CategoryTypeDto categoryType = new CategoryTypeDto();
            categoryType.setType(dto.getCategoryType().getType());
            categoryType.setId(categoryType.getId());
            return categoryType;
        }
        return new CategoryTypeDto();

    }

    public static List<CategoryExportDto> mapToExportDto(List<CategoryDto> dto) {
        List<CategoryExportDto> expList = new ArrayList<>();
        for (CategoryDto companyDto : dto) {
            expList.add(mapToExportDto(companyDto));
        }
        return expList;
    }

    public static List<CategoryDto> mapToDto(List<CategoryExportDto> dto) {
        List<CategoryDto> expList = new ArrayList<>();
        for (CategoryExportDto companyDto : dto) {
            expList.add(mapToDto(companyDto));
        }
        return expList;
    }

}
