package com.epam.esm.repository.specfication;

import com.epam.esm.entity.GiftCertificate;

public class FindGiftCertificatesByName extends FindSpecification<GiftCertificate> {
    @Override
    public String toSqlClause(boolean isConjunction) {
        return null;
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
