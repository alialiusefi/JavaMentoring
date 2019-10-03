package com.epam.esm.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseConverter {

    @Autowired
    protected ModelMapper modelMapper;

}
