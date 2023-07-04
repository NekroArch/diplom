package com.example.addonservice.service;

import com.example.addonservice.dto.AddonsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddonService {
    List<AddonsDto> getAll(Pageable pageable);

    AddonsDto getById(Integer id);

    void save(AddonsDto addonDto);

    void delete(Integer id);

    void update(AddonsDto addonDto);
}
