package com.epam.esm.repository;

import com.epam.esm.entity.User;
import com.epam.esm.repository.specification.Specification;

public interface UserRepository extends CRUDRepository<User> {

    default User update(User entity) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    default void delete(Specification specification) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    default void delete(User entity) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }
}
