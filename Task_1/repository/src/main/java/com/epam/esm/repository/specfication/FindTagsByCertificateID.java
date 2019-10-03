package com.epam.esm.repository.specfication;

import com.epam.esm.entity.Tag;

public class FindTagsByCertificateID implements Specification<Tag> {

    private long id;
    private static final String SQL_CLAUSE = "select tag.id, tag.tag_name " +
            "from tag inner join tagged_giftcertificates " +
            "on tag.id = tagged_giftcertificates.tag_id " +
            "where gift_certificate_id = ?";
    private static final String CONJ_SQL_CLAUSE = "and where gift_certificate_id = ?";

    public FindTagsByCertificateID(long id) {
        this.id = id;
    }

    @Override
    public String toSqlClause(boolean isConjunction) {
        return isConjunction ? CONJ_SQL_CLAUSE : SQL_CLAUSE;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{id};
    }


}
