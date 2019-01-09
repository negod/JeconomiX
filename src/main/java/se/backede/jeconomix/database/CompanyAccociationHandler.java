/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import se.backede.generics.persistence.mapper.DtoEntityBaseMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.dao.AccociatedCompanyDao;
import se.backede.jeconomix.database.entity.CompanyAccociation;
import se.backede.jeconomix.dto.CompanyAccociationDto;
import se.backede.jeconomix.dto.mappers.CompanyAccociationMapper;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CompanyAccociationHandler extends AccociatedCompanyDao {

    private static final CompanyAccociationHandler INSTANCE = new CompanyAccociationHandler();

    protected CompanyAccociationHandler() {
    }

    public static final CompanyAccociationHandler getInstance() {
        return INSTANCE;
    }

    public Optional<CompanyAccociationDto> getAccociatedCompanyByName(String name) {
        return super.getByAccociatedCompanyByName(name).map(company -> {
            return CompanyAccociationMapper.INSTANCE.mapToCompanyAccociationDto(company);
        });
    }

    public Optional<CompanyAccociationDto> createAccociatedCompany(CompanyAccociationDto dto) {
        CompanyAccociation mapToCompanyAccociation = CompanyAccociationMapper.INSTANCE.mapToCompanyAccociation(dto);
        return super.executeTransaction(() -> super.persist(mapToCompanyAccociation)).map(persisted -> {
            return CompanyAccociationMapper.INSTANCE.mapToCompanyAccociationDto(persisted);
        });
    }

}
