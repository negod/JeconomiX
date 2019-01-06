/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import se.backede.jeconomix.database.entity.CompanyAccociation;
import se.backede.jeconomix.dto.CompanyAccociationDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Mapper
public interface CompanyAccociationMapper {

    CompanyAccociationMapper INSTANCE = Mappers.getMapper(CompanyAccociationMapper.class);

    CompanyAccociation mapToCompanyAccociation(CompanyAccociationDto company);

    @Mappings({
        @Mapping(target = "company", ignore = true)
    })
    CompanyAccociationDto mapToCompanyAccociationDto(CompanyAccociation company);

    List<CompanyAccociation> mapToCompanyAccociationList(List<CompanyAccociationDto> companies);

    List<CompanyAccociationDto> mapToCompanyAccociationDtoList(List<CompanyAccociation> companies);

}
