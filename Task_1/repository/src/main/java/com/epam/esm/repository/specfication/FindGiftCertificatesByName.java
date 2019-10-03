package com.epam.esm.repository.specfication;

import com.epam.esm.entity.GiftCertificate;

public class FindGiftCertificatesByName extends FindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "where ? like '%' || giftcertificates.name || '%'";

    private static final String CONJ_SQL_CLAUSE = "and ? like '%' || giftcertificates.name || '%' ";

    private String name;

    public FindGiftCertificatesByName(String name) {
        this.name = name;
    }

    @Override
    public String toSqlClause(boolean isConjunction) {
        return isConjunction ? SQL_CLAUSE : CONJ_SQL_CLAUSE;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{name};
    }
}
