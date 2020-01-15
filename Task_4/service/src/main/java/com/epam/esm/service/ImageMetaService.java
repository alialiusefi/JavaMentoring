package com.epam.esm.service;

import java.io.File;

public interface ImageMetaService {

    File getImageByName(String name);

    File getRandomImage();

}
