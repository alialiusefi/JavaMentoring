package com.epam.esm.controller;

import com.epam.esm.service.ImageMetaService;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RequestMapping("/v1/image")
@RestController
@Validated
public class ImageController {

    private ImageMetaService imageMetaService;
    private Logger logger = LogManager.getLogger(ImageController.class);

    @Autowired
    public ImageController(ImageMetaService imageMetaService) {
        this.imageMetaService = imageMetaService;
    }

    @GetMapping("/random")
    public ResponseEntity<byte[]> getRandomImage() {
        File file = imageMetaService.getRandomImage();
        byte[] media = new byte[0];
        try {
            media = IOUtils.toByteArray(new FileInputStream(file));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return createResponse(media);
    }

    @GetMapping("/{filenamewithextension}")
    public ResponseEntity<byte[]> getImageByName(@PathVariable String filenamewithextension) {
        File file = imageMetaService.getImageByName(filenamewithextension);
        byte[] media = new byte[0];
        try {
            media = IOUtils.toByteArray(new FileInputStream(file));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return createResponse(media);
    }

    @PostMapping("/")
    public ResponseEntity<byte[]> uploadImage(@Image List<MultipartFile> file) {

    }

    private ResponseEntity<byte[]> createResponse(byte[] image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }


}
