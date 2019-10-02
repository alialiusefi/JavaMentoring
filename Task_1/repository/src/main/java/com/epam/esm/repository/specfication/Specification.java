package com.epam.esm.repository.specfication;

import com.epam.esm.entity.Entity;

public abstract interface Specification<T extends Entity> {

    default boolean specified(T entity){
        throw new UnsupportedOperationException("Unimplemented method!");
    }

    String toSqlClauses();

    Object[] getParameters();

}
