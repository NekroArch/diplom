package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.CartsDto;
import org.example.dto.DishForCartDto;
import org.example.service.CartsService;
import org.example.service.DishItemService;
import org.example.util.myUser.Jwt;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/carts")
public class CartsController {
    @Autowired
    private CartsService cartsService;
    @Autowired
    private DishItemService dishItemService;
    @Autowired
    private Jwt jwt;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public List<CartsDto> getAll(@PageableDefault Pageable pageable) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getAll");

        return cartsService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public CartsDto getById(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getById");

        return cartsService.getById(id);
    }

    @GetMapping("/user-cart")
    @PreAuthorize("isAuthenticated()")
    public CartsDto getUserCart(HttpServletRequest request) throws SQLException, InterruptedException {
        log.info("Executing method getUserCart");

        return cartsService.getById(jwt.getUser(request).getId());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public CartsDto save(@RequestBody CartsDto cart) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method save");

        return cartsService.save(cart);
    }

    @PostMapping(value = "/add-dish")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public void addDishInCart(@RequestBody DishForCartDto cart, HttpServletRequest request) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method addDishInCart");

        dishItemService.saveDishItemInCartWithRelations(cart, jwt.getUser(request).getId());
    }

    @DeleteMapping(value = "/delete-dish")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public void deleteDishInCart(@RequestBody Integer dishId) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method deleteDishInCart");

        dishItemService.deleteDishFromCart(dishId);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public void delete(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method delete");

        cartsService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public CartsDto update(@RequestBody CartsDto cart) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method update");

        return cartsService.update(cart);
    }


}
