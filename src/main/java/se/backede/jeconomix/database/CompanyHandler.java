/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import se.backede.generics.persistence.mapper.DtoEntityBaseMapper;
import se.backede.generics.persistence.update.ObjectUpdate;
import se.backede.generics.persistence.update.UpdateType;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.database.dao.CompanyDao;
import se.backede.jeconomix.database.entity.Company;
import se.backede.jeconomix.database.entity.Company_;
import se.backede.jeconomix.dto.CategoryDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CompanyHandler extends CompanyDao {

    private DtoEntityBaseMapper<CompanyDto, Company> companyMapper = new DtoEntityBaseMapper(CompanyDto.class, Company.class);

    private static final CompanyHandler companyHandler = new CompanyHandler();

    protected CompanyHandler() {
    }

    public static final CompanyHandler getInstance() {
        return companyHandler;
    }

    public Optional<CompanyDto> createCompany(CompanyDto company) {
        return companyMapper.mapFromDtoToEntity(company).map(companyEntity -> {
            companyEntity.getTransactions().clear();
            return super.executeTransaction(() -> super.persist(companyEntity)).map(persistedCompany -> {
                return companyMapper.mapFromEntityToDto(persistedCompany).get();
            });
        }).orElse(Optional.empty());
    }

    public Optional<CompanyDto> updateCompany(CompanyDto companyDto) {
        return companyMapper.mapFromDtoToEntity(companyDto).map(companyEntity -> {
            return super.executeTransaction(() -> super.update(companyEntity)).map(newCompany -> {
                return companyMapper.mapFromEntityToDto(newCompany).get();
            });
        }).orElse(Optional.empty());
    }

    public Optional<CompanyDto> addAccociatedCompany(CompanyDto companyDto) {
        return companyMapper.mapFromDtoToEntity(companyDto).map(company -> {
            return super.executeTransaction(() -> super.update(company)).map(persistedCompany -> {
                return companyMapper.mapFromEntityToDto(persistedCompany).get();
            }).get();
        });

    }

    public Optional<List<CompanyDto>> getAllCompanies() {
        return super.getAll().map(companies -> {
            return companyMapper.mapToDtoList(companies).get();
        });
    }

    public Optional<CompanyDto> getCompanyByName(String name) {
        log.debug("Getting company by name {}", name);
        return super.getCompanyByCompanyName(name).map(company -> {
            return companyMapper.mapFromEntityToDto(company).get();
        });
    }

    public Optional<CompanyDto> setCategory(CompanyDto companyDto, CategoryDto category) {

        ObjectUpdate update = new ObjectUpdate();
        update.setObject(Company_.category.getName());
        update.setType(UpdateType.UPDATE);
        update.setObjectId(category.getId());

        return super.executeTransaction(() -> super.update(companyDto.getId(), update)).map(company -> {
            return companyMapper.mapFromEntityToDto(company).get();
        });

    }

    public Optional<CompanyDto> getCompanyById(String id) {
        return super.getById(id).map(company -> {
            return companyMapper.mapFromEntityToDto(company).get();
        });
    }

    public void reIndex() {
        super.indexEntity();
    }

}
