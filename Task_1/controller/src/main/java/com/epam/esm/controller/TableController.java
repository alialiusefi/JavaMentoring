package com.epam.esm.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;

@Controller
public abstract class TableController {

    protected ModelMapper modelMapper;

    protected TableController(ModelMapper modelMapper)
    {
        this.modelMapper = modelMapper;
    }


}
