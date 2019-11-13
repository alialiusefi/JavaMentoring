package com.epam.esm.repository.specification;

public interface NativeSpecification<T> {

    String getSQLClause(boolean isConjunction);

}
