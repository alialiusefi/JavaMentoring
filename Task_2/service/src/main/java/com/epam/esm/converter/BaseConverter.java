package com.epam.esm.converter;

import org.modelmapper.ModelMapper;

public abstract class BaseConverter {

    protected ModelMapper modelMapper;

    protected BaseConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


}
