package org.example.demo.controller;

import org.example.demo.dto.DishesDto;
import org.example.demo.service.DishesService;
import org.example.demo.service.Impl.DishesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishesService dishesService;

    @Autowired
    public DishController(DishesService dishesService) {
        this.dishesService = dishesService;
    }


    @GetMapping
    public List<DishesDto> getAll(@PageableDefault Pageable pageable){
        return dishesService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public DishesDto getAll(@PathVariable int id){
        return dishesService.getById(id);
    }

}
