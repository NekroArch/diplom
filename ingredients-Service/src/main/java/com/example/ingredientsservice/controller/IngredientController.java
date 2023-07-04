package com.example.ingredientsservice.controller;

import com.example.ingredientsservice.dto.IngredientsDto;
import com.example.ingredientsservice.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping
    public List<IngredientsDto> getAll(@PageableDefault Pageable pageable) {
        return ingredientService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public IngredientsDto getAll(@PathVariable int id) {
        return ingredientService.getById(id);
    }

}
