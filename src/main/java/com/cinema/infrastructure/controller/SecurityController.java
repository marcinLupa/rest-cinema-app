package com.cinema.infrastructure.controller;

import com.cinema.application.dto.RegisterUserDTO;
import com.cinema.application.service.SecurityService;
import com.cinema.infrastructure.dto.RefreshTokenDto;
import com.cinema.infrastructure.dto.TokensDto;
import com.cinema.infrastructure.security.tokens.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class SecurityController {

    private final TokenManager tokenManager;
    private final SecurityService securityService;

    @PostMapping("/register")
    public Long register(@RequestBody RegisterUserDTO registerUserDTO) {
        return securityService.register(registerUserDTO);
    }
    @GetMapping("/activate-user")
    public String verification(@RequestParam String token) {
        var activatedUserId = securityService.activate(token);
        return "User with id " + activatedUserId + " has been activated";
    }
    @PostMapping("/refresh-tokens")
    public TokensDto refreshTokens(@RequestBody RefreshTokenDto refreshTokenDto) {
        return tokenManager.parseFromRefreshToken(refreshTokenDto);
    }
}
