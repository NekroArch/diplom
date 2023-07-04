package org.example.demo.mapper;

import org.example.demo.dto.DishesDto;
import org.example.entities.entity.Dishes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DishMapper {
    DishesDto mapToDishesDto(Dishes user);

    Dishes mapToDishes(DishesDto userDto);
}
