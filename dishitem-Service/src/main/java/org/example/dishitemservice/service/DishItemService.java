package org.example.dishitemservice.service;

import org.example.dishitemservice.dto.DishForCartDto;
import org.example.dishitemservice.dto.DishItemDto;
import org.example.dishitemservice.dto.SendDishForCart;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;

public interface DishItemService {
    List<DishItemDto> getAll(Pageable pageable);

    DishItemDto getById(Integer id);

    DishItemDto save(DishItemDto dishesDto);

    void delete(Integer id);

    DishItemDto update(DishItemDto dishesDto);
    void deleteDishFromCart(Integer dishId);
    void saveDishItemInCartWithRelations(SendDishForCart dishForCartDto) throws SQLException, InterruptedException;
}
