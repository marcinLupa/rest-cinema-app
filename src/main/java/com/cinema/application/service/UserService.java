package com.cinema.application.service;


import com.cinema.application.validator.impl.UserDtoValidator;
import com.cinema.domain.model.User;
import com.cinema.domain.repository.UserRepository;
import com.cinema.infrastructure.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoValidator userDtoValidator;

    /**
     * @role Role.ADMIN
     **/
    public Optional<User> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE USER EXCEPTION");
        }
        return Optional.of(userRepository
                .findOne(id)
                .orElseThrow());
    }

    /**
     * @role Role.ADMIN
     **/
    public List<User> findAll() {
        return userRepository
                .findAll();
    }


    /**
     * @role Role.ADMIN
     **/
    public void delete(Long id) {
        if (id == null) {
            throw new AppException("USER MOVIE ID IS NULL");
        }
        userRepository.delete(id);
    }

    /**
     * method only to help ticket service
     **/
     Optional<User> findByEmail(String email) {

        return Optional.of(userRepository
                .findByEmail(email)
                .orElseThrow(() -> new AppException("FIND BY EMAIL EXCEPTION")));
    }
}
