package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MenuDto;
import org.example.service.MenuService;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping
    @PreAuthorize("hasAnyAuthority('user','admin', 'anonymous')")
    public List<MenuDto> getAll(@PageableDefault Pageable pageable) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getAll");

        return menuService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('user','admin', 'anonymous')")
    public MenuDto getById(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getById");

        return menuService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public MenuDto save(@RequestBody MenuDto menu) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method save");

        return menuService.save(menu);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('delete')")
    public void delete(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method delete");

        menuService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('update')")
    public MenuDto update(@RequestBody MenuDto menu) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method update");

        return menuService.update(menu);
    }

    @GetMapping(value = "/list/{name}")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public MenuDto getDishInMenuByName(@PathVariable String name){
        log.info("Executing method getDishInMenuByName");

        return menuService.getDishInMenuByName(name);
    }
}
