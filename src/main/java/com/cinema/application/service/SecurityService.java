package com.cinema.application.service;

import com.cinema.application.dto.RegisterUserDTO;
import com.cinema.application.exceptions.SecurityServiceException;
import com.cinema.domain.model.User;
import com.cinema.domain.model.verification_token.VerificationToken;
import com.cinema.domain.model.verification_token.VerificationTokenRepository;
import com.cinema.domain.repository.RoleRepository;
import com.cinema.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;


    public Long register(RegisterUserDTO registerUserDTO) {
        if (Objects.isNull(registerUserDTO)) {
            throw new SecurityServiceException("REGISTER USER OBJECT IS NULL");
        }

        if (Objects.isNull(registerUserDTO.getUsername())) {
            throw new SecurityServiceException("REGISTER - USERNAME IS NULL");
        }
        if (Objects.isNull(registerUserDTO.getEmail())) {
            throw new SecurityServiceException("REGISTER - EMAIL IS NULL");
        }
        if (userRepository.findByUsername(registerUserDTO.getUsername()).isPresent()) {
            throw new SecurityServiceException("REGISTER - USER ALREADY EXISTS");
        }
        if (userRepository.findByEmail(registerUserDTO.getEmail()).isPresent()) {
            throw new SecurityServiceException("REGISTER - EMAIL ALREADY EXISTS");
        }
        if (!Objects.equals(registerUserDTO.getPassword(), registerUserDTO.getPasswordConfirmation())) {
            throw new SecurityServiceException("PASSWORD ARE NOT THE SAME");
        }
        if (Objects.isNull(registerUserDTO.getRoles())) {
            throw new SecurityServiceException("REGISTER -ROLES ARE NULL");
        }

        var roles = roleRepository.findAllByName(registerUserDTO.getRoles());

        var userToInsert = User.builder()
                .age(registerUserDTO.getAge())
                .name(registerUserDTO.getName())
                .surname(registerUserDTO.getSurname())
                .username(registerUserDTO.getUsername())
                .password(passwordEncoder.encode(registerUserDTO.getPassword()))
                .email(registerUserDTO.getEmail())
                .roles(new HashSet<>(roles))
                .build();


        var token = UUID.randomUUID().toString().replace("\\W", "");

        var verificationToken = VerificationToken
                .builder()
                .token(token)
                .user(userToInsert)
                .expirationDateTime(LocalDateTime.now().plusMinutes(5))
                .build();

        var insertedVerification = verificationTokenRepository
                .save(verificationToken)
                .orElseThrow(() -> new SecurityServiceException("cannot insert verification token"));

        var url = "http://localhost:8080/security/activate-user?token=" + token;
        var message = "Click to activate: " + url;
        emailService.send(userToInsert.getEmail(), "Register activation", message);

        return insertedVerification
                .getUser()
                .getId();
    }

    public Long activate(String token) {
        User user = verificationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new SecurityServiceException("cannot get verification token object"))
                .getUser();
        user.setActivated(true);

        userRepository
                .save(user);

        return user.getId();
    }
}
