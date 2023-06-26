package org.example.dao;

import org.example.entities.entity.Orders;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrdersDao extends AbstractDao<Orders> {
    Orders getOrdersWithFetchById(Integer id);

    Integer saveOrderFromCart(Integer id);

    void saveOrderedDishesRelations(Integer userId, Integer orderId);

    List<Orders> getAllOrdersForUserById(Integer id, Pageable pageable);
}
