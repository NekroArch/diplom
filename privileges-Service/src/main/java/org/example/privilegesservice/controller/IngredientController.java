package org.example.privilegesservice.controller;

import org.example.privilegesservice.dto.PrivilegesDto;
import org.example.privilegesservice.service.PrivilegesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/privileges")
public class IngredientController {

    private final PrivilegesService privilegesService;

    @Autowired
    public IngredientController(PrivilegesService privilegesService) {
        this.privilegesService = privilegesService;
    }


    @GetMapping
    public List<PrivilegesDto> getAll(@PageableDefault Pageable pageable) {
        return privilegesService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public PrivilegesDto getAll(@PathVariable int id) {
        return privilegesService.getById(id);
    }

}
