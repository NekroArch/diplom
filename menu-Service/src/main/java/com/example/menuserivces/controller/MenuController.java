package com.example.menuserivces.controller;

import com.example.menuserivces.dto.MenuDto;
import com.example.menuserivces.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    @GetMapping
    public List<MenuDto> getAll(@PageableDefault Pageable pageable) {
        return menuService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public MenuDto getAll(@PathVariable int id) {
        return menuService.getById(id);
    }

}
