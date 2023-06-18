package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AddonsCategoryDto;
import org.example.service.AddonsCategoryService;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addonsCategory")
public class AddonsCategoryController {

    @Autowired
    private AddonsCategoryService addonsCategoryService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<AddonsCategoryDto> getAll(@PageableDefault Pageable pageable) throws Exception {
        log.info("Executing method getAll");

        return addonsCategoryService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public AddonsCategoryDto getById(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getById");

        return addonsCategoryService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public AddonsCategoryDto save(@RequestBody AddonsCategoryDto addonsCategory) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method save");

        return addonsCategoryService.save(addonsCategory);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('delete')")
    public void delete(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method delete");

        addonsCategoryService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('update')")
    public AddonsCategoryDto update(@RequestBody AddonsCategoryDto addonsCategory) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method update");

        return addonsCategoryService.update(addonsCategory);
    }
}
