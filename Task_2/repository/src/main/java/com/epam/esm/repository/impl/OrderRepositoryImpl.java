package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.repository.BaseCRUDRepository;
import com.epam.esm.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderRepositoryImpl extends BaseCRUDRepository<Order> implements OrderRepository {

    @Autowired
    protected OrderRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Order add(Order entity) {
        this.entityManager.persist(entity);
        return entity;
    }


    @Override
    public void delete(Order entity) {
        Order managedOrder = this.entityManager.merge(entity);
        this.entityManager.remove(managedOrder);
    }
}
