package com.epam.esm.service;

import com.java.esm.repository.TagRepo;

public class TagService extends Service<TagRepo> {

    protected TagService(TagRepo repository) {
        super(repository);
    }
}
