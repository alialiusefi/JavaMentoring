package com.epam.esm.service.implementation;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.BaseCRUDRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.specfication.FindAllTags;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private BaseCRUDRepository<Tag> repository;
    private TagConverter tagConverter;

    @Autowired
    public TagServiceImpl(TagRepository repository, TagConverter tagConverter) {
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
    public TagDTO getByName(String name) {
        TagDTO tagFound = tagConverter.toDTO(repository.findByName(name));
        if (tagFound == null) {
            throw new ResourceNotFoundException("Tag with name: " + name + " was not found!");
        }
        return tagFound;
    }

    @Override
    public List<TagDTO> getAllTags() {
        try {
            return tagConverter.toDTOList(
                    repository.query(new FindAllTags()));
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Didn't find tags");
        }
    }


    @Transactional
    @Override
    public TagDTO add(TagDTO tag) {
        try {
            TagDTO dto = getByName(tag.getName());
            throw new BadRequestException("Tag with this name already exists!");
        } catch (EmptyResultDataAccessException e) {
            return tagConverter.toDTO(repository.add(tagConverter.toEntity(tag)));
        }
    }

    @Transactional
    @Override
    public boolean delete(TagDTO tag) {
        TagDTO tagFound = getByID(tag.getId());
        if (tagFound != null) {
            repository.delete(tagConverter.toEntity(tagFound));
            return true;
        }
        throw new ResourceNotFoundException("Tag with this id doesn't exist");
    }

    @Transactional
    public boolean delete(long tagID) {
        TagDTO tagFound = getByID(tagID);
        if (tagFound != null) {
            repository.delete(tagConverter.toEntity(tagFound));
            return true;
        }
        throw new ResourceNotFoundException("Tag with this id doesn't exist");
    }

    @Override
    public TagDTO update(TagDTO dto) {
        throw new UnsupportedOperationException("Update method not implemented yet!");
    }


}
