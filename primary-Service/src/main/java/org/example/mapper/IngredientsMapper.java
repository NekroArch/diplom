package org.example.mapper;

import org.example.dao.IngredientsDao;
import org.example.dto.IngredientsDto;
import org.example.dto.OrdersDto;
import org.example.entity.Ingredients;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientsMapper {
    IngredientsDto mapToIngredientsDto(Ingredients ingredients);

    Ingredients mapToIngredients(IngredientsDto ingredientsDto);
}
