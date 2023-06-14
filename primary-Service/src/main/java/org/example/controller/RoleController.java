package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.Pageable;
import org.example.dto.RolesDto;
import org.example.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RolesService rolesService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin')")
    public List<RolesDto> getAll(@RequestParam Pageable pageable) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getAll");

        return rolesService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public RolesDto getById(@PathVariable int id) throws SQLException, InterruptedException, JsonProcessingException {
        log.info("Executing method getById");

        return rolesService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public RolesDto save(@RequestBody RolesDto role) throws SQLException, InterruptedException {
        log.info("Executing method save");

        return rolesService.save(role);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('delete')")
    public void delete(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method delete");

        rolesService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('update')")
    public RolesDto update(@RequestBody RolesDto role) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method update");

        return rolesService.update(role);
    }
}
