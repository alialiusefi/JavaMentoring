package com.epam.esm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v3/image")
@RestController
@Validated
public class ImageController {

    @GetMapping("/random")
    public ResponseEntity<String> getGiftCertificate() {
        return new ResponseEntity<>("Hello",HttpStatus.OK);
    }



}
