package com.epam.esm.service.implementation;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.specification.FindAllTags;
import com.epam.esm.repository.specification.FindTagByID;
import com.epam.esm.repository.specification.FindTagByName;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository repository;
    private TagConverter tagConverter;

    @Autowired
    public TagServiceImpl(TagRepository repository, TagConverter tagConverter) {
        this.repository = repository;
        this.tagConverter = tagConverter;
    }

    @Override
    public TagDTO getByID(long id) {
        Tag tagfound = repository.queryEntity(new FindTagByID(id)).orElseThrow(() ->
                new ResourceNotFoundException("Tag with this id doesn't exist!"));
        return tagConverter.toDTO(tagfound);
    }

    @Override
    public List<TagDTO> getAll(int pageNumber, int size) {
        try {
            List<Tag> tagsFound = repository.queryList(new FindAllTags(), pageNumber, size);
            return tagConverter.toDTOList(tagsFound);
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
        } catch (ResourceNotFoundException e) {
            return tagConverter.toDTO(repository.add(tagConverter.toEntity(tag)));
        }
    }

    @Override
    public TagDTO getByName(String name) {
        Tag tagfound = repository.queryEntity(new FindTagByName(name)).
                orElseThrow(() -> new ResourceNotFoundException("Tag with this name already exists!"));
        return tagConverter.toDTO(tagfound);
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
    @Override
    public boolean delete(long tagID) {
        TagDTO tagFound = getByID(tagID);
        if (tagFound != null) {
            repository.delete(tagConverter.toEntity(tagFound));
            return true;
        }
        throw new ResourceNotFoundException("Tag with this id doesn't exist");
    }


}
