package org.example.dao;

import org.example.entities.entity.Menu;

public interface MenuDao extends AbstractDao<Menu> {
    Menu findDishesInMenuByName(String name);
}
