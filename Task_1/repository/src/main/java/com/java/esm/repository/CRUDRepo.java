package com.java.esm.repository;

import com.java.esm.entity.Entity;
import com.java.esm.repository.specfication.Specification;

import java.util.List;

public interface CRUDRepo<T extends Entity> {

    void add(T entity);

    void update (T entity);

    List query(Specification specification);

    void delete(T entity);

    void delete(Specification specification);



}
