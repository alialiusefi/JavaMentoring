package com.epam.esm.service.implementation;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.epam.esm.config.YAMLConfig;
import com.epam.esm.dto.ImageMetaDataDTO;
import com.epam.esm.entity.ImageMetadataEntity;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.ImageMetaDataRepository;
import com.epam.esm.repository.specification.CountFindAllImageMetaData;
import com.epam.esm.repository.specification.FindImageMetaDataByID;
import com.epam.esm.repository.specification.FindImageMetaDataByName;
import com.epam.esm.service.ImageMetaService;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Optional;
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
    public InputStream getImageByName(String name) {
        String lowercaseName = name.toLowerCase() + ".png";
        Optional<ImageMetadataEntity> optional = imageMetaDataRepository.queryEntity(
                new FindImageMetaDataByName(lowercaseName));
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("Couldn't find image with that name!");
        }
        ImageMetadataEntity entity = optional.get();
        return getImageFromS3(lowercaseName, yamlConfig.getBucketName(), entity.getKey());
    }

    @Override
    public InputStream getRandomImage() {
        Long imageCount = imageMetaDataRepository.queryCount(new CountFindAllImageMetaData())
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find images"));
        int low = 1;
        long high = imageCount;
        long result = ThreadLocalRandom.current().nextLong(low, high + 1);
        ImageMetadataEntity entity = imageMetaDataRepository.queryEntity(new FindImageMetaDataByID(result)).
                orElseThrow(() -> new ResourceNotFoundException("Cannot get random image!"));
        return getImageFromS3(entity.getName(), yamlConfig.getBucketName(), entity.getKey());
    }

    @Transactional
    @Override
    public ImageMetaDataDTO uploadImage(File file) {
        ImageMetadataEntity entity = new ImageMetadataEntity(file.getName().toLowerCase(), file.getName().toLowerCase(),
                LocalDate.now());
        Optional<ImageMetadataEntity> entityFound = imageMetaDataRepository.queryEntity(new FindImageMetaDataByName(entity.getName()));
        if (entityFound.isPresent()) {
            throw new BadRequestException("Image with the name of " + entity.getName() + " already exists!");
        }
        entity = imageMetaDataRepository.add(entity);
        uploadImageToS3(yamlConfig.getBucketName(), file.getName(), file);
        return new ImageMetaDataDTO(entity.getName(), entity.getDateCreated());
    }

    private void uploadImageToS3(String bucketname, String key, File file) {
        try {
            s3client.putObject(bucketname, key, file);
        } catch (AmazonS3Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    //todo: fix copy to local project file bug
    private InputStream getImageFromS3(String filename, String bucketName, String key) {
        //try {
            S3Object s3Object = s3client.getObject(bucketName, key);
            S3ObjectInputStream stream = s3Object.getObjectContent();
            //File imageFile = new File(filename + ".png");
            try {
                //FileUtils.copyInputStreamToFile(stream, imageFile);
                return stream;
            /*} catch (IOException e) {
                logger.error(e.getMessage(), e);
            }*/
        } catch (AmazonS3Exception e) {
            throw new ResourceNotFoundException("Error while getting image from amazon s3", e);
        }
        //return null;
    }
}
