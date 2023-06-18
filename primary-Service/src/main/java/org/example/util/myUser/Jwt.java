package org.example.util.myUser;

import jakarta.servlet.http.HttpServletRequest;
import org.example.security.jwt.JwtProvider;
import org.example.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Jwt {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserServiceImpl userDetailsService;


    public MyUser getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String s = jwtProvider.extractMail(token);
        return (MyUser) userDetailsService.loadUserByUsername(s);
    }
}
