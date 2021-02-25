package com.javaschool.onlineshop.mappers;

import com.javaschool.onlineshop.model.dto.CategoryDTO;
import com.javaschool.onlineshop.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO categoryToCategoryDTO(Category category);

    Category categoryDTOToCategory(CategoryDTO categoryDTO);
}
