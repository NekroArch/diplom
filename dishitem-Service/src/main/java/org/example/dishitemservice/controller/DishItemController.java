package org.example.dishitemservice.controller;

import org.example.dishitemservice.dto.DishItemDto;
import org.example.dishitemservice.service.DishItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dish-items")
public class DishItemController {

    private final DishItemService dishesService;

    @Autowired
    public DishItemController(DishItemService dishesService) {
        this.dishesService = dishesService;
    }


    @GetMapping
    public List<DishItemDto> getAll(@PageableDefault Pageable pageable){
        return dishesService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public DishItemDto getAll(@PathVariable int id){
        return dishesService.getById(id);
    }

}
