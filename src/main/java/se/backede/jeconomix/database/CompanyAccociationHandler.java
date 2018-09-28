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

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CompanyAccociationHandler extends AccociatedCompanyDao {

    private final DtoEntityBaseMapper<CompanyAccociationDto, CompanyAccociation> MAPPER = new DtoEntityBaseMapper<>(CompanyAccociationDto.class, CompanyAccociation.class);

    private static final CompanyAccociationHandler INSTANCE = new CompanyAccociationHandler();

    protected CompanyAccociationHandler() {
    }

    public static final CompanyAccociationHandler getInstance() {
        return INSTANCE;
    }

    public Optional<CompanyAccociationDto> getAccociatedCompanyByName(String name) {
        return super.getByAccociatedCompanyByName(name).map(company -> {
            return MAPPER.mapFromEntityToDto(company).get();
        });
    }

    public Optional<CompanyAccociationDto> createAccociatedCompany(CompanyAccociationDto dto) {
        return MAPPER.mapFromDtoToEntity(dto).map(entity -> {
            return super.executeTransaction(() -> super.persist(entity)).map(persisted -> {
                return MAPPER.mapFromEntityToDto(persisted).get();
            });
        }).orElse(Optional.empty());
    }

}
