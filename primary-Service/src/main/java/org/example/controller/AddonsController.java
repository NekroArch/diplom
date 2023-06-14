package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AddonsDto;
import org.example.dto.Pageable;
import org.example.service.AddonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addons")
public class AddonsController {
    @Autowired
    private AddonsService addonsService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public List<AddonsDto> getAll(@RequestParam Pageable pageable) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getAll");

        return addonsService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public AddonsDto getById(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getById");

        return addonsService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public AddonsDto save(@RequestBody AddonsDto addon) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method save");

        return addonsService.save(addon);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('delete')")
    public void delete(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method delete");

        addonsService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('update')")
    public AddonsDto update(@RequestBody AddonsDto addon) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method update");

        return addonsService.update(addon);
    }
}
