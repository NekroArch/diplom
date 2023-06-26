package org.example.userservice.controller;

import org.example.userservice.dto.UsersDto;
import org.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UsersDto> getAll(@PageableDefault Pageable pageable) throws SQLException, InterruptedException {
        return userService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public UsersDto getById(@PathVariable Integer id){
        try {
            return userService.getById(id);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
