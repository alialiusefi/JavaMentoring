package com.epam.esm.service.implementation;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindAllTags;
import com.epam.esm.repository.specification.FindMostUsedTagOfMostExpensiveUserOrders;
import com.epam.esm.repository.specification.FindTagByID;
import com.epam.esm.repository.specification.FindTagByName;
import com.epam.esm.repository.specification.FindUserByUserID;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;
    private TagConverter tagConverter;
    private UserRepository userRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagConverter tagConverter, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.tagConverter = tagConverter;
        this.userRepository = userRepository;
    }

    @Override
    public TagDTO getByID(Long id) {
        Tag tagfound = tagRepository.queryEntity(new FindTagByID(id)).orElseThrow(() ->
                new ResourceNotFoundException("Tag with this id doesn't exist!"));
        return tagConverter.toDTO(tagfound);
    }

    @Override
    public List<TagDTO> getAll(int pageNumber, int size) {
        try {
            List<Tag> tagsFound = tagRepository.queryList(new FindAllTags(), pageNumber, size);
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
            return tagConverter.toDTO(tagRepository.add(tagConverter.toEntity(tag)));
        }
    }

    @Override
    public TagDTO getByName(String name) {
        Tag tagfound = tagRepository.queryEntity(new FindTagByName(name)).
                orElseThrow(() -> new ResourceNotFoundException("Tag with this name already exists!"));
        return tagConverter.toDTO(tagfound);
    }

    @Override
    public List<TagDTO> getMostUsedTagOfMostExpensiveUserOrders(Long userID) {
        UserEntity userEntity = userRepository.queryEntity(new FindUserByUserID(userID))
                .orElseThrow(() -> new ResourceNotFoundException("User with this id doesnt exists!"));
        List<Tag> tags = tagRepository.queryList(
                new FindMostUsedTagOfMostExpensiveUserOrders(userID), 1, 1);
        return tagConverter.toDTOList(tags);
    }

    @Transactional
    @Override
    public boolean delete(TagDTO tag) {
        TagDTO tagFound = getByID(tag.getId());
        if (tagFound != null) {
            tagRepository.delete(tagConverter.toEntity(tagFound));
            return true;
        }
        throw new ResourceNotFoundException("Tag with this id doesn't exist");
    }

    @Transactional
    @Override
    public boolean delete(long tagID) {
        TagDTO tagFound = getByID(tagID);
        if (tagFound != null) {
            tagRepository.delete(tagConverter.toEntity(tagFound));
            return true;
        }
        throw new ResourceNotFoundException("Tag with this id doesn't exist");
    }


}
