package com.example.statusservice.service;

import com.example.statusservice.dto.StatusDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatusService {
    List<StatusDto> getAll(Pageable pageable);

    StatusDto getById(Integer id);

    void save(StatusDto addonDto);

    void delete(Integer id);

    void update(StatusDto addonDto);
}
