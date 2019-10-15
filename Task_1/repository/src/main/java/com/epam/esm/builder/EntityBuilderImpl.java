package com.epam.esm.builder;

public class EntityBuilderImpl implements EntityBuilder {

    protected Long id;

    @Override
    public void setID(long id) {
        this.id = id;
    }
}
