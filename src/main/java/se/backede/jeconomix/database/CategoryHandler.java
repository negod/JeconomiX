/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import com.negod.generics.persistence.update.ObjectUpdate;
import com.negod.generics.persistence.update.UpdateType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.query.Query;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.constants.EntityQueries;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.database.dao.CategoryDao;
import se.backede.jeconomix.database.entity.Category;
import se.backede.jeconomix.dto.CategoryTypeDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CategoryHandler extends CategoryDao {

    DtoEntityBaseMapper<CategoryDto, Category> mapper = new DtoEntityBaseMapper<>(CategoryDto.class, Category.class);

    private static final CategoryHandler INSTANCE = new CategoryHandler();

    protected CategoryHandler() {
    }

    public static final CategoryHandler getInstance() {
        return INSTANCE;
    }

    public Optional<CategoryDto> getCategoryById(String id) {
        return super.executeTransaction(() -> super.getById(id)).map(category -> {
            return mapper.mapFromEntityToDto(category).get();
        });
    }

    public Optional<CategoryDto> createCategory(CategoryDto category) {
        return mapper.mapFromDtoToEntity(category).map(categoryEntity -> {
            return CategoryTypeHandler.getInstance().getById(categoryEntity.getCategoryType().getId()).map(categoryType -> {
                categoryEntity.setCategoryType(categoryType);
                return super.executeTransaction(() -> super.persist(categoryEntity)).map(persisted -> {
                    return mapper.mapFromEntityToDto(persisted).get();
                }).get();
            });
        }).orElse(Optional.empty());
    }

    public Optional<List<CategoryDto>> getAllCategories() {
        Optional<List<Category>> all = super.getAll();
        if (all.isPresent()) {
            return mapper.mapToDtoList(all.get());
        }
        return Optional.empty();
    }

    public Optional<List<CategoryDto>> getFilteredCategories(Integer year, CategoryTypeEnum... type) {
        try {
            super.startTransaction();
            Query query = super.getHibernateSession().getNamedQuery(EntityQueries.FILTERED_CATEGORIES_BY_YEAR);
            query.setParameter("year", year);
            query.setParameter("type", type);
            query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Category> categories = (List<Category>) query.list();
            return mapper.mapToDtoList(categories);
        } catch (NoResultException ex) {
            log.debug("No result for query when getting Category");
        } finally {
            super.commitTransaction();
        }
        return Optional.empty();
    }

    public Optional<List<CategoryDto>> getFilteredCategories(CategoryTypeEnum... type) {
        try {
            super.startTransaction();
            Query query = super.getHibernateSession().getNamedQuery(EntityQueries.FILTERED_CATEGORIES);
            query.setParameter("type", Arrays.asList(type));
            query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Category> categories = (List<Category>) query.list();
            return mapper.mapToDtoList(categories);
        } catch (NoResultException ex) {
            log.debug("No result for query when getting Category");
        } finally {
            super.commitTransaction();
        }
        return Optional.empty();
    }

    public Optional<CategoryDto> setCategoryType(CategoryDto category, CategoryTypeDto categoryType) {
        if (categoryType != null) {
            ObjectUpdate update = new ObjectUpdate();
            update.setObject("categoryType");
            update.setType(UpdateType.UPDATE);
            update.setObjectId(categoryType.getId());

            super.startTransaction();
            Optional<Category> billCategory = super.update(category.getId(), update);
            super.commitTransaction();

            if (billCategory.isPresent()) {
                return mapper.mapFromEntityToDto(billCategory.get());
            }

        }

        return Optional.empty();
    }
}
