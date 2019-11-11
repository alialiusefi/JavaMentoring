package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindAllGiftCertificates extends FindSpecification<GiftCertificate> {


    @Override
    public void getQuery(CriteriaQuery<GiftCertificate> criteriaQuery, CriteriaBuilder builder) {
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(root);
    }
}
