package org.example.privilegesservice.service;

import org.example.privilegesservice.dto.PrivilegesDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PrivilegesService {
    List<PrivilegesDto> getAll(Pageable pageable);

    PrivilegesDto getById(Integer id);

    void save(PrivilegesDto dishesDto);

    void delete(Integer id);

    void update(PrivilegesDto dishesDto);

}
