package com.example.measureunitservice.controller;

import com.example.measureunitservice.dto.MeasureUnitsDto;
import com.example.measureunitservice.service.MeasureUnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/measure-units")
public class MeasureUnitsController {

    private final MeasureUnitsService measureUnitsService;

    @Autowired
    public MeasureUnitsController(MeasureUnitsService measureUnitsService) {
        this.measureUnitsService = measureUnitsService;
    }


    @GetMapping
    public List<MeasureUnitsDto> getAll(@PageableDefault Pageable pageable) {
        return measureUnitsService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public MeasureUnitsDto getAll(@PathVariable int id) {
        return measureUnitsService.getById(id);
    }

}
