package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/v1/tags")
@RestController
@Validated
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public List<TagDTO> getTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    public TagDTO getTag(@PathVariable long id) {
        return tagService.getByID(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO saveTag(@RequestBody @Valid TagDTO tag) {
        return tagService.add(tag);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        tagService.delete(id);
    }

}
