package com.example.addoncategoryservice.service;

import com.example.addoncategoryservice.dto.AddonCategoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddonCategoryService {
    List<AddonCategoryDto> getAll(Pageable pageable);

    AddonCategoryDto getById(Integer id);

    void save(AddonCategoryDto dishesDto);

    void delete(Integer id);

    void update(AddonCategoryDto dishesDto);

}
