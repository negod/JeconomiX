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
import se.backede.jeconomix.dto.mappers.CompanyMapper;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CompanyHandler extends CompanyDao {

    private static final CompanyHandler companyHandler = new CompanyHandler();

    protected CompanyHandler() {
    }

    public static final CompanyHandler getInstance() {
        return companyHandler;
    }

    public Optional<CompanyDto> createCompany(CompanyDto company) {
        Company mapToCompany = CompanyMapper.INSTANCE.mapToCompany(company);
        mapToCompany.getTransactions().clear();
        return super.executeTransaction(() -> super.persist(mapToCompany)).map(persistedCompany -> {
            return CompanyMapper.INSTANCE.mapToCompanyDto(persistedCompany);
        });
    }

    public Optional<CompanyDto> updateCompany(CompanyDto companyDto) {
        Company mapToCompany = CompanyMapper.INSTANCE.mapToCompany(companyDto);
        return super.executeTransaction(() -> super.update(mapToCompany)).map(newCompany -> {
            return CompanyMapper.INSTANCE.mapToCompanyDto(newCompany);
        });
    }

    public Optional<CompanyDto> addAccociatedCompany(CompanyDto companyDto) {
        Company mapToCompany = CompanyMapper.INSTANCE.mapToCompany(companyDto);
        return super.executeTransaction(() -> super.update(mapToCompany)).map(persistedCompany -> {
            return CompanyMapper.INSTANCE.mapToCompanyDto(persistedCompany);
        });
    }

    public Optional<List<CompanyDto>> getAllCompanies() {
        return super.getAll().map(companies -> {
            return CompanyMapper.INSTANCE.mapToCompanyDtoList(companies);
        });
    }

    public Optional<CompanyDto> getCompanyByName(String name) {
        return super.getCompanyByCompanyName(name).map(company -> {
            return CompanyMapper.INSTANCE.mapToCompanyDto(company);
        });
    }

    public Optional<CompanyDto> setCategory(CompanyDto companyDto, CategoryDto category) {

        ObjectUpdate update = new ObjectUpdate();
        update.setObject(Company_.category.getName());
        update.setType(UpdateType.UPDATE);
        update.setObjectId(category.getId());

        return super.executeTransaction(() -> super.update(companyDto.getId(), update)).map(company -> {
            return CompanyMapper.INSTANCE.mapToCompanyDto(company);
        });

    }

    public Optional<CompanyDto> getCompanyById(String id) {
        return super.getById(id).map(company -> {
            return CompanyMapper.INSTANCE.mapToCompanyDto(company);
        });
    }

    public void reIndex() {
        super.indexEntity();
    }

}
