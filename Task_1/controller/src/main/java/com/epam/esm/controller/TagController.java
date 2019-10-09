package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/tags")
public class TagController {

    @Autowired
    private ITagService ITagService;


    @GetMapping("/{id}")
    public TagDTO getTag(@PathVariable long id) {
        return ITagService.getTag(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO saveTag(@RequestBody @Valid TagDTO tag) {
        ITagService.addTag(tag);
        return ITagService.getTag(tag.getId());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable long id) {
        ITagService.deleteTag(id);
    }

}
