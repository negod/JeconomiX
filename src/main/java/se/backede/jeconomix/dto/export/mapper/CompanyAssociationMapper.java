/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import se.backede.jeconomix.dto.CompanyAccociationDto;
import se.backede.jeconomix.dto.export.CompanyAccociationExportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyAssociationMapper {

    public static CompanyAccociationExportDto mapToExportDto(CompanyAccociationDto input) {
        CompanyAccociationExportDto dto = new CompanyAccociationExportDto();
        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setUpdatedDate(input.getUpdatedDate());
        dto.setOriginalName(input.getOriginalName());
        return dto;
    }

    public static CompanyAccociationDto mapToDto(CompanyAccociationExportDto input) {
        CompanyAccociationDto dto = new CompanyAccociationDto();
        dto.setName(input.getName());
        dto.setOriginalName(input.getOriginalName());
        dto.setId(input.getId());
        dto.setUpdatedDate(input.getUpdatedDate());
        return dto;
    }

    public static Set<CompanyAccociationExportDto> mapToExportDto(Set<CompanyAccociationDto> dto) {
        Set<CompanyAccociationExportDto> expList = new HashSet<>();
        for (CompanyAccociationDto companyDto : dto) {
            expList.add(mapToExportDto(companyDto));
        }
        return expList;
    }

    public static Set<CompanyAccociationDto> mapToDto(Set<CompanyAccociationExportDto> dto) {
        Set<CompanyAccociationDto> expList = new HashSet<>();
        for (CompanyAccociationExportDto companyDto : dto) {
            expList.add(mapToDto(companyDto));
        }
        return expList;
    }

}
