package com.epam.esm.repository.specfication;

import com.epam.esm.entity.GiftCertificate;

public class SortGiftCertificatesByDate extends SortSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE_ASC = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates " +
            "order by giftcertificates.date_created asc ";
    private static final String SQL_CLAUSE_DESC = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates " +
            "order by giftcertificates.date_created desc ";

    private static final String CONJ_SQL_CLAUSE_ASC = "order by giftcertificates.date_created asc ";
    private static final String CONJ_SQL_CLAUSE_DESC = "order by giftcertificates.date_created desc ";

    private int sortOrder;

    public SortGiftCertificatesByDate(int sortOrder) {
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
        return new Object[]{};
    }
}
