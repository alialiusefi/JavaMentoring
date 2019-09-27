package com.java.esm.repository.specfication;

import com.java.esm.entity.Entity;

public abstract interface Specification<T extends Entity> {

    // for in-memory search
    default boolean specified(T entity){
        throw new UnsupportedOperationException("Unimplemented method!");
    }

    String toSqlClauses();

}
