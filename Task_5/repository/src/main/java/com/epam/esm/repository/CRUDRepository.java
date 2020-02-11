package com.epam.esm.repository;

import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.repository.specification.CountSpecification;
import com.epam.esm.repository.specification.Specification;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T extends AbstractEntity> {


    T add(T entity);

    List<T> queryList(Specification<T> specification, Integer pageNumber, Integer pageSize);

    Optional<Long> queryCount(CountSpecification specification);

    Optional<T> queryEntity(Specification<T> specification);

    T update(T entity);

    void delete(Specification specification);

    void delete(T entity);

}
