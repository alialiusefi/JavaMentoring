package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.BaseCRUDRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


@Repository
public class GiftCertificateRepositoryImpl extends BaseCRUDRepository<GiftCertificate>
        implements GiftCertificateRepository {

    @Autowired
    public GiftCertificateRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }


}
