package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.AddonsDao;
import org.example.dto.AddonsDto;
import org.example.mapper.AddonsMapper;
import org.example.service.AddonsService;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddonsServiceImpl implements AddonsService {
    private final AddonsDao addonsDao;
    private final AddonsMapper addonsMapper;

    @Override
    public List<AddonsDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return addonsDao.getAll(pageable).stream().map(addonsMapper::mapToAddonsDto).toList();
    }

    @Override
    public AddonsDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return addonsMapper.mapToAddonsDto(addonsDao.getById(id));
    }

    @Transactional
    @Override
    public AddonsDto save(AddonsDto entityDto){
        log.debug("Executing method save with {}", entityDto);
        try {
            return addonsMapper.mapToAddonsDto(addonsDao.save(addonsMapper.mapToAddons(entityDto)));
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        addonsDao.delete(id);
    }

    @Transactional
    @Override
    public AddonsDto update(AddonsDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return addonsMapper.mapToAddonsDto(addonsDao.update(addonsMapper.mapToAddons(entityDto)));
    }
}
