package com.example.measureunitservice.service;

import com.example.measureunitservice.dto.MeasureUnitsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MeasureUnitsService {
    List<MeasureUnitsDto> getAll(Pageable pageable);

    MeasureUnitsDto getById(Integer id);

    void save(MeasureUnitsDto dishesDto);

    void delete(Integer id);

    void update(MeasureUnitsDto dishesDto);

}
