package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MeasureUnitsDto;
import org.example.service.MeasureUnitsService;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/measure-units")
public class MeasureUnitsController {
    @Autowired
    private MeasureUnitsService measureUnitsService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin')")
    public List<MeasureUnitsDto> getAll( @PageableDefault Pageable pageable) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getAll");

        return measureUnitsService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public MeasureUnitsDto getById(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getById");

        return measureUnitsService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public MeasureUnitsDto save(@RequestBody MeasureUnitsDto measureUnits) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method save");

        return measureUnitsService.save(measureUnits);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('delete')")
    public void delete(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method delete");

        measureUnitsService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('update')")
    public MeasureUnitsDto update(@RequestBody MeasureUnitsDto measureUnits) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method update");

        return measureUnitsService.update(measureUnits);
    }
}
