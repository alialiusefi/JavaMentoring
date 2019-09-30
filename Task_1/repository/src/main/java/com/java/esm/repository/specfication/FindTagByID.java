package com.java.esm.repository.specfication;

import com.java.esm.entity.Tag;

public final class FindTagByID extends FindSpecification<Tag> {

    private long id;
    private static final String SQL_QUERY = "select tag.id, tag.tag_name from tag where tag.id = ?";

    public FindTagByID(long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return SQL_QUERY;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{
                id
        };
    }
}
