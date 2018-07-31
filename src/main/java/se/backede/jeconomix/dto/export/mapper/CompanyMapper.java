/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export.mapper;

import java.util.ArrayList;
import java.util.List;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.export.CompanyExportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyMapper {

    public static CompanyExportDto mapToExportDto(CompanyDto input) {
        CompanyExportDto dto = new CompanyExportDto();
        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setUpdatedDate(input.getUpdatedDate());
        dto.setCategory(input.getCategory().getId());
        return dto;
    }

    public static CompanyDto mapToDto(CompanyExportDto input) {
        CompanyDto dto = new CompanyDto(input.getName());
        dto.setId(input.getId());
        dto.setUpdatedDate(input.getUpdatedDate());
        CategoryDto expDto = new CategoryDto();
        expDto.setId(input.getCategory());
        return dto;
    }

    public static List<CompanyExportDto> mapToExportDto(List<CompanyDto> dto) {
        List<CompanyExportDto> expList = new ArrayList<>();
        for (CompanyDto companyDto : dto) {
            expList.add(mapToExportDto(companyDto));
        }
        return expList;
    }

    public static List<CompanyDto> mapToDto(List<CompanyExportDto> dto) {
        List<CompanyDto> expList = new ArrayList<>();
        for (CompanyExportDto companyDto : dto) {
            expList.add(mapToDto(companyDto));
        }
        return expList;
    }

}
