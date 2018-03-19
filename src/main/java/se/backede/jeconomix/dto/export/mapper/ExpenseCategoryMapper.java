/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export.mapper;

import java.util.ArrayList;
import java.util.List;
import se.backede.jeconomix.dto.ExpenseCategoryDto;
import se.backede.jeconomix.dto.ExpenseCategoryDto;
import se.backede.jeconomix.dto.export.ExpenseCategoryExportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class ExpenseCategoryMapper {

    public static ExpenseCategoryExportDto mapToExportDto(ExpenseCategoryDto input) {
        ExpenseCategoryExportDto dto = new ExpenseCategoryExportDto();
        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setUpdatedDate(input.getUpdatedDate());
        return dto;
    }

    public static ExpenseCategoryDto mapToDto(ExpenseCategoryExportDto input) {
        ExpenseCategoryDto dto = new ExpenseCategoryDto();
        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setUpdatedDate(input.getUpdatedDate());
        ExpenseCategoryDto expDto = new ExpenseCategoryDto();
        return dto;
    }

    public static List<ExpenseCategoryExportDto> mapToExportDto(List<ExpenseCategoryDto> dto) {
        List<ExpenseCategoryExportDto> expList = new ArrayList<>();
        for (ExpenseCategoryDto companyDto : dto) {
            expList.add(mapToExportDto(companyDto));
        }
        return expList;
    }

    public static List<ExpenseCategoryDto> mapToDto(List<ExpenseCategoryExportDto> dto) {
        List<ExpenseCategoryDto> expList = new ArrayList<>();
        for (ExpenseCategoryExportDto companyDto : dto) {
            expList.add(mapToDto(companyDto));
        }
        return expList;
    }

}
