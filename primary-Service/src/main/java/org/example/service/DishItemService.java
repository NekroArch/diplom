package org.example.service;

import org.example.dto.DishForCartDto;
import org.example.dto.DishItemDto;

import java.sql.SQLException;

public interface DishItemService extends Service<DishItemDto> {
    void deleteDishFromCart(Integer dishId);
    void saveDishItemInCartWithRelations(DishForCartDto dishForCartDto) throws SQLException, InterruptedException;
}
