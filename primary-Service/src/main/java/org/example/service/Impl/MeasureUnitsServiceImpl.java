package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.MeasureUnitsDao;
import org.example.dto.MeasureUnitsDto;
import org.example.mapper.MeasureUnitsMapper;
import org.example.service.MeasureUnitsService;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeasureUnitsServiceImpl implements MeasureUnitsService {
    private final MeasureUnitsDao measureUnitsDao;
    private final MeasureUnitsMapper measureUnitsMapper;

    @Override
    public List<MeasureUnitsDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return measureUnitsDao.getAll(pageable).stream().map(measureUnitsMapper::mapToMeasureUnitsDto).toList();
    }

    @Override
    public MeasureUnitsDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return measureUnitsMapper.mapToMeasureUnitsDto(measureUnitsDao.getById(id));
    }

    @Transactional
    @Override
    public MeasureUnitsDto save(MeasureUnitsDto entityDto){
        log.debug("Executing method save with {}", entityDto);
        try {
            return measureUnitsMapper.mapToMeasureUnitsDto(measureUnitsDao.save(measureUnitsMapper.mapToMeasureUnits(entityDto)));
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        measureUnitsDao.delete(id);
    }

    @Transactional
    @Override
    public MeasureUnitsDto update(MeasureUnitsDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return measureUnitsMapper.mapToMeasureUnitsDto(measureUnitsDao.update(measureUnitsMapper.mapToMeasureUnits(entityDto)));
    }
}
