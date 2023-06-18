package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AddonsDto;
import org.example.service.AddonsService;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
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
    @PreAuthorize("hasAnyAuthority('user', 'admin', 'anonymous')")
    public List<AddonsDto> getAll(@PageableDefault Pageable pageable) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getAll");

        return addonsService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('user', 'admin', 'anonymous')")
    public AddonsDto getById(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getById");

        return addonsService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public AddonsDto save(@RequestBody AddonsDto addon){
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
