package org.example.mapper;

import org.example.dto.DishesDto;
import org.example.entities.entity.Dishes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DishMapper {
    DishesDto mapToDishesDto(Dishes dishes);

    Dishes mapToDishes(DishesDto dishesDto);
}
