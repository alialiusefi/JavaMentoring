package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.specification.Specification;

public interface OrderRepository extends CRUDRepository<Order> {

    default Order update(Order entity) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    default void delete(Specification specification) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    default void delete(User entity) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }
}
