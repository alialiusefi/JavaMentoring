package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

public class FindAllCertificates extends FindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry from giftcertificates";
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
