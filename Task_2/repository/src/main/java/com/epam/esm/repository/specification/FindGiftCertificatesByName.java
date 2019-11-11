package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class FindGiftCertificatesByName extends FindSpecification<GiftCertificate> {

    private String name;

    public FindGiftCertificatesByName(String name) {
        this.name = name;
    }

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        throw new UnsupportedOperationException("Unimplemented this specification yet!");
    }
    //todo: use custom function instead of like

}
