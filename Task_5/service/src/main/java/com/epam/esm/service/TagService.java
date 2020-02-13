package com.epam.esm.service;


import com.epam.esm.dto.TagDTO;

import java.util.List;
import java.util.Map;


public interface TagService extends BaseService<TagDTO> {

    TagDTO getByName(String name);

    List<TagDTO> getMostUsedTagOfMostExpensiveUserOrders(Long userID);

    @Override
    default TagDTO update(TagDTO dto) {
        throw new UnsupportedOperationException("Update method not implemented yet!");
    }

    @Override
    default TagDTO patch(Map<Object, Object> fields, Long id) {
        throw new UnsupportedOperationException("Patch method not implemented yet!");
    }

    List<TagDTO> getAllByNameConsists(String tagName, Integer pageNumber, Integer pageSize);

}
