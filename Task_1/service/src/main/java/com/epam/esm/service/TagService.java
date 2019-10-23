package com.epam.esm.service;


import com.epam.esm.dto.TagDTO;

import java.util.List;


public interface TagService extends BaseService<TagDTO> {

    TagDTO getByName(String name);

    List<TagDTO> getAllTags();
}
