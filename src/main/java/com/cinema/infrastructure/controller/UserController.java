package com.cinema.infrastructure.controller;

import com.cinema.application.dto.RegisterUserDTO;
import com.cinema.application.service.UserService;
import com.cinema.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    public User findOne(@PathVariable Long id) {
        return userService
                .findOne(id)
                .orElseThrow();
    }

//    @GetMapping
//    public List<User> findAll() {
//
//        return userService.findAll();
//    }

//    @PostMapping
//    public User add(@RequestBody RegisterUserDTO registerUserDTO) {
//        return userService
//                .add(registerUserDTO)
//                .orElseThrow();
//    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
