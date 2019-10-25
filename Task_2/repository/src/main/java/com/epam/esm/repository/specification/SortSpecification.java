package com.epam.esm.repository.specification;



import com.epam.esm.entity.AbstractEntity;

import java.util.Comparator;

public abstract class SortSpecification<T extends AbstractEntity> implements Specification<T> {

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
