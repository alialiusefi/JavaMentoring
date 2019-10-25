package com.epam.esm.repository.specification;


import com.epam.esm.entity.AbstractEntity;

public abstract interface Specification<T extends AbstractEntity> {

    default boolean specified(T entity) {
        throw new UnsupportedOperationException("Unimplemented method!");
    }

    String toSqlClause(boolean isConjunction);

    Object[] getParameters();

}
