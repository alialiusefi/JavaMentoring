package com.java.esm.repository.specfication;

import com.java.esm.entity.Entity;

import java.util.List;

abstract public class FindSpecification <T extends Entity> extends Specification<T> {

    public abstract List<T> find(List<T> list);

}
