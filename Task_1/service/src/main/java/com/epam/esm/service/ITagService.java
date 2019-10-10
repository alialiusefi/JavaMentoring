package com.epam.esm.service;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.CRUDRepo;
import com.epam.esm.repository.TagRepo;
import com.epam.esm.repository.specfication.FindTagByID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ITagService implements IService<TagDTO> {

    private CRUDRepo<Tag> repository;
    private TagConverter tagConverter;

    @Autowired
    public ITagService(TagRepo repository, TagConverter tagConverter) {
        this.repository = repository;
        this.tagConverter = tagConverter;
    }

    @Override
    public TagDTO getByID(long id) {
        try {
            Tag tagFound = repository.findByID(id);
            return tagConverter.toDTO(tagFound);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Tag with ID: " + id + " was not found!");
        }
    }

    @Override
    public TagDTO add(TagDTO tag) {
        return tagConverter.toDTO(repository.add(tagConverter.toEntity(tag)));
    }

    @Override
    public boolean delete(TagDTO tag) {
        repository.delete(tagConverter.toEntity(tag));
        return true;
    }

    public boolean delete(long tagID) {
        FindTagByID findTagByID = new FindTagByID(tagID);
        repository.delete(findTagByID);
        return true;
    }

    @Override
    public TagDTO update(TagDTO dto) {
        throw new UnsupportedOperationException("Update method not implemented yet!");
    }

}
