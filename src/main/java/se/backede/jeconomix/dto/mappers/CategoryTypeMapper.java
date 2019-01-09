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
import se.backede.jeconomix.database.entity.CategoryType;
import se.backede.jeconomix.dto.CategoryTypeDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Mapper(uses = {CategoryMapper.class})
public interface CategoryTypeMapper {

    CategoryTypeMapper INSTANCE = Mappers.getMapper(CategoryTypeMapper.class);

    CategoryType mapToCategoryType(CategoryTypeDto categoryType);

    CategoryTypeDto mapToCategoryTypeDto(CategoryType categoryType);

    List<CategoryType> mapToCategoryTypeList(List<CategoryTypeDto> categoryTypes);

    List<CategoryTypeDto> mapToCategoryTypeDtoList(List<CategoryType> categoryTypes);

}
