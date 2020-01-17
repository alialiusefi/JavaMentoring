package com.epam.esm.repository.impl;

import com.epam.esm.entity.ImageMetadataEntity;
import com.epam.esm.repository.BaseCRUDRepository;
import com.epam.esm.repository.ImageMetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ImageMetaDataRepositoryImpl extends BaseCRUDRepository<ImageMetadataEntity>
        implements ImageMetaDataRepository {
    @Autowired
    protected ImageMetaDataRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }


}
