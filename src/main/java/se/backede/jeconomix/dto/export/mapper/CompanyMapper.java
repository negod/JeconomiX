/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.ExpenseCategoryDto;
import se.backede.jeconomix.dto.export.CompanyExportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyMapper {

    private static final CompanyMapper MAPPER = new CompanyMapper();

    protected CompanyMapper() {
    }

    public static final CompanyMapper getInstance() {
        return MAPPER;
    }

    public CompanyExportDto mapToExportDto(CompanyDto input) {
        CompanyExportDto dto = new CompanyExportDto();
        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setUpdatedDate(input.getUpdatedDate());
        dto.setExpenseCategory(input.getExpenseCategory().getId());
        return dto;
    }

    public CompanyDto mapToDto(CompanyExportDto input) {
        CompanyDto dto = new CompanyDto();
        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setUpdatedDate(input.getUpdatedDate());
        ExpenseCategoryDto expDto = new ExpenseCategoryDto();
        expDto.setId(input.getExpenseCategory());
        return dto;
    }

    public List<CompanyExportDto> mapToExportDto(List<CompanyDto> dto) {
        List<CompanyExportDto> expList = new ArrayList<>();
        for (CompanyDto companyDto : dto) {
            expList.add(mapToExportDto(companyDto));
        }
        return expList;
    }

    public List<CompanyDto> mapToDto(List<CompanyExportDto> dto) {
        List<CompanyDto> expList = new ArrayList<>();
        for (CompanyExportDto companyDto : dto) {
            expList.add(mapToDto(companyDto));
        }
        return expList;
    }

}
