package com.epam.esm.converter;

import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;

public abstract class BaseConverter {

    protected ModelMapper modelMapper;

    protected BaseConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Class getGenericClass() {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }


}
