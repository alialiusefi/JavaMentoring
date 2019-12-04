package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

public interface TagRepository extends CRUDRepository<Tag> {

    @Override
    default Tag update(Tag entity) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

}
