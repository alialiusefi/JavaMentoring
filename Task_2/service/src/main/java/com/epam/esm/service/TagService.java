package com.epam.esm.service;


import com.epam.esm.dto.TagDTO;

import java.util.Map;


public interface TagService extends BaseService<TagDTO> {

    TagDTO getByName(String name);

    @Override
    default TagDTO update(TagDTO dto) {
        throw new UnsupportedOperationException("Update method not implemented yet!");
    }

    @Override
    default TagDTO patch(Map<Object, Object> fields, Long id) {
        throw new UnsupportedOperationException("Update method not implemented yet!");
    }

}
