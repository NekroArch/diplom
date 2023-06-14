package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.IngredientsDto;
import org.example.dto.Pageable;
import org.example.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ingredients")
public class IngredientsController {
    @Autowired
    private IngredientsService ingredientsService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin')")
    public List<IngredientsDto> getAll(@RequestParam Pageable pageable) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getAll");

        return ingredientsService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public IngredientsDto getById(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getById");

        return ingredientsService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public IngredientsDto save(@RequestBody IngredientsDto ingredient) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method save");

        return ingredientsService.save(ingredient);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('delete')")
    public void delete(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method delete");

        ingredientsService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('update')")
    public IngredientsDto update(@RequestBody IngredientsDto ingredient) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method update");

        return ingredientsService.update(ingredient);
    }
}
