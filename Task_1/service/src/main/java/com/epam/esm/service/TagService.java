package com.epam.esm.service;

import com.java.esm.entity.Tag;
import com.java.esm.repository.TagRepo;
import com.java.esm.repository.specfication.FindTagByID;
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
