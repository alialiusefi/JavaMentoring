package com.epam.esm.repository;

import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.repository.specification.Specification;

import java.util.List;

public interface CRUDRepository<T extends AbstractEntity> {

    T add(T entity);

    void update(T entity);

    T findByID(long id);

    T findByName(String name);

    List<T> query(Specification specification);

    void delete(T entity);

    void delete(Specification specification);


}
