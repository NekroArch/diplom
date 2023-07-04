package com.example.orderservice.service;

import com.example.orderservice.dto.AllOrdersForUser;
import com.example.orderservice.dto.OrdersDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    List<OrdersDto> getAll(Pageable pageable);

    OrdersDto getById(Integer id);

    void save(OrdersDto dishesDto);

    void delete(Integer id);

    void update(OrdersDto dishesDto);

    void saveOrderFromCart(Integer userId);

    List<OrdersDto> getAllOrdersForUser(AllOrdersForUser allOrdersForUser);
}
