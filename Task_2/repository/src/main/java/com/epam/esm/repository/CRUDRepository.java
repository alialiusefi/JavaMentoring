package com.epam.esm.repository;

import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.repository.specification.Specification;

import java.util.List;

public interface CRUDRepository<T extends AbstractEntity> {

  /*  T add(T entity);

    void update(T entity);
*/
    List<T> queryList(Specification<T> specification, int pageNumber, int pageSize);

    T queryEntity(Specification<T> specification);
/*

    void delete(T entity);

    void delete(Specification specification);
*/


}
