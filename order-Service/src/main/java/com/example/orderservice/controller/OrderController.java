package com.example.orderservice.controller;

import com.example.orderservice.dto.OrdersDto;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public List<OrdersDto> getAll(@PageableDefault Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public OrdersDto getAll(@PathVariable int id) {
        return orderService.getById(id);
    }

}
