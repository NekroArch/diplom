package org.example.dishitemservice.dao;

import org.example.entities.entity.Carts;

public interface CartsDao extends AbstractDao<Carts> {
    Carts getDishInCartsWithFetchById(Integer id);

    void clearCardByUserId(Integer userId);

    Integer getCartIdByUserId(Integer id);
}
