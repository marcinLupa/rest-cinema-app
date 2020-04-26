package com.cinema.application.service;

import com.cinema.application.dto.RegisterUserDTO;
import com.cinema.application.exceptions.SecurityServiceException;
import com.cinema.domain.model.User;
import com.cinema.domain.repository.RoleRepository;
import com.cinema.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public Long register(RegisterUserDTO registerUserDTO) {
        System.out.println(registerUserDTO);
        if (Objects.isNull(registerUserDTO)) {
            throw new SecurityServiceException("REGISTER USER OBJECT IS NULL");
        }

        if (Objects.isNull(registerUserDTO.getUsername())) {
            throw new SecurityServiceException("REGISTER - USERNAME IS NULL");
        }
        if (Objects.isNull(registerUserDTO.getEmail())) {
            throw new SecurityServiceException("REGISTER - EMAIL IS NULL");
        }
        if(userRepository.findByUsername(registerUserDTO.getUsername()).isPresent()){
            throw new SecurityServiceException("REGISTER - USER ALREADY EXISTS");
        }
        if(userRepository.findByEmail(registerUserDTO.getEmail()).isPresent()){
            throw new SecurityServiceException("REGISTER - EMAIL ALREADY EXISTS");
        }
        if(!Objects.equals(registerUserDTO.getPassword(),registerUserDTO.getPasswordConfirmation())){
            throw new SecurityServiceException("PASSWORD ARE NOT THE SAME");
        }
        if(Objects.isNull(registerUserDTO.getRoles())){
            throw new SecurityServiceException("REGISTER -ROLES ARE NULL");
        }

        var roles =roleRepository.findAllByName(registerUserDTO.getRoles());

        var userToInsert= User.builder()
                .age(registerUserDTO.getAge())
                .name(registerUserDTO.getName())
                .surname(registerUserDTO.getSurname())
                .username(registerUserDTO.getUsername())
                .password(registerUserDTO.getPassword())
                .email(registerUserDTO.getEmail())
                .roles(new HashSet<>(roles))
                .build();

        return userRepository
                .save(userToInsert)
                .orElseThrow(()->new SecurityServiceException("REGISTER USER EXCEPTION"))
                .getId();
    }
}
