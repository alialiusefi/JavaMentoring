package com.epam.esm.repository.specification;

public interface NativeSpecification<T> extends Specification {

    String getSQLClause(boolean isConjunction);


}
