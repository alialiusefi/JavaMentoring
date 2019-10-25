package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

public class FindGiftCertificatesByName extends FindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates " +
            "where public.consists(?,giftcertificates.name)  ";

    private static final String CONJ_SQL_CLAUSE = "and public.consists(?,giftcertificates.name) ";

    private String name;

    public FindGiftCertificatesByName(String name) {
        this.name = name;
    }

    @Override
    public String toSqlClause(boolean isConjunction) {
        return isConjunction ? CONJ_SQL_CLAUSE : SQL_CLAUSE;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{name};
    }
}
