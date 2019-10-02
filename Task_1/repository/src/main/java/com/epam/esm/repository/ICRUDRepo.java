package com.epam.esm.repository;

import com.epam.esm.entity.Entity;
import com.epam.esm.repository.specfication.Specification;

import java.util.List;

public interface ICRUDRepo<T extends Entity> {

    default void add(T entity) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    default void update(T entity) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    default List<T> query(Specification specification) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    default void delete(T entity) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    default void delete(Specification specification) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }



}
