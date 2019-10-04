package com.epam.esm.repository;

import com.epam.esm.entity.Entity;
import com.epam.esm.repository.specfication.Specification;

import java.util.List;

public interface ICRUDRepo<T extends Entity> {

    void add(T entity);

    void update(T entity);

    List<T> query(Specification specification);


    void delete(T entity);

    void delete(Specification specification);


}
