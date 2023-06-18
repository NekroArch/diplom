package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.StatusDto;
import org.example.service.StatusService;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/status")
public class StatusController {
    @Autowired
    private StatusService statusService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin')")
    public List<StatusDto> getAll(@PageableDefault Pageable pageable) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getAll");

        return statusService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public StatusDto getById(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getById");

        return statusService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public StatusDto save(@RequestBody StatusDto status) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method save");

        return statusService.save(status);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('delete')")
    public void delete(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method delete");

        statusService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('update')")
    public StatusDto update(@RequestBody StatusDto status) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method update");

        return statusService.update(status);
    }
}
