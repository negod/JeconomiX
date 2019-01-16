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
import se.backede.jeconomix.database.entity.Company;
import se.backede.jeconomix.dto.CompanyDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Mapper(uses = {CategoryMapper.class, CompanyAccociationMapper.class, TransactionCompanyMapper.class})
public interface CompanyTransactionsMapper {

    CompanyTransactionsMapper INSTANCE = Mappers.getMapper(CompanyTransactionsMapper.class);

    Company mapToCompany(CompanyDto company);

    CompanyDto mapToCompanyDto(Company company);

    List<Company> mapToCompanyList(List<CompanyDto> companies);

    List<CompanyDto> mapToCompanyDtoList(List<Company> companies);

}
