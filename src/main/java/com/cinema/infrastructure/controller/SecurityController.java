package com.cinema.infrastructure.controller;

import com.cinema.application.dto.RegisterUserDTO;
import com.cinema.application.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class SecurityController {
    private final SecurityService securityService;

    @PostMapping("/register")
    public Long register(@RequestBody RegisterUserDTO registerUserDTO) {
        return securityService.register(registerUserDTO);
    }
}
