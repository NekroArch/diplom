package org.example.cartservice.service;

import org.example.cartservice.dto.CartsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartsService {

    List<CartsDto> getAll(Pageable pageable);

    CartsDto getById(Integer id);

    CartsDto save(CartsDto dishesDto);

    void delete(Integer id);

    CartsDto update(CartsDto dishesDto);

    void saveCartAfterRegistration(Integer id);

    CartsDto getByUserName(String userName, String userExchange, String routingKey);
}
