package org.example.dao;

import org.example.entity.Carts;

public interface CartsDao extends AbstractDao<Carts> {
    Carts getDishInCartsWithFetchById(Integer id);

    void clearCardByUserId(Integer userId);

    Integer getCartIdByUserId(Integer id);
}
