package com.cinema.infrastructure.repository.jpa;

import com.cinema.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {
}
