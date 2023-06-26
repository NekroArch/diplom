package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.AddonsCategoryDao;
import org.example.dto.AddonsCategoryDto;
import org.example.mapper.AddonsCategoryMapper;
import org.example.service.AddonsCategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddonsCategoryServiceImpl implements AddonsCategoryService {
    private final AddonsCategoryDao addonsCategoryDao;
    private final AddonsCategoryMapper addonsCategoryMapper;

    @Override
    public List<AddonsCategoryDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return addonsCategoryDao.getAll(pageable).stream().map(addonsCategoryMapper::mapToAddonsCategoryDto).toList();
    }

    @Override
    public AddonsCategoryDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return addonsCategoryMapper.mapToAddonsCategoryDto(addonsCategoryDao.getById(id));
    }

    @Transactional
    @Override
    public AddonsCategoryDto save(AddonsCategoryDto entityDto) {
        log.debug("Executing method save with {}", entityDto);
        try {
            return addonsCategoryMapper.mapToAddonsCategoryDto(addonsCategoryDao.save(addonsCategoryMapper.mapToAddonsCategory(entityDto)));
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        addonsCategoryDao.delete(id);
    }

    @Transactional
    @Override
    public AddonsCategoryDto update(AddonsCategoryDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return addonsCategoryMapper.mapToAddonsCategoryDto(addonsCategoryDao.update(addonsCategoryMapper.mapToAddonsCategory(entityDto)));
    }
}
