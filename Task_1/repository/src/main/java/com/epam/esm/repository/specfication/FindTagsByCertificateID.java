package com.epam.esm.repository.specfication;

import com.epam.esm.entity.Tag;

public class FindTagsByCertificateID implements Specification<Tag> {

    private long id;
    private static final String SQL_CLAUSE = "select (tag_id,gift_certificate_id)" +
            " from tagged_certificates where gift_certificate_id = ?";

    public FindTagsByCertificateID(long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return SQL_CLAUSE;
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }


}
