package com.cinema.infrastructure.security.filters;

import com.cinema.infrastructure.dto.AuthenticationUserDto;
import com.cinema.infrastructure.dto.TokensDto;
import com.cinema.infrastructure.security.tokens.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, TokenManager tokenManager) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
    }


    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        try {
            AuthenticationUserDto authenticationUserDto = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthenticationUserDto.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationUserDto.getUsername(),
                    authenticationUserDto.getPassword(),
                    Collections.emptyList()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SecurityException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        TokensDto tokensDto = tokenManager.generateTokens(authResult);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(tokensDto));
        response.getWriter().flush();
        response.getWriter().close();
    }
}



