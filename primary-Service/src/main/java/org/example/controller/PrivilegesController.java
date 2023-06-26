package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PrivilegesDto;
import org.example.service.PrivilegesService;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/privileges")
public class PrivilegesController {
    @Autowired
    private PrivilegesService privilegesService;
    @GetMapping
    @PreAuthorize("hasAuthority('admin')")
    public List<PrivilegesDto> getAll(@PageableDefault Pageable pageable) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getAll");

        return privilegesService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public PrivilegesDto getById(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getById");

        return privilegesService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public PrivilegesDto save(@RequestBody PrivilegesDto privilege) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method save");

        return privilegesService.save(privilege);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('delete')")
    public void delete(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method delete");

        privilegesService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('update')")
    public PrivilegesDto update(@RequestBody PrivilegesDto privilege) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method update");

        return privilegesService.update(privilege);
    }
}
