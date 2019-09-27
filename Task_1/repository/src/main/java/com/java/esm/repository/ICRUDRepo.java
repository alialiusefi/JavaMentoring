package com.java.esm.repository;

import com.java.esm.entity.Entity;
import com.java.esm.repository.specfication.Specification;

import java.util.List;

public interface ICRUDRepo<T extends Entity> {

    default void add(T entity) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    default void update(T entity) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    default List query(Specification specification) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    default void delete(T entity) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    default void delete(Specification specification) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }


}
