package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepo;
import com.epam.esm.repository.specfication.FindTagByID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService extends BaseService {

    private TagRepo repository;

    @Autowired
    public TagService(TagRepo repository) {
        this.repository = repository;
    }

    public Tag getTag(long id){
        List<Tag> tags = repository.query(
                new FindTagByID(id)
        );
        return tags.get(0);
    }

    public void addTag(Tag tag){
        repository.add(tag);
    }

    public void deleteTag(Tag tag){
        repository.delete(tag);
    }

}
