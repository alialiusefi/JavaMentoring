package com.epam.esm.repository.specfication;

import com.epam.esm.entity.GiftCertificate;

public class FindGiftCertificatesByTagID extends FindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "where tag_id = ?";
    //todo: fix this
    private static final String CONJ_SQL_CLAUSE = null;

    private long tagID;

    public FindGiftCertificatesByTagID(long id) {
        this.tagID = id;
    }

    @Override
    public String toSqlClause(boolean isConjunction) {
        return isConjunction ? SQL_CLAUSE : CONJ_SQL_CLAUSE;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{tagID};
    }
}
