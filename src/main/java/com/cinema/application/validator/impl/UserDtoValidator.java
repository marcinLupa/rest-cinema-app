package com.cinema.application.validator.impl;

import com.cinema.application.dto.RegisterUserDTO;
import com.cinema.application.validator.AbstractValidator;
import com.cinema.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class UserDtoValidator extends AbstractValidator<RegisterUserDTO> {

    private final UserRepository userRepository;
    @Override
    public Map<String, String> validate(RegisterUserDTO item) {
        errors.clear();

        if (Objects.isNull(item)) {
            errors.put("USER VALIDATOR", "USER DTO IS NULL");
        }

        if (!isOne(item.getEmail())) {
            errors.put("USER VALIDATOR", "USER WITH GIVEN TITLE ALREADY EXISTS");
        }

        return errors;
    }

    private boolean isOne(String email) {

        return userRepository
                .findByEmail(email).isEmpty();
    }
}
