/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export.mapper;

import java.util.ArrayList;
import java.util.List;
import se.backede.jeconomix.dto.BillCategoryDto;
import se.backede.jeconomix.dto.export.BillCategoryExportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BillCategoryMapper {
    
    public static BillCategoryExportDto mapToExportDto(BillCategoryDto input) {
        BillCategoryExportDto dto = new BillCategoryExportDto();
        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setUpdatedDate(input.getUpdatedDate());
        return dto;
    }

    public static BillCategoryDto mapToDto(BillCategoryExportDto input) {
        BillCategoryDto dto = new BillCategoryDto();
        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setUpdatedDate(input.getUpdatedDate());
        BillCategoryDto expDto = new BillCategoryDto();
        return dto;
    }

    public static List<BillCategoryExportDto> mapToExportDto(List<BillCategoryDto> dto) {
        List<BillCategoryExportDto> expList = new ArrayList<>();
        for (BillCategoryDto companyDto : dto) {
            expList.add(mapToExportDto(companyDto));
        }
        return expList;
    }

    public static List<BillCategoryDto> mapToDto(List<BillCategoryExportDto> dto) {
        List<BillCategoryDto> expList = new ArrayList<>();
        for (BillCategoryExportDto companyDto : dto) {
            expList.add(mapToDto(companyDto));
        }
        return expList;
    }
    
}
