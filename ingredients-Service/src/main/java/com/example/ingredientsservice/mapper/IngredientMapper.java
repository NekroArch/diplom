package com.example.ingredientsservice.mapper;

import com.example.ingredientsservice.dto.IngredientsDto;
import org.example.entities.entity.Ingredients;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientsDto mapToIngredientsDto(Ingredients ingredients);

    Ingredients mapToIngredients(IngredientsDto ingredientsDto);
}
