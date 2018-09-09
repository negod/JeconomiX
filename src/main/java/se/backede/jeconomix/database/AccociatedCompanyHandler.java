/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.exception.DaoException;
import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import com.negod.generics.persistence.update.ObjectUpdate;
import com.negod.generics.persistence.update.UpdateType;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.dao.AccociatedCompanyDao;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.database.dao.CompanyDao;
import se.backede.jeconomix.database.entity.Company;
import se.backede.jeconomix.database.entity.CompanyAccociation;
import se.backede.jeconomix.database.entity.Transaction;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CompanyAccociationDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class AccociatedCompanyHandler extends AccociatedCompanyDao {

    private final DtoEntityBaseMapper<CompanyAccociationDto, CompanyAccociation> accociatedCompanyMapper = new DtoEntityBaseMapper(CompanyAccociationDto.class, CompanyAccociation.class);
    private final DtoEntityBaseMapper<CompanyDto, Company> companyMapper = new DtoEntityBaseMapper(CompanyDto.class, Company.class);

    private static final AccociatedCompanyHandler INSTANCE = new AccociatedCompanyHandler();

    protected AccociatedCompanyHandler() {
    }

    public static final AccociatedCompanyHandler getInstance() {
        return INSTANCE;
    }

    public Optional<CompanyAccociationDto> getAccociatedCompanyByName(String name) {
        return super.getByAccociatedCompanyByName(name).map(company -> {
            return accociatedCompanyMapper.mapFromEntityToDto(company).get();
        });
    }

}
