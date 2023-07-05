package com.example.menuserivces.service;

import com.example.menuserivces.dto.MenuDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuService {
    List<MenuDto> getAll(Pageable pageable);

    MenuDto getById(Integer id);

    void save(MenuDto dishesDto);

    void delete(Integer id);

    void update(MenuDto dishesDto);

    MenuDto getDishInMenuByName(String name);
}
