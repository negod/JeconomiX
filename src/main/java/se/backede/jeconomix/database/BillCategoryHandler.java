/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.exception.ConstraintException;
import com.negod.generics.persistence.exception.DaoException;
import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.dto.BillCategoryDto;
import se.backede.jeconomix.database.dao.BillCategoryDao;
import se.backede.jeconomix.database.entity.BillCategory;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class BillCategoryHandler {

    BillCategoryDao dao = new BillCategoryDao();
    DtoEntityBaseMapper<BillCategoryDto, BillCategory> mapper = new DtoEntityBaseMapper(BillCategoryDto.class, BillCategory.class);

    private static final BillCategoryHandler companyHandler = new BillCategoryHandler();

    protected BillCategoryHandler() {
    }

    public static final BillCategoryHandler getInstance() {
        return companyHandler;
    }

    public Optional<BillCategoryDto> getById(String id) {
        try {
            dao.startTransaction();
            Optional<BillCategory> byId = dao.getById(id);
            dao.commitTransaction();

            if (byId.isPresent()) {
                return mapper.mapFromEntityToDto(byId.get());
            }
        } catch (DaoException ex) {
            Logger.getLogger(BillCategoryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    public Optional<BillCategoryDto> createBillCategory(BillCategoryDto company) {
        Optional<BillCategory> entity = mapper.mapFromDtoToEntity(company);
        if (entity.isPresent()) {
            try {
                dao.startTransaction();
                Optional<BillCategory> persist = dao.persist(entity.get());
                dao.commitTransaction();

                if (persist.isPresent()) {
                    return mapper.mapFromEntityToDto(entity.get());
                }

            } catch (DaoException | ConstraintException ex) {
                log.error("Error when persisting expense category", ex);
            }
        }
        return Optional.empty();
    }

    public Optional<List<BillCategoryDto>> getAllBillCategories() {
        try {
            Optional<List<BillCategory>> all = dao.getAll();
            if (all.isPresent()) {
                return mapper.mapToDtoList(all.get());
            }
        } catch (DaoException e) {
            log.error("Error when getting expenseCategories", e);
        }
        return Optional.empty();
    }
}
