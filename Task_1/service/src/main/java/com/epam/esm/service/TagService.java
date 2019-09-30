package com.epam.esm.service;

import com.java.esm.entity.Tag;
import com.java.esm.repository.TagRepo;
import com.java.esm.repository.specfication.FindTagByID;

import java.util.List;

public class TagService extends Service<TagRepo> {

    protected TagService(TagRepo repository) {
        super(repository);
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
