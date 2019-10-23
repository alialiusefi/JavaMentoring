package com.epam.esm.repository.specfication;


import com.epam.esm.entity.Entity;

import java.util.Comparator;

public abstract class SortSpecification<T extends Entity> implements Specification<T> {

    protected Comparator<T> comparator;

    public SortSpecification() {
    }

    public SortSpecification(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public void setComparator(Comparator specificationComparator) {
        this.comparator = specificationComparator;
    }

}
