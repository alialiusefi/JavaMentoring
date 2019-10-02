package com.epam.esm.repository.specfication;

import com.epam.esm.entity.GiftCertificate;

public class FindGiftCertificateByID extends FindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select (id,name,description,price" +
            ",date_created,date_modified,duration_till_expiry) " +
            "from giftcertificates where id = ?";

    private long id;

    public FindGiftCertificateByID(long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return SQL_CLAUSE;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{id};
    }
}
