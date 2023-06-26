package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.RolesDao;
import org.example.dto.RolesDto;
import org.example.mapper.RolesMapper;
import org.example.service.RolesService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {

    private final RolesDao rolesDao;

    private final RolesMapper rolesMapper;

    @Override
    public List<RolesDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return rolesDao.getAll(pageable).stream().map(rolesMapper::mapToRoleDto).toList();
    }

    @Override
    public RolesDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return rolesMapper.mapToRoleDto(rolesDao.getById(id));
    }

    @Transactional
    @Override
    public RolesDto save(RolesDto entityDto){
        log.debug("Executing method save with {}", entityDto);
        try {
            return rolesMapper.mapToRoleDto(rolesDao.save(rolesMapper.mapToRole(entityDto)));
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        rolesDao.delete(id);
    }

    @Transactional
    @Override
    public RolesDto update(RolesDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return rolesMapper.mapToRoleDto(rolesDao.update(rolesMapper.mapToRole(entityDto)));
    }
}
