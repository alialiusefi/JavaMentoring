package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

public class FindGiftCertificateByID extends FindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates where giftcertificates.id = ? ";

    private static final String CONJ_SQL_CLAUSE = "and where giftcertificates.id = ? ";

    private Long id;

    public FindGiftCertificateByID(Long id) {
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
