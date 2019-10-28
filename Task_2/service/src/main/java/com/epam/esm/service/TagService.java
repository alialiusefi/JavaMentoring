package com.epam.esm.service;


import com.epam.esm.dto.TagDTO;


public interface TagService extends BaseService<TagDTO> {

    TagDTO getByName(String name);

    @Override
    default TagDTO update(TagDTO dto) {
        throw new UnsupportedOperationException("Update method not implemented yet!");
    }

}
