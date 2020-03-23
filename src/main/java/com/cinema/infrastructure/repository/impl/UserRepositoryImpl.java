package com.cinema.infrastructure.repository.impl;

import com.cinema.domain.model.Ticket;
import com.cinema.domain.model.User;
import com.cinema.domain.repository.UserRepository;
import com.cinema.infrastructure.repository.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> findOne(Long id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll();
    }

    @Override
    public Optional<User> add(User user) {
        return Optional.of(jpaUserRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        jpaUserRepository.deleteById(id);
    }
}
