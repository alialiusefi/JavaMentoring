package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tags")
public class TagController extends ResourceController {

    @Autowired
    private TagService tagService;


    @GetMapping("/")
    public @ResponseBody
    TagDTO getTag() {
        return tagService.getTag(1);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    TagDTO getTag(@PathVariable long id) {
        return tagService.getTag(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTag(@RequestBody @Valid TagDTO tag) {
        tagService.addTag(tag);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
    }

}
