package org.example.roleservice.service;

import org.example.roleservice.dto.RolesDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    List<RolesDto> getAll(Pageable pageable);

    RolesDto getById(Integer id);

    void save(RolesDto addonDto);

    void delete(Integer id);

    void update(RolesDto addonDto);
}
