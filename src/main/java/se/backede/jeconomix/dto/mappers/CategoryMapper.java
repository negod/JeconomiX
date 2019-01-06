/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import se.backede.jeconomix.database.entity.Category;
import se.backede.jeconomix.database.entity.Category;
import se.backede.jeconomix.dto.CategoryDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category mapToCategory(CategoryDto category);

    @Mappings({
        @Mapping(target = "companies", ignore = true)
    })
    CategoryDto mapToCategoryDto(Category category);

    List<Category> mapToCategoryList(List<CategoryDto> categories);

    List<CategoryDto> mapToCategoryDtoList(List<Category> categories);

}
