package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.BaseCRUDRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
public class GiftCertificateRepositoryImpl extends BaseCRUDRepository<GiftCertificate>
        implements GiftCertificateRepository {


    @Autowired
    public GiftCertificateRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }


    @Override
    public GiftCertificate add(GiftCertificate entity) {
        entityManager.persist(entity);
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public GiftCertificate update(GiftCertificate entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(GiftCertificate entity) {
        GiftCertificate giftCertificate = entityManager.merge(entity);
        entityManager.remove(giftCertificate);
    }

    @Override
    public void delete(Specification specification) {
        List<GiftCertificate> certificatesToDelete = queryList(specification, null, null);
        for (GiftCertificate certificate : certificatesToDelete) {
            delete(certificate);
        }
    }

}
