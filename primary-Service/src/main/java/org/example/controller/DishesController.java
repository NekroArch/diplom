package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.DishesDto;
import org.example.service.DishesService;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishesController {
    @Autowired
    private DishesService dishesService;
    @GetMapping
    public List<DishesDto> getAll(@PageableDefault Pageable pageable) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getAll");

        return dishesService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public DishesDto getById(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getById");

        return dishesService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public DishesDto save(@RequestBody DishesDto dish) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method save");

        return dishesService.save(dish);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('delete')")
    public void delete(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method delete");

        dishesService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('update')")
    public DishesDto update(@RequestBody DishesDto dish) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method update");

        return dishesService.update(dish);
    }
}
