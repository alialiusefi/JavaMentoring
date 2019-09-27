package com.java.esm.repository.specfication;

import com.java.esm.entity.Entity;

import java.util.List;

public abstract class FindSpecification <T extends Entity> implements Specification<T> {

    public abstract List<T> find(List<T> list);

}
