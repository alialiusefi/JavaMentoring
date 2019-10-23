package com.epam.esm.repository.specfication;

import com.epam.esm.entity.Tag;

public class FindTagByName extends FindSpecification<Tag> {

    private String name;
    private static final String SQL_QUERY = "select tag.id, tag.tag_name from tag where tag.tag_name = ?";
    private static final String CONJ_SQL_QUERY = "and where tag.tag_name = ?";

    public FindTagByName(String name) {
        this.name = name;
    }

    @Override
    public String toSqlClause(boolean isConjunction) {
        return isConjunction ? CONJ_SQL_QUERY : SQL_QUERY;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{name};
    }
}
