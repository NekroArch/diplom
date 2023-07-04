package org.example.demo.service;

import org.example.demo.dto.DishesDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DishesService {
    List<DishesDto> getAll(Pageable pageable);

    DishesDto getById(Integer id);

    void save(DishesDto dishesDto);

    void delete(Integer id);

    void update(DishesDto dishesDto);

}
