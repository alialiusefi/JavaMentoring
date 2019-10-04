package com.epam.esm.repository.specfication;

import com.epam.esm.entity.GiftCertificate;

public class SortGiftCertificatesByName extends SortSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE_ASC = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "order by giftcertificates.date_created asc ";
    private static final String SQL_CLAUSE_DESC = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "order by giftcertificates.date_created desc ";

    private static final String CONJ_SQL_CLAUSE_ASC = "giftcertificates.date_created asc ";
    private static final String CONJ_SQL_CLAUSE_DESC = "giftcertificates.date_created desc ";

    private int sortOrder;

    public SortGiftCertificatesByName(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toSqlClause(boolean isConjunction) {
        if (sortOrder == 1) {
            return isConjunction ? CONJ_SQL_CLAUSE_ASC : SQL_CLAUSE_ASC;
        } else {
            if (sortOrder == -1) {
                return isConjunction ? CONJ_SQL_CLAUSE_DESC : SQL_CLAUSE_DESC;
            }
        }
        return SQL_CLAUSE_ASC;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{sortOrder};
    }
}
