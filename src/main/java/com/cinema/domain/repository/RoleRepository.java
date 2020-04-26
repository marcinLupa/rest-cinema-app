package com.cinema.domain.repository;

import com.cinema.domain.model.role.Role;
import com.cinema.domain.repository.generic.GenericRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository  extends GenericRepository<Role, Long> {

    List<Role> findAllByName(Set<String> roles);
}
