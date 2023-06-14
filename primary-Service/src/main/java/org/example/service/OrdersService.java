package org.example.service;

import org.example.dto.OrdersDto;
import org.example.dto.Pageable;

import java.util.List;

public interface OrdersService extends Service<OrdersDto> {
    void saveOrderFromCart(Integer id);
    List<OrdersDto> getAllOrdersForUser(Integer id, Pageable pageable);
}
