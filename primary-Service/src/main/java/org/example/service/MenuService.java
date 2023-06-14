package org.example.service;

import org.example.dto.MenuDto;

public interface MenuService extends Service<MenuDto> {
    MenuDto getDishInMenuByName(String name);
}
