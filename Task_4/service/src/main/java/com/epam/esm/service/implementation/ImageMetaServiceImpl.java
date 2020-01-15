package com.epam.esm.service.implementation;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.epam.esm.config.YAMLConfig;
import com.epam.esm.entity.ImageMetadataEntity;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.ImageMetaDataRepository;
import com.epam.esm.repository.specification.CountFindAllImageMetaData;
import com.epam.esm.repository.specification.FindImageMetaDataByID;
import com.epam.esm.repository.specification.FindImageMetaDataByName;
import com.epam.esm.service.ImageMetaService;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ImageMetaServiceImpl implements ImageMetaService {

    private ImageMetaDataRepository imageMetaDataRepository;
    private AmazonS3 s3client;
    private Logger logger = LogManager.getLogger(ImageMetaServiceImpl.class);
    private YAMLConfig yamlConfig;

    @Autowired
    public ImageMetaServiceImpl(ImageMetaDataRepository imageMetaDataRepository, AmazonS3 s3client, YAMLConfig yamlConfig) {
        this.imageMetaDataRepository = imageMetaDataRepository;
        this.s3client = s3client;
        this.yamlConfig = yamlConfig;
    }


    @Override
    public File getImageByName(String name) {
        String lowercaseName = name.toLowerCase();
        Optional<ImageMetadataEntity> optional = imageMetaDataRepository.queryEntity(
                new FindImageMetaDataByName(lowercaseName));
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("Couldn't find image with that name!");
        }
        ImageMetadataEntity entity = optional.get();
        return getImageFromS3(lowercaseName, yamlConfig.getBucketName() , entity.getKey());
    }

    @Override
    public File getRandomImage() {
        Long imageCount = imageMetaDataRepository.queryCount(new CountFindAllImageMetaData())
                .orElseThrow(()-> new ResourceNotFoundException("Couldn't find images"));
        int low = 1;
        long high = imageCount;
        long result = ThreadLocalRandom.current().nextLong(low, high + 1);
        ImageMetadataEntity entity = imageMetaDataRepository.queryEntity(new FindImageMetaDataByID(result)).
                orElseThrow(()-> new ResourceNotFoundException("Cannot get random image!"));
        return getImageFromS3(entity.getName(), yamlConfig.getBucketName(),entity.getKey());
    }

    private File getImageFromS3(String filename, String bucketName, String key) {
        try {
            S3Object s3Object = s3client.getObject(bucketName, key);
            S3ObjectInputStream stream = s3Object.getObjectContent();
            File imageFile = new File(filename + ".png");
            try {
                FileUtils.copyInputStreamToFile(stream, imageFile);
                return imageFile;
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } catch (AmazonS3Exception e) {
            throw new ResourceNotFoundException("Error while getting image from amazon s3",e);
        }
        return null;
    }
}
