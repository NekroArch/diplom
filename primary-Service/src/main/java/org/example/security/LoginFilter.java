package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtProvider jwtProvider;

    public LoginFilter (JwtProvider jwtProvider, AuthenticationManager authenticationManager){
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult){
        String token = prepareJwt(authResult);
        response.addHeader(HttpHeaders.AUTHORIZATION, token);
    }

    private String prepareJwt(Authentication authResult){
        return jwtProvider.buildToken(authResult.getName());
    }

}
