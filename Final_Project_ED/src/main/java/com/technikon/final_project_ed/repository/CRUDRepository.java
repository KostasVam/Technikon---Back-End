package com.technikon.final_project_ed.repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author iracl
 */
public interface CRUDRepository<T> {

    Optional<T> save(T t);

    Optional<T> findById(long id);

    List<T> findAll();

    Optional<T> update(long id, T t);

    boolean delete(long id);
}
