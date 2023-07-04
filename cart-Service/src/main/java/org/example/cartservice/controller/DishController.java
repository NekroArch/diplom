package org.example.cartservice.controller;

import org.example.cartservice.dto.CartsDto;
import org.example.cartservice.service.CartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class DishController {

    private final CartsService dishesService;

    @Autowired
    public DishController(CartsService dishesService) {
        this.dishesService = dishesService;
    }


    @GetMapping
    public List<CartsDto> getAll(@PageableDefault Pageable pageable){
        return dishesService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public CartsDto getById(@PathVariable int id){
        return dishesService.getById(id);
    }

}
