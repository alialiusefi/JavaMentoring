package com.epam.esm.repository.specfication;

import com.epam.esm.entity.GiftCertificate;

public class FindGiftCertificatesByTagID extends FindSpecification<GiftCertificate> {

    private long tagID;

    public FindGiftCertificatesByTagID(long id) {
        this.tagID = id;
    }

    @Override
    public String toSqlClause(boolean isConjunction) {
        return null;
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
