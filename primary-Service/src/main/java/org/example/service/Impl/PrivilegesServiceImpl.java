package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.PrivilegesDao;
import org.example.dto.Pageable;
import org.example.dto.PrivilegesDto;
import org.example.mapper.PrivilegesMapper;
import org.example.service.PrivilegesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PrivilegesServiceImpl implements PrivilegesService {

    private final PrivilegesDao privilegesDao;

    private final PrivilegesMapper privilegesMapper;

    @Override
    public List<PrivilegesDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return privilegesDao.getAll(pageable).stream().map(privilegesMapper::mapToPrivilegesDto).toList();
    }

    @Override
    public PrivilegesDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return privilegesMapper.mapToPrivilegesDto(privilegesDao.getById(id));
    }

    @Transactional
    @Override
    public PrivilegesDto save(PrivilegesDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method save with {}", entityDto);
        return privilegesMapper.mapToPrivilegesDto(privilegesDao.save(privilegesMapper.mapToPrivileges(entityDto)));
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        privilegesDao.delete(id);
    }

    @Transactional
    @Override
    public PrivilegesDto update(PrivilegesDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return privilegesMapper.mapToPrivilegesDto(privilegesDao.update(privilegesMapper.mapToPrivileges(entityDto)));
    }
}
