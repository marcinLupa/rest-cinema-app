package com.cinema.domain.repository.generic;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T,ID> {

    Optional<T> findOne(ID id);
    List<T> findAll();
    Optional<T> add(T t);
    void delete(ID id);
}
