/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.GenericDao;
import com.negod.generics.persistence.exception.ConstraintException;
import com.negod.generics.persistence.exception.DaoException;
import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import com.negod.generics.persistence.update.ObjectUpdate;
import com.negod.generics.persistence.update.UpdateType;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.database.dao.CategoryDao;
import se.backede.jeconomix.database.entity.Category;
import se.backede.jeconomix.database.entity.CategoryType;
import se.backede.jeconomix.dto.CategoryTypeDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CategoryHandler {

    final CategoryDao DAO = new CategoryDao();
    DtoEntityBaseMapper<CategoryDto, Category> mapper = new DtoEntityBaseMapper(CategoryDto.class, Category.class);

    private static final CategoryHandler companyHandler = new CategoryHandler();

    protected CategoryHandler() {
    }

    public static final CategoryHandler getInstance() {
        return companyHandler;
    }

    public Optional<CategoryDto> getById(String id) {
        try {
            DAO.startTransaction();
            Optional<Category> byId = DAO.getById(id);
            DAO.commitTransaction();

            if (byId.isPresent()) {
                return mapper.mapFromEntityToDto(byId.get());
            }
        } catch (DaoException ex) {
            Logger.getLogger(CategoryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    public Optional<CategoryDto> createCategory(CategoryDto category) {
        Optional<Category> entity = mapper.mapFromDtoToEntity(category);
        Optional<CategoryDto> persistedCategory = Optional.empty();
        if (entity.isPresent()) {
            try {

                DAO.startTransaction();

                Optional<CategoryType> categoryType = CategoryTypeHandler.getInstance().getById(category.getCategoryType().getId());
                entity.get().setCategoryType(categoryType.get());
                Optional<Category> persist = DAO.persist(entity.get());

                if (persist.isPresent()) {
                    persistedCategory = mapper.mapFromEntityToDto(entity.get());
                }

                DAO.commitTransaction();

            } catch (DaoException | ConstraintException ex) {
                log.error("Error when persisting expense category", ex);

            }
        }
        return persistedCategory;
    }

    private Predicate<CategoryDto> isCategoryType(CategoryTypeEnum type) {
        return p -> p.getCategoryType().getType().equals(type);
    }

    public Optional<List<CategoryDto>> getAllCategories() {
        try {
            Optional<List<Category>> all = DAO.getAll();
            if (all.isPresent()) {
                return mapper.mapToDtoList(all.get());
            }
        } catch (DaoException e) {
            log.error("Error when getting expenseCategories", e);
        }
        return Optional.empty();
    }

    public Optional<List<CategoryDto>> getFilteredCategories(CategoryTypeEnum type) {
        try {

            Optional<List<Category>> all = DAO.getAll();
            if (all.isPresent()) {
                Optional<List<CategoryDto>> mapToDtoList = mapper.mapToDtoList(all.get());
                List<CategoryDto> collect = mapToDtoList.get()
                        .stream()
                        .filter(isCategoryType(type))
                        .collect(Collectors.<CategoryDto>toList());

                return Optional.ofNullable(collect);
            }
        } catch (DaoException e) {
            log.error("Error when getting expenseCategories", e);
        }
        return Optional.empty();
    }

    public Optional<CategoryDto> setCategoryType(CategoryDto category, CategoryTypeDto categoryType) {
        if (categoryType != null) {
            ObjectUpdate update = new ObjectUpdate();
            update.setObject("categoryType");
            update.setType(UpdateType.UPDATE);
            update.setObjectId(categoryType.getId());

            try {
                DAO.startTransaction();
                Optional<Category> billCategory = DAO.update(category.getId(), update);
                DAO.commitTransaction();

                if (billCategory.isPresent()) {
                    return mapper.mapFromEntityToDto(billCategory.get());
                }

            } catch (DaoException ex) {
                log.error("Error when updating expense category");
            }
        }

        return Optional.empty();
    }
}
