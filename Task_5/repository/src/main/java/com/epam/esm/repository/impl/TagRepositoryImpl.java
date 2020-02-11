package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.BaseCRUDRepository;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class TagRepositoryImpl extends BaseCRUDRepository<Tag>
        implements TagRepository {

    @Autowired
    public TagRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }


}
