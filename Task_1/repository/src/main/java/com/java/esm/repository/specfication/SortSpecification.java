package com.java.esm.repository.specfication;


import com.java.esm.entity.Entity;

import java.util.Comparator;
import java.util.List;

abstract public class SortSpecification<T extends Entity> extends Specification<T> {

    protected Comparator<T> comparator;

    public Comparator getComparator() {
        return comparator;
    }

    public void setComparator(Comparator specificationComparator) {
        this.comparator = specificationComparator;
    }

    public abstract List<T> sort(List<T> list);

}
