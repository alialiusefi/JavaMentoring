package com.java.esm.repository.specfication;

import com.java.esm.entity.Tag;

public final class FindTagByID extends FindSpecification<Tag> {

    private long id;

    public FindTagByID(long id) {
        this.id = id;
    }

    /*use String.format()*/
    @Override
    public String toSqlClauses() {
        return null;
    }
}
