package com.epam.esm.repository.specification;

import com.epam.esm.entity.Tag;

public class FindAllTags extends FindSpecification<Tag> {

    private static final String SQL_CLAUSE = "select id,tag_name from tag";
    private static final String CONJ_SQL_CLAUSE = "";

    @Override
    public String toSqlClause(boolean isConjunction) {
        return SQL_CLAUSE;
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
