package org.example.mapper;

import org.example.dto.IngredientsDto;
import org.example.entities.entity.Ingredients;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientsMapper {
    IngredientsDto mapToIngredientsDto(Ingredients ingredients);

    Ingredients mapToIngredients(IngredientsDto ingredientsDto);
}
