package com.epam.esm.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;

@Controller
public abstract class ResourceController {

    protected ModelMapper modelMapper;

    protected ResourceController(ModelMapper modelMapper)
    {
        this.modelMapper = modelMapper;
    }


}
