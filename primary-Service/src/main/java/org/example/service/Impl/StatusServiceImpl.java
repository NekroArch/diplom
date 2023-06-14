package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.StatusDao;
import org.example.dto.Pageable;
import org.example.dto.StatusDto;
import org.example.mapper.StatusMapper;
import org.example.service.StatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusMapper statusMapper;

    private final StatusDao statusDao;
    @Override
    public List<StatusDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return statusDao.getAll(pageable).stream().map(statusMapper::mapToStatusDto).toList();
    }

    @Override
    public StatusDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return statusMapper.mapToStatusDto(statusDao.getById(id));
    }
    @Transactional
    @Override
    public StatusDto save(StatusDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method save with {}", entityDto);
        return statusMapper.mapToStatusDto(statusDao.save(statusMapper.mapToStatus(entityDto)));
    }
    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        statusDao.delete(id);
    }
    @Transactional
    @Override
    public StatusDto update(StatusDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update {}", entityDto);
        return statusMapper.mapToStatusDto(statusDao.update(statusMapper.mapToStatus(entityDto)));
    }
}
