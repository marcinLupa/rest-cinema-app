package com.cinema.infrastructure.controller;

import com.cinema.application.dto.MovieDTO;
import com.cinema.application.dto.UserDTO;
import com.cinema.application.service.MovieService;
import com.cinema.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    public UserDTO findOne(@PathVariable Long id) {
        return userService
                .findOne(id)
                .orElseThrow();
    }

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public UserDTO add(@RequestBody UserDTO userDTO){
        return userService
                .add(userDTO)
                .orElseThrow();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
}
