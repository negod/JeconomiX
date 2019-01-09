/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import se.backede.generics.persistence.mapper.DtoEntityBaseMapper;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.dao.CategoryTypeDao;
import se.backede.jeconomix.database.entity.CategoryType;
import se.backede.jeconomix.dto.CategoryTypeDto;
import se.backede.jeconomix.dto.mappers.CategoryTypeMapper;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CategoryTypeHandler {

    private final CategoryTypeDao dao = new CategoryTypeDao();

    private static final CategoryTypeHandler INSTANCE = new CategoryTypeHandler();

    protected CategoryTypeHandler() {
    }

    public static final CategoryTypeHandler getInstance() {
        return INSTANCE;
    }

    public Optional<List<CategoryTypeDto>> getAllCategoryTypes() {
        Optional<List<CategoryType>> all = dao.getAll();
        if (all.isPresent()) {
            return Optional.ofNullable(CategoryTypeMapper.INSTANCE.mapToCategoryTypeDtoList(all.get()));
        }
        return Optional.empty();
    }

    public Optional<CategoryType> getById(String id) {
        return dao.getById(id);
    }

}
