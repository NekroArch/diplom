package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.MenuDao;
import org.example.dto.MenuDto;
import org.example.dto.Pageable;
import org.example.mapper.MenuMapper;
import org.example.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuDao menuDao;

    private final MenuMapper menuMapper;
    @Override
    public List<MenuDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return menuDao.getAll(pageable).stream().map(menuMapper::mapToMenuDto).toList();
    }


    @Override
    public MenuDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return menuMapper.mapToMenuDto(menuDao.getById(id));
    }
    @Transactional
    @Override
    public MenuDto save(MenuDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method save with {}", entityDto);
        return menuMapper.mapToMenuDto(menuDao.save(menuMapper.mapToMenu(entityDto)));
    }
    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        menuDao.delete(id);
    }
    @Transactional
    @Override
    public MenuDto update(MenuDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);

        return menuMapper.mapToMenuDto(menuDao.update(menuMapper.mapToMenu(entityDto)));
    }

    @Override
    public MenuDto getDishInMenuByName(String name) {
        log.debug("Executing method getDishInMenuByName with Menu name {}", name);

        return menuMapper.mapToMenuDto(menuDao.findDishesInMenuByName(name));
    }
}
