package com.epam.esm.service;

import com.epam.esm.dto.ImageMetaDataDTO;

import java.io.File;
import java.io.InputStream;

public interface ImageMetaService {

    InputStream getImageByName(String name);

    InputStream getRandomImage();

    ImageMetaDataDTO uploadImage(File file);



}
