package com.epam.esm.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GiftCertificateController extends ResourceController{

    protected GiftCertificateController(ModelMapper modelMapper) {
        super(modelMapper);
    }


}
