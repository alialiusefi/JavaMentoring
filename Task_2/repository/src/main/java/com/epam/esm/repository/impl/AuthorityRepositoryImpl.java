package com.epam.esm.repository.impl;

import com.epam.esm.entity.Authority;
import com.epam.esm.repository.AuthorityRepository;
import com.epam.esm.repository.BaseCRUDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AuthorityRepositoryImpl extends BaseCRUDRepository<Authority>
        implements AuthorityRepository {

    @Autowired
    public AuthorityRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
