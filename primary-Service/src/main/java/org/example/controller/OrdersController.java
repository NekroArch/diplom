package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.OrdersDto;
import org.example.dto.Pageable;
import org.example.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public List<OrdersDto> getAll(@RequestParam Pageable pageable) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getAll");

        return ordersService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public OrdersDto getById(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getById");

        return ordersService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public OrdersDto save(@RequestBody OrdersDto order) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method save");

        return ordersService.save(order);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public void delete(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method delete");

        ordersService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public OrdersDto update(@RequestBody OrdersDto order) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method update");

        return ordersService.update(order);
    }

    @PostMapping(value = "/create-orders")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public void createOrder(Integer userId) {
        log.info("Executing method createOrder");

        ordersService.saveOrderFromCart(userId);
    }

    @GetMapping(value = "/get-orders/{id}")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public List<OrdersDto> getAllOrdersForUser(@PathVariable Integer id, Pageable pageable){
        log.info("Executing method getAllOrdersForUser");

        return ordersService.getAllOrdersForUser(id, pageable);
    }


}
