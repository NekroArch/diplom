package com.example.ingredientsservice.service;

import com.example.ingredientsservice.dto.IngredientsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IngredientService {
    List<IngredientsDto> getAll(Pageable pageable);

    IngredientsDto getById(Integer id);

    void save(IngredientsDto dishesDto);

    void delete(Integer id);

    void update(IngredientsDto dishesDto);

}
