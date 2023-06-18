package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.LoginDto;
import org.example.dto.UsersDto;
import org.example.security.jwt.JwtProvider;
import org.example.service.CartsService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@Slf4j
@RestController
@RequestMapping("/aunt")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    CartsService cartsService;

    @PostMapping(value = "/login")
    public String login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        log.info("Executing method login");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getMail(), loginDto.getPassword())
        );

        String token = jwtProvider.buildToken(loginDto.getMail());
        response.addHeader(HttpHeaders.AUTHORIZATION, token);
        return token;

    }

    @PostMapping(value = "/register")
    public void register(@Valid @RequestBody UsersDto usersDto) {
        log.info("Executing method register");

        UsersDto save = userService.save(usersDto);
        cartsService.saveCartAfterRegistration(save.getId());
    }
}
