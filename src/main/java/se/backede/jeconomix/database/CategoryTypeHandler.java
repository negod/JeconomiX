/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.exception.ConstraintException;
import com.negod.generics.persistence.exception.DaoException;
import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import static java.lang.StrictMath.log;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.dao.CategoryTypeDao;
import se.backede.jeconomix.database.entity.Category;
import se.backede.jeconomix.database.entity.CategoryType;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CategoryTypeDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CategoryTypeHandler {

    CategoryTypeDao dao = new CategoryTypeDao();

    DtoEntityBaseMapper<CategoryTypeDto, CategoryType> mapper = new DtoEntityBaseMapper(CategoryTypeDto.class, CategoryType.class);

    private static final CategoryTypeHandler INSTANCE = new CategoryTypeHandler();

    protected CategoryTypeHandler() {
    }

    public static final CategoryTypeHandler getInstance() {
        return INSTANCE;
    }

    public Optional<List<CategoryTypeDto>> getAllCategoryTypes() {
        try {
            Optional<List<CategoryType>> all = dao.getAll();
            if (all.isPresent()) {
                return mapper.mapToDtoList(all.get());
            }
        } catch (DaoException e) {
            log.error("Error when getting category types", e);
        }
        return Optional.empty();
    }

}