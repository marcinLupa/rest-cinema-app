package com.cinema.infrastructure.repository.impl;

import com.cinema.domain.model.role.Role;
import com.cinema.domain.repository.RoleRepository;
import com.cinema.infrastructure.repository.jpa.JpaRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final JpaRoleRepository jpaRoleRepository;
    @Override
    public Optional<Role> findOne(Long id) {
        return jpaRoleRepository.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return jpaRoleRepository.findAll();
    }

    @Override
    public Optional<Role> save(Role role) {
        return Optional.of(jpaRoleRepository.save(role));
    }

    @Override
    public void delete(Long id) {
        jpaRoleRepository.deleteById(id);

    }

    @Override
    public List<Role> findAllByName(Set<String> roles) {
        return jpaRoleRepository.findAllByNameIn(roles);
    }
}
