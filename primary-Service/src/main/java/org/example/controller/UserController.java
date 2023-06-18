package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UsersDto;
import org.example.service.UserService;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin')")
    public List<UsersDto> getAll(@PageableDefault Pageable pageable) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getAll");

        return userService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public UsersDto getById(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method getById");

        return userService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public UsersDto save(@RequestBody UsersDto user) {
        log.info("Executing method save");

        return userService.save(user);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('delete')")
    public void delete(@PathVariable int id) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method delete");

        userService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('update')")
    public UsersDto update(@RequestBody UsersDto user) throws JsonProcessingException, SQLException, InterruptedException {
        log.info("Executing method update");

        return userService.update(user);
    }


}
