package com.epam.esm.repository.specfication;

import com.epam.esm.entity.Tag;

public final class FindTagByID extends FindSpecification<Tag> {

    private Long id;
    private static final String SQL_QUERY = "select tag.id, tag.tag_name from tag where tag.id = ?";
    private static final String CONJ_SQL_QUERY = "and where tag.id = ?";

    public FindTagByID(Long id) {
        this.id = id;
    }

    @Override
    public String toSqlClause(boolean isConjunction) {
        return isConjunction ? CONJ_SQL_QUERY : SQL_QUERY;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{
                id
        };
    }
}
