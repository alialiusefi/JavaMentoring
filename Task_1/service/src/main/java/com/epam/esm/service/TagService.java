package com.epam.esm.service;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.TagRepo;
import com.epam.esm.repository.specfication.FindTagByID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private TagRepo repository;
    private TagConverter tagConverter;

    @Autowired
    public TagService(TagRepo repository, TagConverter tagConverter) {
        this.repository = repository;
        this.tagConverter = tagConverter;
    }

    public TagDTO getTag(long id) {
        List<Tag> tags = repository.query(
                new FindTagByID(id));
        if (tags.isEmpty()) {
            throw new ResourceNotFoundException("Tag with this id is doesn't exist");
        }
        return tagConverter.toDTO(tags.get(0));
    }

    public TagDTO addTag(TagDTO tag) {
        repository.add(tagConverter.toEntity(tag));
        return getTag(tag.getId());
    }

    public boolean deleteTag(TagDTO tag) {
        repository.delete(tagConverter.toEntity(tag));
        TagDTO dto = getTag(tag.getId());
        return dto == null;
    }

    public boolean deleteTag(long tagID) {
        FindTagByID findTagByID = new FindTagByID(tagID);
        repository.delete(findTagByID);
        TagDTO dto = getTag(tagID);
        return dto == null;
    }

}
