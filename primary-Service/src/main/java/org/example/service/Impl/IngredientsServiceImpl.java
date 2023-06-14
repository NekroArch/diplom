package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.IngredientsDao;
import org.example.dto.IngredientsDto;
import org.example.dto.Pageable;
import org.example.mapper.IngredientsMapper;
import org.example.service.IngredientsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IngredientsServiceImpl implements IngredientsService {
    private final IngredientsDao ingredientsDao;

    private final IngredientsMapper ingredientsMapper;

    @Override
    public List<IngredientsDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return ingredientsDao.getAll(pageable).stream().map(ingredientsMapper::mapToIngredientsDto).toList();
    }

    @Override
    public IngredientsDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return ingredientsMapper.mapToIngredientsDto(ingredientsDao.getById(id));
    }
    @Transactional
    @Override
    public IngredientsDto save(IngredientsDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method save with {}", entityDto);
        return ingredientsMapper.mapToIngredientsDto(ingredientsDao.save(ingredientsMapper.mapToIngredients(entityDto)));
    }
    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        ingredientsDao.delete(id);
    }
    @Transactional
    @Override
    public IngredientsDto update(IngredientsDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return ingredientsMapper.mapToIngredientsDto(ingredientsDao.update(ingredientsMapper.mapToIngredients(entityDto)));
    }
}
