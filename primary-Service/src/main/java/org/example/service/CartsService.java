package org.example.service;

import org.example.dto.CartsDto;

public interface CartsService extends Service<CartsDto> {
    void saveCartAfterRegistration(Integer id);

    CartsDto getById(String userName);
}
