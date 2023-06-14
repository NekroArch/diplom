package org.example.dao;

import org.example.entity.Menu;

public interface MenuDao extends AbstractDao<Menu> {
    Menu findDishesInMenuByName(String name);
}
