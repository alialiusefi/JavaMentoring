package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.BaseCRUDRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.specification.FindTagByName;
import com.epam.esm.repository.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl extends BaseCRUDRepository<Tag>
        implements TagRepository {


    @Autowired
    public TagRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Tag add(Tag entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(Specification specification) {
        List<Tag> tagsToDelete = queryList(specification, null, null);
        for (Tag i : tagsToDelete) {
            Tag managedTag = entityManager.merge(i);
            entityManager.remove(managedTag);
        }
    }

    @Override
    public void delete(Tag entity) {
        Tag managedTag = entityManager.merge(entity);
        entityManager.remove(managedTag);
    }

}
