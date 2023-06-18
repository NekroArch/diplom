package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.DishesDao;
import org.example.dto.DishesDto;
import org.example.mapper.DishMapper;
import org.example.service.DishesService;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DishesServiceImpl implements DishesService {
    private final DishesDao dishesDao;
    private final DishMapper dishMapper;

    @Override
    public List<DishesDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return dishesDao.getAll(pageable).stream().map(dishMapper::mapToDishesDto).collect(Collectors.toList());
    }

    @Override
    public DishesDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return dishMapper.mapToDishesDto(dishesDao.getById(id));
    }

    @Transactional
    @Override
    public DishesDto save(DishesDto entityDto){
        log.debug("Executing method save with {}", entityDto);
        try {
            return dishMapper.mapToDishesDto(dishesDao.save(dishMapper.mapToDishes(entityDto)));
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        dishesDao.delete(id);
    }

    @Transactional
    @Override
    public DishesDto update(DishesDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return dishMapper.mapToDishesDto(dishesDao.update(dishMapper.mapToDishes(entityDto)));
    }
}
