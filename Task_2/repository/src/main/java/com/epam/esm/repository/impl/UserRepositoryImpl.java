package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.BaseCRUDRepository;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepositoryImpl extends BaseCRUDRepository<User> implements UserRepository {

    @Autowired
    protected UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public User add(User entity) {
        this.entityManager.persist(entity);
        return entity;
    }


    @Override
    public void delete(User entity) {
        User managedOrder = this.entityManager.merge(entity);
        this.entityManager.remove(managedOrder);
    }
}
