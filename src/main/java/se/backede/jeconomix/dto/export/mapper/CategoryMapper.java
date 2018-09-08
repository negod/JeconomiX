/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export.mapper;

import java.util.ArrayList;
import java.util.List;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.export.CategoryExportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryMapper {

    public static CategoryExportDto mapToExportDto(CategoryDto input) {
        CategoryExportDto dto = new CategoryExportDto();
        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setUpdatedDate(input.getUpdatedDate());
        return dto;
    }

    public static CategoryDto mapToDto(CategoryExportDto input) {
        CategoryDto dto = new CategoryDto();
        dto.setId(input.getId());
        dto.setUpdatedDate(input.getUpdatedDate());
        CategoryDto expDto = new CategoryDto();
        return dto;
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
