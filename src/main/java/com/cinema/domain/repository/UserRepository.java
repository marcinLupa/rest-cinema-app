package com.cinema.domain.repository;

import com.cinema.domain.model.User;
import com.cinema.domain.repository.generic.GenericRepository;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
